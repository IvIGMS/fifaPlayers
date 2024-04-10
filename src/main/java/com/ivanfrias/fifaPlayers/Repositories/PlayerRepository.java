package com.ivanfrias.fifaPlayers.Repositories;

import com.ivanfrias.fifaPlayers.Models.enums.GroupByEnum;
import com.ivanfrias.fifaPlayers.Models.Entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
