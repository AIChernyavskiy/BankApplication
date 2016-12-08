import org.hibernate.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.ModelAndView;
import ru.sbertech.Controller.IndexController;

import static org.mockito.Matchers.any;


@ContextConfiguration(locations = "classpath:application-context-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class IndexControllerTest {

    public ApplicationContext testApplicationContext = new ClassPathXmlApplicationContext("application-context-test.xml");

    @Autowired
    IndexController indexController;

    @Before
    public void setUp(){
        SessionFactory mockedSessionFactory =  Mockito.mock(SessionFactory.class);
        Session mockedSession = Mockito.mock(Session.class);
        Transaction mockedTransaction = Mockito.mock(Transaction.class);
        SQLQuery mockedQuery = Mockito.mock(SQLQuery.class);
        Mockito.when(mockedSession.createSQLQuery(any(String.class))).thenReturn(mockedQuery);
        Mockito.when(mockedSessionFactory.openSession()).thenReturn(mockedSession);
        Mockito.when(mockedSession.beginTransaction()).thenReturn(mockedTransaction);
        Mockito.when(mockedSessionFactory.getCurrentSession()).thenReturn(mockedSession);
        Mockito.when(mockedSession.getTransaction()).thenReturn(mockedTransaction);
        indexController.setSessionFactory(mockedSessionFactory);
    }

    @Test
    public void testIndex(){
        ModelAndView modelAndView = indexController.index();
        try {
            indexController.getBroker().stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertEquals("index",modelAndView.getViewName());
    }

    @Test
    public void testIndexDropAndCreateDB() {
        ModelAndView modelAndView = indexController.indexDropAndCreateDB();
        Assert.assertEquals("index",modelAndView.getViewName());
        Assert.assertNotNull(modelAndView.getModel().get("messageDB"));
    }
}
