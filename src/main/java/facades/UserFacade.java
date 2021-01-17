package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.BookingDTO;
import dto.CreditCardDTO;
import dto.FetchHotelDTO;
import dto.HotelDTO;
import dto.UserDTO;
import entities.Booking;
import entities.CreditCard;
import entities.Hotel;
import entities.Role;
import entities.User;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import security.errorhandling.AuthenticationException;

/**
 * @author lam@cphbusiness.dk
 */
public class UserFacade {

    private static EntityManagerFactory emf;
    private static UserFacade instance;
    private static final ExecutorService threadPool = Executors.newCachedThreadPool();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private UserFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static UserFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }

    public User getVeryfiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = em.find(User.class, username);
            if (user == null || !user.verifyPassword(password)) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }
        return user;
    }

    public UserDTO registerUser(UserDTO userDTO) {

        EntityManager em = emf.createEntityManager();

        User user = new User(userDTO.userName, userDTO.userPass, userDTO.fullName, userDTO.email, userDTO.phone);

        for (CreditCardDTO creditCard : userDTO.creditCards) {
            user.AddCreditCard(new CreditCard(creditCard.cardType, creditCard.cardNumber, creditCard.expDate));
        }

        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new UserDTO(user);

    }

    public BookingDTO bookHotel(BookingDTO bookingDTO, String hotelId) throws InterruptedException, ExecutionException, TimeoutException {
        EntityManager em = emf.createEntityManager();

        Hotel hotel;

        String hotelDTO = fetcher.HotelFetcher.fetchHotelById(threadPool, gson, hotelId);
        hotel = gson.fromJson(hotelDTO, Hotel.class);

        
        Booking booking = new Booking(bookingDTO.startDate, bookingDTO.nights, hotel.getPricePrNight());

        System.out.println("price" + hotel.getPricePrNight());
        booking.setHotel(hotel);

        User user = em.find(User.class, bookingDTO.userName);

        user.addBookings(booking);
        
        try {
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        return new BookingDTO(booking);
    }

}
