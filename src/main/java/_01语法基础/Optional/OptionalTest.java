package _01语法基础.Optional;

import java.util.Optional;

public class OptionalTest {
    static String test1(User user) {
        if (user!=null){
            Department department = user.getDepartment();
            if(department!=null){
                String departmentName = department.getName();
                System.out.println(departmentName);
                return departmentName;
            }
        }
        return null;
    }
    static String test2(User user) {
        Optional<User> optional = Optional.ofNullable(user);
        return optional.map(User::getDepartment)
                .map(Department::getName)
                .orElse(null);
    }

    public static void main(String[] args) {
        User user = new User();
        test1(user);
        test2(user);
    }
}

class User{
    private String name;
    private Department department;

    public Department getDepartment() {
        return department;
    }
}
class Department{
    private String name;

    public String getName() {
        return name;
    }
}
