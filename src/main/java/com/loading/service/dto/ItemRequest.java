package com.loading.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@ToString
public class ItemRequest {

    @NotEmpty
    private List<@Valid ItemDTO> items;
}
