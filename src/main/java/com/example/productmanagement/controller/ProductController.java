package com.example.productmanagement.controller;

import com.example.productmanagement.model.Product;
import com.example.productmanagement.repository.IProductRepository;
import com.example.productmanagement.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private IProductService productService;

    @Autowired
    private IProductRepository productRepository;
    @GetMapping
    public ModelAndView listProduct(){
        ModelAndView modelAndView = new ModelAndView("/product/list");
        Iterable<Product> products = productService.findAll();
        modelAndView.addObject("products", products);
        return modelAndView;
    }
    @GetMapping("/create")
    public ModelAndView showCreate(){
        ModelAndView modelAndView = new ModelAndView("/product/create");
        modelAndView.addObject("products", new Product());
        return modelAndView;
    }

    @PostMapping("/create")
    public String saveProduct(@ModelAttribute("product") Product product){
        productService.save(product);
        return "redirect:/products";
    }
    @GetMapping("/{id}/update")
    public ModelAndView showEdit(@PathVariable Long id){
        Optional<Product> productOptional = productService.findById(id);
        if (productOptional.isPresent()){
            ModelAndView modelAndView = new ModelAndView("/product/update");
            modelAndView.addObject("products", productOptional);
            return modelAndView;
        }else {
            return new ModelAndView("/error");
        }
    }
    @PostMapping("/update")
    public String editProduct(@ModelAttribute("product") Product product){
        productService.save(product);
        return "redirect:/products";
    }

    @PostMapping("/{id}/delete")
    public String deleteProduct(@ModelAttribute Product product){
        productService.remove(product.getId());
        return "redirect:/products";
    }
}
