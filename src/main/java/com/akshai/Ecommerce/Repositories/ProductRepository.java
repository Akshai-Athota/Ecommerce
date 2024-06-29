package com.akshai.Ecommerce.Repositories;

import com.akshai.Ecommerce.Models.Product;
import org.springframework.data.repository.ListCrudRepository;

public interface ProductRepository extends ListCrudRepository<Product,Long> {

}
