package co.edu.javeriana.campaigns.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CampaignProduct implements java.io.Serializable {

    private String campaignProductId;
    private String campaignId;
    private String productId;
    private String action;
    private String status;

}