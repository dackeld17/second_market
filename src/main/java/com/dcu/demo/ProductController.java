package com.dcu.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller

public class ProductController {
    private final ProductRepository productRepository;

    @GetMapping("/productList")
    String productsList(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products",products);
        return "productList";



    }
    @GetMapping("/productRegister")
    String productRegister(Model model){
        return "productRegistration";
    }

    @PostMapping("/productRegister")
    String productRegister(@ModelAttribute Product product) {
        System.out.println(product.getImage());
        System.out.println(product.getTitle());
        System.out.println(product.getCompany());
        System.out.println(product.getPrice());
        System.out.println(product.getRelease_date());

        //jpa를 통해 데이터베이스에 저장
        productRepository.save(product);
        //저장후 상품목록 페이지로 이동
        return "redirect:/productList";

    }

    @GetMapping("/productDetail/{id}")
    String productDetail(@PathVariable Long id, Model model){
        Optional<Product> product = productRepository.findById(id);     //Optional 없는 값 예외처리(에러방지)

        model.addAttribute("product",product.get());
        if(product.isPresent()){//안에 있을때만
            return  "productDetail";
        }else{
            return "redirect:/productList";
        }


    }

}
