import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "VisitCount",urlPatterns = {"/"})
public class VisitCount extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie visit = new Cookie("id","0");

        // Cookie有效时间1天
        visit.setMaxAge(60*60*24);

        response.addCookie(visit);

    }
}
