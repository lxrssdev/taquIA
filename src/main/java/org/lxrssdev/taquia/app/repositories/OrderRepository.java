package org.lxrssdev.taquia.app.repositories;

import org.lxrssdev.taquia.app.entities.Order;
import org.lxrssdev.taquia.app.entities.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByStatus(OrderStatus status);
    List<Order> findByCreatedAt(LocalDateTime date);
    List<Order> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);




}
