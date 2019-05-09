import com.sun.xml.internal.bind.v2.model.core.ID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Count",urlPatterns = {"/count"})
public class Count extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 检查是否为第一次请求
        Cookie[] cookies = request.getCookies();
        Cookie cookie = null;
        int count = 0;

        // 第一次访问
        if (cookies.length == 0){
            // 创建cookie
            cookie = new Cookie("id","1");
            // 设置有效期为1天
            cookie.setMaxAge(60*60*24);
            count = 1;
        }else {
            // 不是第一次访问
            for (Cookie element: cookies){
                if (element.getName().equals("id")){
                    count = Integer.parseInt(element.getValue())+1;
                    element.setValue(""+count);
                    response.addCookie(element);
                }
            }
        }

        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.println("这是你今天第"+count+"次访问本站");
    }
}
