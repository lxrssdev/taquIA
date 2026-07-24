package org.lxrssdev.taquia.app.repositories;

import org.lxrssdev.taquia.app.entities.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findByOrderId(Long id);

    @Query("SELECT COALESCE(SUM(d.quantity * d.product.price), 0) FROM OrderDetail d")
    BigDecimal sumTotalSales();

}
