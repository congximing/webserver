package com.cxm.server;

import java.io.IOException;

/**
 * 服务器小脚本
 */
public class LoginServlet implements Servlet {
    @Override
    public void service(Request request, Response response) {
        StringBuilder content = new StringBuilder();
        content.append("<html>");
        content.append("<head>");
        content.append("<meta charset=\"UTF-8\">");
        content.append("<title>");
        content.append("服务器响应成功");
        content.append("</title>");
        content.append("</head>");
        content.append("<body>");
        content.append("终于回来了。。。。。"+request.getParaValue("name"));
        content.append("</body>");
        content.append("</html>");
        response.print(content.toString());
        try {
            response.pushTOBrowser(200);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
