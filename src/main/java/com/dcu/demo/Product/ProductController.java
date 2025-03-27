package com.dcu.demo.Product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller

public class ProductController {
    private final ProductService productService;

    //상품목록 페이지
    @GetMapping({"/","/productList"})
    String productsList(Model model) {
        List<Product> products= productService.productFindAll();
        model.addAttribute("products",products);
        return "product/productList";
    }
    //상품등록페이지
    @GetMapping("/productRegister")
    String productRegister(Model model){
        return "product/productRegistration";
    }

    @PostMapping("/productRegister")
    String productRegister(@ModelAttribute Product product) {
        //jpa를 통해 데이터베이스에 저장
        productService.productSave(product);
        //저장후 상품목록 페이지로 이동
        return "redirect:/product/productList";

    }

    @GetMapping("/productDetail/{id}")
    String productDetail(@PathVariable Long id, Model model){
        Optional<Product> product = productService.productFindById(id); //Optional 없는 값 예외처리(에러방지)
        if(product.isPresent()){//안에 있을때만
            model.addAttribute("product",product.get());
            return  "product/productDetail";
        }else{
            return "redirect:/product/productList";
        }


    }

    @GetMapping("/productEdit/{id}")
    String productEdit(@PathVariable Long id, Model model){
        Optional<Product> product = productService.productFindById(id);
        if(product.isPresent()){
            model.addAttribute("product",product.get());
            return  "product/productEdit";
        }else{
            return "redirect:/product/productList";
        }
    }
    @PostMapping("/productEdit")
    String productEdit(@ModelAttribute Product product) {
        //jpa를 통해 데이터베이스에 저장
        productService.productSave(product);
        //저장후 상품목록 페이지로 이동
        return "redirect:/product/productList";

    }
    @PostMapping("/productDelete")
    String productDelete(@ModelAttribute Product product){
            productService.productDelete(product.getId());
            return "redirect:/product/productList";
    }

}
