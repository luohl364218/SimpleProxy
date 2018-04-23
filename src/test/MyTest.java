import instance.UserService;
import instance.UserServiceImpl;
import invocationhandler.SimpleInvocationHandlerImpl;
import proxy.SimpleProxy;
import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Proxy;

public class MyTest {

    public static void main(String[] args) throws Throwable {
        System.out.println("---------------JDK动态代理----------------");
        UserService userService = (UserService) Proxy.newProxyInstance(MyTest.class.getClassLoader(),
                new Class<?>[]{UserService.class},
                new InvocationHandlerImpl(new UserServiceImpl()));

        userService.execute();

        createProxyFile();

        System.out.println("---------------自定义动态代理----------------");
        UserService service = (UserService) SimpleProxy.newProxyInstance(MyTest.class.getClassLoader(),
                UserService.class,
                new SimpleInvocationHandlerImpl(new UserServiceImpl()));

        service.execute();

    }
    /**
     * 输出我们内存中的动态代理类的字节码文件
     * @author heylinlook
     * @date 2018/4/23 15:07
     * @param
     * @return
     */
    public static void createProxyFile() throws IOException {
        byte[] generateProxyClass = ProxyGenerator.generateProxyClass("$Proxy0", new Class<?>[]{UserService.class});

        FileOutputStream outputStream = new FileOutputStream("$Proxy0.class");
        outputStream.write(generateProxyClass);
        outputStream.close();
    }
}
