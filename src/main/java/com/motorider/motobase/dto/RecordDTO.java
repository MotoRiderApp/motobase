package com.motorider.motobase.dto;

import com.motorider.motobase.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecordDTO {

    private String description;

    private Double currentKm;

    private Double value;

    private Date date;

    private Date notification;

    private String category;

    private LocationDTO location;

    private Status status;
}
