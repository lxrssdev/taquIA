package org.lxrssdev.taquia.app.services;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.lxrssdev.taquia.app.dto.CartItemDTO;
import org.lxrssdev.taquia.app.entities.*;

import static org.lxrssdev.taquia.app.entities.OrderStatus.*;

import org.lxrssdev.taquia.app.exceptions.EmptyCartException;
import org.lxrssdev.taquia.app.exceptions.NoAvailableTables;
import org.lxrssdev.taquia.app.repositories.OrderDetailRepository;
import org.lxrssdev.taquia.app.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductService productService;
    private final RestaurantTableService restaurantTableService;
    private final CartService cartService;
    private static final Random random = new Random();


    @Transactional
    public Order confirmOrder(HttpSession session){
        List<CartItemDTO> cart = cartService.getCart(session);
        if(cart.isEmpty()){
            throw new EmptyCartException("El carrito esta vacio!");
        }

        List<RestaurantTable> availableTables = restaurantTableService.findByIsAvailable(true);
        if(availableTables.isEmpty()){
            throw new NoAvailableTables("No hay mesas disponibles por el momento!");
        }
        int randomNumber = random.nextInt(availableTables.size());
        RestaurantTable table = availableTables.get(randomNumber);
        table.setAvailable(false);

        Order order = new Order();
        //asignar nada mas status, y la mesa del restaurante, porque la fecha de creacion y el id se le ponen automaticamente
        order.setStatus(PENDING);
        order.setRestaurantTable(table);
        Order orderSaved = orderRepository.save(order);
        for (CartItemDTO item : cart){
            Product product = productService.findById(item.getProductId());
            if(!product.isAvailable()){
                throw new RuntimeException("El producto ya no esta disponible!");
            }
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(orderSaved);
            orderDetail.setProduct(product);
            orderDetail.setQuantity(item.getQuantity());
            orderDetail.setObservations(item.getObservations());
            orderDetailRepository.save(orderDetail);
        }
        cartService.clear(session);
        return order;
    }

    public List<Order> findByStatusOrders(OrderStatus status){
        return orderRepository.findByStatus(status);
    }

    public Order findById(Long id){
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("La orden con ese id no existe!"));
    }

    @Transactional
    public Order updateStatus(Long id, OrderStatus status){
        Order order = findById(id);
        order.setStatus(status);
        return orderRepository.save(order);
    }

}
