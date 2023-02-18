这是什么？

一个基于Netty简易IM系统，只是实现了关键的通信功能，
后续可以根据这个来进行一个改造成弹幕系统，聊天室等

怎么用？
分别启动项目netty-server一个程序和netty-client两个程序（后续测试群发功能），然后参考执行如下命令：

```shell
#第一个登录的用户
curl --location --request POST 'localhost:8090/test/mock' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'type=AUTH_REQUEST' \
--data-urlencode 'message={"accessToken":"zhu"}'

#第二个登录的用户，注意端口好和第一个不一样，需要启动俩程序
curl --location --request POST 'localhost:8091/test/mock' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'type=AUTH_REQUEST' \
--data-urlencode 'message={"accessToken":"zc"}'

#单人消息接口1
curl --location --request POST 'localhost:8090/test/mock' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'type=CHAT_SEND_TO_ONE_REQUEST' \
--data-urlencode 'message={"fromUser":"zhu","toUser":"zc", "msgId":"1","content":"zhu发给zc"}'

#单人消息接口2
curl --location --request POST 'localhost:8091/test/mock' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'type=CHAT_SEND_TO_ONE_REQUEST' \
--data-urlencode 'message={"fromUser":"zc","toUser":"zhu", "msgId":"1","content":"zc发给zhu"}'

#群发接口
curl --location --request POST 'localhost:8091/test/mock' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'type=CHAT_SEND_TO_ALL_REQUEST' \
--data-urlencode 'message={ "groupId":"1","msgId":"1","content":"群发消息"}'

```

1. 启动好项目
2. 首先需要执行俩登录接口
3. 然后测试单人或者群发消息接口

