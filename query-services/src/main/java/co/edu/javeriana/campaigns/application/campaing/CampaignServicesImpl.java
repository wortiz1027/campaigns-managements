package co.edu.javeriana.campaigns.application.campaing;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class CampaignServicesImpl implements CampaignServices {

    @Override
    public CompletableFuture<Response> getAllCampaign(Pageable paging) {
        return null;
    }

    @Override
    public CompletableFuture<ResponseProduct> getCampaignByText(String text, Pageable paging) {
        return null;
    }

    @Override
    public CompletableFuture<ResponseProductDetail> getCampaignDetail(String code) {
        return null;
    }

}
