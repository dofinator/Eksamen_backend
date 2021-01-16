/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author chris
 */
@Entity
public class Hotel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String phone;
    private int pricePrNight;
    @OneToMany(mappedBy = "hotel")
    private List<Booking> bookings;

    public Hotel() {
    }

    public Hotel(Long id, String name, String address, String phone, int pricePrNight) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.pricePrNight = pricePrNight;
        this.bookings = new ArrayList();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getPricePrNight() {
        return pricePrNight;
    }

    public void setPricePrNight(int pricePrNight) {
        this.pricePrNight = pricePrNight;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(Booking booking) {
        if(booking != null){
            this.bookings.add(booking);
            booking.setHotel(this);
        }
    }
    
    
    
}
