package com.motorider.motobase.entity;

import com.motorider.motobase.enums.Status;
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

    private String model;

    private String year;

    private List<Record> records;

    private Status status;
}
