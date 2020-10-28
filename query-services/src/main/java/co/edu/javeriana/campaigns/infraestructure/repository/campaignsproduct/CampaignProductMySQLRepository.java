package co.edu.javeriana.campaigns.infraestructure.repository.campaignsproduct;

import co.edu.javeriana.campaigns.domain.CampaignProduct;
import co.edu.javeriana.campaigns.domain.Campaigns;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Repository
@RequiredArgsConstructor
public class CampaignProductMySQLRepository implements CampaignProductRepository {

    private final JdbcTemplate template;

    @Override
    public Optional<Page<Campaigns>> findByAll(Pageable paging) {
        return Optional.empty();
    }

    @Override
    public Optional<CampaignProduct> findById(String code) {
        return Optional.empty();
    }

    @Override
    public Optional<Page<CampaignProduct>> findByText(String text, Pageable paging) {
        return Optional.empty();
    }

    @Override
    public CompletableFuture<String> create(CampaignProduct data) {
        return null;
    }

    @Override
    public CompletableFuture<String> update(CampaignProduct data) {
        return null;
    }

    @Override
    public CompletableFuture<String> delete(CampaignProduct data) {
        return null;
    }
}
