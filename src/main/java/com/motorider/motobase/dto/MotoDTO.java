package com.motorider.motobase.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MotoDTO {

    private String name;

    private String brand;

    private Double km;

    private Double cc;

}
