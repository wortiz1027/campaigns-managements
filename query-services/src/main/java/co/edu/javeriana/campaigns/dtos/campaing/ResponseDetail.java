package co.edu.javeriana.campaigns.dtos.campaing;

import co.edu.javeriana.campaigns.dtos.Status;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Estructura con la informacion de la campania registrada")
public class ResponseDetail implements java.io.Serializable {

    @ApiModelProperty(notes = "Campo que indica el estado de la consulta")
    private Status status;

    @ApiModelProperty(notes = "Estructura con el detalle de la campania")
    private CampaingDto campaing;

}
