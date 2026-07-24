package org.lxrssdev.taquia.app.controllers;


import lombok.AllArgsConstructor;
import org.lxrssdev.taquia.app.entities.Order;
import org.lxrssdev.taquia.app.entities.OrderStatus;
import org.lxrssdev.taquia.app.entities.RestaurantTable;
import org.lxrssdev.taquia.app.repositories.OrderRepository;
import org.lxrssdev.taquia.app.services.OrderService;
import org.lxrssdev.taquia.app.services.RestaurantTableService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class WaiterController {

    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private final RestaurantTableService restaurantTableService;

    @GetMapping("/waiter")
    public String showWaiterPanel(Model model){
        model.addAttribute("readyOrders", orderRepository.findByStatus(OrderStatus.READY));
        return "waiter";
    }

    @PostMapping("/waiter/orders/{id}/delivered")
    public ResponseEntity<Void> readyStatus(@PathVariable Long id, Model model){
        Order order = orderService.updateStatus(id, OrderStatus.DELIVERED);
        RestaurantTable table = order.getRestaurantTable();
        if(table != null){
            table.setAvailable(true);
            restaurantTableService.save(table);
        }
        return ResponseEntity.ok().build();
    }

}
