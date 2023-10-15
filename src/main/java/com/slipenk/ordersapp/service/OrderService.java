package com.slipenk.ordersapp.service;

import com.slipenk.ordersapp.entity.Order;
import com.slipenk.ordersapp.entity.OrderItem;
import com.slipenk.ordersapp.entity.Product;
import com.slipenk.ordersapp.exceptions.BadDataException;
import com.slipenk.ordersapp.exceptions.CustomNotFoundException;
import com.slipenk.ordersapp.repository.OrderRepository;
import com.slipenk.ordersapp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.slipenk.ordersapp.dictionary.Dictionary.CANNOT_BUY;
import static com.slipenk.ordersapp.dictionary.Dictionary.ERROR_PRODUCT_NOT_FOUND;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public List<Order> addOrders(List<Order> orders) {
        for (Order order : orders) {
            for (OrderItem orderItem : order.getOrderItems()) {
                proceedWithProduct(orderItem.getOrderedProduct(), orderItem);
            }
        }
        return new ArrayList<>(orderRepository.saveAll(orders));
    }

    @Scheduled(fixedDelay = 10 * 60 * 1000)
    public void cleanupNotPaidOrders() {
        List<Order> notPaidOrders = orderRepository
                .findByPaidAndCreatedDateTimeBefore(Boolean.FALSE, new Timestamp(System.currentTimeMillis() - 10 * 60 * 1000));

        if (!notPaidOrders.isEmpty()) {
            orderRepository.deleteAll(notPaidOrders);
        }
    }

    private void proceedWithProduct(Product product, OrderItem orderItem) {
        checkIfProductExists(product.getName());
        int residue = checkIfCanBuy(product, orderItem);
        removeTheResidue(product, residue);
    }

    private void checkIfProductExists(String productName) {
        Optional<Product> productOptional = productRepository.findByName(productName);
        if (productOptional.isEmpty()) {
            throw new CustomNotFoundException(ERROR_PRODUCT_NOT_FOUND);
        }
    }

    private int checkIfCanBuy(Product product, OrderItem orderItem) {
        int productTotalQuantity = product.getTotalQuantity();
        int requestedQuantity = orderItem.getQuantity();

        if (requestedQuantity > productTotalQuantity) {
            throw new BadDataException(CANNOT_BUY);
        }

        return productTotalQuantity - requestedQuantity;
    }

    private void removeTheResidue(Product product, int residue) {
        product.setTotalQuantity(residue);
    }

    public List<Order> payOrders(List<Order> orders) {
        for (Order order : orders) {
            order.setPaid(Boolean.TRUE);
        }
        return new ArrayList<>(orderRepository.saveAll(orders));
    }
}
