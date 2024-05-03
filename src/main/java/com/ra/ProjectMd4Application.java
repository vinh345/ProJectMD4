package com.ra;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ra.model.entity.Role;
import com.ra.model.entity.RoleName;
import com.ra.model.entity.User;
import com.ra.repository.IRoleRepository;
import com.ra.repository.IUserRepository;

@SpringBootApplication
public class ProjectMd4Application {

    public static void main(String[] args) {


        SpringApplication.run(ProjectMd4Application.class, args);

    }
//    @Bean
//    public CommandLineRunner runner(
//    		PasswordEncoder passwordEncoder,
//    		IRoleRepository roleRepository,
//    		IUserRepository userRepository){
//        return args -> {
//            Set<Role> set = new HashSet<>();
//            Optional<Role> admin = roleRepository.findByRoleName(RoleName.ADMIN);
//            if (admin.isEmpty()) {
//            	roleRepository.save(new Role(null, RoleName.ADMIN));
//            }
//        	set.add(admin.get());
//        	Optional<Role> user = roleRepository.findByRoleName(RoleName.USER);
//            if (user.isEmpty()) {
//            	roleRepository.save(new Role(null, RoleName.USER));
//            }
//        	set.add(user.get());
//            if (roleRepository.findByRoleName(RoleName.MANAGER).isEmpty()) {
//            	roleRepository.save(new Role(null, RoleName.MANAGER));
//            }
//            if (userRepository.findByUsername("admin123").isEmpty()) {
//                User roleAdmin = User.builder()
//             		   .username("admin123")
//             		   .password(passwordEncoder.encode("admin123"))
//             		   .roleSet(set)
//             		   .status(true)
//             		   .isDeleted(false)
//             		   .createdAt(new Date())
//             		   .updatedAt(new Date())
//             		   .build();
//                userRepository.save(roleAdmin);
//            }
//        };
//    }

}
