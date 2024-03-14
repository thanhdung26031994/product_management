package com.example.productmanagement.controller;

import com.example.productmanagement.dto.ProductFormDTO;
import com.example.productmanagement.model.Category;
import com.example.productmanagement.model.Product;
import com.example.productmanagement.repository.IProductRepository;
import com.example.productmanagement.service.ICategoryService;
import com.example.productmanagement.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {

//    @Value("${upload}")
//    private String upload;

    @Autowired
    private IProductService productService;
    @Autowired
    private ICategoryService categoryService;

    @ModelAttribute("categories")
    public Iterable<Category> listCategory(){
        return categoryService.findAll();
    }

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
    public String saveProduct(@ModelAttribute Product product){
//        MultipartFile file = productFormDTO.getImageAt();
//        String fileName = file.getOriginalFilename();
//        try {
//            FileCopyUtils.copy(file.getBytes(), new File(upload + fileName));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String fileName = file.getOriginalFilename();
//        try{
//            String uploadFile = "D:/image/";
//            Path uploadPath = Paths.get(uploadFile);
//            if (!Files.exists(uploadPath)){
//                Files.createDirectories(uploadPath);
//            }
//            try (InputStream inputStream = file.getInputStream()){
//                Files.copy(inputStream, Paths.get(uploadFile + fileName), StandardCopyOption.REPLACE_EXISTING);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Product product = new Product();
//        if (product.getId() == null){
//            product.setId(productFormDTO.getId());
//        }
//        product.setName(productFormDTO.getName());
//        product.setPrice(productFormDTO.getPrice());
//        product.setImageAt(fileName);
//        product.setCategory(productFormDTO.getCategory());
        productService.save(product);
        return "redirect:/products";
    }
    @GetMapping("/{id}/update")
    public ModelAndView showEdit(@PathVariable Long id){
        Optional<Product> productOptional = productService.findById(id);
        if (productOptional.isPresent()){
            ModelAndView modelAndView = new ModelAndView("/product/update");
            modelAndView.addObject("products", productOptional.get());
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

    @GetMapping("/{id}/delete")
    public String deleteProduct(@PathVariable Long id){
        productService.remove(id);
        return "redirect:/products";
    }
}
