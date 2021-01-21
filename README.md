# 基于HTTP协议编写的小型WebServer服务器 #

运行流程：
	
1. 将HTTP协议头部信息和内容封装成Servlet,发送给服务端
2. 服务端进行解析servlet,进行处理，将响应信息封装成Response对象，返回给客户端
3. 客户端进行解析
