package com.detectiveos.service;
import com.detectiveos.exception.AuthenticationException;
import com.detectiveos.model.User;
import com.detectiveos.repository.UserRepository;
import com.detectiveos.util.PasswordUtil;
public class AuthService {
    private final UserRepository repo=new UserRepository();
    public User authenticate(String username,String password) throws AuthenticationException {
        User user=repo.findByUsername(username);
        if(user==null || !user.getPasswordHash().equals(PasswordUtil.sha256(password))) throw new AuthenticationException("Invalid username or password.");
        return user;
    }
}
