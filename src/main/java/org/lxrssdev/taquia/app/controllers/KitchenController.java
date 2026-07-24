package org.lxrssdev.taquia.app.controllers;


import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.lxrssdev.taquia.app.entities.Order;
import org.lxrssdev.taquia.app.entities.OrderStatus;
import org.lxrssdev.taquia.app.services.OrderDetailService;
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
public class KitchenController {

    private final OrderService orderService;
    private final OrderDetailService orderDetailService;


    @GetMapping("/kitchen")
    public String showKitchen(Model model){
        model.addAttribute("orders", orderService.findByStatusOrders(OrderStatus.PENDING));
        model.addAttribute("preparingOrders", orderService.findByStatusOrders(OrderStatus.PREPARING));
        return "kitchen";
    }

    @GetMapping("/kitchen/orders/{id}")
    public String showOrderDetails(@PathVariable Long id, Model model, HttpSession session){
        Order order = orderService.findById(id);
        model.addAttribute("orderDetails", orderDetailService.getOrderDetails(id));
        model.addAttribute("order", order);
        return "kitchen-detail";
    }

    @PostMapping("/kitchen/orders/{id}/start")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id, Model model){
        orderService.updateStatus(id, OrderStatus.PREPARING);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/kitchen/orders/{id}/ready")
    public ResponseEntity<Void> readyStatus(@PathVariable Long id, Model model){
        orderService.updateStatus(id, OrderStatus.READY);
        return ResponseEntity.ok().build();
    }

}
