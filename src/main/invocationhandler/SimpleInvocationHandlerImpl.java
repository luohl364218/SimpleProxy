package invocationhandler;


import instance.UserService;

import java.lang.reflect.Method;

/**
 * 自定义调用处理类，在里面进行切面处理
 * @author heylinlook
 * @date 2018/4/23 15:06
 * @param
 * @return
 */
public class SimpleInvocationHandlerImpl implements SimpleInvocationHandler {

    private UserService userService;

    public SimpleInvocationHandlerImpl(UserService userService) {
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
