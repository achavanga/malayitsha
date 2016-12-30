package za.co.thinking.security.service;

import java.util.List;
import za.co.thinking.model.security.User;

/**
 * File Name : IUserService.java Project Name : MainThinkify-ejb
 *
 * @since Dec 20, 2016, 10:29:25 AM
 * @author Abel Chavanga <achavanga@fnb.co.za>
 *
 */
//@Local
public interface IUserService {

    List<User> getAllUsers();

    void seveUser(User user);

    User findByEmail(String email);
    
    User findById(Long id);
}
