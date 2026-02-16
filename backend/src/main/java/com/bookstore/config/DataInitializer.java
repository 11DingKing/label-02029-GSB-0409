package com.bookstore.config;

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
        if (!userRepository.existsByEmail("admin")) {
            User admin = new User();
            admin.setEmail("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setNickname("管理员");
            admin.setRole("ADMIN");
            admin.setStatus(1);
            userRepository.save(admin);
        }
        
        // 创建测试用户
        if (!userRepository.existsByEmail("user@test.com")) {
            User user = new User();
            user.setEmail("user@test.com");
            user.setPassword(passwordEncoder.encode("123456"));
            user.setNickname("测试用户");
            user.setRole("USER");
            user.setStatus(1);
            userRepository.save(user);
        }
    }
}
