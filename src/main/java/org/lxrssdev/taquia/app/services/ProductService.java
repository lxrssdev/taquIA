package org.lxrssdev.taquia.app.services;


import org.lxrssdev.taquia.app.entities.Product;
import org.lxrssdev.taquia.app.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ProductService{

    private final ProductRepository productRepository;


    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    //Obtener todos los productos
    public List<Product> findAllProducts(){
        return productRepository.findAll();
    }

    public Product findById(Long id){
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El producto no existe!"));
    }

    public void save(Product product){
        productRepository.save(product);
    }

    public void updateProduct(Long id, Product product){
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontro producto con ese id!"));

        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setAvailable(product.isAvailable());

        productRepository.save(existingProduct);
    }

    public void delete(Long id){
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Producto inexistente!"));
        productRepository.delete(product);
    }

    public void markAsUnavailable(Long id){
        Optional<Product> productoOpt = productRepository.findById(id);
        productoOpt.ifPresent(product -> product.setAvailable(false));
        productoOpt.ifPresent(productRepository::save);
    }


}
