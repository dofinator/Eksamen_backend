
package dto;

import entities.Booking;
import entities.CreditCard;
import entities.User;
import java.util.List;


public class UserDTO {
    
    public String userName;
    public String userPass;
    public String fullName;
    public String email;
    public String phone;
    public List<CreditCardDTO> creditCards;
    public List<BookingDTO> bookings;

    public UserDTO(User user) {
        this.userName = user.getUserName();
        this.userPass = user.getUserPass();
        this.fullName = user.getFullName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        for (CreditCard creditCard : user.getCreditCards()) {
            creditCards.add(new CreditCardDTO(creditCard));
        }
        for (Booking booking : user.getBookings()) {
            bookings.add(new BookingDTO(booking));
        }
    }
    
    
    
}
