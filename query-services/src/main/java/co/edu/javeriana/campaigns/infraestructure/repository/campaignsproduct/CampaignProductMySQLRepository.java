package co.edu.javeriana.campaigns.infraestructure.repository.campaignsproduct;

import co.edu.javeriana.campaigns.domain.CampaignProduct;
import co.edu.javeriana.campaigns.domain.Status;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Repository
@RequiredArgsConstructor
public class CampaignProductMySQLRepository implements CampaignProductRepository {

    private final JdbcTemplate template;

    private int count() {
        return this.template.queryForObject("SELECT count(*) FROM CAMPAIGNS", Integer.class);
    }

    @Override
    public Optional<CampaignProduct> findById(CampaignProduct data) {
        try {
            String sql = "SELECT * " +
                    "FROM CAMPAIGNS_PRODUCTS " +
                    "WHERE CAMPAIGNS_PRODUCTS_ID = ? AND CAMPAIGNS_ID = ? AND PRODUCT_ID = ?";

            return template.queryForObject(sql,
                    new Object[]{data.getCampaignProductId(), data.getCampaignId(), data.getProductId()},
                    (rs, rowNum) ->
                            Optional.of(new CampaignProduct(
                                    rs.getString("CAMPAIGNS_PRODUCTS_ID"),
                                    rs.getString("CAMPAIGNS_ID"),
                                    rs.getString("PRODUCT_ID"),
                                    "",
                                    ""
                            ))
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Page<CampaignProduct>> findProductsByCampaignId(CampaignProduct data, Pageable paging) {
        try {
            String sql = "SELECT PRODUCT_ID, " +
                         "CAMPAIGNS_PRODUCTS_ID," +
                         "CAMPAIGNS_ID " +
                         "FROM CAMPAIGNS_PRODUCTS " +
                         "WHERE CAMPAIGNS_ID = ? " +
                         "ORDER BY PRODUCT_ID ASC " +
                         "LIMIT %d OFFSET %d";

            List<CampaignProduct> products = this.template.query(String.format(sql, paging.getPageSize(), paging.getOffset()),
                    new Object[] { data.getCampaignId() },
                    new CampaignsProductsRowMapper());

            return Optional.of(new PageImpl<>(products, paging, count()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public CompletableFuture<String> create(CampaignProduct data) {
        try {
            if (findById(data).isPresent()) return CompletableFuture.completedFuture(Status.EXIST.name());

            String sql = "INSERT INTO CAMPAIGNS_PRODUCTS (CAMPAIGNS_PRODUCTS_ID, " +
                                                        "CAMPAIGNS_ID, " +
                                                        "PRODUCT_ID ) " +
                                                        "VALUES (?,?,?)";

            template.update(sql,
                    data.getCampaignProductId(),
                    data.getCampaignId(),
                    data.getProductId());

            return CompletableFuture.completedFuture(Status.CREATED.name());
        } catch(Exception e) {
            return CompletableFuture.completedFuture(Status.ERROR.name());
        }
    }

    @Override
    public CompletableFuture<String> update(CampaignProduct data) {
        try {
            if (findById(data).isPresent()) return CompletableFuture.completedFuture(Status.EXIST.name());

            String sql = "UPDATE CAMPAIGNS_PRODUCTS SET " +
                                "PRODUCT_ID = ? " +
                                "WHERE CAMPAIGNS_PRODUCTS_ID = ?" +
                                "AND CAMPAIGNS_ID = ? " ;

            this.template.update(sql,
                    data.getProductId(),
                    data.getCampaignProductId(),
                    data.getCampaignId());

            return CompletableFuture.completedFuture(Status.UPDATED.name());
        } catch (Exception e) {
            return CompletableFuture.completedFuture(Status.ERROR.name());
        }
    }

    @Override
    public CompletableFuture<String> delete(CampaignProduct data) {
        try {
            if (findById(data).isEmpty()) return CompletableFuture.completedFuture(Status.NO_EXIST.name());

            String sql = "DELETE FROM CAMPAIGNS_PRODUCTS WHERE CAMPAIGNS_ID = ?";

            this.template.update(sql, data.getCampaignId());

            return CompletableFuture.completedFuture(Status.DELETED.name());
        } catch (Exception e) {
            e.printStackTrace();
            return CompletableFuture.completedFuture(Status.ERROR.name());
        }
    }
}
