package com.example.storeeverythingv1.data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto
{
    private Long id;
    @NotEmpty
    @Size(min = 3, max = 20, message = "Imię powinno składać się z od 3 do 20 liter")
    private String firstName;
    @NotEmpty
    @Size(min = 3, max = 20, message = "Imię powinno składać się z od 3 do 20 liter")
    private String lastName;
    @NotEmpty(message = "Email nie powinien być pusty")
    @Email(message = "Niepoprawny adres e-mail")
    private String email;

    @Size(min = 5, message = "Hasło powinno składać się z conajmniej 5 znaków")
    private String password;
}