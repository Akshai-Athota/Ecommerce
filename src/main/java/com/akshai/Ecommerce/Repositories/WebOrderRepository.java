package com.akshai.Ecommerce.Repositories;

import com.akshai.Ecommerce.Models.LocalUser;
import com.akshai.Ecommerce.Models.WebOrder;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface WebOrderRepository extends ListCrudRepository<WebOrder,Long> {
    List<WebOrder> findByUser(LocalUser user);
}
