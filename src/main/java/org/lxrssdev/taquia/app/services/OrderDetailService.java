package org.lxrssdev.taquia.app.services;


import lombok.AllArgsConstructor;
import org.lxrssdev.taquia.app.entities.OrderDetail;
import org.lxrssdev.taquia.app.repositories.OrderDetailRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;

    public List<OrderDetail> getOrderDetails(Long orderId){
        return orderDetailRepository.findByOrderId(orderId);
    }

}
