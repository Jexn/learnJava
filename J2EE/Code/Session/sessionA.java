import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class sessionA extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("userID");
        String password = req.getParameter("password");

        req.setAttribute("ID",name);
        req.setAttribute("passwd",password);

        // 获取session对象
        HttpSession session = req.getSession();
        System.out.println("userID:"+name);
        System.out.println("password:"+password);

        // 向Session中写入属性
        session.setAttribute("userID",name);
        session.setAttribute("password",password);

    }
}
