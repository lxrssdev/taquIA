package org.lxrssdev.taquia.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartItemViewDTO{

    private Long productId;
    private String name;
    private BigDecimal price;
    private Integer quantity;
    private String observations;
    private BigDecimal subtotal;

}
