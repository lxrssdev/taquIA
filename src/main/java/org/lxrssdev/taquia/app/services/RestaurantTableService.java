package org.lxrssdev.taquia.app.services;


import lombok.AllArgsConstructor;
import org.lxrssdev.taquia.app.entities.RestaurantTable;
import org.lxrssdev.taquia.app.repositories.RestaurantTableRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<RestaurantTable> findByIsAvailable(boolean available){
        return restaurantTableRepository.findByAvailable(available);
    }


    public RestaurantTable save(RestaurantTable table){
        return restaurantTableRepository.save(table);
    }


}
