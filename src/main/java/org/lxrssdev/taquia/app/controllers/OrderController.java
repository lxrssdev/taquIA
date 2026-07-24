package org.lxrssdev.taquia.app.controllers;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.lxrssdev.taquia.app.entities.Order;
import org.lxrssdev.taquia.app.services.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.Random;

@Controller
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/order-success")
    public String orderSuccess(Model model){
        return "order-success";
    }

    @PostMapping("/order/confirm")
    public String confirmOrder(HttpSession session, RedirectAttributes attributes){

        Order order = orderService.confirmOrder(session);

        Random random = new Random();
        attributes.addFlashAttribute("order", order);
        attributes.addFlashAttribute("orderId", order.getId());
        attributes.addFlashAttribute("tableNumber",
                order.getRestaurantTable().getNumber());
        attributes.addFlashAttribute("estimatedTime",
                random.nextInt(21) + 20); //Tiempo aleatorio entre 20 y 40 min
        return "redirect:/order-success";
    }
}
