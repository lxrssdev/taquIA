package org.lxrssdev.taquia.app.services;

import jakarta.servlet.http.HttpSession;
import org.lxrssdev.taquia.app.dto.CartItemDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    private static final String CART_SESSION = "cart";

    public List<CartItemDTO> getCart(HttpSession session){
        List<CartItemDTO> cart = (List<CartItemDTO>) session.getAttribute("cart");
        if(cart == null){
            cart = new ArrayList<>();
            session.setAttribute(CART_SESSION, cart);
        }
        return cart;

    }

    public void save(List<CartItemDTO> cartItems, HttpSession session){
        session.setAttribute(CART_SESSION, cartItems);
    }

    public void removeItem(Long productId, HttpSession session){
        List<CartItemDTO> cart = getCart(session);
        cart.removeIf(p -> p.getProductId().equals(productId));
        session.setAttribute(CART_SESSION, cart);
    }

    public void updateObservation(Long productId, String observation, HttpSession session){
        List<CartItemDTO> cart = getCart(session);
        for(CartItemDTO item: cart){
            if(item.getProductId().equals(productId)){
                item.setObservations(observation);
                break;
            }
        }
        session.setAttribute(CART_SESSION, cart);
    }

    public void increaseQuantity(Long productId, HttpSession session){
        List<CartItemDTO> cart = getCart(session);
        for(CartItemDTO item : cart){
            if(item.getProductId().equals(productId)){
                item.setQuantity(item.getQuantity() + 1);
                break;
            }
        }
        session.setAttribute(CART_SESSION, cart);

    }

    public void decreaseQuantity(Long productId, HttpSession session){
        List<CartItemDTO> cart = getCart(session);
        CartItemDTO itemToRemove = null;
        for(CartItemDTO item : cart){
            if(item.getProductId().equals(productId)){
                item.setQuantity(item.getQuantity() - 1);

                if(item.getQuantity() <= 0){
                    itemToRemove = item;
                }
                break;
            }
        }
        if(itemToRemove != null){
            cart.remove(itemToRemove);
        }
        session.setAttribute(CART_SESSION, cart);
    }

    public void clear(HttpSession session){
        session.removeAttribute(CART_SESSION);
    }

}
