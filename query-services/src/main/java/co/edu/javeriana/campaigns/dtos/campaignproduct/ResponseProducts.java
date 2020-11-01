package co.edu.javeriana.campaigns.dtos.campaignproduct;

import co.edu.javeriana.campaigns.dtos.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseProducts implements java.io.Serializable {

    private Status status;
    private List<ProductDto> products;

}
