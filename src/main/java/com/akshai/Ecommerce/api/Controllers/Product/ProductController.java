package com.akshai.Ecommerce.api.Controllers.Product;

import com.akshai.Ecommerce.Models.Product;
import com.akshai.Ecommerce.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping
    public List<Product> getAllProdutcs(){
        return productService.getAllProducts();
    }
}
