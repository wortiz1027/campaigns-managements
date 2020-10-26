package co.edu.javeriana.campaigns.infraestructure.controller.campaignproduct;

import co.edu.javeriana.campaigns.application.campaignsproducts.CampaignProductCommandService;
import co.edu.javeriana.campaigns.domain.Status;
import co.edu.javeriana.campaigns.domain.dtos.campaignproducts.Request;
import co.edu.javeriana.campaigns.domain.dtos.campaignproducts.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ManagementCampaignProductCommandController {

    private final CampaignProductCommandService service;

    @PostMapping("/campaigns/products")
    public ResponseEntity<CompletableFuture<Response>> management(@RequestBody Request data) throws ExecutionException, InterruptedException {

        if (data == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (data.getProducts() == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (data.getProducts().size() == 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        CompletableFuture<Response> rs = service.mamnageCampaignProduct(data.getProducts());

        if (rs.get().getStatus().getStatus().equalsIgnoreCase(Status.SUCCESS.name()))
            return new ResponseEntity<>(rs, HttpStatus.OK);

        if (rs.get().getStatus().getStatus().equalsIgnoreCase(Status.ERROR.name()))
            return new ResponseEntity<>(rs, HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(rs, HttpStatus.CONFLICT);
    }

}
