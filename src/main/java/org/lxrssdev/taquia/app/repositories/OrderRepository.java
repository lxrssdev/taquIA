package org.lxrssdev.taquia.app.repositories;

import org.lxrssdev.taquia.app.entities.Order;
import org.lxrssdev.taquia.app.entities.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByStatus(OrderStatus status);

}
