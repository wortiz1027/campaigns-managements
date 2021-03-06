package co.edu.javeriana.campaigns.dtos.campaignproduct;

import co.edu.javeriana.campaigns.domain.Image;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDto implements java.io.Serializable {

    private String productId;
    private String productCode;
    private String productName;
    private String productDescription;
    private LocalDate startDate;
    private LocalDate endDate;
    private ProductType type;
    private Long productPrice;
    private String originCity;
    private String destinationCity;
    private Image image;
    private Vendor vendor;
    private String status;
}
