package com.ra.service.userService;


import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.ra.model.dto.response.UserResponse;
import org.apache.http.auth.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ra.exception.DataConflictException;
import com.ra.exception.DataNotFoundException;
import com.ra.model.dto.request.FormLogin;
import com.ra.model.dto.request.FormRegister;
import com.ra.model.dto.request.FormUserEdit;
import com.ra.model.dto.request.FormUserPasswordChange;
import com.ra.model.dto.response.JWTResponse;
import com.ra.model.dto.response.PageResponse;
import com.ra.model.entity.Address;
import com.ra.model.entity.Role;
import com.ra.model.entity.RoleName;
import com.ra.model.entity.User;
import com.ra.repository.IAddressRepository;
import com.ra.repository.IRoleRepository;
import com.ra.repository.IUserRepository;
import com.ra.security.jwt.JWTProvider;
import com.ra.security.principle.UserDetailsCustom;

@Service
public class UserServiceImpl implements IUserService {
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
    @Autowired
    private IAddressRepository addressRepository;

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User save(User t) {
        return userRepository.save(t);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public ResponseEntity<User> register(FormRegister formRegister) throws DataNotFoundException, DataConflictException {
        Optional<User> user = userRepository.findByUsername(formRegister.getUsername());
        if (user.isPresent()) {
            throw new DataConflictException("username đã tồn tại");
        }
        Optional<Role> roleUser = iRoleRepository.findByRoleName(RoleName.USER);
        if (roleUser.isEmpty()) {
            throw new DataNotFoundException("ROLE_USER không tồn tại");
        }
        Set<Role> roles = new HashSet<>();
        roles.add(roleUser.get());
        User users = User.builder()
                .email(formRegister.getEmail())
                .fullName(formRegister.getFullName())
                .username(formRegister.getUsername())
                .password(passwordEncoder.encode(formRegister.getPassword()))
                .status(true)
                .roleSet(roles)
                .build();
        userRepository.save(users);
        addressRepository.save(Address.builder()
                .user(users)
                .fullAddress(formRegister.getAddress())
                .phone(formRegister.getPhone())
                .receiveName(formRegister.getUsername())
                .build());
        return ResponseEntity.status(HttpStatus.CREATED).body(users);
    }

    /**
     * @param formLogin
     * @return
     * @throws AuthenticationException khi đăng nhập bị lỗi
     */
    @Override
    public ResponseEntity<JWTResponse> login(FormLogin formLogin) throws AuthenticationException {
        // xac thực username & password
        Authentication authentication = manager.authenticate(
                new UsernamePasswordAuthenticationToken(formLogin.getUsername(), formLogin.getPassword()));
        UserDetailsCustom detailsCustom = (UserDetailsCustom) authentication.getPrincipal();
        String accessToken = jwtProvider.generateAccessToken(detailsCustom);
        JWTResponse response = JWTResponse.builder()
                .email(detailsCustom.getEmail())
                .fullName(detailsCustom.getFullName())
                .roleSet(detailsCustom.getAuthorities())
                .status(detailsCustom.isStatus())
                .accessToken(accessToken)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<PageResponse> findAllUser(Pageable pageable) {
        Page<User> user = userRepository.findAll(pageable);
        PageResponse response = PageResponse.builder()
                .data(user.getContent())
                .totalPages(user.getTotalPages())
                .last(user.isLast())
                .first(user.isFirst())
                .number(user.getNumber())
                .size(user.getSize())
                .sort(user.getSort())
                .totalElements(user.getNumberOfElements())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public List<User> findAllExceptAdmin() {
        return userRepository.findAll().stream()
                .filter(user -> user.getRoleSet().stream()
                        .anyMatch(role -> !role.getRoleName().equals(RoleName.ADMIN))
                ).toList();
    }

    @Override
    public ResponseEntity<UserResponse> findByUserName(String name) throws DataNotFoundException {
        User user = userRepository.findByUsername(name).orElseThrow(() -> new DataNotFoundException("user not found"));
        UserResponse userResponse = UserResponse.builder()
                .username(user.getUsername())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .avatar(user.getAvatar())
                .build();
        return ResponseEntity.ok(userResponse);
    }

    @Override
    public ResponseEntity<FormUserEdit> editUser(String name, FormUserEdit formUserEdit) throws DataNotFoundException {
        User user = userRepository.findByUsername(name).orElseThrow(() -> new DataNotFoundException("user not found"));
        if (formUserEdit.getFullName() != null) {
            user.setFullName(formUserEdit.getFullName());
        }
        if (formUserEdit.getEmail() != null) {
            user.setEmail(formUserEdit.getEmail());
        }
        if (formUserEdit.getPhone() != null) {
            user.setPhone(formUserEdit.getPhone());
        }
        if (formUserEdit.getAvatar() != null) {
            user.setAvatar(formUserEdit.getAvatar());
        }
        userRepository.save(user);
        return ResponseEntity.ok(FormUserEdit.builder()
                .fullName(user.getFullName())
                .email(user.getEmail())
                .avatar(user.getAvatar())
                .phone(user.getPhone())
                .build());
    }


    @Override
    public ResponseEntity<Void> changePassword(String name, FormUserPasswordChange form) throws DataNotFoundException {
        User user = userRepository.findByUsername(name).orElseThrow(() -> new DataNotFoundException("user not found"));
        if (!passwordEncoder.matches(form.getOldPass(), user.getPassword())) {
            throw new DataNotFoundException("oldPass không hợp lệ");
        }
        if (form.getNewPass() == null || !form.getNewPass().equals(form.getConfirmNewPass())) {
            throw new DataNotFoundException("newPass / confirmNewPass không hợp lệ");
        }
        user.setPassword(passwordEncoder.encode(form.getNewPass()));
        userRepository.save(user);
        return ResponseEntity.noContent().build();
    }


    @Override
    public ResponseEntity<List<User>> findByUsernameContaining(String name) {
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findByUsernameContaining(name));
    }

    @Override
    public ResponseEntity<String> changeUserStatus(String userId) throws DataNotFoundException {
        Long id;
        try {
            id = Long.valueOf(userId);
        } catch (NumberFormatException ex) {
            throw new DataNotFoundException("userId không phải là số");
        }
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setStatus(!user.getStatus()); // Đảo ngược trạng thái
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).body(user.getStatus()
                    ? "User unlocked" : "User locked");
        } else {
            throw new DataNotFoundException("user not found");
        }
    }
}
