package com.github.accounting.model.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecordInPersistence {
    private Long id;
    private Long userId;
    private BigDecimal amount;
    private String note;
    private List<TagInPersistence> tagList;
    private Integer category;
    //0 -> disable, 1 -> enable.
    private Integer status;
    private LocalDate createTime;
    private LocalDate updateTime;
}
