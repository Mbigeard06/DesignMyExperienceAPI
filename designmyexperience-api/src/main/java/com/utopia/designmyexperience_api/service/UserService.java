package com.utopia.designmyexperience_api.service;

import com.utopia.designmyexperience_api.dao.IUserDao;
import com.utopia.designmyexperience_api.model.BusinessOwner;
import com.utopia.designmyexperience_api.model.Client;
import com.utopia.designmyexperience_api.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final IUserDao userDao;
    private final EmailService emailService;

    // Constructor injection
    public UserService(IUserDao userDao, EmailService emailService) {
        this.userDao = userDao;
        this.emailService = emailService;
    }

    /**
     * Retrieves a user by ID.
     * @param id The user ID.
     * @return User object if found, otherwise null.
     */
    public User getUser(int id) {
        return userDao.getUser(id);
    }

    /**
     *
     * @return list of the users
     */
    public List<User> getUsers(){
        return userDao.getUsers();
    }

    /**
     *
     * @param id user id
     * @return business owner
     */
    public BusinessOwner getBusinessOwner(int id){
        return userDao.getBusinessOwner(id);
    }

    /**
     *
     * @param id user id
     * @return client
     */
    public Client getClient(int id){
        return userDao.getClient(id);
    }

    /**
     *
     * @param businessOwner
     * @param password
     * @return index of the business owner created (-1 if failed)
     */
    public int createBusinessOwner(BusinessOwner businessOwner, String password){
        int userId = userDao.createBusinessOwner(businessOwner, password);
        if (userId != -1) {
            emailService.welcomeBusinessOwner(businessOwner); 
        }
        return userId;
    }

    /**
     *
     * @param client client to create
     * @param password password
     * @return index of the client created (-1 if failed)
     */
    public int createClient(Client client, String password){
        int userId = userDao.createClient(client, password);
        if (userId != -1) {
            emailService.welcomeClient(client); // Email sent if email sucessfull
        }
        return userId;
    }

    /**
     * Checks user credentials based on email and password.
     * @param email Email used to authenticate.
     * @param password Raw password to compare.
     * @return The authenticated User if credentials are correct, otherwise null.
     */
    public User checkCredential(String email, String password){
        try {
            return userDao.checkCredentiels(email, password);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error checking user credentials", e);
        }
    }



}
