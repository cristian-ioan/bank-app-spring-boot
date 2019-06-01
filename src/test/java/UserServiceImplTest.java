import com.banking.config.AppConfig;
import com.banking.model.User;
import com.banking.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@Transactional
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    @Rollback(false)
    @Transactional
    public void createUserTest(){
//        LocalDateTime time = LocalDateTime.now();
//        User user = new User("alex", "alex123", time, time);
//        userService.createUser(user);
//        List<User> userList =userService.findAll();
//        Assert.assertEquals(5, userList.size());
    }

    @Test
    public void getUserById(){
//        List<User> userList = userService.findAll();
//        Assert.assertEquals("bogdan", userList.get(0).getUserName());
//        Assert.assertEquals("Ioan1", userList.get(2).getUserName());
    }

}
