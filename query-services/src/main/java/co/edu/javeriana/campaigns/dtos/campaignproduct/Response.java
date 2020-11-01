package co.edu.javeriana.campaigns.dtos.campaignproduct;

import co.edu.javeriana.campaigns.dtos.Status;
import lombok.Data;

@Data
public class Response implements java.io.Serializable {

    private Status status;
    private CampaignProductDto campaign;

}
