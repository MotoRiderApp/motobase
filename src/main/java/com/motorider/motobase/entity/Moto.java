package com.motorider.motobase.entity;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Moto {

    private String name;

    private Double km;

    private Double cc;

    private String brand;

    private List<Record> records;
}
