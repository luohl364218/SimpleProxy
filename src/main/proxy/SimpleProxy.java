package proxy;

import classloader.SimpleClassLoader;
import invocationhandler.SimpleInvocationHandler;
import invocationhandler.SimpleInvocationHandlerImpl;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 自定义动态代理类 模拟代理类的全过程
 * @author heylinlook
 * @date 2018/4/23 15:05
 * @param
 * @return
 */
public class SimpleProxy {

    static String rt = "\r\n";

    public static Object newProxyInstance(ClassLoader loader,
                                          Class<?> interfaces,
                                          SimpleInvocationHandler h) {
        //FIXME 该路径需要自己定义,我们定义为proxy/temp的绝对路径，这样代理类就会在该文件夹下生成
        String path = "C:\\HailinLuo\\CODING\\JAVA\\SimpleProxy\\src\\main\\proxy\\temp\\$Proxy0.java";

        //1、用已知的接口，遍历里面的方法，以字符串的形式拼凑出内存里的代理类（动态代理类与被代理类实现同一接口在此体现）
        String proxyClass = get$Proxy0(interfaces);
        //2、将代理类的写到本地java文件
        outputFile(proxyClass, path);
        //3、编译java文件
        compileJavaFile(path);
        //4、将对应编译出来的字节码加载到JVM内存
        return loadClassToJvm(h);
    }
    //（4）将字节码加载到内存
    private static Object loadClassToJvm(SimpleInvocationHandler h) {
        try {
            //使用自定义类加载器,传入的路径是我们代理类生成的路径（这里采用绝对路径演示，其实应该要设为相对路径）
            SimpleClassLoader loader = new SimpleClassLoader("C:\\HailinLuo\\CODING\\JAVA\\SimpleProxy\\src\\main\\proxy\\temp");
            //得到动态代理类的反射对象
            Class<?> $Proxy0 = loader.findClass("$Proxy0");
            //通过反射机制获取动态代理类$Proxy0的构造函数，其参数类型是调用处理器接口类型
            Constructor<?> constructors = $Proxy0.getConstructor(SimpleInvocationHandler.class);
            //通过构造函数创建动态代理类实例
            return constructors.newInstance(h);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
    //（3）将源码进行编译成字节码
    private static void compileJavaFile(String fileName) {
        try {
            //获得当前系统中的编译器
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            //获得文件管理者
            StandardJavaFileManager manager = compiler.getStandardFileManager(null, null, null);
            Iterable<? extends JavaFileObject> fileObjects = manager.getJavaFileObjects(fileName);
            //编译任务
            JavaCompiler.CompilationTask task = compiler.getTask(null, manager, null, null, null, fileObjects);
            //开始编译，执行完可在当前目录下看到.class文件
            task.call();
            //关闭文件管理者
            manager.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //（2）将源码写入本地文件
    private static void outputFile(String proxyClass, String path) {

        File f = new File(path);
        try {
            FileWriter fw = new FileWriter(f);
            fw.write(proxyClass);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //（1）使用字符串拼凑动态代理对象的java源码
    private static String get$Proxy0(Class<?> interfaces) {

        Method[] methods = interfaces.getMethods();
        //FIXME 需要修改第一行+第二行 路径要符合
        String proxyClass = "package proxy.temp;" + rt
                + "import invocationhandler.SimpleInvocationHandler;" + rt
                + "import java.lang.reflect.Method;" + rt
                + "public class $Proxy0 implements " + interfaces.getName() + "{"
                + rt + "SimpleInvocationHandler h;" + rt
                + "public $Proxy0(SimpleInvocationHandler h) {" + rt
                + "this.h = h;" + rt + "}" + getMethodString(methods, interfaces)
                + rt + "}";
        return proxyClass;
    }

    private static String getMethodString(Method[] methods, Class<?> interfaces) {
        String proxyMethod = "";

        for (Method method : methods) {
            proxyMethod += "public String " + method.getName()
                    + "() throws Throwable {" + rt + "Method md = "
                    + interfaces.getName() + ".class.getMethod(\"" + method.getName()
                    + "\",new Class[]{});" + rt
                    + "return (String)this.h.invoke(this, md, null);" + rt + "}" + rt;
        }

        return proxyMethod;
    }
}
