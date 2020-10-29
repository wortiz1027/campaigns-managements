package co.edu.javeriana.campaigns.dtos.campaignproduct;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductType implements java.io.Serializable {
    private String id;
    private String description;
}
