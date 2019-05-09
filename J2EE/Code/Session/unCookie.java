import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URI;

public class unCookie extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        session.setAttribute("address","China");
        System.out.println("unCookie:"+session);

        // 不使用resp.encodeRedirectURL(uri);进行重定向
        // resp.sendRedirect(req.getContextPath()+"/otherCookie");

        // 禁用Cookie实现session传递
        String uri = req.getContextPath()+"/otherCookie";
        uri = resp.encodeRedirectURL(uri);
        resp.sendRedirect(uri);
    }
}
