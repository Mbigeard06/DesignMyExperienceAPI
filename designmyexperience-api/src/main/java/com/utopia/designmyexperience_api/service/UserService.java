package com.utopia.designmyexperience_api.service;

import com.utopia.designmyexperience_api.dao.IUserDao;
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
    public User getUserById(int id) {
        return userDao.getUser(id);
    }

    /**
     *
     * @return list of the users
     */
    public List<User> getUsers(){
        return userDao.getUsers();
    }

}