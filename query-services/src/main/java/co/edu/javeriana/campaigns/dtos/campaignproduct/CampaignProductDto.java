package co.edu.javeriana.campaigns.dtos.campaignproduct;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class CampaignProductDto implements java.io.Serializable {

    private String id;
    private Map<String, Object> data;

}
