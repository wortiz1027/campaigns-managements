package co.edu.javeriana.campaigns.dtos.campaing;

import co.edu.javeriana.campaigns.dtos.Status;
import lombok.Data;

@Data
public class ResponseDetail implements java.io.Serializable {

    private Status status;
    private CampaingDto campaing;

}
