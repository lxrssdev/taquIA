package org.lxrssdev.taquia.app.controllers;

import jakarta.servlet.http.HttpSession;
import org.lxrssdev.taquia.app.services.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart/items")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/{id}/increase")
    public ResponseEntity<Void> increaseQuantity(@PathVariable Long id, HttpSession session){
        cartService.increaseQuantity(id, session);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/decrease")
    public ResponseEntity<Void> decreaseQuantity(@PathVariable Long id, HttpSession session){
        cartService.decreaseQuantity(id, session);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeItem(@PathVariable Long id, HttpSession session){
        cartService.removeItem(id, session);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/observations")
    public ResponseEntity<Void> updateObservation(@PathVariable Long id,
                                                  @RequestParam String observation,
                                                  HttpSession session){
        cartService.updateObservation(id, observation, session);
        return ResponseEntity.ok().build();
    }
}
