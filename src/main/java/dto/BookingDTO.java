/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Booking;

/**
 *
 * @author chris
 */
public class BookingDTO {
    
    public long id;
    public String startDate;
    public String nights;
    public int pricePrNight;

    public BookingDTO(Booking booking) {
        this.id = booking.getId();
        this.startDate = booking.getStartDate();
        this.nights = booking.getNights();
        this.pricePrNight = booking.getPricePrNight();
    }
    
    
}
