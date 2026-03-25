package com.hacheery.accountbe.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "notes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Note extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @Column(name = "project_name")
    private String projectName;

    private String type;

    private String priority;

    private String status;

    @Column(name = "task_id")
    private Long taskId;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String attachments;
}
