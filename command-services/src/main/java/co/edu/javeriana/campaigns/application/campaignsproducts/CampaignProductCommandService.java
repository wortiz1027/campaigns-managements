package co.edu.javeriana.campaigns.application.campaignsproducts;

import co.edu.javeriana.campaigns.domain.CampaignProduct;
import co.edu.javeriana.campaigns.domain.dtos.campaignproducts.Response;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CampaignProductCommandService {

    CompletableFuture<Response> mamnageCampaignProduct(List<CampaignProduct> campaigns);

}
