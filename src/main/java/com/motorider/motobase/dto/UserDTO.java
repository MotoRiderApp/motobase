package com.motorider.motobase.dto;

import com.motorider.motobase.enums.TypeAccount;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String idUser;

    private String idSocialNetwork;

    private TypeAccount typeAccount;

    private String name;

    private String email;

}
