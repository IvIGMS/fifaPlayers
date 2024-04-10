package com.ivanfrias.fifaPlayers.Models.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Limits {
    private BigDecimal min;
    private BigDecimal max;
}
