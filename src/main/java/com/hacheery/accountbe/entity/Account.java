package com.hacheery.accountbe.entity;

import com.hacheery.accountbe.converter.JsonListConverter;
import com.hacheery.accountbe.converter.JsonMapConverter;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Account extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 500)
    private String url;

    @Column(name = "platform_icon", nullable = false, length = 100)
    private String platformIcon;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = JsonListConverter.class)
    private List<String> tags;

    @Column(name = "login_details", columnDefinition = "TEXT")
    @Convert(converter = JsonMapConverter.class)
    private Map<String, Object> loginDetails;

    @Column(name = "last_updated")
    private LocalDate lastUpdated;
}
