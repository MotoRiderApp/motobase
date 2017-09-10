package com.motorider.motobase.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Record {

    private String description;

    private Double currentKm;

    private Double value;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date date;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date notification;

    private String category;
}
