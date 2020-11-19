package co.edu.javeriana.campaigns.dtos.campaignproduct;

import co.edu.javeriana.campaigns.dtos.Status;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Estructura con la informacion de los productos asociados con las campanias registradas")
public class Response implements java.io.Serializable {

    @ApiModelProperty(notes = "Campo que indica el estado de la consulta")
    private Status status;

    @ApiModelProperty(notes = "Estructura con la informacion de la campania consultada y sus productos")
    private CampaignProductDto campaign;

}
