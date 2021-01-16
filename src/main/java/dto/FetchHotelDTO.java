/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

/**
 *
 * @author chris
 */
public class FetchHotelDTO {
    
    public int id;
    public String name;
    public String address;
    public String email;
    public String phone;
    public String price;
    public String url;

    public FetchHotelDTO(int id, String name, String address, String email, String phone, String price, String url) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.price = price;
        this.url = url;
    }
    
    
}
