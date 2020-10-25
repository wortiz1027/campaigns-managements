package co.edu.javeriana.campaigns.infraestructure.repository.campaignproduct;

import co.edu.javeriana.campaigns.domain.CampaignProduct;
import co.edu.javeriana.campaigns.domain.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Repository
@RequiredArgsConstructor
public class CampaignProductRepositoryImpl implements CampaignProductRepository {

    private final JdbcTemplate template;

    @Override
    public Optional<CampaignProduct> findById(CampaignProduct campaigns) {
        try {
            String sql = "SELECT * " +
                         "FROM CAMPAIGNS_PRODUCTS " +
                         "WHERE CAMPAIGNS_PRODUCTS_ID = ? AND CAMPAIGNS_ID = ? AND PRODUCT_ID = ?";

            return template.queryForObject(sql,
                    new Object[]{campaigns.getCampaignProductId(), campaigns.getCampaignId(), campaigns.getProductId()},
                    (rs, rowNum) ->
                            Optional.of(new CampaignProduct(
                                    rs.getString("CAMPAIGNS_PRODUCTS_ID"),
                                    rs.getString("CAMPAIGNS_ID"),
                                    rs.getString("PRODUCT_ID")
                            ))
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public CompletableFuture<String> create(CampaignProduct campaigns) {
        try {
            if (findById(campaigns).isPresent()) return CompletableFuture.completedFuture(Status.EXIST.name());

            String sql = "INSERT INTO CAMPAIGNS_PRODUCTS (CAMPAIGNS_PRODUCTS_ID, " +
                                                          "CAMPAIGNS_ID, " +
                                                          "PRODUCT_ID ) " +
                                                          "VALUES (?,?,?)";

            template.update(sql,
                            campaigns.getCampaignProductId(),
                            campaigns.getCampaignId(),
                            campaigns.getProductId());

            return CompletableFuture.completedFuture(Status.CREATED.name());
        } catch(Exception e) {
            return CompletableFuture.completedFuture(Status.ERROR.name());
        }
    }

    @Override
    public CompletableFuture<String> update(CampaignProduct campaigns) {
        try {
            if (findById(campaigns).isPresent()) return CompletableFuture.completedFuture(Status.EXIST.name());

            String sql = "UPDATE CAMPAIGNS_PRODUCTS SET " +
                            "PRODUCT_ID = ? " +
                            "WHERE CAMPAIGNS_PRODUCTS_ID = ?" +
                            "AND CAMPAIGNS_ID = ? " ;

            this.template.update(sql,
                    campaigns.getProductId(),
                    campaigns.getCampaignProductId(),
                    campaigns.getCampaignId());

            return CompletableFuture.completedFuture(Status.UPDATED.name());
        } catch (Exception e) {
            return CompletableFuture.completedFuture(Status.ERROR.name());
        }
    }

    @Override
    public CompletableFuture<String> delete(CampaignProduct campaigns) {
        try {
            if (findById(campaigns).isEmpty()) return CompletableFuture.completedFuture(Status.NO_EXIST.name());

            String sql = "DELETE FROM CAMPAIGNS_PRODUCTS WHERE CAMPAIGNS_ID = ?";

            this.template.update(sql, campaigns.getCampaignId());

            return CompletableFuture.completedFuture(Status.DELETED.name());
        } catch (Exception e) {
            return CompletableFuture.completedFuture(Status.ERROR.name());
        }
    }
}
