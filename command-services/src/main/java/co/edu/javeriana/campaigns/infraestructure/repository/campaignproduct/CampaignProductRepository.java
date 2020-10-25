package co.edu.javeriana.campaigns.infraestructure.repository.campaignproduct;

import co.edu.javeriana.campaigns.domain.CampaignProduct;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface CampaignProductRepository {

    Optional<CampaignProduct> findById(CampaignProduct campaigns);
    CompletableFuture<String> create(CampaignProduct campaigns);
    CompletableFuture<String> update(CampaignProduct campaigns);
    CompletableFuture<String> delete(CampaignProduct campaigns);

}
