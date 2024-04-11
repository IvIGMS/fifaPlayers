package com.ivanfrias.fifaPlayers.Services;

import com.ivanfrias.fifaPlayers.Models.DTO.*;
import com.ivanfrias.fifaPlayers.Models.Entity.Player;
import com.ivanfrias.fifaPlayers.Models.enums.CountryEnum;
import com.ivanfrias.fifaPlayers.Models.enums.GroupByEnum;
import com.ivanfrias.fifaPlayers.Models.enums.OrderByEnum;
import com.ivanfrias.fifaPlayers.Repositories.PlayerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@RunWith(MockitoJUnitRunner.class)
class PlayerServiceImplTest {

    @InjectMocks
    private PlayerServiceImpl playerService;

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private Query queryMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getPlayersOk() {
        Filters filters = getFilters();

        Mockito.when(queryMock.getResultList()).thenReturn(getPlayers());
        Mockito.when(entityManager.createNativeQuery(Mockito.anyString())).thenReturn(queryMock);
        List<PlayerBasicDTO> result = playerService.getPlayers(filters);

        var names = result.stream()
                        .map(PlayerBasicDTO::getName)
                        .reduce("", (a, b) -> a + b);

        var ids = result.stream()
                .map(PlayerBasicDTO::getId)
                .reduce(0L, Long::sum);

        var rating = result.stream()
                .map(PlayerBasicDTO::getOverallRating)
                .reduce(BigDecimal.ZERO, BigDecimal::add);;

        assertEquals("T. KroosM. NeuerV. van Dijk", names);
        assertEquals(17947L, ids);
        assertEquals(BigDecimal.valueOf(267), rating);
    }

    private List<Object[]> getPlayers(){
        Object[] player1 = new Object[] {
                17934L,
                "T. Kroos",
                null,
                java.sql.Date.valueOf("1990-01-04"),
                BigDecimal.valueOf(34),
                null,
                null,
                "CM",
                "Germany",
                BigDecimal.valueOf(90),
                null,
                BigDecimal.valueOf(76500000),
                null,
                "Right"
        };

        Object[] player2 = new Object[] {
                8L,
                "M. Neuer",
                null,
                java.sql.Date.valueOf("1986-03-27"),
                BigDecimal.valueOf(38),
                null,
                null,
                "GK",
                "Germany",
                BigDecimal.valueOf(89),
                null,
                BigDecimal.valueOf(38000000),
                null,
                "Right"
        };

        Object[] player3 = new Object[] {
                5L,
                "V. van Dijk",
                null,
                java.sql.Date.valueOf("1991-07-08"),
                BigDecimal.valueOf(32),
                null,
                null,
                "CB",
                "Netherlands",
                BigDecimal.valueOf(88),
                null,
                BigDecimal.valueOf(59500000),
                null,
                "Right"
        };

        List<Object[]> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);

        return players;
    }

    private Filters getFilters(){
        return Filters.builder()
                .countries(Arrays.asList(CountryEnum.NETHERLANDS, CountryEnum.GERMANY))
                .ageLimits(Limits.builder()
                        .min(BigDecimal.valueOf(20))
                        .max(BigDecimal.valueOf(35))
                        .build())
                .averageLimits(Limits.builder()
                        .min(BigDecimal.valueOf(80))
                        .max(BigDecimal.valueOf(90))
                        .build())
                .orderBy(OrderByEnum.OVERALL_RATING)
                .build();
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
    void countPlayersGroupOk() {
        GroupByEnum groupBy = GroupByEnum.NATIONALITY;
        Filters filters = getFilters();

        Object[] group1 = new Object[] {"Brazil", 7L};
        Object[] group2 = new Object[] {"Spain", 5L};
        Object[] group3 = new Object[] {"Argentina", 9L};

        List<Object[]> groups = Arrays.asList(group1, group2, group3);

        Mockito.when(queryMock.getResultList()).thenReturn(groups);
        Mockito.when(entityManager.createNativeQuery(Mockito.anyString())).thenReturn(queryMock);
        PlayersGroupDTO result = playerService.countPlayersGroup(groupBy, filters);

        var countries = result.getColumns().stream()
                .map(GroupByDTO::getGroupBy)
                .reduce("", (a, b) -> a + b);

        var amount = result.getColumns().stream()
                .map(GroupByDTO::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        assertEquals("BrazilSpainArgentina", countries);
        assertEquals(BigDecimal.valueOf(21), amount);
    }

    @Test
    void getQueryPlayerOk() {
        Filters filters = getFilters();

        String result = playerService.getQueryPlayer(filters);
        String expected = "SELECT * FROM fifa_players " +
                "WHERE nationality IN ('Netherlands','Germany') " +
                "AND overall_rating>=80 AND overall_rating<=90 AND age>=20 AND age<=35 " +
                "ORDER BY overall_rating DESC";

        assertEquals(expected, result);
    }

    @Test
    void getQueryGroupOk() {
        Filters filters = getFilters();
        String result = playerService.getQueryGroup(GroupByEnum.NATIONALITY.getValue(), filters);
        String expected = "SELECT nationality, COUNT(*) as amount " +
                "FROM fifa_players WHERE nationality IN ('Netherlands','Germany') " +
                "AND overall_rating>=80 AND overall_rating<=90 AND age>=20 AND age<=35 GROUP BY nationality " +
                "ORDER BY amount DESC";

        assertEquals(expected, result);
    }
}