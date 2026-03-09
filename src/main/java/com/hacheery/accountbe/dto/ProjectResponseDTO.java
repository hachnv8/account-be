package com.hacheery.accountbe.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class ProjectResponseDTO {
    private Long id;
    private String name;
    private Integer count;
    private LocalDate lastUpdated;
    private String description;
    private List<String> techStack;
    private String status;
    private String category;
    private String repoUrl;
    private String prodUrl;
}
