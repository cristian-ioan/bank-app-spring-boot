import com.banking.config.AppConfig;
import com.banking.model.Authentication;
import com.banking.model.User;
import com.banking.repository.AuthenticationRepository;
import com.banking.repository.UserRepository;
import com.banking.service.impl.AuthenticationServiceImpl;
import com.banking.service.impl.UserServiceImpl;
import com.banking.util.TokenGeneratorUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@Transactional
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @InjectMocks
    private AuthenticationServiceImpl authenticationServiceImpl;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationRepository authenticationRepository;

    @Test
    public void findAllUsersTest(){
        List<User> userList = new ArrayList<>();
        User userOne = new User("alex", "alex", LocalDateTime.now(), LocalDateTime.now());
        User userTwo = new User("bogdan", "bogdan", LocalDateTime.now(), LocalDateTime.now());
        User userThree = new User("andrei", "andrei", LocalDateTime.now(), LocalDateTime.now());

        userList.add(userOne);
        userList.add(userTwo);
        userList.add(userThree);

        Mockito.when(userRepository.findAll()).thenReturn(userList);

        List<User> users = userServiceImpl.findAll();

        Assert.assertEquals(3, users.size());
        Mockito.verify(userRepository, times(1)).findAll();

    }

    @Test
    public void getUserById(){
        Mockito.when(userRepository.findById(1L)).thenReturn(new User("Gupta","Lokesh",
                LocalDateTime.now(),LocalDateTime.now()));

        User user = userServiceImpl.findById(1L);

        Assert.assertEquals("Gupta", user.getUserName());
        Assert.assertEquals("Lokesh", user.getPassword());
        Mockito.verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void createUserTest(){
        User user = new User("alex", "alex", LocalDateTime.now(), LocalDateTime.now());

        userServiceImpl.createUser(user);

        Mockito.verify(userRepository, times(1)).save(user);
    }

    @Test
    public void loginUserTest() {
        User user = userRepository.findUserByUserNameAndPassword("alex", "alex");
        String generateToken = TokenGeneratorUtils.generateToken();
        Authentication authentication = new Authentication(generateToken, user, LocalDateTime.now());

        authenticationServiceImpl.createAuthentication(authentication);

        Mockito.verify(authenticationRepository, times(1)).save(authentication);
    }

    @Test
    public void logoutUserTest() {
        String generateToken = TokenGeneratorUtils.generateToken();
        Authentication authentication = authenticationRepository.findAuthenticationByToken(generateToken);

        authenticationServiceImpl.deleteAuthentication(authentication);

        Mockito.verify(authenticationRepository, times(1)).delete(authentication);

    }

}
