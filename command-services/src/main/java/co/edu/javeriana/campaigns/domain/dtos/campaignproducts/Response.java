package co.edu.javeriana.campaigns.domain.dtos.campaignproducts;

import lombok.Data;

import java.util.List;

@Data
public class Response implements java.io.Serializable {

    private Status status;
    private List<Product> product;

}
