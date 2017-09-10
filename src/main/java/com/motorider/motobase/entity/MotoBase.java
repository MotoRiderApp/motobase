package com.motorider.motobase.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "motobase")
public class MotoBase {

    @Id
    private String id;

    private String idUser;

    private String idFacebook;

    private String name;

    private String email;

    private List<Moto> motos;






}
