package com.example.productmanagement.controller;

import com.example.productmanagement.model.Category;
import com.example.productmanagement.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;
    @GetMapping("/categories")
    public ModelAndView listCategory(){
        ModelAndView modelAndView = new ModelAndView("/category/list");
        Iterable<Category> categories = categoryService.findAll();
        modelAndView.addObject("categories", categories);
        return modelAndView;
    }

    @GetMapping("/create-cate")
    public ModelAndView showCreate(){
        ModelAndView modelAndView = new ModelAndView("/category/create");
        modelAndView.addObject("category", new Category());
        return modelAndView;
    }

    @PostMapping("/create-cate")
    public String save(@ModelAttribute("category") Category category){
        categoryService.save(category);
        return "redirect:/categories";
    }

    @GetMapping("/edit-cate/{id}")
    public ModelAndView showEdit(@PathVariable Long id){
        Optional<Category> categoryOptional = categoryService.findById(id);
        if (categoryOptional.isPresent()){
            ModelAndView modelAndView = new ModelAndView("/category/update");
            modelAndView.addObject("category", categoryOptional.get());
            return modelAndView;
        }else {
            return new ModelAndView("/error");
        }
    }
    @PostMapping("/edit-cate")
    public String editCategory(@ModelAttribute("category") Category category){
        categoryService.save(category);
        return "redirect:/categories";
    }

}
