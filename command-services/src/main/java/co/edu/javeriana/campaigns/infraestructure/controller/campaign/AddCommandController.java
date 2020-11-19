package co.edu.javeriana.campaigns.infraestructure.controller.campaign;


import co.edu.javeriana.campaigns.application.campaigns.CampaignCommandService;
import co.edu.javeriana.campaigns.domain.Campaigns;
import co.edu.javeriana.campaigns.domain.Status;
import co.edu.javeriana.campaigns.domain.dtos.campaign.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api(value="Registro de campanias ofrecidos por toures balon")
public class AddCommandController {

    private final CampaignCommandService service;

    @ApiOperation(value = "Creacion de campanias en el sistema", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Creacion exitosa de la campania"),
            @ApiResponse(code = 400, message = "Error en los datos de entrada no se envio informacion de la campania"),
            @ApiResponse(code = 500, message = "Error interno en el servidor, contacte y reporte con el administrador")
    })
    @PostMapping("/campaigns")
    public ResponseEntity<CompletableFuture<Response>> create(@RequestBody Campaigns data) throws ExecutionException, InterruptedException {

        if (data == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        CompletableFuture<Response> rs = service.createCampaigns(data);

        if (rs.get().getStatus().equalsIgnoreCase(Status.CREATED.name()))
            return new ResponseEntity<>(rs, HttpStatus.CREATED);

        if (rs.get().getStatus().equalsIgnoreCase(Status.ERROR.name()))
            return new ResponseEntity<>(rs, HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(rs, HttpStatus.CONFLICT);
    }

}
