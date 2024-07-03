package com.akshai.Ecommerce.Repositories;

import com.akshai.Ecommerce.Models.LocalUser;
import com.akshai.Ecommerce.Models.VerificationToken;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface VerificationTokenRepository extends ListCrudRepository<VerificationToken,Long> {

    Optional<VerificationToken> findByToken(String token);

    void deleteByUser(LocalUser user);
}
