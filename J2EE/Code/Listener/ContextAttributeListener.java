import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;

// Context域属性监听
public class ContextAttributeListener implements ServletContextAttributeListener {
    @Override
    public void attributeAdded(ServletContextAttributeEvent scae) {
        System.out.println("增加了属性："+scae.getName()+" = "+scae.getValue());
    }

    @Override
    public void attributeRemoved(ServletContextAttributeEvent scae) {
        System.out.println("移除了属性："+scae.getName()+" = "+scae.getValue());
    }

    @Override
    public void attributeReplaced(ServletContextAttributeEvent scae) {
        System.out.println("修改了属性："+scae.getName()+" = "+scae.getValue());
    }
}
