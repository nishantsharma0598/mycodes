import java.util.*;

public class UserController {
    
    public List<User> users;

    UserController(){
        users = new ArrayList<>(); 
    }

    void addUser(int id, String name, String emailId){

        User user = new User(id, name, emailId);

        users.add(user);

        

    }
}
