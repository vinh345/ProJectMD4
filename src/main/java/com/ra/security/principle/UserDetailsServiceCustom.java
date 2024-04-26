package com.ra.security.principle;


import com.ra.model.entity.User;
import com.ra.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceCustom implements UserDetailsService {
    @Autowired
    private IUserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User>  users = userRepository.findByUsername(username);
        if (users.isEmpty()) {
            throw new UsernameNotFoundException("user not found");
        }
        return UserDetailsCustom.build(users.get(0));
    }
}
