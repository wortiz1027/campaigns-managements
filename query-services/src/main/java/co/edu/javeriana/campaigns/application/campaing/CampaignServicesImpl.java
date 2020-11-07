package co.edu.javeriana.campaigns.application.campaing;

import co.edu.javeriana.campaigns.domain.Campaigns;
import co.edu.javeriana.campaigns.domain.Image;
import co.edu.javeriana.campaigns.dtos.Status;
import co.edu.javeriana.campaigns.dtos.campaignproduct.Request;
import co.edu.javeriana.campaigns.dtos.campaing.CampaingDto;
import co.edu.javeriana.campaigns.dtos.campaing.Response;
import co.edu.javeriana.campaigns.dtos.campaing.ResponseDetail;
import co.edu.javeriana.campaigns.dtos.campaing.ResponseImage;
import co.edu.javeriana.campaigns.infraestructure.repository.campaings.CampaignRepository;
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
public class CampaignServicesImpl implements CampaignServices {

    private static final Logger LOG = LoggerFactory.getLogger(CampaignServicesImpl.class);

    @Value("${events.rpc.images.exchange}")
    private String rpcImagesExchange;

    @Value("${events.rpc.images.routing-key}")
    private String rpcImagesRoutingKey;

    private final AsyncRabbitTemplate template;
    private final CampaignRepository repository;

    @Override
    public CompletableFuture<Response> getAllCampaign(Pageable paging) {
        Response response = new Response();
        Status status = new Status();
        try {
            Optional<Page<Campaigns>> campaigns = this.repository.findByAll(paging);

            if (!campaigns.isPresent()) {
                status.setCode(co.edu.javeriana.campaigns.domain.Status.EMPTY.name());
                status.setDescription("There are not rows availables");
                response.setStatus(status);
                return CompletableFuture.completedFuture(response);
            }

            List<Campaigns> cpg = campaigns.get().getContent();

            callImages(Optional.of(cpg));

            List<CampaingDto> dtos = new ArrayList<>();

            for (Campaigns row : cpg) {
                CampaingDto item = new CampaingDto();
                item.setCampaignId(row.getCampaignId());
                item.setCampaignCode(row.getCampaignCode());
                item.setCampaignName(row.getCampaignName());
                item.setCampaignDescription(row.getCampaignDescription());

                Image image = new Image();
                image.setId(row.getImage().getId());
                image.setUrl(row.getImage().getUrl());

                item.setImage(image);
                item.setStartDate(row.getStartDate());
                item.setEndDate(row.getEndDate());
                item.setDiscount(row.getDiscount());
                item.setStatus(row.getStatus());
                item.setAction(row.getAction());

                dtos.add(item);
            }

            Map<String, Object> data = new HashMap<>();
            data.put("campaigns", dtos);
            data.put("currentPage", campaigns.get().getNumber());
            data.put("totalItems", campaigns.get().getTotalElements());
            data.put("totalPages", campaigns.get().getTotalPages());

            status.setCode(co.edu.javeriana.campaigns.domain.Status.SUCCESS.name());
            status.setDescription("There are rows availables");
            response.setStatus(status);
            response.setData(data);

            return CompletableFuture.completedFuture(response);
        } catch (Exception e) {
            e.printStackTrace();
            status.setCode(co.edu.javeriana.campaigns.domain.Status.ERROR.name());
            status.setDescription(String.format("There is an error getting campaigns informations: %s", e.getMessage()));
            response.setStatus(status);
            return CompletableFuture.completedFuture(response);
        }
    }

    @Override
    public CompletableFuture<Response> getCampaignByText(String text, Pageable paging) {
        Response response = new Response();
        Status status = new Status();
        try {
            Optional<Page<Campaigns>> campaigns = this.repository.findByText(text, paging);

            if (!campaigns.isPresent()) {
                status.setCode(co.edu.javeriana.campaigns.domain.Status.EMPTY.name());
                status.setDescription("There are not rows availables");
                response.setStatus(status);
                return CompletableFuture.completedFuture(response);
            }

            List<Campaigns> cpg = campaigns.get().getContent();

            callImages(Optional.of(cpg));

            List<CampaingDto> dtos = new ArrayList<>();

            for (Campaigns row : cpg) {
                CampaingDto item = new CampaingDto();
                item.setCampaignId(row.getCampaignId());
                item.setCampaignCode(row.getCampaignCode());
                item.setCampaignName(row.getCampaignName());
                item.setCampaignDescription(row.getCampaignDescription());

                Image image = new Image();
                image.setId(row.getImage().getId());
                image.setUrl(row.getImage().getUrl());

                item.setImage(image);
                item.setStartDate(row.getStartDate());
                item.setEndDate(row.getEndDate());
                item.setDiscount(row.getDiscount());
                item.setStatus(row.getStatus());
                item.setAction(row.getAction());

                dtos.add(item);
            }

            Map<String, Object> data = new HashMap<>();
            data.put("campaigns"  , dtos);
            data.put("currentPage", campaigns.get().getNumber());
            data.put("totalItems" , campaigns.get().getTotalElements());
            data.put("totalPages" , campaigns.get().getTotalPages());

            status.setCode(co.edu.javeriana.campaigns.domain.Status.SUCCESS.name());
            status.setDescription("There are rows availables");
            response.setStatus(status);
            response.setData(data);

            return CompletableFuture.completedFuture(response);
        } catch (Exception e) {
            status.setCode(co.edu.javeriana.campaigns.domain.Status.ERROR.name());
            status.setDescription(String.format("Error getting rows: %s", e.getMessage()));
            response.setStatus(status);

            return CompletableFuture.completedFuture(response);
        }
    }

    @Override
    public CompletableFuture<ResponseDetail> getCampaignDetail(String code) {
        ResponseDetail response = new ResponseDetail();
        Status status = new Status();
        try {
            Optional<Campaigns> campaign = this.repository.findByCode(code);

            if (!campaign.isPresent()) {
                status.setCode(co.edu.javeriana.campaigns.domain.Status.EMPTY.name());
                status.setDescription(String.format("There is not information for campaign with code: %s", code));
                response.setStatus(status);
                return CompletableFuture.completedFuture(response);
            }

            List<Campaigns> campaigns = new ArrayList<>();
            campaigns.add(campaign.get());

            callImages(Optional.of(campaigns));

            CampaingDto item = new CampaingDto();
            item.setCampaignId(campaign.get().getCampaignId());
            item.setCampaignCode(campaign.get().getCampaignCode());
            item.setCampaignName(campaign.get().getCampaignName());
            item.setCampaignDescription(campaign.get().getCampaignDescription());

            Image image = new Image();
            image.setId(campaign.get().getImage().getId());
            image.setUrl(campaign.get().getImage().getUrl());

            item.setImage(image);
            item.setStartDate(campaign.get().getStartDate());
            item.setEndDate(campaign.get().getEndDate());
            item.setDiscount(campaign.get().getDiscount());
            item.setStatus(campaign.get().getStatus());
            item.setAction(campaign.get().getAction());

            status.setCode(co.edu.javeriana.campaigns.domain.Status.SUCCESS.name());
            status.setDescription(String.format("There is informations for campaign with code: %s", code));
            response.setStatus(status);
            response.setCampaing(item);

            return CompletableFuture.completedFuture(response);
        } catch (Exception e) {
            status.setCode(co.edu.javeriana.campaigns.domain.Status.ERROR.name());
            status.setDescription(String.format("Error getting rows: %s", e.getMessage()));
            response.setStatus(status);

            return CompletableFuture.completedFuture(response);
        }
    }

    private void callImages(Optional<List<Campaigns>> campaigns) {

        String ids = "";

        for (Campaigns row : campaigns.get()) {
            ids = ids.concat(row.getImage().getId()).concat(",");
        }

        LOG.info(ids.substring(0, ids.length() - 1));

        Request request = new Request();
        request.setIds(ids.substring(0, ids.length() - 1));

        AsyncRabbitTemplate.RabbitConverterFuture<ResponseImage> future = this.template.convertSendAndReceiveAsType(
                                                                                            rpcImagesExchange,
                                                                                            rpcImagesRoutingKey,
                                                                                            request,
                                                                                            new ParameterizedTypeReference<>() {});

        try {
            ResponseImage res = future.get();
            LOG.info("Message received: {}", res);

            for (Campaigns row : campaigns.get()) {
                for (Image i : res.getImages()) {
                    if (row.getImage().getId().equalsIgnoreCase(i.getId())) {
                        row.getImage().setUrl(i.getUrl());
                    }
                }
            }

        } catch (InterruptedException | ExecutionException e) {
            LOG.error("Cannot get response.", e);
        }

    }

}
