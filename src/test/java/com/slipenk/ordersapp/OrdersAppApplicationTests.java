package com.slipenk.ordersapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.slipenk.ordersapp.controller.OrderController;
import com.slipenk.ordersapp.entity.Order;
import com.slipenk.ordersapp.entity.OrderItem;
import com.slipenk.ordersapp.entity.OwnUser;
import com.slipenk.ordersapp.entity.Product;
import com.slipenk.ordersapp.exceptions.BadDataException;
import com.slipenk.ordersapp.exceptions.RestExceptionHandler;
import com.slipenk.ordersapp.repository.OrderRepository;
import com.slipenk.ordersapp.repository.ProductRepository;
import com.slipenk.ordersapp.repository.UserRepository;
import com.slipenk.ordersapp.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.slipenk.ordersapp.dictionary.Dictionary.NUMBER_2200_99;
import static com.slipenk.ordersapp.dictionary.Dictionary.NUMBER_3200_99;
import static com.slipenk.ordersapp.dictionary.Dictionary.ORDERS_FULL_PATH;
import static com.slipenk.ordersapp.dictionary.Dictionary.APPLE_IPHONE_12_64GB_WHITE;
import static com.slipenk.ordersapp.dictionary.Dictionary.APPLE_IPHONE_14_128GB_MIDNIGHT;
import static com.slipenk.ordersapp.dictionary.Dictionary.CANNOT_BUY;
import static com.slipenk.ordersapp.dictionary.Dictionary.DOLLAR_SIGN;
import static com.slipenk.ordersapp.dictionary.Dictionary.INSERT_DATA_SQL_PATH;
import static com.slipenk.ordersapp.dictionary.Dictionary.IPHONE_11;
import static com.slipenk.ordersapp.dictionary.Dictionary.IPHONE_13_PRO;
import static com.slipenk.ordersapp.dictionary.Dictionary.PAY_ORDER_FULL_PATH;
import static com.slipenk.ordersapp.dictionary.Dictionary.PRODUCTS_FULL_PATH;
import static com.slipenk.ordersapp.dictionary.Dictionary.SALAH;
import static com.slipenk.ordersapp.dictionary.Dictionary.SLIPENK;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(INSERT_DATA_SQL_PATH)
@Transactional
@AutoConfigureMockMvc
@SpringBootTest
class OrdersAppApplicationTests {

    private final OrderRepository orderRepository;
    private final ObjectMapper objectMapper;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderService orderService;
    private final UserDetailsService userDetailsService;
    private final OrderController orderController;
    private MockMvc mockMvc;

    @Autowired
    OrdersAppApplicationTests(MockMvc mockMvc,
                              ObjectMapper objectMapper,
                              ProductRepository productRepository,
                              UserDetailsService userDetailsService,
                              UserRepository userRepository,
                              OrderService orderService,
                              OrderRepository orderRepository,
                              OrderController orderController) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.productRepository = productRepository;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.orderService = orderService;
        this.orderRepository = orderRepository;
        this.orderController = orderController;
    }

    @Test
    void createProductsAsManager() throws Exception {
        Product product1 = new Product(0, APPLE_IPHONE_14_128GB_MIDNIGHT, new BigDecimal(NUMBER_3200_99), 100, null);
        Product product2 = new Product(0, APPLE_IPHONE_12_64GB_WHITE, new BigDecimal(NUMBER_2200_99), 80, null);

        List<Product> productList = new ArrayList<>(List.of(product1, product2));

        User user = (User) userDetailsService.loadUserByUsername(SLIPENK);

        mockMvc.perform(post(PRODUCTS_FULL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productList))
                        .with(user(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(DOLLAR_SIGN, hasSize(greaterThan(0))))
                .andExpect(jsonPath("$[0].name", is(APPLE_IPHONE_14_128GB_MIDNIGHT)))
                .andExpect(jsonPath("$[0].price", is(32000.99)))
                .andExpect(jsonPath("$[0].totalQuantity", is(100)))
                .andExpect(jsonPath("$[1].name", is(APPLE_IPHONE_12_64GB_WHITE)))
                .andExpect(jsonPath("$[1].price", is(22000.99)))
                .andExpect(jsonPath("$[1].totalQuantity", is(80)));

        Optional<Product> product_1 = productRepository.findByName(APPLE_IPHONE_14_128GB_MIDNIGHT);
        Optional<Product> product_2 = productRepository.findByName(APPLE_IPHONE_12_64GB_WHITE);

        assertTrue(product_1.isPresent());
        assertTrue(product_2.isPresent());
        assertEquals(APPLE_IPHONE_14_128GB_MIDNIGHT, product_1.get().getName());
        assertEquals(0, new BigDecimal(NUMBER_3200_99).compareTo(product_1.get().getPrice()));
        assertEquals(100, product_1.get().getTotalQuantity());
        assertEquals(APPLE_IPHONE_12_64GB_WHITE, product_2.get().getName());
        assertEquals(0, new BigDecimal(NUMBER_2200_99).compareTo(product_2.get().getPrice()));
        assertEquals(80, product_2.get().getTotalQuantity());
    }

    @Test
    void createProductsAsClient() throws Exception {
        Product product1 = new Product(0, APPLE_IPHONE_14_128GB_MIDNIGHT, new BigDecimal(NUMBER_3200_99), 100, null);
        Product product2 = new Product(0, APPLE_IPHONE_12_64GB_WHITE, new BigDecimal(NUMBER_2200_99), 80, null);

        List<Product> productList = new ArrayList<>(List.of(product1, product2));

        User user = (User) userDetailsService.loadUserByUsername(SALAH);

        mockMvc.perform(post(PRODUCTS_FULL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productList))
                        .with(user(user)))
                .andExpect(status().isForbidden());
    }

    @Test
    void listAllAvailableItems() throws Exception {
        User user = (User) userDetailsService.loadUserByUsername(SALAH);

        mockMvc.perform(get(PRODUCTS_FULL_PATH)
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
    }

    @Test
    void addOrder() throws Exception {
        List<Order> orders = new ArrayList<>(List.of(getOrder(20, 30)));

        User user = (User) userDetailsService.loadUserByUsername(SLIPENK);

        mockMvc.perform(post(ORDERS_FULL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orders))
                        .with(user(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(DOLLAR_SIGN, hasSize(greaterThan(0))))
                .andExpect(jsonPath("$[0].paid", is(Boolean.FALSE)))
                .andExpect(jsonPath("$[0].orderItems[0].orderedProduct.name", is(IPHONE_13_PRO)))
                .andExpect(jsonPath("$[0].orderItems[1].orderedProduct.name", is(IPHONE_11)))
                .andExpect(jsonPath("$[0].orderItems[0].orderedProduct.totalQuantity", is(80)))
                .andExpect(jsonPath("$[0].orderItems[1].orderedProduct.totalQuantity", is(50)));

        Optional<OwnUser> userOptional = userRepository.findByUsername(SALAH);
        assertTrue(userOptional.isPresent());

        List<Order> orderList = orderRepository.findByOwnUser(userOptional.get());
        assertEquals(IPHONE_13_PRO, orderList.get(1).getOrderItems().get(0).getOrderedProduct().getName());
        assertEquals(IPHONE_11, orderList.get(1).getOrderItems().get(1).getOrderedProduct().getName());
        assertEquals(80, orderList.get(1).getOrderItems().get(0).getOrderedProduct().getTotalQuantity());
        assertEquals(50, orderList.get(1).getOrderItems().get(1).getOrderedProduct().getTotalQuantity());

    }

    @Test
    void addOrderQuantityMoreThanExists() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(orderController)
                .setControllerAdvice(new RestExceptionHandler())
                .build();

        List<Order> orders = new ArrayList<>(List.of(getOrder(200, 300)));

        User user = (User) userDetailsService.loadUserByUsername(SLIPENK);

        mockMvc.perform(post(ORDERS_FULL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orders))
                        .with(user(user)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadDataException))
                .andExpect(result -> assertEquals(CANNOT_BUY, Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    void testCheckFindNotPaidBeforeTime() {
        Order notPaidOrder = getOrder(2, 3);
        assertNotNull(notPaidOrder);

        orderRepository.save(notPaidOrder);

        List<Order> orders = orderRepository.findByPaidAndCreatedDateTimeBefore(Boolean.FALSE, new Timestamp(System.currentTimeMillis()));

        assertFalse(orders.isEmpty());
        assertEquals(Boolean.FALSE, orders.get(0).isPaid());
    }

    @Test
    void testCheckCleanupNotPaidOrders() {
        Order notPaidOrder = getOrder(2, 3);
        assertNotNull(notPaidOrder);

        orderRepository.save(notPaidOrder);

        orderService.cleanupNotPaidOrders();

        List<Order> orders = orderRepository.findByPaid(Boolean.FALSE);
        assertEquals(1, orders.size());
    }

    @Test
    void payForOrder() throws Exception {
        Optional<OwnUser> userOptional = userRepository.findByUsername(SALAH);
        assertTrue(userOptional.isPresent());

        List<Order> orders = orderRepository.findByOwnUser(userOptional.get());
        assertFalse(orders.get(0).isPaid());

        User user = (User) userDetailsService.loadUserByUsername(SLIPENK);

        mockMvc.perform(post(PAY_ORDER_FULL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orders))
                        .with(user(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(DOLLAR_SIGN, hasSize(greaterThan(0))))
                .andExpect(jsonPath("$[0].paid", is(Boolean.TRUE)))
                .andExpect(jsonPath("$[0].orderItems[0].orderedProduct.name", is(IPHONE_13_PRO)))
                .andExpect(jsonPath("$[0].orderItems[1].orderedProduct.name", is(IPHONE_11)))
                .andExpect(jsonPath("$[0].orderItems[0].orderedProduct.totalQuantity", is(100)))
                .andExpect(jsonPath("$[0].orderItems[1].orderedProduct.totalQuantity", is(80)));

        orders = orderRepository.findByOwnUser(userOptional.get());
        assertTrue(orders.get(0).isPaid());
    }

    private Order getOrder(int quantity_1, int quantity_2) {
        Optional<OwnUser> userOptional = userRepository.findByUsername(SALAH);
        Optional<Product> productOptional_1 = productRepository.findByName(IPHONE_13_PRO);
        Optional<Product> productOptional_2 = productRepository.findByName(IPHONE_11);

        assertTrue(productOptional_1.isPresent());
        assertTrue(productOptional_2.isPresent());

        assertTrue(userOptional.isPresent());

        OrderItem orderItem_1 = new OrderItem();
        orderItem_1.setId(0);
        orderItem_1.setOrderedProduct(productOptional_1.get());
        orderItem_1.setQuantity(quantity_1);
        orderItem_1.setOrder(null);

        OrderItem orderItem_2 = new OrderItem();
        orderItem_2.setId(0);
        orderItem_2.setOrderedProduct(productOptional_2.get());
        orderItem_2.setQuantity(quantity_2);
        orderItem_2.setOrder(null);

        Order order = new Order();
        order.setId(0);
        order.setPaid(Boolean.FALSE);
        order.setCreatedDateTime(new Timestamp(System.currentTimeMillis() - 15 * 60 * 1000));
        order.setOwnUser(userOptional.get());
        order.setOrderItems(List.of(orderItem_1, orderItem_2));

        return order;
    }

}
