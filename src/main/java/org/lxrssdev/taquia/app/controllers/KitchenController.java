package org.lxrssdev.taquia.app.controllers;


import lombok.AllArgsConstructor;
import org.lxrssdev.taquia.app.services.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class KitchenController {

    private final OrderService orderService;

    @GetMapping("/kitchen")
    public String showKitchen(Model model){
        model.addAttribute("orders", orderService.findPendingOrders());
        return "kitchen";
    }
}
