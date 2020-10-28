package co.edu.javeriana.campaigns.infraestructure.repository.campaings;

import co.edu.javeriana.campaigns.domain.Campaigns;
import co.edu.javeriana.campaigns.domain.Image;
import co.edu.javeriana.campaigns.domain.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Repository
@RequiredArgsConstructor
public class CampaignMySQLRepository implements CampaignRepository {

    private final JdbcTemplate template;

    @Override
    public Optional<Page<Campaigns>> findByAll(Pageable paging) {
        return Optional.empty();
    }

    @Override
    public Optional<Campaigns> findById(String code) {
        try {
            String sql = "SELECT * FROM CAMPAIGNS WHERE CAMPAIGNS_CODE = ?";

            return template.queryForObject(sql,
                    new Object[]{code},
                    (rs, rowNum) ->
                            Optional.of(new Campaigns(
                                    rs.getString("CAMPAIGNS_ID"),
                                    rs.getString("CAMPAIGNS_CODE"),
                                    rs.getString("CAMPAIGNS_NAME"),
                                    rs.getString("CAMPAIGNS_DESC"),
                                    new Image(rs.getString("IMAGE_ID"), ""),
                                    rs.getDate("START_DATE").toLocalDate(),
                                    rs.getDate("END_DATE").toLocalDate(),
                                    rs.getBigDecimal("DISCOUNT"),
                                    rs.getString("STATUS"),
                                    ""
                            ))
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Page<Campaigns>> findByText(String text, Pageable paging) {
        return Optional.empty();
    }

    @Override
    public CompletableFuture<String> create(Campaigns data) {
        try {
            if (findById(data.getCampaignCode()).isPresent()) return CompletableFuture.completedFuture(Status.EXIST.name());

            String sql = "INSERT INTO CAMPAIGNS (CAMPAIGNS_ID, " +
                                                "CAMPAIGNS_CODE, " +
                                                "CAMPAIGNS_NAME, " +
                                                "CAMPAIGNS_DESC, " +
                                                "IMAGE_ID, " +
                                                "START_DATE, " +
                                                "END_DATE, " +
                                                "DISCOUNT, " +
                                                "STATUS ) " +
                                                "VALUES (?,?,?,?,?,?,?,?,?)";

            template.update(sql,
                    data.getCampaignId(),
                    data.getCampaignCode(),
                    data.getCampaignName(),
                    data.getCampaignDescription(),
                    data.getImage().getId(),
                    data.getStartDate(),
                    data.getEndDate(),
                    data.getDiscount(),
                    data.getStatus());

            return CompletableFuture.completedFuture(Status.CREATED.name());
        } catch(Exception e) {
            return CompletableFuture.completedFuture(Status.ERROR.name());
        }
    }

    @Override
    public CompletableFuture<String> update(Campaigns data) {
        try {
            if (findById(data.getCampaignCode()).isPresent()) return CompletableFuture.completedFuture(Status.EXIST.name());

            String sql = "UPDATE CAMPAIGNS SET " +
                                    "CAMPAIGNS_CODE = ?, " +
                                    "CAMPAIGNS_NAME = ?, " +
                                    "CAMPAIGNS_DESC = ?, " +
                                    "IMAGE_ID = ?, " +
                                    "START_DATE = ?, " +
                                    "END_DATE = ?, " +
                                    "DISCOUNT = ?, " +
                                    "STATUS = ? " +
                                    "WHERE CAMPAIGNS_ID = ? " ;

            this.template.update(sql,
                    data.getCampaignCode(),
                    data.getCampaignName(),
                    data.getCampaignDescription(),
                    data.getImage().getId(),
                    data.getStartDate(),
                    data.getEndDate(),
                    data.getDiscount(),
                    data.getStatus(),
                    data.getCampaignId());

            return CompletableFuture.completedFuture(Status.UPDATED.name());
        } catch (Exception e) {
            return CompletableFuture.completedFuture(Status.ERROR.name());
        }
    }

    @Override
    public CompletableFuture<String> delete(Campaigns data) {
        try {
            if (findById(data.getCampaignCode()).isEmpty()) return CompletableFuture.completedFuture(Status.NO_EXIST.name());

            String sql = "DELETE FROM CAMPAIGNS WHERE CAMPAIGNS_ID = ?";

            this.template.update(sql, data.getCampaignId());

            return CompletableFuture.completedFuture(Status.DELETED.name());
        } catch (Exception e) {
            return CompletableFuture.completedFuture(Status.ERROR.name());
        }
    }
}
