package co.edu.javeriana.campaigns.events;

import co.edu.javeriana.campaigns.domain.CampaignProduct;
import co.edu.javeriana.campaigns.domain.Campaigns;
import co.edu.javeriana.campaigns.domain.Status;
import co.edu.javeriana.campaigns.infraestructure.repository.campaignsproduct.CampaignProductRepository;
import co.edu.javeriana.campaigns.infraestructure.repository.campaings.CampaignRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EventHandler {

    private static final Logger LOG = LoggerFactory.getLogger(EventHandler.class);

    private final CampaignRepository campaignRepository;
    private final CampaignProductRepository campaignProductRepository;

    @RabbitListener(queues = "${events.amqp.campaign.queue}")
    public void consumerCampaigns(Campaigns data) {
        LOG.info("recibiendo campaign: {}", data);

        Optional<Campaigns> campaigns = this.campaignRepository.findById(data.getCampaignId());

        LOG.info("CREANDO_campaign: {}", data.getStatus());
        if (data.getAction().equalsIgnoreCase(Status.CREATED.name()) && campaigns.isEmpty()) {
            LOG.info("CREANDO_campaign: {}", data);
            this.campaignRepository.create(data);
        }

        if (data.getAction().equalsIgnoreCase(Status.UPDATED.name()) && !campaigns.isPresent()) {
            this.campaignRepository.update(campaigns.get());
        }

        if (data.getAction().equalsIgnoreCase(Status.DELETED.name()) && !campaigns.isPresent()) {
            this.campaignRepository.delete(campaigns.get());
        }

        LOG.info("campaign with code [{}] has been saved", data.getCampaignCode());
    }

    @RabbitListener(queues = "${events.amqp.campaignproduct.queue}")
    public void consumerCampaignsProducts(CampaignProduct data) {
        LOG.info("recibiendo_campaign_product: {}", data);

        Optional<CampaignProduct> campaigns = this.campaignProductRepository.findById(data);

        if (data.getAction().equalsIgnoreCase(Status.CREATED.name()) && campaigns.isEmpty()) {
            this.campaignProductRepository.create(data);
        }

        if (data.getAction().equalsIgnoreCase(Status.UPDATED.name()) && !campaigns.isPresent()) {
            this.campaignProductRepository.update(campaigns.get());
        }

        if (data.getAction().equalsIgnoreCase(Status.DELETED.name()) && !campaigns.isPresent()) {
            this.campaignProductRepository.delete(campaigns.get());
        }

        LOG.info("campaign product with code [{}] has been saved", data.getCampaignId());
    }

}
