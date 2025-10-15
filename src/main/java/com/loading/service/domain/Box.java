package com.loading.service.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "boxes")
@Getter
@Setter
@ToString
public class Box {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    @Size(max = 20)
    @NotBlank
    private String txref;

    @Min(1)
    @Max(500)
    private int weightLimit;

    @Min(0)
    @Max(100)
    private int battery;

    @Enumerated(EnumType.STRING)
    private BoxStatus state;

    @Version
    private Long version;

}
