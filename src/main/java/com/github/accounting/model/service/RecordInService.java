package com.github.accounting.model.service;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecordInService {
    private Long id;
    private Long userId;
    private BigDecimal amount;
    private String note;
    private List<TagInService> tagList;
    private String category;
}
