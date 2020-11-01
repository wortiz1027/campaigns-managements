package co.edu.javeriana.campaigns.application.campaingproduct;

import co.edu.javeriana.campaigns.domain.CampaignProduct;
import co.edu.javeriana.campaigns.dtos.campaignproduct.Response;
import org.springframework.data.domain.Pageable;

import java.util.concurrent.CompletableFuture;

public interface CampaignProductsServices {

    CompletableFuture<Response> getCampaignProducts(CampaignProduct data, Pageable paging);

}
