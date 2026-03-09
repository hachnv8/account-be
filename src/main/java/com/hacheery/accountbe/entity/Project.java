package com.hacheery.accountbe.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "projects")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Project extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "project_tech_stacks", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "tech_stack")
    private List<String> techStack;

    @Column(length = 50)
    private String status;

    @Column(length = 100)
    private String category;

    @Column(name = "repo_url")
    private String repoUrl;

    @Column(name = "prod_url")
    private String prodUrl;

    @Column(name = "count", columnDefinition = "integer default 0")
    private Integer count = 0;

    @Column(name = "last_updated")
    private LocalDate lastUpdated;
}
