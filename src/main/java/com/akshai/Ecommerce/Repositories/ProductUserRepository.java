package com.akshai.Ecommerce.Repositories;

import com.akshai.Ecommerce.Models.ProductUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductUserRepository extends CrudRepository<ProductUser,Long> {

    Optional<ProductUser> findByUsernameIgnoreCase(String username);
    Optional<ProductUser> findByEmailIgnoreCase(String email);
}
