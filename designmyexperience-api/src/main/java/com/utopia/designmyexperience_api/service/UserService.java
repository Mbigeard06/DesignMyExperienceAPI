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

    // Constructor injection
    public UserService(IUserDao userDao) {
        this.userDao = userDao;
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
    public User getBusinessOwner(int id){
        return userDao.getBusinessOwner(id);
    }

    /**
     *
     * @param id user id
     * @return client
     */
    public User getClient(int id){
        return userDao.getClient(id);
    }

    /**
     *
     * @param businessOwner
     * @param password
     * @return index of the business owner created (-1 if failed)
     */
    public int createBusinessOwner(BusinessOwner businessOwner, String password){
        return userDao.createBusinessOwner(businessOwner, password);
    }

    /**
     *
     * @param client client to create
     * @param password password
     * @return index of the client created (-1 if failed)
     */
    public int createClient(Client client, String password){
        return userDao.createClient(client, password);
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