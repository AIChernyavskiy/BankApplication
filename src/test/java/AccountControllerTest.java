import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.ModelAndView;
import ru.sbertech.Controller.AccountController;
import ru.sbertech.DAO.AccountDAOImpl;
import ru.sbertech.DAO.ClientDaoImpl;

@ContextConfiguration(locations = "classpath:application-context-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class AccountControllerTest {

    public ApplicationContext testApplicationContext = new ClassPathXmlApplicationContext("application-context-test.xml");

    @Autowired
    AccountController accountController;

    @Autowired
    ClientDaoImpl clientDao;

    @Autowired
    AccountDAOImpl accountDAO;

    @Test
    public void testFrontPageClient() {
        ModelAndView modelAndView = accountController.frontPageAccount();
        Assert.assertEquals("Account",modelAndView.getViewName());
    }
}
