package com.hacheery.accountbe.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Task extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String taskdesc;

    // This field appears in the frontend as "task" (maybe a short label or type)
    private String task;

    // upcoming, inprogress, completed
    private String status;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String attachments;
}
