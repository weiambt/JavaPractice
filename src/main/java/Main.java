import _01语法基础.反射.User;
import com.alibaba.fastjson.JSON;

/*
    测试maven打jar包
 */
public class Main {
    public static void main(String[] args) {
        String jsonstr = "{\"name\":\"zhangsan\",\"age\":11}";;
        User user = JSON.parseObject(jsonstr, User.class);
        System.out.println(user);
    }
}
