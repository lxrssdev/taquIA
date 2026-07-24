package org.lxrssdev.taquia.app.repositories;

import org.lxrssdev.taquia.app.entities.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Long> {
    Optional<RestaurantTable> findByNumber(Integer tableNumber);
    List<RestaurantTable> findByAvailable(boolean available);
}
