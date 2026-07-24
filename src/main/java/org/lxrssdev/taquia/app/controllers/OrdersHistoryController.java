package org.lxrssdev.taquia.app.controllers;


import lombok.AllArgsConstructor;
import org.lxrssdev.taquia.app.repositories.OrderRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class OrdersHistoryController {

    private final OrderRepository orderRepository;

    @GetMapping("/orders-history")
    public String showHistory(Model model){
        model.addAttribute("historyOrders", orderRepository.findAll());
        return "orders-history";
    }

}
