package co.edu.javeriana.campaigns.application.campaing;

import org.springframework.data.domain.Pageable;

import java.util.concurrent.CompletableFuture;

public interface CampaignServices {

    CompletableFuture<Response> getAllCampaign(Pageable paging);
    CompletableFuture<ResponseProduct> getCampaignByText(String text, Pageable paging);
    CompletableFuture<ResponseProductDetail> getCampaignDetail(String code);

}
