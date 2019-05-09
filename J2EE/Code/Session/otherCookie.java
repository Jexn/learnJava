import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

public class otherCookie extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession  session = req.getSession(false);
        PrintWriter out = resp.getWriter();

        // 遍历session数据
        Enumeration<String> names = session.getAttributeNames();
        while (names.hasMoreElements()){
            String name = names.nextElement();
            System.out.println("otherCookie:"+session.getAttribute(name));
            out.println(name+":"+session.getAttribute(name));
        }
        System.out.println("otherCookie session:"+session);
    }
}
