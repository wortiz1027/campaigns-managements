package co.edu.javeriana.campaigns.application.campaing;

import co.edu.javeriana.campaigns.dtos.campaing.Response;
import co.edu.javeriana.campaigns.dtos.campaing.ResponseDetail;
import org.springframework.data.domain.Pageable;

import java.util.concurrent.CompletableFuture;

public interface CampaignServices {

    CompletableFuture<Response> getAllCampaign(Pageable paging);
    CompletableFuture<Response> getCampaignByText(String text, Pageable paging);
    CompletableFuture<ResponseDetail> getCampaignDetail(String code);

}
