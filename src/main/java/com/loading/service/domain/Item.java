package com.loading.service.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "items")
@Getter
@Setter
@ToString
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "^[A-Za-z0-9_-]+$", message = "Invalid name; allowed letters, numbers, hyphen and underscore")
    @NotBlank
    private String name;

    @Min(1)
    private int weight;

    @Pattern(regexp = "^[A-Z0-9_]+$", message = "Invalid code; allowed uppercase letters, digits and underscore")
    @NotBlank
    @Column(unique = true)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "box_txref")
    private Box box;

}
