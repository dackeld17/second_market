package com.dcu.demo.Product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class ProductController {
    private final ProductService productService;
    private final FileService fileService;

    @GetMapping("/product-list")
    String productList(String keyword, Model model){
        List<ProductDTO> products = productService.productSearch(keyword);
        model.addAttribute("products",products);
        return "/product/productList";

    }

    //상품목록 페이지
    @GetMapping({"/","/productList"})
    String productsList(Model model) {
        List<ProductDTO> products= productService.productFindAll();
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

    @GetMapping("/product-registration")
    public String productRegister(){
        return "product/product-registration";
    }


    @PostMapping("/product-registration")
    public String productRegister(@ModelAttribute ProductCreateDto productCreateDto) throws IOException {
        String imagePath = fileService.imageSave(productCreateDto.getImage());
        productService.productSave(productCreateDto, imagePath);
        return "redirect:/productList";
    }



    @GetMapping("/productDetail/{id}")
    public String productDetail(@PathVariable Long id, Model model){
        Optional<ProductDTO> product = productService.productFindById(id); //Optional 없는 값 예외처리(에러방지)
        if(product.isPresent()){//안에 있을때만
            System.out.println(product.get().getCreateAt());
            model.addAttribute("product", product.get());
            return  "product/productDetail";
        }else{
            return "redirect:/product/productList";
        }
    }

    @GetMapping("/productEdit/{id}")
    public String productEdit(@PathVariable Long id, Model model){
        Optional<ProductDTO> product = productService.productFindById(id);
        if(product.isPresent()){
            product.get().setCreateAt(LocalDateTime.now());
            model.addAttribute("product",product.get());
            return  "product/productEdit";
        }else{
            return "redirect:/product/productList";
        }
    }

    @PostMapping("/productEdit")
    public String productEdit(@ModelAttribute ProductUpdateDTO productUpdateDTO) {
        //jpa를 통해 데이터베이스에 저장
        productService.productUpdate(productUpdateDTO);
        //저장후 상품목록 페이지로 이동
        return "redirect:/productDetail/"+ productUpdateDTO.getId();

    }
    @PostMapping("/productDelete")
    String productDelete(@ModelAttribute ProductDeleteDTO productDeleteDTO){
            productService.productDelete(productDeleteDTO);
            return "redirect:/productList";
    }



}
