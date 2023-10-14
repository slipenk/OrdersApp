package com.slipenk.ordersapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.slipenk.ordersapp.entity.Order;
import com.slipenk.ordersapp.entity.OrderItem;
import com.slipenk.ordersapp.entity.OwnUser;
import com.slipenk.ordersapp.entity.Product;
import com.slipenk.ordersapp.repository.ProductRepository;
import com.slipenk.ordersapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.slipenk.ordersapp.dictionary.Dictionary.ADD_ORDER_FULL_PATH;
import static com.slipenk.ordersapp.dictionary.Dictionary.ADD_PRODUCTS_FULL_PATH;
import static com.slipenk.ordersapp.dictionary.Dictionary.APPLE_IPHONE_12_64GB_WHITE;
import static com.slipenk.ordersapp.dictionary.Dictionary.APPLE_IPHONE_14_128GB_MIDNIGHT;
import static com.slipenk.ordersapp.dictionary.Dictionary.GET_PRODUCTS_FULL_PATH;
import static com.slipenk.ordersapp.dictionary.Dictionary.IPHONE_11;
import static com.slipenk.ordersapp.dictionary.Dictionary.IPHONE_13_PRO;
import static com.slipenk.ordersapp.dictionary.Dictionary.SALAH;
import static com.slipenk.ordersapp.dictionary.Dictionary.SLIPENK;
import static com.slipenk.ordersapp.dictionary.Dictionary.DOLLAR_SIGN;
import static com.slipenk.ordersapp.dictionary.Dictionary.ERROR_DURING_CONVERSION_INTO_JSON;
import static com.slipenk.ordersapp.dictionary.Dictionary.INSERT_DATA_SQL_PATH;
import static com.slipenk.ordersapp.dictionary.Dictionary.PRODUCTS_WITH_SIZE;
import static com.slipenk.ordersapp.dictionary.Dictionary.WERE_ADDED_SUCCESSFULLY;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Sql(INSERT_DATA_SQL_PATH)
@AutoConfigureMockMvc
@SpringBootTest
class OrdersAppApplicationTests {

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    private final UserDetailsService userDetailsService;

    private final Logger logger = Logger.getLogger(OrdersAppApplicationTests.class.getName());

    @Autowired
    OrdersAppApplicationTests(MockMvc mockMvc,
                              ObjectMapper objectMapper,
                              ProductRepository productRepository,
                              UserDetailsService userDetailsService,
                              UserRepository userRepository) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.productRepository = productRepository;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
    }

    @Test
    void createProductsAsManager() {
        Product product1 = new Product(0, APPLE_IPHONE_14_128GB_MIDNIGHT, 32000, 100);
        Product product2 = new Product(0, APPLE_IPHONE_12_64GB_WHITE, 22000, 80);

        List<Product> productList = new ArrayList<>(List.of(product1, product2));

        User user = (User) userDetailsService.loadUserByUsername(SLIPENK);

        try {
            mockMvc.perform(post(ADD_PRODUCTS_FULL_PATH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(productList))
                            .with(user(user)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath(DOLLAR_SIGN, is(PRODUCTS_WITH_SIZE + 2 + WERE_ADDED_SUCCESSFULLY)));
        } catch (Exception e) {
            logger.log(Level.SEVERE, ERROR_DURING_CONVERSION_INTO_JSON);
        }

        Optional<Product> product_1 = productRepository.findByName(APPLE_IPHONE_14_128GB_MIDNIGHT);
        Optional<Product> product_2 = productRepository.findByName(APPLE_IPHONE_12_64GB_WHITE);

        assertTrue(product_1.isPresent());
        assertTrue(product_2.isPresent());
        assertEquals(APPLE_IPHONE_14_128GB_MIDNIGHT, product_1.get().getName());
        assertEquals(32000, product_1.get().getPrice());
        assertEquals(100, product_1.get().getTotalQuantity());
        assertEquals(APPLE_IPHONE_12_64GB_WHITE, product_2.get().getName());
        assertEquals(22000, product_2.get().getPrice());
        assertEquals(80, product_2.get().getTotalQuantity());
    }

    @Test
    void createProductsAsClient() {
        Product product1 = new Product(0, APPLE_IPHONE_14_128GB_MIDNIGHT, 32000, 100);
        Product product2 = new Product(0, APPLE_IPHONE_12_64GB_WHITE, 22000, 80);

        List<Product> productList = new ArrayList<>(List.of(product1, product2));

        User user = (User) userDetailsService.loadUserByUsername(SALAH);

        try {
            mockMvc.perform(post(ADD_PRODUCTS_FULL_PATH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(productList))
                            .with(user(user)))
                    .andExpect(status().isForbidden());
        } catch (Exception e) {
            logger.log(Level.SEVERE, ERROR_DURING_CONVERSION_INTO_JSON);
        }
    }

    @Test
    void listAllAvailableItems() {
        User user = (User) userDetailsService.loadUserByUsername(SALAH);

        try {
            mockMvc.perform(get(GET_PRODUCTS_FULL_PATH)
                            .with(user(user)))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath(DOLLAR_SIGN, hasSize(greaterThan(0))))
                    .andExpect(jsonPath("$[0].name", is("iPhone 13 Pro")))
                    .andExpect(jsonPath("$[0].price", is(30000.0)))
                    .andExpect(jsonPath("$[0].totalQuantity", is(100)))
                    .andExpect(jsonPath("$[1].name", is("iPhone 11")))
                    .andExpect(jsonPath("$[1].price", is(25000.0)))
                    .andExpect(jsonPath("$[1].totalQuantity", is(80)))
                    .andExpect(jsonPath("$[2].name", is("iPhone 8")))
                    .andExpect(jsonPath("$[2].price", is(15000.0)))
                    .andExpect(jsonPath("$[2].totalQuantity", is(50)));
        } catch (Exception e) {
            logger.log(Level.SEVERE, ERROR_DURING_CONVERSION_INTO_JSON);
        }
    }

    @Test
    void postOrder() {
        Optional<OwnUser> userOptional = userRepository.findByUsername(SALAH);
        Optional<Product> productOptional_1 = productRepository.findByName(IPHONE_13_PRO);
        Optional<Product> productOptional_2 = productRepository.findByName(IPHONE_11);


        assertTrue(productOptional_1.isPresent());
        assertTrue(productOptional_2.isPresent());

        assertTrue(userOptional.isPresent());

        OrderItem orderItem_1 = new OrderItem();
        orderItem_1.setId(0);
        orderItem_1.setOrderedProduct(productOptional_1.get());
        orderItem_1.setQuantity(20);
        orderItem_1.setOrder(null);

        OrderItem orderItem_2 = new OrderItem();
        orderItem_2.setId(0);
        orderItem_2.setOrderedProduct(productOptional_2.get());
        orderItem_2.setQuantity(30);
        orderItem_2.setOrder(null);

        Order order = new Order();
        order.setId(0);
        order.setPaid(false);
        order.setOrderItems(List.of(orderItem_1, orderItem_2));

        List<Order> orders = new ArrayList<>(List.of(order));

        User user = (User) userDetailsService.loadUserByUsername(SLIPENK);

        try {
            mockMvc.perform(post(ADD_ORDER_FULL_PATH)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(orders))
                            .with(user(user)))
                    .andExpect(jsonPath(DOLLAR_SIGN, hasSize(greaterThan(0))))
                    .andExpect(jsonPath("$[0].paid", is(Boolean.FALSE)))
                    .andExpect(jsonPath("$[0].orderItems[0].orderedProduct.name", is(IPHONE_13_PRO)))
                    .andExpect(jsonPath("$[0].orderItems[1].orderedProduct.name", is(IPHONE_11)));

        } catch (Exception e) {
            logger.log(Level.SEVERE, ERROR_DURING_CONVERSION_INTO_JSON);
        }

    }

}
