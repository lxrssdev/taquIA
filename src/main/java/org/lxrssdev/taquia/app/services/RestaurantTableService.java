package org.lxrssdev.taquia.app.services;


import lombok.AllArgsConstructor;
import org.lxrssdev.taquia.app.entities.RestaurantTable;
import org.lxrssdev.taquia.app.repositories.RestaurantTableRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RestaurantTableService {

    private final RestaurantTableRepository restaurantTableRepository;

    public List<RestaurantTable> findAll(){
        return restaurantTableRepository.findAll();
    }

    public RestaurantTable findById(Long id){
        return restaurantTableRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mesa inexistente!"));
    }

    public RestaurantTable findByTableNumber(Integer tableNumber){
        return restaurantTableRepository.findByNumber(tableNumber)
                .orElseThrow(() -> new RuntimeException("Mesa inexistente!"));
    }

    public RestaurantTable save(RestaurantTable table){
        return restaurantTableRepository.save(table);
    }


}
