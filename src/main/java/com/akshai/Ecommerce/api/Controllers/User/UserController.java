package com.akshai.Ecommerce.api.Controllers.User;

import com.akshai.Ecommerce.Models.Address;
import com.akshai.Ecommerce.Models.LocalUser;
import com.akshai.Ecommerce.Repositories.AddressRepository;
import com.akshai.Ecommerce.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private AddressRepository addressRepository;
    
    @Autowired
    private UserService userService;

    @GetMapping("/{userId}/address")
    public ResponseEntity<List<Address>> getUserAddress(@PathVariable Long userId, @AuthenticationPrincipal LocalUser user){
        if(userService.userHasPermission(user,userId)){
            return ResponseEntity.ok(addressRepository.findByUser_Id(userId));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PutMapping("/{userId}/address")
    public  ResponseEntity<Address> addAddress(@AuthenticationPrincipal LocalUser user,@PathVariable Long userId,
                                               @RequestBody Address address){
        if(!userService.userHasPermission(user,userId)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        address.setId(null);
        LocalUser refUser = new LocalUser();
        refUser.setId(userId);
        address.setUser(refUser);
        return  ResponseEntity.ok(addressRepository.save(address));
    }

    @PatchMapping("/{userId}/address/{addressId}")
    public ResponseEntity<Address> patchAddress(
            @AuthenticationPrincipal LocalUser user, @PathVariable Long userId,
            @PathVariable Long addressId, @RequestBody Address address) {
        if (!userService.userHasPermission(user, userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (address.getId() == addressId) {
            Optional<Address> opOriginalAddress = addressRepository.findById(addressId);
            if (opOriginalAddress.isPresent()) {
                LocalUser originalUser = opOriginalAddress.get().getUser();
                if (originalUser.getId() == userId) {
                    address.setUser(originalUser);
                    return ResponseEntity.ok(addressRepository.save(address));
                }
            }
        }
        return ResponseEntity.badRequest().build();
    }

    
}
