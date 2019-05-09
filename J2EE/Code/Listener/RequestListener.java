import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

public class RequestListener implements ServletRequestListener, ServletContextAttributeListener {

    // request属性监听
    @Override
    public void attributeAdded(ServletContextAttributeEvent scae) {
        System.out.println("新建了request属性：" + scae.getName() + " = " + scae.getValue());
    }

    @Override
    public void attributeRemoved(ServletContextAttributeEvent scae) {
        System.out.println("移除了request属性：" + scae.getName() + " = " + scae.getValue());
    }

    @Override
    public void attributeReplaced(ServletContextAttributeEvent scae) {
        System.out.println("修改了request属性：" + scae.getName() + " = " + scae.getValue());
    }

    // request生命周期监听
    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        System.out.println("request已被销毁:" + sre.getServletRequest().getRemoteAddr());
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        System.out.println("request已被创建：" + sre.getServletRequest().getRemoteAddr());
    }
}
