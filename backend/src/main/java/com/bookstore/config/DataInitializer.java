package com.bookstore.config;

import com.bookstore.constant.UserStatus;
import com.bookstore.entity.User;
import com.bookstore.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public void run(String... args) {
        // 创建管理员账号
        if (!userRepository.existsByEmail("admin@bookstore.com")) {
            User admin = new User();
            admin.setEmail("admin@bookstore.com");
            admin.setPassword(passwordEncoder.encode("123456"));
            admin.setNickname("管理员");
            admin.setRole("ADMIN");
            admin.setStatus(UserStatus.ACTIVE);
            userRepository.save(admin);
        }
        
        // 创建测试用户
        if (!userRepository.existsByEmail("user@bookstore.com")) {
            User user = new User();
            user.setEmail("user@bookstore.com");
            user.setPassword(passwordEncoder.encode("123456"));
            user.setNickname("测试用户");
            user.setRole("USER");
            user.setStatus(UserStatus.ACTIVE);
            userRepository.save(user);
        }
        
        // 创建卖家用户
        if (!userRepository.existsByEmail("seller@bookstore.com")) {
            User seller = new User();
            seller.setEmail("seller@bookstore.com");
            seller.setPassword(passwordEncoder.encode("123456"));
            seller.setNickname("卖家");
            seller.setRole("USER");
            seller.setStatus(UserStatus.ACTIVE);
            userRepository.save(seller);
        }
    }
}
