package com.dcu.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;


     List<Product> productFindAll() {
        return productRepository.findAll();
    }

    //상품 등록 메서드
    void productSave(Product product) {
        productRepository.save(product);
    }

    //특정 ID 상태 조회
    Optional<Product> productFindById(Long id) {
        return productRepository.findById(id);
    }

    //특정 ID 상품 삭제
    void productDelete(Long id){
         productRepository.deleteById(id);
    }
}
