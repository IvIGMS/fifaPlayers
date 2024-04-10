package com.ivanfrias.fifaPlayers.Models.DTO;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PlayersGroupDTO {
    private String label;
    private List<GroupByDTO> columns;
}
