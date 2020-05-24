package com.github.accounting.model.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagInPersistence {
    private Long id;
    private Long userId;
    private String description;
    //0 -> disable, 1 -> enable
    private Integer status;
    private LocalDate createTime;
    private LocalDate updateTime;
}
