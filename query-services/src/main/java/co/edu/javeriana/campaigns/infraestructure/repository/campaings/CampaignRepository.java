package co.edu.javeriana.campaigns.infraestructure.repository.campaings;

import co.edu.javeriana.campaigns.domain.Campaigns;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface CampaignRepository {

    Optional<Page<Campaigns>> findByAll(Pageable paging);
    Optional<Campaigns> findById(String id);
    Optional<Campaigns> findByCode(String code);
    Optional<Page<Campaigns>> findByText(String text, Pageable paging);
    CompletableFuture<String> create(Campaigns data);
    CompletableFuture<String> update(Campaigns data);
    CompletableFuture<String> delete(Campaigns data);

}
