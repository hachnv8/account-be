package com.hacheery.accountbe.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

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

    @Column(name = "count", columnDefinition = "integer default 0")
    private Integer count = 0;

    @Column(name = "last_updated")
    private LocalDate lastUpdated;
}
