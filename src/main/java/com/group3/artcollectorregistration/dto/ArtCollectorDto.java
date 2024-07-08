package com.group3.artcollectorregistration.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtCollectorDto {


    private Long id;

    @NotEmpty
    private String username;

    @NotEmpty(message = "Email required")
    private String email;

    @NotEmpty(message = "Password required")
    private String password;

    public boolean isAdminRegistration() {
        return email.endsWith(("@admin.com"));
    }
}
