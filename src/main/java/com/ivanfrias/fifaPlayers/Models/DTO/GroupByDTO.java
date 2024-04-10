package com.ivanfrias.fifaPlayers.Models.DTO;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class GroupByDTO {
    private final String groupBy;
    private final BigDecimal amount;
}
