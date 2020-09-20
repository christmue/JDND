package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.controllers.UserController;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private UserController userController;
    private UserRepository userRepository = mock(UserRepository.class);
    private CartRepository cartRepository = mock(CartRepository.class);
    private BCryptPasswordEncoder bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);

    @Before
    public void setUp() {
        userController = new UserController();
        TestUtils.injectObject(userController, "userRepository", userRepository);
        TestUtils.injectObject(userController, "cartRepository", cartRepository);
        TestUtils.injectObject(userController, "bCryptPasswordEncoder", bCryptPasswordEncoder);
    }

    @Test
    public void createUserHappyPath() throws Exception {
        when(bCryptPasswordEncoder.encode("TestPassword")).thenReturn("hashedTestPassword");
        CreateUserRequest req =  new CreateUserRequest();
        req.setUsername("TestUser");
        req.setPassword("TestPassword");
        req.setConfirmPassword("TestPassword");

        ResponseEntity<User> res = userController.createUser(req);
        assertNotNull(res);
        assertEquals(200, res.getStatusCodeValue());

        User user = res.getBody();
        assertNotNull(user);
        assertEquals(0, user.getId());
        assertEquals("TestUser", user.getUsername());
        assertEquals("hashedTestPassword", user.getPassword());
    }
}
