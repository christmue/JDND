package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {

    private OrderController orderController;
    private UserRepository userRepository = mock(UserRepository.class);;
    private OrderRepository orderRepository = mock(OrderRepository.class);

    private Cart testCart;
    private Item testItem;
    private User testUser;
    private UserOrder testOrder;

    @Before
    public void setUp() {
        orderController = new OrderController();
        TestUtils.injectObject(orderController, "userRepository", userRepository);
        TestUtils.injectObject(orderController, "orderRepository", orderRepository);
        createTestObjects();
    }

    @Test
    public void submitOrderHappyPath() {
        when(userRepository.findByUsername("testUser")).thenReturn(testUser);
        ResponseEntity<UserOrder> res = orderController.submit("testUser");
        assertEquals(HttpStatus.OK, res.getStatusCode());
    }

    @Test
    public void submitOrderInvalidUsername() {
        ResponseEntity<UserOrder> response = orderController.submit("invalidUser");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getOrdersByUserHappyPath() {
        when(userRepository.findByUsername("testUser")).thenReturn(testUser);
        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("testUser");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getOrdersByUserInvalidUsername() {
        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("invalidUser");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    public void createTestObjects() {
        testItem = new Item();
        testItem.setId(1L);
        testItem.setName("testItem");
        testItem.setDescription("testDescription");
        testItem.setPrice(new BigDecimal(3));

        testCart = new Cart();
        testCart.setId(1L);
        testCart.setItems(new ArrayList<Item>());
        testCart.getItems().add(testItem);
        testCart.setTotal(new BigDecimal(3));
        testCart.setUser(testUser);

        testUser = new User();
        testUser.setUsername("testUser");
        testUser.setPassword("testPassword");
        testUser.setId(1L);
        testUser.setCart(testCart);

        testOrder = new UserOrder();
        testOrder.setItems(new ArrayList<>());
        testOrder.getItems().add(testItem);
        testOrder.setTotal(new BigDecimal(3));
        testOrder.setUser(testUser);
        testOrder.setId(1L);
    }


}
