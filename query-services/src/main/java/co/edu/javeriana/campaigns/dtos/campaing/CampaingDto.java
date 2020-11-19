package co.edu.javeriana.campaigns.dtos.campaing;

import co.edu.javeriana.campaigns.domain.Image;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "Estructura con la informacion del detalle de la campania registradas")
public class CampaingDto implements java.io.Serializable {

    @ApiModelProperty(notes = "Campo con el identificador de la campania")
    private String campaignId;

    @ApiModelProperty(notes = "Campo con el codigo asociado a la campania")
    private String campaignCode;

    @ApiModelProperty(notes = "Campo con el nombre que describe la campania")
    private String campaignName;

    @ApiModelProperty(notes = "Campo con la descripcion adicional de la campania")
    private String campaignDescription;

    @ApiModelProperty(notes = "Estructura con la informacion de la imagen asociada a la campania")
    private Image image;

    @ApiModelProperty(notes = "Campo con la fecha inicial de la campania")
    private LocalDate startDate;

    @ApiModelProperty(notes = "Campo con la fecha de finalizacion de la campania")
    private LocalDate endDate;

    @ApiModelProperty(notes = "Campo con la informacion del descuento que aplica en la campania")
    private BigDecimal discount;

    @ApiModelProperty(notes = "Campo con el estado de la campania")
    private String status;

    @ApiModelProperty(notes = "Campo con la informacion de la accion a ejecutar")
    private String action;

}
