package com.loading.service.dto;
import com.loading.service.domain.Status;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class BoxRequest {

    @NotBlank
    @Size(max = 20)
    private String txref;

    @Min(1)
    @Max(500)
    private int weightLimit;

    @Min(0)
    @Max(100)
    private int battery;

    private Status status;
}
