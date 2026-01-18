package com.textonly.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Gift {
    private String name;
    private String iconResourceName;
    private Integer price;
}
