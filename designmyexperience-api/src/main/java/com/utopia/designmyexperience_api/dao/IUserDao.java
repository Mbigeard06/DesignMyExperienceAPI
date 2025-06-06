package com.utopia.designmyexperience_api.dao;
import com.utopia.designmyexperience_api.model.BusinessOwner;
import com.utopia.designmyexperience_api.model.Client;
import com.utopia.designmyexperience_api.model.User;

import java.util.List;

/**
 * User dao interface. All class that fetch user data on the db has to implement it.
 */
public interface IUserDao {
    /**
     *
     * @param id user id
     * @return User
     */
    User getUser(int id);

    /**
     *
     * @return List<Users> all the users
     */
    List<User> getUsers();

    /**
     *
     * @param id user id
     * @return businnesOwner
     */
    BusinessOwner getBusinessOwner(int id);

    /**
     *
     * @param id user id
     * @return Client
     */
    Client getClient(int id);

    /**
     *
     * @param businessOwner businessOwner to add in the db
     * @return int -1 if failed
     */
    int createBusinessOwner(BusinessOwner businessOwner, String hashedPassword);

    /**
     *
     * @param client to add in the db
     * @return int -1 if failed
     */
    int createClient(Client client, String hashedPassword);

    /**
     *
     * @param mail client mail
     * @param plainPassword client password input
     * @return User if the credentials are valid
     */
    public User checkCredentiels(String mail, String plainPassword);
}
