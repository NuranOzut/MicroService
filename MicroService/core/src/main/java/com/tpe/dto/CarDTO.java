package com.tpe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CarDTO {

    private Long id;

    private String brand;

    private String model;

    private Integer doors;

    private Double pricePerHour;

    private Integer age;

}
