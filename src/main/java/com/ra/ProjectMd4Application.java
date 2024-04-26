package com.ra;

import com.ra.model.entity.Role;
import com.ra.model.entity.RoleName;
import com.ra.model.entity.User;
import com.ra.repository.IUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class ProjectMd4Application {

    public static void main(String[] args) {
        SpringApplication.run(ProjectMd4Application.class, args);
    }
//        @Bean
//    public CommandLineRunner runner(PasswordEncoder passwordEncoder, IUserRepository userRepository){
//        return args -> {
//            Role admin = new Role(null, RoleName.ROLE_ADMIN)  ;
//            Role user = new Role(null, RoleName.ROLE_USER)  ;
//            Set<Role> set = new HashSet<>();
//            set.add(admin);
//            set.add(user);
//
//           User roleAdmin = new User(null,"admin123","admin",null,passwordEncoder.encode("admin123"),set,true);
//           userRepository.save(roleAdmin);
//        };
//    }

}
