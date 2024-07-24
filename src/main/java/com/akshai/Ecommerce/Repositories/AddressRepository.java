package com.akshai.Ecommerce.Repositories;

import com.akshai.Ecommerce.Models.Address;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends ListCrudRepository<Address,Long> {

    List<Address> findByUser_Id(Long id);
}
