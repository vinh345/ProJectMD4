package com.ra.service;


import com.ra.exception.DataNotFoundException;
import com.ra.exception.NoDataException;
import com.ra.model.dto.request.FormLogin;
import com.ra.model.dto.request.FormRegister;
import com.ra.model.dto.response.JWTResponse;
import com.ra.model.entity.Role;
import com.ra.model.entity.RoleName;
import com.ra.model.entity.User;
import com.ra.repository.IRoleRepository;
import com.ra.repository.IUserRepository;
import com.ra.security.jwt.JWTProvider;
import com.ra.security.principle.UserDetailsCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements IUserService{
    @Autowired
    private IRoleRepository iRoleRepository;
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTProvider jwtProvider;
    @Autowired
    private IUserRepository userRepository;
    @Override
    public boolean register(FormRegister formRegister) {
        Optional<Role> roleUser = iRoleRepository.findByRoleName(RoleName.ROLE_USER);
        if (roleUser.isEmpty()) {
            throw  new NoSuchElementException("role not found");
        }
        Set<Role> roles = new HashSet<>();
        roles.add(roleUser.get());
        User user = User.builder()
                .email(formRegister.getEmail())
                .fullName(formRegister.getFullName())
                .username(formRegister.getUsername())
                .password(passwordEncoder.encode(formRegister.getPassword()))
                .status(true)
                .roleSet(roles)
                .build();
        userRepository.save(user);
        return true;
    }

    @Override
    public JWTResponse login(FormLogin formLogin) {
        // xac thực username vaf password
        Authentication authentication = null;
        try {
           authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(formLogin.getUsername(),formLogin.getPassword()));
        }catch (AuthenticationException e){
            throw new RuntimeException("username or password incorrect");
        }
        UserDetailsCustom detailsCustom = (UserDetailsCustom) authentication.getPrincipal();
        String accessToken = jwtProvider.generateAccessToken(detailsCustom);
        return JWTResponse.builder()
                .email(detailsCustom.getEmail())
                .fullName(detailsCustom.getFullName())
                .roleSet(detailsCustom.getAuthorities())
                .status(detailsCustom.isStatus())
                .accessToken(accessToken)
                .build();
    }

    @Override
    public Page<User> findAll(Pageable pageable) throws NoDataException {
        Page<User> users = userRepository.findAll(pageable);
        if (users.isEmpty()) {
            throw new NoDataException("No users found");
        }
        return users;
    }



    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void remove(Long aLong) {

    }

    @Override
    public List<User> findAllExceptAdmin() {
        return userRepository.findAll().stream().filter(user -> user.getRoleSet().stream().anyMatch(role -> !role.getRoleName().equals(RoleName.ROLE_ADMIN))).toList();
    }

    @Override
    public List<User> findByUserName(String name) throws DataNotFoundException {
        List<User> byUsername = userRepository.findByUsername(name);
        if (byUsername.isEmpty()){
            throw new DataNotFoundException("tên không tồn tại");
        }
        return byUsername;
    }

    @Override
    public String toggleUserStatus(Long userId) throws DataNotFoundException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setStatus(!user.isStatus()); // Đảo ngược trạng thái
            userRepository.save(user);
            return user.isStatus() ? "User unlocked" : "User locked";
        } else {
          throw new DataNotFoundException("user not found");
        }
    }
}
