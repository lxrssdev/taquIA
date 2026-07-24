package org.lxrssdev.taquia.app.controllers;

import jakarta.servlet.http.HttpSession;
import org.lxrssdev.taquia.app.dto.CartItemDTO;
import org.lxrssdev.taquia.app.dto.CartItemViewDTO;
import org.lxrssdev.taquia.app.entities.Product;
import org.lxrssdev.taquia.app.repositories.ProductRepository;
import org.lxrssdev.taquia.app.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Controller
public class HomeController {

    private final ProductService productService;

    public HomeController(ProductRepository productRepository, ProductService productService) {
        this.productService = productService;
    }

    @GetMapping({"/", "/menu"})
    public String showHome(Model model){
        model.addAttribute("title", "TaquIA | Menú");
        model.addAttribute("products", productService.findAllProducts());
        return "home";
    }

    @GetMapping("/order-details")
    public String showDetails(Model model, HttpSession session){
        model.addAttribute("title", "Detalles del pedido!");
        List<CartItemDTO> cart = (List<CartItemDTO>) session.getAttribute("cart");
        BigDecimal subTotal = BigDecimal.ZERO;
        BigDecimal totalVenta = BigDecimal.ZERO;
        if(cart == null){
            cart = new ArrayList<>();
        }
        List<CartItemViewDTO> cartView = new ArrayList<>();
        for(CartItemDTO item : cart){
            Product product = productService.findById(item.getProductId());
            CartItemViewDTO cartViewDto = new CartItemViewDTO();
            cartViewDto.setProductId(item.getProductId());
            cartViewDto.setName(product.getName());
            cartViewDto.setPrice(product.getPrice());
            cartViewDto.setQuantity(item.getQuantity());
            cartViewDto.setObservations(item.getObservations());
            System.out.printf("Observaciones: %s", item.getObservations());
            //calculamos el subtotal
            subTotal = product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            cartViewDto.setSubtotal(subTotal);
            cartView.add(cartViewDto);
            totalVenta = totalVenta.add(subTotal);
        }
        model.addAttribute("cart", cartView);
        model.addAttribute("total", totalVenta);
        return "order-detail";
    }

    //Save cart in session scope
    @PostMapping("/order-detail")
    public ResponseEntity<String> saveCart(@RequestBody List<CartItemDTO> cartItems,
                                             HttpSession session){
        session.setAttribute("cart", cartItems);
        System.out.println("carrito guardado en la sesion: " + cartItems);
        return ResponseEntity.ok().build();
    }

}
