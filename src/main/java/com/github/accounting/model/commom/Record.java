package com.github.accounting.model.commom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Record {
    private Long id;
    private Long userId;
    private BigDecimal amount;
    private String status;
    private String note;
    private List<Tag> tagList;
    private String category;
}
