package com.example.demo.repository;

import com.example.demo.entity.User;
import org.junit.BeforeClass;
import org.junit.ClassRule;
//import org.junit.Test;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class UserRepositoryTest {
    @Container
    public static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.0")
            .withDatabaseName("test")
            .withUsername("user")
            .withPassword("secret");

    @DynamicPropertySource
    static void mySQLProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
    }

    @Autowired
    private UserRepository userRepository;

//    @BeforeClass
//    public static void beforeClass(/*ConfigurableApplicationContext configurableApplicationContext*/) {
//        System.out.println(mysql.getJdbcUrl());
//        TestPropertyValues
//                .of("spring.datasource.url=" + mysql.getJdbcUrl());
////                .applyTo(configurableApplicationContext);
//    }

    @Test
    public void test() {
        System.out.println("Hello World!");
        User user = new User();
        user.setName("Test");
        user.setEmail("user@email.com");
        userRepository.save(user);

        List<User> users = (List) userRepository.findAll();
        for (User u : users) {
            System.out.println(u);
        }
        assertThat(users.size()).isEqualTo(1);
    }
}