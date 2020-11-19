package co.edu.javeriana.campaigns.dtos.campaignproduct;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

@Data
@ApiModel(description = "Estructura con la informacion de los productos asociados con las campanias registradas")
public class CampaignProductDto implements java.io.Serializable {

    @ApiModelProperty(notes = "Campo que indica el identificador de la campania")
    private String id;

    @ApiModelProperty(notes = "Listado de campania y sus productos")
    private Map<String, Object> data;

}
