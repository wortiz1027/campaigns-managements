package co.edu.javeriana.campaigns.application.campaingproduct;

import co.edu.javeriana.campaigns.domain.CampaignProduct;
import co.edu.javeriana.campaigns.domain.Image;
import co.edu.javeriana.campaigns.dtos.Status;
import co.edu.javeriana.campaigns.dtos.campaignproduct.*;
import co.edu.javeriana.campaigns.infraestructure.repository.campaignsproduct.CampaignProductRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class CampaignProductsServicesImpl implements CampaignProductsServices {

    private static final Logger LOG = LoggerFactory.getLogger(CampaignProductsServicesImpl.class);

    @Value("${events.rpc.products.exchange}")
    private String rpcProductsExchange;

    @Value("${events.rpc.products.routing-key}")
    private String rpcProductsRoutingKey;

    private final AsyncRabbitTemplate template;
    private final CampaignProductRepository repository;

    @Override
    public CompletableFuture<Response> getCampaignProducts(CampaignProduct data, Pageable paging) {
        Response response = new Response();
        Status status = new Status();
        try {
            Optional<Page<CampaignProduct>> products = this.repository.findProductsByCampaignId(data, paging);

            if (!products.isPresent()) {
                status.setCode(co.edu.javeriana.campaigns.domain.Status.EMPTY.name());
                status.setDescription("There are not rows availables");
                response.setStatus(status);
                return CompletableFuture.completedFuture(response);
            }

            List<CampaignProduct> prd = products.get().getContent();

            Optional<ResponseProducts> res = callProducts(Optional.of(prd));

            if (res.get().getProducts().isEmpty()) {
                status.setCode(co.edu.javeriana.campaigns.domain.Status.EMPTY.name());
                status.setDescription(String.format("There are not products informations available with campaing id: {%s}", data.getCampaignId()));
                response.setStatus(status);
                return CompletableFuture.completedFuture(response);
            }

            Map<String, Object> dta = new HashMap<>();
            dta.put("products", res.get().getProducts());
            dta.put("currentPage", products.get().getNumber());
            dta.put("totalItems", products.get().getTotalElements());
            dta.put("totalPages", products.get().getTotalPages());

            CampaignProductDto detail = new CampaignProductDto();
            detail.setId(data.getCampaignId());
            detail.setData(dta);

            status.setCode(co.edu.javeriana.campaigns.domain.Status.SUCCESS.name());
            status.setDescription(String.format("There are rows with products linked with campaing id: %s", data.getCampaignId()));
            response.setStatus(status);
            response.setCampaign(detail);

            return CompletableFuture.completedFuture(response);
        } catch (Exception e) {
            status.setCode(co.edu.javeriana.campaigns.domain.Status.ERROR.name());
            status.setDescription(String.format("There is an error getting products type: %s", e.getMessage()));
            response.setStatus(status);
            return CompletableFuture.completedFuture(response);
        }
    }

    private Optional<ResponseProducts> callProducts(Optional<List<CampaignProduct>> products) {
        String ids = "";

        for (CampaignProduct row : products.get()) {
            ids = ids.concat(row.getProductId()).concat(",");
        }

        LOG.info(ids.substring(0, ids.length() - 1));

        Request request = new Request();
        request.setIds(ids.substring(0, ids.length() - 1));

        AsyncRabbitTemplate.RabbitConverterFuture<ResponseProducts> future = this.template.convertSendAndReceiveAsType(
                                                                                                rpcProductsExchange,
                                                                                                rpcProductsRoutingKey,
                                                                                                request,
                                                                                                new ParameterizedTypeReference<>() {});

        try {
            ResponseProducts res = future.get();
            LOG.info("Message received: {}", res);
            return Optional.of(res);
        } catch (InterruptedException | ExecutionException e) {
            LOG.error("Cannot get response.", e);
            return Optional.empty();
        }

    }

}
