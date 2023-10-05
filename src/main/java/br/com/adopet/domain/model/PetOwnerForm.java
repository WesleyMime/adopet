package br.com.adopet.domain.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PetOwnerForm {

    @NotBlank
    private String name;
    @Email
    private String email;
    @NotBlank
    private String password;
}
