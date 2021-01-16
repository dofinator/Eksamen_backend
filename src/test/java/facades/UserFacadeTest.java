package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.BookingDTO;
import dto.UserDTO;
import entities.Booking;
import entities.CreditCard;
import entities.Hotel;
import utils.EMF_Creator;
import entities.Role;
import entities.User;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import security.errorhandling.AuthenticationException;

//Uncomment the line below, to temporarily disable this test
//Disabled
public class UserFacadeTest {

    private static EntityManagerFactory emf;
    private static UserFacade facade;
    private static final ExecutorService threadPool = Executors.newCachedThreadPool();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private static User u1;
    private static User u2;
    private static User u3;

    private static CreditCard c1;
    private static CreditCard c2;

    private static Role user;

    public UserFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = UserFacade.getUserFacade(emf);
        EntityManager em = emf.createEntityManager();

        u1 = new User("test", "test", "Christoffer wenger", "test@test.com", "28939704");
        c1 = new CreditCard("dankort", "1234", "20/2001");

        u1.AddCreditCard(c1);

        em.getTransaction().begin();
        em.persist(u1);
        em.getTransaction().commit();
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the code below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {

    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    // TODO: Delete or change this method 
    @Test
    public void testVerifyUser() throws AuthenticationException {
        User user = facade.getVeryfiedUser("test", "test");
        assertEquals("test", u1.getUserName());
    }

    @Test
    public void testRegisterUser() {
        EntityManager em = emf.createEntityManager();

        User user = new User("register", "register", "Register manden", "register@reg.com", "45451424");
        CreditCard card = new CreditCard("dankort", "4321", "20/2001");

        user.AddCreditCard(card);

        UserDTO userDto = facade.registerUser(new UserDTO(user));

        assertEquals("Register manden", userDto.fullName);
    }

    @Test
    public void testBookHotel() throws InterruptedException, ExecutionException, TimeoutException, TimeoutException {
        EntityManager em = emf.createEntityManager();

        String id = "4042";

        String hotelDTO = fetcher.HotelFetcher.fetchHotelById(threadPool, gson, id);
        Hotel hotel = gson.fromJson(hotelDTO, Hotel.class);

        Booking booking = new Booking("22-12-2019", 2, hotel.getPricePrNight());

        BookingDTO bookingDTO1 = new BookingDTO(booking);
        
        BookingDTO bookingDTO2 = facade.bookHotel(bookingDTO1, u1.getUserName(), id);

        assertEquals(bookingDTO2.startDate, "22-12-2019");
    }
}
