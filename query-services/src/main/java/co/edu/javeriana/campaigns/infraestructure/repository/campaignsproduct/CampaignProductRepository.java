package co.edu.javeriana.campaigns.infraestructure.repository.campaignsproduct;

import co.edu.javeriana.campaigns.domain.CampaignProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface CampaignProductRepository {

    Optional<CampaignProduct> findById(CampaignProduct data);
    Optional<Page<CampaignProduct>> findByCampaignId(CampaignProduct data, Pageable paging);
    CompletableFuture<String> create(CampaignProduct data);
    CompletableFuture<String> update(CampaignProduct data);
    CompletableFuture<String> delete(CampaignProduct data);

}
