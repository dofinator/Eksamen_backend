/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Booking;
import entities.Hotel;
import java.util.List;

/**
 *
 * @author chris
 */
public class HotelDTO {
 
    public long id;
    public String name;
    public String address;
    public String phone;
    public int pricePrNight;
    public List<BookingDTO> bookings;

    public HotelDTO(Hotel hotel) {
        this.id = hotel.getId();
        this.name = hotel.getName();
        this.address = hotel.getAddress();
        this.phone = hotel.getPhone();
        this.pricePrNight = hotel.getPricePrNight();
        for (Booking booking : hotel.getBookings()) {
            bookings.add(new BookingDTO(booking));
        }
    }
    
    
}
