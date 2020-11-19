package co.edu.javeriana.campaigns.domain;

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
@ApiModel(description = "Estructura para el manejo de la informacion de la campania")
public class Campaigns implements java.io.Serializable {

    @ApiModelProperty(notes = "Campo con el identificador de la campania")
    private String campaignId;

    @ApiModelProperty(notes = "Campo con el codigo de la campania")
    private String campaignCode;

    @ApiModelProperty(notes = "Campo con el nombre de la campania")
    private String campaignName;

    @ApiModelProperty(notes = "Campo con la descripcion del detalle de la campania")
    private String campaignDescription;

    @ApiModelProperty(notes = "Estructura con el detalle de la imagen")
    private Image image;

    @ApiModelProperty(notes = "Fecha de inicio de la vigencia de la campania")
    private LocalDate startDate;

    @ApiModelProperty(notes = "Fecha final de la vigencia de la campania")
    private LocalDate endDate;

    @ApiModelProperty(notes = "Campo con informacion del descuento de la campania")
    private BigDecimal discount;

    @ApiModelProperty(notes = "Campo con el estado de la campania")
    private String status;

    @ApiModelProperty(notes = "Campo con la informacion de la accion a ejecutar de la campania")
    private String action;

}
