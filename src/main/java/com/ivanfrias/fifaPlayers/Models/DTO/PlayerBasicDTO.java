package com.ivanfrias.fifaPlayers.Models.DTO;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PlayerBasicDTO {
    private Long id;
    private String name;
    private LocalDate birthDate;
    private BigDecimal age;
    private String positions;
    private String nationality;
    private BigDecimal overallRating;
    private BigDecimal valueEuro;
    private String preferredFoot;


}
