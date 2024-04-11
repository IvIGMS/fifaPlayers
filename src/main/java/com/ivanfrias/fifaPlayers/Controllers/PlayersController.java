package com.ivanfrias.fifaPlayers.Controllers;

import com.ivanfrias.fifaPlayers.Models.DTO.Filters;
import com.ivanfrias.fifaPlayers.Models.DTO.PlayerBasicDTO;
import com.ivanfrias.fifaPlayers.Models.enums.GroupByEnum;
import com.ivanfrias.fifaPlayers.Models.DTO.PlayersGroupDTO;
import com.ivanfrias.fifaPlayers.Models.Entity.Player;
import com.ivanfrias.fifaPlayers.Services.PlayerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/players")
public class PlayersController {

    final PlayerService playerService;

    @GetMapping("/getplayer/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable Long id){
        return ResponseEntity.ok(playerService.getPlayerById(id));
    }

    @PostMapping("/getplayers")
    public ResponseEntity<List<PlayerBasicDTO>> getPlayers(@RequestBody Filters filters){
        return ResponseEntity.ok(playerService.getPlayers(filters));
    }

    @PostMapping("/getplayersgroup")
    public ResponseEntity<PlayersGroupDTO> getPlayersGroup(
            @ParameterObject @org.springframework.web.bind.annotation.RequestParam GroupByEnum groupBy,
            @RequestBody Filters filters) {
        return ResponseEntity.ok(playerService.countPlayersGroup(groupBy, filters));
    }
}
