package com.ivanfrias.fifaPlayers.Models.DTO;

import com.ivanfrias.fifaPlayers.Models.enums.CountryEnum;
import com.ivanfrias.fifaPlayers.Models.enums.OrderByEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Filters {
    private List<CountryEnum> countries;
    private Limits averageLimits;
    private Limits ageLimits;
    private OrderByEnum orderBy;
}
