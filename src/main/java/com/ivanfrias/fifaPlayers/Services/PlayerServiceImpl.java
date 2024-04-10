package com.ivanfrias.fifaPlayers.Services;

import com.ivanfrias.fifaPlayers.Models.Constants.QueryConstants;
import com.ivanfrias.fifaPlayers.Models.DTO.*;
import com.ivanfrias.fifaPlayers.Models.Entity.Player;
import com.ivanfrias.fifaPlayers.Models.enums.CountryEnum;
import com.ivanfrias.fifaPlayers.Models.enums.GroupByEnum;
import com.ivanfrias.fifaPlayers.Repositories.PlayerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService{

    @PersistenceContext
    private EntityManager entityManager;

    final PlayerRepository playerRepository;

    @Override
    public List<PlayerBasicDTO> getPlayers(Filters filters) {
        String query = getQueryPlayer(filters);

        List<Object[]> response = entityManager.createNativeQuery(query).getResultList();

        List<PlayerBasicDTO> list = response.stream()
                .map(row -> PlayerBasicDTO.builder()
                        .id((Long) row[0])
                        .name((String) row[1])
                        .birthDate(row[3] != null ? ((java.sql.Date) row[3]).toLocalDate() : null)
                        .age((BigDecimal) row[4])
                        .positions((String) row[7])
                        .nationality((String) row[8])
                        .overallRating((BigDecimal) row[9])
                        .valueEuro((BigDecimal) row[11])
                        .preferredFoot((String) row[13])
                        .build())
                .toList();

        return list;
    }

    @Override
    public Player getPlayerById(Long id) {
        Optional<Player> currentPlayer = playerRepository.findById(id);
        return currentPlayer.orElseGet(() -> Player.builder().build());
    }

    @Override
    public PlayersGroupDTO countPlayersByNationality(GroupByEnum groupBy, Filters filters) {

        String query = getQueryGroup(groupBy.getValue(), filters);

        List<Object[]> response = entityManager.createNativeQuery(query).getResultList();

        List<GroupByDTO> list = response.stream()
                .map(row -> GroupByDTO.builder()
                        .groupBy(String.valueOf(row[0]))
                        .amount((BigDecimal.valueOf((Long) row[1])))
                        .build())
                .toList();

        return PlayersGroupDTO.builder()
                .label(groupBy.getValue())
                .columns(list)
                .build();
    }

    public String getQueryPlayer(Filters filters) {
        StringBuilder query = new StringBuilder();
        query.append(QueryConstants.SELECT).append(" * FROM fifa_players ");

        ApplyFilterCountries(query, filters.getCountries());
        ApplyLimits(query, filters.getAverageLimits(), QueryConstants.OVERALL_RATING);
        ApplyLimits(query, filters.getAgeLimits(), QueryConstants.AGE);

        // todo el order by tiene que ser dinamico
        query.append(QueryConstants.ORDER_BY).append(filters.getOrderBy().getValue()).append(QueryConstants.DESC);
        return query.toString();
    }

    public String getQueryGroup(String groupBy, Filters filters) {
        StringBuilder query = new StringBuilder();
        query.append(QueryConstants.SELECT).append(groupBy).append(", COUNT(*) as amount FROM fifa_players ");

        ApplyFilterCountries(query, filters.getCountries());
        ApplyLimits(query, filters.getAverageLimits(), QueryConstants.OVERALL_RATING);
        ApplyLimits(query, filters.getAgeLimits(), QueryConstants.AGE);

        query.append(QueryConstants.GROUP_BY).append(groupBy).append(QueryConstants.ORDER_BY).append(QueryConstants.AMOUNT).append(QueryConstants.DESC);
        return query.toString();
    }

    private void ApplyFilterCountries(StringBuilder query, List<CountryEnum> countries) {
        if (Objects.nonNull(countries) && !countries.isEmpty()) {
            query.append(QueryConstants.WHERE).append(QueryConstants.NATIONALITY).append(" IN (");

            for (CountryEnum country : countries) {
                query.append("'").append(country.getValue()).append("',");
            }

            query.setLength(query.length() - 1);
            query.append(")");
        }
    }

    private void ApplyLimits(StringBuilder query, Limits limits, String field) {
        if(Objects.nonNull(limits)){
            if (Objects.nonNull(limits.getMin())) {
                query.append(QueryConstants.AND).append(field).append(QueryConstants.MAYOR).append(limits.getMin());
            }
            if (Objects.nonNull(limits.getMax())) {
                query.append(QueryConstants.AND).append(field).append(QueryConstants.MINUS).append(limits.getMax());
            }
        }
    }
}










