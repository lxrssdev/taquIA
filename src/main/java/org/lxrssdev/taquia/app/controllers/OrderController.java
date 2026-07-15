package org.lxrssdev.taquia.app.controllers;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.lxrssdev.taquia.app.entities.Order;
import org.lxrssdev.taquia.app.services.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order/confirm")
    public String confirmOrder(HttpSession session){
        Order order = orderService.confirmOrder(session);
        return "order-succes";
    }
}
