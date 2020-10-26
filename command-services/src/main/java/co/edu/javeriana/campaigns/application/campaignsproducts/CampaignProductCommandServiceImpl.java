package co.edu.javeriana.campaigns.application.campaignsproducts;

import co.edu.javeriana.campaigns.domain.CampaignProduct;
import co.edu.javeriana.campaigns.domain.Campaigns;
import co.edu.javeriana.campaigns.domain.Status;
import co.edu.javeriana.campaigns.domain.dtos.campaignproducts.Product;
import co.edu.javeriana.campaigns.domain.dtos.campaignproducts.Response;
import co.edu.javeriana.campaigns.infraestructure.repository.campaignproduct.CampaignProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class CampaignProductCommandServiceImpl implements CampaignProductCommandService {

    @Value("${events.amqp.campaignproduct.exchange}")
    private String campaignProductExchange;

    @Value("${events.amqp.campaignproduct.routing-key}")
    private String campaignProductRoutingKey;

    private final CampaignProductRepository repository;

    //@Qualifier("template2")
    private final AmqpTemplate template;

    @Override
    public CompletableFuture<Response> mamnageCampaignProduct(List<CampaignProduct> campaigns) {
        Response response = new Response();
        co.edu.javeriana.campaigns.domain.dtos.campaignproducts.Status statusResponse = new co.edu.javeriana.campaigns.domain.dtos.campaignproducts.Status();
        try {
            List<Product> products = new ArrayList<>();

            for (CampaignProduct cpg : campaigns) {
                String status = "";
                Product product = new Product();
                if (cpg.getAction().equalsIgnoreCase(Status.CREATED.name())) {
                    status = this.repository.create(cpg).get();
                }

                if (cpg.getAction().equalsIgnoreCase(Status.UPDATED.name())) {
                    status = this.repository.update(cpg).get();
                }

                if (cpg.getAction().equalsIgnoreCase(Status.DELETED.name())) {
                    status = this.repository.delete(cpg).get();
                }

                product.setStatus(status);
                product.setCode(cpg.getProductId());
                products.add(product);
                this.template.convertAndSend("campaigns-product-exchange-events", "campaigns-product-command-key", cpg);
            }

            statusResponse.setStatus(Status.SUCCESS.name());
            statusResponse.setDescription("Products operations ha been execute succesfully!");

            response.setStatus(statusResponse);
            response.setProduct(products);
            return CompletableFuture.completedFuture(response);
        } catch (Exception e) {
            statusResponse.setStatus(Status.SUCCESS.name());
            statusResponse.setDescription(String.format("Exception creating row has been release: {%s}", e.getMessage()));
            response.setStatus(statusResponse);
            return CompletableFuture.completedFuture(response);
        }
    }

}
