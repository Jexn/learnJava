import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

// Context生命周期监听
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Web应用初始化");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Web已销毁");
    }
}
