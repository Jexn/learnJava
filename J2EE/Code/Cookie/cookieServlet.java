import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "cookieServlet",urlPatterns = {"/cookie"})
public class cookieServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 创建Cookie
        Cookie cookie1 = new Cookie("company","google");
        Cookie cookie2 = new Cookie("user","Mick");

        // 设置Cookie有效期为1天
        /*
         *注意setMaxAge的值，单位为秒
         * 当值大于0时，Cookie存放到客户端硬盘
         * 当值等于0时，Cookie一生成就失效
         * 当值小于0时，与不设置效果相同，Cookie存放在缓存，浏览器关闭就失效
         */
        cookie1.setMaxAge(60*60*24);

        // 指定Cookie绑定路径,注意必须加上当前项目名称
        // 如果不绑定路径，默认本访问路径下所有自路径都会加上本Cookie
        cookie1.setPath(req.getContextPath()+"/some");

        // 响应加上路径
        resp.addCookie(cookie1);
        resp.addCookie(cookie2);
    }
}
