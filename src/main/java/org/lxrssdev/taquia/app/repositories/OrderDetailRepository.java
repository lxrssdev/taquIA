package org.lxrssdev.taquia.app.repositories;

import org.lxrssdev.taquia.app.entities.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}
