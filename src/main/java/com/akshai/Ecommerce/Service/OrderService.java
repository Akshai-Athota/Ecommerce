package com.akshai.Ecommerce.Service;

import com.akshai.Ecommerce.Models.LocalUser;
import com.akshai.Ecommerce.Models.WebOrder;
import com.akshai.Ecommerce.Repositories.WebOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    WebOrderRepository webOrderRepository;

    public List<WebOrder> getAllUserOrders(LocalUser user){
        return webOrderRepository.findByUser(user);
    }
}
