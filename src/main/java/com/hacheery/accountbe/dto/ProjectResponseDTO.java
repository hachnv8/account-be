package com.hacheery.accountbe.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ProjectResponseDTO {
    private Long id;
    private String name;
    private Integer count;
    private LocalDate lastUpdated;
}
