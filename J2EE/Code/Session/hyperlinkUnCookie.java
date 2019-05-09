import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class hyperlinkUnCookie extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");

        HttpSession session = req.getSession();
        session.setAttribute("Phone","123456789");

        PrintWriter out = resp.getWriter();
        String uri = "otherCookie";
        // 解决Cookie禁用后，非重定向的Session跟踪问题
        uri = resp.encodeURL(uri);

        out.println("<a href='"+uri+"'>跳转到</a>到otherCookie");
    }
}
