package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;

public class CartControllerTest {

    private CartController cartController;
    private UserRepository userRepository;
    private CartRepository cartRepository;
    private ItemRepository itemRepository;

    @Before
    public void setUp() {
        cartController = new CartController();
        TestUtils.injectObject(cartController, "userRepository", userRepository);
        TestUtils.injectObject(cartController, "cartRepository", cartRepository);
        TestUtils.injectObject(cartController, "itemRepository", itemRepository);
    }

    @Test
    public void attItemToCart() {

    }

}
