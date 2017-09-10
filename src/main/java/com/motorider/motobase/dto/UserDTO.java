package com.motorider.motobase.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String idUser;

    private String idFacebook;

    private String name;

    private String email;

}
