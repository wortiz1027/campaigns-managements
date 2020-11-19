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
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Api(value="Actualizacion de la campania ofrecidos por toures balon")
public class UpdateCommandController {

    private final CampaignCommandService service;

    @ApiOperation(value = "Actualizacion de la campania en el sistema", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Actualizacion exitosa de la campania"),
            @ApiResponse(code = 400, message = "Error en los datos de entrada no se envio informacion de la campania"),
            @ApiResponse(code = 500, message = "Error interno en el servidor, contacte y reporte con el administrador")
    })
    @PutMapping("/campaigns")
    public ResponseEntity<CompletableFuture<Response>> update(@RequestBody Campaigns data) throws ExecutionException, InterruptedException {

        if (data == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        CompletableFuture<Response> rs = service.updateCampaigns(data);

        if (rs.get().getStatus().equalsIgnoreCase(Status.UPDATED.name()))
            return new ResponseEntity<>(rs, HttpStatus.OK);

        if (rs.get().getStatus().equalsIgnoreCase(Status.ERROR.name()))
            return new ResponseEntity<>(rs, HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(rs, HttpStatus.CONFLICT);
    }

}
