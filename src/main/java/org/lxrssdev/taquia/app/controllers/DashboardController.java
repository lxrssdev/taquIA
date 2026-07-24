package org.lxrssdev.taquia.app.controllers;

import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.lxrssdev.taquia.app.dto.StatsDTO;
import org.lxrssdev.taquia.app.entities.OrderStatus;
import org.lxrssdev.taquia.app.entities.Product;
import org.lxrssdev.taquia.app.repositories.OrderDetailRepository;
import org.lxrssdev.taquia.app.repositories.OrderRepository;
import org.lxrssdev.taquia.app.repositories.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Controller
@AllArgsConstructor
public class DashboardController {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;

    @GetMapping("/dashboard")
    public String showDashboard(Model model){

        //Stats
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);
        long ordersToday = orderRepository.findByCreatedAtBetween(startOfDay, endOfDay).size();
        StatsDTO stats = new StatsDTO(ordersToday,
                orderDetailRepository.sumTotalSales(),
                orderRepository.findByStatus(OrderStatus.PENDING).size(),
                orderRepository.findByStatus(OrderStatus.PREPARING).size(),
                orderRepository.findByStatus(OrderStatus.READY).size()

        );
        model.addAttribute("stats", stats);
        //Prducts
        model.addAttribute("products", productRepository.findAll());
        return "dashboard";
    }

    @PostMapping("/admin/products")
    public ResponseEntity<Product> createProduct(@RequestBody Product product, Model model){
        productRepository.save(product);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/admin/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id,
                                                 @RequestBody Product product){
        Product productToEdit = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El producto no existe!"));
        productToEdit.setName(product.getName());
        //Convert price to BigDecimal
        BigDecimal price = new BigDecimal(String.valueOf(product.getPrice()));
        productToEdit.setPrice(price);
        productToEdit.setAvailable(product.isAvailable());
        productRepository.save(productToEdit);
        return ResponseEntity.ok(productToEdit);
    }

    @DeleteMapping("/admin/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El producto no existe!"));
        productRepository.delete(product);
        return ResponseEntity.ok("Producto eliminado correctamente!");
    }






}
