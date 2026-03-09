package com.hacheery.accountbe.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProjectRequestDTO {
    private String name;
    private String description;
    private List<String> techStack;
    private String status;
    private String category;
    private String repoUrl;
    private String prodUrl;
}
