package co.edu.javeriana.campaigns.dtos.campaing;

import co.edu.javeriana.campaigns.dtos.Status;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

@Data
@ApiModel(description = "Estructura con la informacion de las campanias registradas")
public class Response implements java.io.Serializable {

    @ApiModelProperty(notes = "Campo que indica el estado de la consulta")
    private Status status;

    @ApiModelProperty(notes = "Listado de campanias registradas en el sistema de toures balon")
    private Map<String, Object> data;

}
