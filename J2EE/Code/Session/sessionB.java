import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class sessionB extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("ID");
        String passwd = req.getParameter("passwd");
        
        HttpSession session = req.getSession(false);

        String name = null;
        String password = null;
        if (session != null){
            name = (String) session.getAttribute("userID");
            password = (String) session.getAttribute("password");
        }

        resp.getWriter().println("sessionB ID:"+id);
        resp.getWriter().println("sessionB passwd:"+passwd);
        resp.getWriter().println("sessionB name:"+name);
        resp.getWriter().println("sessionB password:"+password);
    }
}
