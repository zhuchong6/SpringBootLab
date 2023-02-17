package com.zhu.dispacher;

import com.zhu.codec.InvocationDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author by zhuhcong
 * @descr MessageHandler容器
 * @date 2023/2/17 01:13
 */
public class MessageHandlerContainer implements InitializingBean {

    private Logger logger = LoggerFactory.getLogger(MessageHandlerContainer.class);

    private final Map<String, MessageHandler> handlerMap = new HashMap<>();

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() throws Exception {
        //application获取所有的MessageHandler Bean
        applicationContext.getBeansOfType(MessageHandler.class).values()
                .forEach(messageHandler -> handlerMap.put(messageHandler.getType(), messageHandler));
        logger.info("[afterPropertiesSet][消息处理器数量：{}]", handlerMap.size());
    }

    /**
     * 获取类型对应的MessageHandler对象
     *
     * @param type 类型
     * @return MessageHandler
     */
    public MessageHandler getMessageHandler(String type) {
        MessageHandler messageHandler = handlerMap.get(type);
        if (messageHandler == null) {
            throw new IllegalArgumentException(String.format("类型(%s) 找不到匹配的 MessageHandler 处理器", type));
        }
        return messageHandler;
    }


    /**
     * 获取MessageHandler处理的消息类型
     *
     * @param messageHandler 处理器
     * @return 消息类
     */
    public static Class<? extends Message> getMessageClazz(MessageHandler messageHandler) {
        //获取Bean对应的Class类名，因为有可能被 AOP 代理过。
        Class<?> targetClass = AopProxyUtils.ultimateTargetClass(messageHandler);

        //获得接口的 Type 数组
        Type[] interfaces = targetClass.getGenericInterfaces();
        Class<?> superclass = targetClass.getSuperclass();

        // 此处，是以父类的接口为准
        while ((Objects.isNull(interfaces) || 0 == interfaces.length) && Objects.nonNull(superclass)) {
            interfaces = superclass.getGenericInterfaces();
            superclass = targetClass.getSuperclass();
        }
        if (Objects.nonNull(interfaces)) {
            // 遍历 interfaces 数组
            for (Type type : interfaces) {
                // 要求 type 是泛型参数
                if (type instanceof ParameterizedType) {
                    ParameterizedType parameterizedType = (ParameterizedType) type;
                    // 要求是 MessageHandler 接口
                    if (Objects.equals(parameterizedType.getRawType(), MessageHandler.class)) {
                        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                        // 取首个元素
                        if (Objects.nonNull(actualTypeArguments) && actualTypeArguments.length > 0) {
                            return (Class<Message>) actualTypeArguments[0];
                        } else {
                            throw new IllegalStateException(String.format("类型(%s) 获得不到消息类型", messageHandler));
                        }
                    }
                }
            }
        }
        throw new IllegalStateException(String.format("类型(%s) 获得不到消息类型", messageHandler));
    }
}