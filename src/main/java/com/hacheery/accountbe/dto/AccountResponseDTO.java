package com.hacheery.accountbe.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
public class AccountResponseDTO {
    private Long id;
    private Long projectId;
    private String name;
    private String url;
    private String platformIcon;
    private List<String> tags;
    private Map<String, Object> loginDetails;
    private LocalDate lastUpdated;
}
