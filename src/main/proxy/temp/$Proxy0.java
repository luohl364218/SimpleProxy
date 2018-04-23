package proxy.temp;
import invocationhandler.SimpleInvocationHandler;
import java.lang.reflect.Method;
public class $Proxy0 implements instance.UserService{
SimpleInvocationHandler h;
public $Proxy0(SimpleInvocationHandler h) {
this.h = h;
}public String execute() throws Throwable {
Method md = instance.UserService.class.getMethod("execute",new Class[]{});
return (String)this.h.invoke(this, md, null);
}

}