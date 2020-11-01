package co.edu.javeriana.campaigns.dtos.campaing;

import co.edu.javeriana.campaigns.dtos.Status;
import lombok.Data;

import java.util.List;

@Data
public class Response implements java.io.Serializable {

    private Status status;
    private List<CampaingDto> campaings;

}
