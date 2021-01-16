/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.BookingDTO;
import dto.UserDTO;
import facades.UserFacade;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author chris
 */
public class tester {

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();
        UserFacade facade = UserFacade.getUserFacade(emf);
        ExecutorService ES = Executors.newCachedThreadPool();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        User user = new User("register", "register", "Register manden", "register@reg.com", "45451424");
        CreditCard card = new CreditCard("dankort", "4321", "20/2001");

        user.AddCreditCard(card);

        UserDTO userDto = facade.registerUser(new UserDTO(user));

        System.out.println(userDto.email);

        String id = "4042";

        String hotelDTO = fetcher.HotelFetcher.fetchHotelById(ES, gson, id);
        Hotel hotel = gson.fromJson(hotelDTO, Hotel.class);

        Booking booking = new Booking("22-12-2019", 2, hotel.getPricePrNight());

        BookingDTO bookingDTO1 = new BookingDTO(booking);

        BookingDTO bookingDTO2 = facade.bookHotel(bookingDTO1, user.getUserName(), id);

        System.out.println(bookingDTO2.pricePrNight);

    }
}
