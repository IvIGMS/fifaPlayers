package com.ivanfrias.fifaPlayers.Services;

import com.ivanfrias.fifaPlayers.Models.DTO.Filters;
import com.ivanfrias.fifaPlayers.Models.DTO.PlayerBasicDTO;
import com.ivanfrias.fifaPlayers.Models.enums.GroupByEnum;
import com.ivanfrias.fifaPlayers.Models.DTO.PlayersGroupDTO;
import com.ivanfrias.fifaPlayers.Models.Entity.Player;

import java.util.List;

public interface PlayerService {
    List<PlayerBasicDTO> getPlayers(Filters filters);
    Player getPlayerById(Long id);
    PlayersGroupDTO countPlayersGroup(GroupByEnum groupBy, Filters filters);
}
