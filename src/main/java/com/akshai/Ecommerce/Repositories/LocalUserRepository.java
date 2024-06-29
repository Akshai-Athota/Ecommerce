package com.akshai.Ecommerce.Repositories;

import com.akshai.Ecommerce.Models.LocalUser;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface LocalUserRepository extends ListCrudRepository<LocalUser,Long> {

    Optional<LocalUser> findByUsernameIgnoreCase(String username);
    Optional<LocalUser> findByEmailIgnoreCase(String email);
}
