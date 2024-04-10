package com.ivanfrias.fifaPlayers.Models.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "fifa_players")
public class Player {
    @Id
    private Long id;
    private String name;
    private String fullName;
    private LocalDate birthDate;
    private BigDecimal age;
    private BigDecimal heightCm;
    private BigDecimal weightKgs;
    private String positions;
    private String nationality;
    private BigDecimal overallRating;
    private BigDecimal potential;
    private BigDecimal valueEuro;
    private BigDecimal wageEuro;
    private String preferredFoot;
    private BigDecimal internationalReputation;
    private BigDecimal weakFoot;
    private BigDecimal skillMoves;
    private String bodyType;
    private BigDecimal releaseClauseEuro;
    private String nationalTeam;
    private BigDecimal nationalRating;
    private String nationalTeamPosition;
    private BigDecimal nationalJerseyNumber;
    private BigDecimal crossing;
    private BigDecimal finishing;
    private BigDecimal headingAccuracy;
    private BigDecimal shortPassing;
    private BigDecimal volleys;
    private BigDecimal dribbling;
    private BigDecimal curve;
    private BigDecimal freekickAccuracy;
    private BigDecimal longPassing;
    private BigDecimal ballControl;
    private BigDecimal acceleration;
    private BigDecimal sprintSpeed;
    private BigDecimal agility;
    private BigDecimal reactions;
    private BigDecimal balance;
    private BigDecimal shotPower;
    private BigDecimal jumping;
    private BigDecimal stamina;
    private BigDecimal strength;
    private BigDecimal longShots;
    private BigDecimal aggression;
    private BigDecimal interceptions;
    private BigDecimal positioning;
    private BigDecimal vision;
    private BigDecimal penalties;
    private BigDecimal composure;
    private BigDecimal marking;
    private BigDecimal standingTackle;
    private BigDecimal slidingTackle;
}
