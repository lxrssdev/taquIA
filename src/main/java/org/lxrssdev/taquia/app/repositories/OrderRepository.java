package org.lxrssdev.taquia.app.repositories;

import org.lxrssdev.taquia.app.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
