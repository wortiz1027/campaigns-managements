package co.edu.javeriana.campaigns.infraestructure.repository.campaignsproduct;

import co.edu.javeriana.campaigns.domain.CampaignProduct;
import co.edu.javeriana.campaigns.domain.Campaigns;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface CampaignProductRepository {

    Optional<Page<Campaigns>> findByAll(Pageable paging);
    Optional<CampaignProduct> findById(String code);
    Optional<Page<CampaignProduct>> findByText(String text, Pageable paging);
    CompletableFuture<String> create(CampaignProduct data);
    CompletableFuture<String> update(CampaignProduct data);
    CompletableFuture<String> delete(CampaignProduct data);

}
