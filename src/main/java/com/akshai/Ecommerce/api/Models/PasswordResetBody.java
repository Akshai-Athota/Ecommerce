package com.akshai.Ecommerce.api.Models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class PasswordResetBody {

    @NotNull
    @NotBlank
    private String token;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$")
    private String password;

    public @NotNull @NotBlank String getToken() {
        return token;
    }

    public void setToken(@NotNull @NotBlank String token) {
        this.token = token;
    }

    public @NotNull @NotBlank @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$") String getPassword() {
        return password;
    }

    public void setPassword(@NotNull @NotBlank @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$") String password) {
        this.password = password;
    }


}
