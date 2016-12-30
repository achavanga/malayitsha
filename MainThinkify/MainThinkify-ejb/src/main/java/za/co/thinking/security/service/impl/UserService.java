package za.co.thinking.security.service.impl;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import za.co.thinking.model.dao.impl.UserDao;
import za.co.thinking.model.security.User;
import za.co.thinking.security.service.IUserService;

/**
 * File Name : UserService.java Project Name : MainThinkify-ejb
 *
 * @since Dec 20, 2016, 10:30:11 AM
 * @author Abel Chavanga <achavanga@fnb.co.za>
 *
 */
@Stateless
//@Local(IUserService.class)
public class UserService implements IUserService {

    @Inject
    private UserDao userDao;

    @Override
    public List<User> getAllUsers() {

        return userDao.findAll();

    }

    @Override
    public void seveUser(User user) {
        userDao.create(user);
    }

    @Override
    public User findByEmail(String email) {
      return  userDao.findByEmail(email);
    }

    @Override
    public User findById(Long id) {
       return userDao.findById(id);
    }
    
}
