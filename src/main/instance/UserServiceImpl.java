package instance;

public class UserServiceImpl implements UserService{
    @Override
    public String execute() throws Throwable {
        System.out.println("step 2 执行方法啦！！");
        return "step 2 执行方法啦！！";
    }
}
