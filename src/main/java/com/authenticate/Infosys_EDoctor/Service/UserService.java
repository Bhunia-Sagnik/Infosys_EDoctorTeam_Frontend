package com.authenticate.Infosys_EDoctor.Service;

import com.authenticate.Infosys_EDoctor.Entity.User;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    public User registerUser(User user);
    public User loginUser(@Valid String username, String password);
    public boolean verifyEmail(String token, String username);
    public boolean resetPassword(String email, String token, String newPassword);
    public boolean sendResetPasswordToken(String email);

    User getUserById(Long userId);

    User getUserByUsername(String username);
}
