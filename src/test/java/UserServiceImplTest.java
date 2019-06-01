import com.banking.config.AppConfig;
import com.banking.exception.WrongTokenException;
import com.banking.exception.WrongUserNamePasswordException;
import com.banking.model.User;
import com.banking.service.impl.UserServiceImpl;
import com.banking.util.TokenGeneratorUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.exceptions.misusing.InvalidUseOfMatchersException;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@Transactional
public class UserServiceImplTest {

    @Mock
    private UserServiceImpl userServiceImpl;

    @Test(expected= InvalidUseOfMatchersException.class)
    public void createUserTest(){
        //create mock
        LocalDateTime time = LocalDateTime.now();
        User user = new User("alex", "alex", time, time);
        userServiceImpl = Mockito.mock(UserServiceImpl.class);

        //define return object for method createUser
        Mockito.when(userServiceImpl.createUser(new User(anyString(), anyString(), any(), any()))).thenReturn(user);

        //use mock in test
        Assert.assertEquals(userServiceImpl.createUser(new User(anyString(), anyString(), any(), any())), user);
        Assert.assertEquals(userServiceImpl.createUser(new User("A", "B", LocalDateTime.now(),
                LocalDateTime.now())), user);
    }

    @Test
    public void loginUserTest() throws WrongUserNamePasswordException {
        String generateToken = TokenGeneratorUtils.generateToken();
        userServiceImpl = Mockito.mock(UserServiceImpl.class);

        Mockito.when(userServiceImpl.loginUser(anyString(), anyString())).thenReturn(generateToken);

        Assert.assertEquals(userServiceImpl.loginUser(anyString(), anyString()), generateToken);
        Assert.assertEquals(userServiceImpl.loginUser("alex", "alex"), generateToken);
    }

    @Test(expected= IllegalArgumentException.class)
    public void logoutUserTest() throws WrongTokenException {
        userServiceImpl = Mockito.mock(UserServiceImpl.class);

        Mockito.doThrow(new IllegalArgumentException()).when(userServiceImpl).logoutUser(anyString());
        userServiceImpl.logoutUser("alexandru");
        Mockito.doThrow(new IllegalArgumentException()).when(userServiceImpl).logoutUser("alexandru");
        try {
            userServiceImpl.logoutUser("alexandru");
            Assert.fail();
        } catch (IllegalArgumentException e) {
            e.getMessage();
        }

    }

}
