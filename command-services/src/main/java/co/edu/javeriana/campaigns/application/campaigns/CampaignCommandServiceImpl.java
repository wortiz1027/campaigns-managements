package co.edu.javeriana.campaigns.application.campaigns;

import co.edu.javeriana.campaigns.domain.Campaigns;
import co.edu.javeriana.campaigns.domain.Status;
import co.edu.javeriana.campaigns.domain.dtos.campaign.Response;
import co.edu.javeriana.campaigns.infraestructure.repository.campaign.CampaignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class CampaignCommandServiceImpl implements CampaignCommandService {

    @Value("${events.amqp.campaign.exchange}")
    private String campaignExchange;

    @Value("${events.amqp.campaign.routing-key}")
    private String campaignRoutingKey;

    private final CampaignRepository repository;

    //@Qualifier("template1")
    private final AmqpTemplate template;

    @Override
    public CompletableFuture<Response> createCampaigns(Campaigns campaigns) {
        Response response = new Response();
        try {
            String status = this.repository.create(campaigns).get();
            response.setStatus(status);

            if (!status.equalsIgnoreCase(Status.CREATED.name())) {
                response.setDescription(String.format("The campaign with id: {%s} has an error", campaigns.getCampaignCode()));
                return CompletableFuture.completedFuture(response);
            }

            campaigns.setAction(Status.CREATED.name());
            this.template.convertAndSend(campaignExchange, campaignRoutingKey, campaigns);
            response.setDescription(String.format("The campaign with id: {%s} has been created", campaigns.getCampaignCode()));
            return CompletableFuture.completedFuture(response);
        } catch (Exception e) {
            response.setStatus(Status.ERROR.name());
            response.setDescription(String.format("Exception creating row has been release: {%s}", e.getMessage()));
            return CompletableFuture.completedFuture(response);
        }
    }

    @Override
    public CompletableFuture<Response> updateCampaigns(Campaigns campaigns) {
        Response response = new Response();
        try {
            String status = this.repository.update(campaigns).get();
            response.setStatus(status);

            if (!status.equalsIgnoreCase(Status.UPDATED.name())) {
                response.setDescription(String.format("The campaign with id: {%s} has an error", campaigns.getCampaignCode()));
                return CompletableFuture.completedFuture(response);
            }

            response.setDescription(String.format("The campaign with id: {%s} has been updated", campaigns.getCampaignCode()));
            campaigns.setAction(Status.UPDATED.name());

            this.template.convertAndSend(campaignExchange, campaignRoutingKey, campaigns);

            return CompletableFuture.completedFuture(response);
        } catch (Exception e) {
            response.setStatus(Status.ERROR.name());
            response.setDescription(String.format("Exception updating row has been release: {%s}", e.getMessage()));
            return CompletableFuture.completedFuture(response);
        }
    }

    @Override
    public CompletableFuture<Response> deleteCampaigns(Campaigns campaigns) {
        Response response = new Response();
        try {
            String status = this.repository.delete(campaigns).get();
            response.setStatus(status);

            if (!status.equalsIgnoreCase(Status.DELETED.name())) {
                response.setDescription(String.format("The campaign with id: {%s} has an error", campaigns.getCampaignCode()));
                return CompletableFuture.completedFuture(response);
            }

            response.setDescription(String.format("The campaign with id: {%s} has been deleted", campaigns.getCampaignCode()));
            campaigns.setAction(Status.DELETED.name());
            this.template.convertAndSend(campaignExchange, campaignRoutingKey, campaigns);

            return CompletableFuture.completedFuture(response);
        } catch (Exception e) {
            response.setStatus(Status.ERROR.name());
            response.setDescription(String.format("Exception deleting row has been release: {%s}", e.getMessage()));
            return CompletableFuture.completedFuture(response);
        }
    }
}
