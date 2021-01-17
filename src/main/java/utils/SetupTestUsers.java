package utils;

import entities.CreditCard;
import entities.Role;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class SetupTestUsers {

    public static void setUpUsers() {

        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

//     IMPORTAAAAAAAAAANT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//     This breaks one of the MOST fundamental security rules in that it ships with default users and passwords
//     CHANGE the three passwords below, before you uncomment and execute the code below
//     Also, either delete this file, when users are created or rename and add to .gitignore
//     Whatever you do DO NOT COMMIT and PUSH with the real passwords
        User user1 = new User("user1", "user1", "user1manden", "user1@user1.dk", "28939704");
        User user2 = new User("user2", "user2", "user2manden", "user2@user2.dk", "29939704");
        User admin = new User("admin", "admin", "adminmanden", "admin@admin.com", "admin");
        User both = new User("both", "both", "bothmanden", "both@both.com", "both");
        CreditCard card1 = new CreditCard("Dankort", "1234", "22/19");
        CreditCard card2 = new CreditCard("Mastercard", "1234", "11/19");
        CreditCard card3 = new CreditCard("Dankort", "4568", "6/19");

        Role userRole = new Role("user");
        Role adminRole = new Role("admin");
        
        user1.AddCreditCard(card1);
        user2.AddCreditCard(card2);
        
        user1.addRole(userRole);
        user2.addRole(userRole);
        admin.addRole(adminRole);

        em.getTransaction().begin();
        em.persist(userRole);
        em.persist(adminRole);
        em.persist(user1);
        em.persist(user2);
        em.persist(admin);
        em.getTransaction().commit();
        
    }

}
