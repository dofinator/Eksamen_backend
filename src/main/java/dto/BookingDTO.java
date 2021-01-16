/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Booking;
import entities.Hotel;

/**
 *
 * @author chris
 */
public class BookingDTO {
    public String startDate;
    public int nights;
    public String pricePrNight;

    public BookingDTO(Booking booking) {
        
        this.startDate = booking.getStartDate();
        this.nights = booking.getNights();
        this.pricePrNight = booking.getPricePrNight();
    }
    
    
}
