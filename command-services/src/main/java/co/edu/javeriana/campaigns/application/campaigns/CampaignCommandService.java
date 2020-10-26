package co.edu.javeriana.campaigns.application.campaigns;

import co.edu.javeriana.campaigns.domain.Campaigns;
import co.edu.javeriana.campaigns.domain.dtos.campaign.Response;

import java.util.concurrent.CompletableFuture;

public interface CampaignCommandService {

    CompletableFuture<Response> createCampaigns(Campaigns campaigns);
    CompletableFuture<Response> updateCampaigns(Campaigns campaigns);
    CompletableFuture<Response> deleteCampaigns(Campaigns campaigns);

}
