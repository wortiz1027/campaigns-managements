package co.edu.javeriana.campaigns.domain.dtos.campaignproducts;

import co.edu.javeriana.campaigns.domain.CampaignProduct;
import lombok.Data;

import java.util.List;

@Data
public class Request implements java.io.Serializable {

    private List<CampaignProduct> products;

}
