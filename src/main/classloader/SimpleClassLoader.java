package classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 自定义的类加载器
 * @author heylinlook
 * @date 2018/4/23 14:34
 * @param
 * @return
 */
public class SimpleClassLoader extends ClassLoader {

    File dir;

    //把文件路径用构造函数传进来
    public SimpleClassLoader(String path) {
        dir = new File(path);
    }

    /*
     * 本方法就是去加载对应的字节码文件
     * */
    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        //如果文件路径可用
        if (dir != null) {
            File clazzFile = new File(dir, name + ".class");
            //如果字节码文件存在
            if (clazzFile.exists()) {
                //把字节码文件加载到VM
                try {
                    //文件流对接class文件
                    FileInputStream inputStream = new FileInputStream(clazzFile);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int len;
                    //将class文件读取到buffer中
                    while ((len = inputStream.read(buffer)) != -1) {
                        //将buffer中的内容读取到baos中的buffer
                        baos.write(buffer, 0, len);
                    }
                    //FIXME 将buffer中的字节读到内存加载为class  这个前缀要动态获取
                    return defineClass("proxy.temp." + name, baos.toByteArray(), 0, baos.size());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return super.findClass(name);
    }
}
