package co.edu.javeriana.campaigns.dtos.campaing;

import co.edu.javeriana.campaigns.domain.Image;
import co.edu.javeriana.campaigns.dtos.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseImage implements java.io.Serializable {

    private Status status;
    private List<Image> images;

}
