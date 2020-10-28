package co.edu.javeriana.campaigns.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Campaigns implements java.io.Serializable {

    private String campaignId;
    private String campaignCode;
    private String campaignName;
    private String campaignDescription;
    private Image image;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal discount;
    private String status;
    private String action;

}