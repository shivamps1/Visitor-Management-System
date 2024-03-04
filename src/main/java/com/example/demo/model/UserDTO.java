package com.example.demo.model;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    @Size(max = 255)
    private String email;

    @Size(max = 255)
    private String phone;

    @NotNull
    @Size(max = 255)
    private String role;

    @NotNull
    private UserStatus status;

    private Long flat;

    private Long address;

}
