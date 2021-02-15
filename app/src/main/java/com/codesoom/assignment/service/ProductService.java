package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.exception.ProductNotFoundException;
import com.codesoom.assignment.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);

        return product.orElseThrow(() ->
                new ProductNotFoundException("존재하지 않는 상품 id가 주어졌으므로 상품을 찾을 수 없습니다. 문제의 id = " + id));
    }

    @Transactional
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public Product updateProduct(Long id, Product newProduct) {
        Product product = productRepository.findById(id).orElseThrow(() ->
                new ProductNotFoundException("존재하지 않는 상품 id가 주어졌으므로 상품을 수정할 수 없습니다. 문제의 id = " + id));

        product.update(newProduct);

        return product;
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() ->
                new ProductNotFoundException("존재하지 않는 상품 id가 주어졌으므로 상품을 삭제할 수 없습니다. 문제의 id = " + id));

        productRepository.delete(product);
    }

}
