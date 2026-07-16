package org.lxrssdev.taquia.app.services;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.lxrssdev.taquia.app.dto.CartItemDTO;
import org.lxrssdev.taquia.app.entities.Order;
import static org.lxrssdev.taquia.app.entities.OrderStatus.*;

import org.lxrssdev.taquia.app.entities.OrderDetail;
import org.lxrssdev.taquia.app.entities.Product;
import org.lxrssdev.taquia.app.entities.RestaurantTable;
import org.lxrssdev.taquia.app.exceptions.EmptyCartException;
import org.lxrssdev.taquia.app.repositories.OrderDetailRepository;
import org.lxrssdev.taquia.app.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductService productService;
    private final RestaurantTableService restaurantTableService;
    private final CartService cartService;


    @Transactional
    public Order confirmOrder(HttpSession session){
        List<CartItemDTO> cart = cartService.getCart(session);
        if(cart.isEmpty()){
            throw new EmptyCartException("El carrito esta vacio!");
        }
        RestaurantTable table = restaurantTableService.findByTableNumber(1); //Cambiar "1" por el numero de mesa asignado
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

    public List<Order> findPendingOrders(){
        return orderRepository.findByStatus(PENDING);
    }

}
