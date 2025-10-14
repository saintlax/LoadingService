package com.loading.service.dto;
import com.loading.service.domain.Item;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ItemDTO {
    @Pattern(regexp = "^[A-Za-z0-9_-]+$")
    @NotBlank
    private String name;

    @Min(1)
    private int weight;

    @Pattern(regexp = "^[A-Z0-9_]+$")
    @NotBlank
    private String code;

    public static ItemDTO from(Item i) {
        ItemDTO dto = new ItemDTO();
        dto.setName(i.getName());
        dto.setCode(i.getCode());
        dto.setWeight(i.getWeight());
        return dto;
    }
}
