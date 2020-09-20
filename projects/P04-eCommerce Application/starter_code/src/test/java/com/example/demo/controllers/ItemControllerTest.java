package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {

    private ItemController itemController;
    private ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void setUp() {
        itemController = new ItemController();
        TestUtils.injectObject(itemController, "itemRepository", itemRepository);
    }

    @Test
    public void getItems() {
        when(itemRepository.findAll()).thenReturn(createItemList());
        assertNotNull(itemController.getItems());
        assertEquals(2, itemController.getItems().getBody().size());
    }

    @Test
    public void getItemById() {
        Optional<Item> optionalItem = Optional.of(createItemList().get(0));
        when(itemRepository.findById(1L)).thenReturn(optionalItem);

        assertNotNull(itemController.getItemById(1L));
        assertEquals("Round Widget", itemController.getItemById(1L).getBody().getName());
    }

    @Test
    public void getItemByName() {
        List<Item> roundItems = new ArrayList<>();
        roundItems.add(createItemList().get(0));
        when(itemRepository.findByName("Round Widget")).thenReturn(roundItems);

        assertNotNull(itemController.getItemsByName("Round Widget"));
        assertEquals("Round Widget", itemController.getItemsByName("Round Widget").getBody().get(0).getName());

        assertEquals(404, itemController.getItemsByName("Some Other Widget").getStatusCodeValue());
    }

    private List<Item> createItemList() {
        Item item1 = new Item();
        item1.setName("Round Widget");
        item1.setPrice(BigDecimal.valueOf(2.99));
        item1.setDescription("A widget that is round.");

        Item item2 = new Item();
        item2.setName("Square Widget");
        item2.setPrice(BigDecimal.valueOf(1.99));
        item2.setDescription("A widget that is square.");

        List<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);

        return items;
    }
}
