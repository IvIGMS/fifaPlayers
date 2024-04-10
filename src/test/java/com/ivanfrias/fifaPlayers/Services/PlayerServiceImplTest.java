package com.ivanfrias.fifaPlayers.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.ivanfrias.fifaPlayers.Models.DTO.Filters;
import com.ivanfrias.fifaPlayers.Models.DTO.Limits;
import com.ivanfrias.fifaPlayers.Models.Entity.Player;
import com.ivanfrias.fifaPlayers.Models.enums.CountryEnum;
import com.ivanfrias.fifaPlayers.Models.enums.GroupByEnum;
import com.ivanfrias.fifaPlayers.Models.enums.OrderByEnum;
import com.ivanfrias.fifaPlayers.Repositories.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
class PlayerServiceImplTest {

    @InjectMocks
    private PlayerServiceImpl playerService;

    @Mock
    private PlayerRepository playerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getPlayers() {
    }

    @Test
    void getPlayerByIdOk() {
        Player player = Player.builder()
                .name("Cristiano Ronaldo")
                .age(BigDecimal.valueOf(37))
                .overallRating(BigDecimal.valueOf(92))
                .nationality(CountryEnum.PORTUGAL.getValue())
                .build();

        Mockito.when(playerRepository.findById(0L)).thenReturn(Optional.ofNullable(player));
        Player result = playerService.getPlayerById(0L);

        assertEquals("Cristiano Ronaldo", result.getName());
        assertEquals(BigDecimal.valueOf(37), result.getAge());
        assertEquals(BigDecimal.valueOf(92), result.getOverallRating());
        assertEquals(CountryEnum.PORTUGAL.getValue(), result.getNationality());
    }

    @Test
    void getPlayerByIdNull() {
        Mockito.when(playerRepository.findById(0L)).thenReturn(Optional.empty());
        Player result = playerService.getPlayerById(0L);

        assertNull(result.getName());
        assertNull(result.getNationality());
        assertNull(result.getOverallRating());
    }

    @Test
    void countPlayersByNationality() {
    }

    @Test
    void getQueryPlayerOk() {
        Filters filters = Filters.builder()
                .countries(Arrays.asList(CountryEnum.SPAIN, CountryEnum.BRAZIL, CountryEnum.ARGENTINA))
                .ageLimits(Limits.builder()
                        .min(BigDecimal.valueOf(20))
                        .max(BigDecimal.valueOf(35))
                        .build())
                .averageLimits(Limits.builder()
                        .min(BigDecimal.valueOf(80))
                        .max(BigDecimal.valueOf(90))
                        .build())
                .orderBy(OrderByEnum.VALUE_EURO)
                .build();

        String result = playerService.getQueryPlayer(filters);
        String expected = "SELECT * FROM fifa_players WHERE nationality IN ('Spain','Brazil','Argentina') AND overall_rating>=80 AND overall_rating<=90 AND age>=20 AND age<=35 ORDER BY value_euro DESC";

        assertEquals(expected, result);
    }

    @Test
    void getQueryGroupOk() {
        Filters filters = Filters.builder()
                .countries(Arrays.asList(CountryEnum.SPAIN, CountryEnum.BRAZIL, CountryEnum.ARGENTINA))
                .ageLimits(Limits.builder()
                        .min(BigDecimal.valueOf(20))
                        .max(BigDecimal.valueOf(35))
                        .build())
                .averageLimits(Limits.builder()
                        .min(BigDecimal.valueOf(80))
                        .max(BigDecimal.valueOf(90))
                        .build())
                .build();
        String result = playerService.getQueryGroup(GroupByEnum.NATIONALITY.getValue(), filters);
        String expected = "SELECT nationality, COUNT(*) as amount FROM fifa_players WHERE nationality IN ('Spain','Brazil','Argentina') AND overall_rating>=80 AND overall_rating<=90 AND age>=20 AND age<=35 GROUP BY nationality ORDER BY amount DESC";

        assertEquals(expected, result);
    }
}