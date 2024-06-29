package com.akshai.Ecommerce.api.Controllers.Order;

import com.akshai.Ecommerce.Models.LocalUser;
import com.akshai.Ecommerce.Models.WebOrder;
import com.akshai.Ecommerce.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping
    public List<WebOrder> getAllUserOrders(@AuthenticationPrincipal LocalUser user){
        return orderService.getAllUserOrders(user);
    }
}
