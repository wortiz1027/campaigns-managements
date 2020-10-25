package co.edu.javeriana.campaigns.infraestructure.repository.campaign;

import co.edu.javeriana.campaigns.domain.Campaigns;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface CampaignRepository {

    Optional<Campaigns> findById(String code);
    CompletableFuture<String> create(Campaigns campaigns);
    CompletableFuture<String> update(Campaigns campaigns);
    CompletableFuture<String> delete(Campaigns campaigns);

}
