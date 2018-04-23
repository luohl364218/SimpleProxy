import instance.UserService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 官方的代理中必须的调用处理实现
 * @author heylinlook
 * @date 2018/4/23 15:08
 * @param
 * @return
 */
public class InvocationHandlerImpl implements InvocationHandler {

    private UserService userService;

    public InvocationHandlerImpl(UserService userService) {
        this.userService = userService;
    }

    /*
    * @proxy 内存中的代理实例 $proxy0
    * @method 内存代理实例中class.forName("被代理类").getMethod("目标方法") 即被代理的类的方法对象
    * @args 方法参数
    * */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();

        method.invoke(userService, args);

        after();

        return "成功了";
    }

    private void before() {
        System.out.println("事务开启！");
    }

    private void after() {
        System.out.println("事务关闭");
    }
}
