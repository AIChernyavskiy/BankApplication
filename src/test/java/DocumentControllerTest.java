import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.ModelAndView;
import ru.sbertech.Controller.DocumentController;
import ru.sbertech.DAO.AccountDAOImpl;
import ru.sbertech.DAO.ClientDaoImpl;
import ru.sbertech.DAO.DocumentDaoImpl;

@ContextConfiguration(locations = "classpath:application-context-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class DocumentControllerTest {

    public ApplicationContext testApplicationContext = new ClassPathXmlApplicationContext("application-context-test.xml");

    @Autowired
    DocumentController documentController;

    @Autowired
    ClientDaoImpl clientDao;

    @Autowired
    AccountDAOImpl accountDAO;

    @Autowired
    DocumentDaoImpl documentDao;

    @Test
    public void testFrontPageClient() {
        ModelAndView modelAndView = documentController.frontPageDocument();
        Assert.assertEquals("Document",modelAndView.getViewName());
    }
}
