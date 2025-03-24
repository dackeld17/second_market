package com.dcu.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {
    @GetMapping("/productList")
    String productsList(Model model) {
        model.addAttribute("phone1","Galaxy s25 ultra");
        model.addAttribute("price1","3,000,000원");
        model.addAttribute("phone2","Galaxy s25 +");
        model.addAttribute("price2","2,000,000원");
        model.addAttribute("phone3","Galaxy s25");
        model.addAttribute("price3","1,000,000원");

        return "./productList";
    }
}
