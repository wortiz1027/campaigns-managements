package co.edu.javeriana.campaigns.infraestructure.controllers.campaignsproducts;

import co.edu.javeriana.campaigns.application.campaingproduct.CampaignProductsServices;
import co.edu.javeriana.campaigns.domain.CampaignProduct;
import co.edu.javeriana.campaigns.domain.Status;
import co.edu.javeriana.campaigns.dtos.campaignproduct.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Api(value="Consultas de los productos asociados a las campanias ofrecidas por toures balon")
public class CampaignsProductQueryController {

    private final CampaignProductsServices services;

    @ApiOperation(value = "Consulta de los productos asociados a la campanmia registrada en el sistema", response = co.edu.javeriana.campaigns.dtos.campaing.Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Consulta exitosa  de los productos y las campanias"),
            @ApiResponse(code = 404, message = "Error no se encontro informacion de las campanias y sus productos"),
            @ApiResponse(code = 500, message = "Error interno en el servidor, contacte y reporte con el administrador")
    })
    @GetMapping("/campaigns/{id}/products")
    public ResponseEntity<CompletableFuture<Response>> all(@PathVariable("id") String id,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "5") int size) throws ExecutionException, InterruptedException {
        Pageable paging = PageRequest.of(page, size);
        CampaignProduct data = new CampaignProduct();
        data.setCampaignId(id);

        CompletableFuture<Response> rs = this.services.getCampaignProducts(data, paging);

        if (rs.get().getStatus().getCode().equalsIgnoreCase(Status.SUCCESS.name()))
            return new ResponseEntity<>(rs, HttpStatus.OK);

        if (rs.get().getStatus().getCode().equalsIgnoreCase(Status.EMPTY.name()))
            return new ResponseEntity<>(rs, HttpStatus.NOT_FOUND);

        if (rs.get().getStatus().getCode().equalsIgnoreCase(Status.ERROR.name()))
            return new ResponseEntity<>(rs, HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(rs, HttpStatus.ACCEPTED);
    }

}
