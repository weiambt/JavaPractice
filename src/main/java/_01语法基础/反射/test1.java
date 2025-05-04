package _01语法基础.反射;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class test1 {

    //获取class
    static void test0() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // 方法1 类.class
        Class clazz = User.class;

        // 方法2 Class.forName
        Class clz = Class.forName("_01语法基础.反射.User"); // 会报ClassNotFoundException异常

        //方法3 从对象获取
        Object obj = new User("zhangsan",11);
        Class<?> aClass = obj.getClass();
        System.out.println(aClass); // null
        Method getNameMethod = aClass.getMethod("getName");
        String name = (String) getNameMethod.invoke(obj);
        System.out.println(name); //zhangsan
    }

    //创建对象
    static void test1() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class clz = Class.forName("_01语法基础.反射.User");
        System.out.println(clz);

        //1、通过class创建
        User o1 = (User)clz.newInstance();
        System.out.println(o1);

        // 2.1无参构造方法
        Constructor constructor =  clz.getConstructor();
        System.out.println(constructor);
        User o = (User)constructor.newInstance();
        System.out.println(o);

        //2.2有参构造方法
        Constructor declaredConstructor = clz.getDeclaredConstructor(String.class, int.class);
        System.out.println(declaredConstructor);
        User user = (User)declaredConstructor.newInstance("name", 13);
        System.out.println(user);

    }

    //访问成员方法
    static void test2() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        User user = new User();

        Class clz = Class.forName("_01语法基础.反射.User");
        Method setNameMethod = clz.getMethod("setName", String.class);
        // user.setName("zhangsan")
        setNameMethod.invoke(user,"zhangsan");
        System.out.println(user);
    }

    // 解析json成对象,底层就是基于反射
    // 必须要求maven的scope中包含运行时
    static void test3() {
        String jsonstr = "{\"name\":\"zhangsan\",\"age\":11}";;
        User user = JSON.parseObject(jsonstr, User.class);
        System.out.println(user);
    }

    public static void main(String[] args) throws Exception {
//        test0();
//        test1();

//        test2();

        test3();

    }
}

