package invocationhandler;

import java.lang.reflect.Method;

public interface SimpleInvocationHandler {

    Object invoke(Object proxy, Method method, Object[] args) throws Throwable;

}
