package co.edu.javeriana.campaigns.dtos.campaignproduct;

import lombok.Data;

import java.util.List;

@Data
public class CampaignProductDto implements java.io.Serializable {

    private String id;
    private List<ProductDto> products;

}