/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.CreditCard;

/**
 *
 * @author chris
 */
public class CreditCardDTO {

    public String cardType;
    public String cardNumber;
    public String expDate;

    public CreditCardDTO(CreditCard card) {

        this.cardType = card.getCardType();
        this.cardNumber = card.getCardNumber();
        this.expDate = card.getExpDate();
    }

}
