package co.edu.javeriana.campaigns.infraestructure.repository.campaings;

import co.edu.javeriana.campaigns.domain.Campaigns;
import co.edu.javeriana.campaigns.domain.Image;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CampaignsRowMapper implements RowMapper<Campaigns> {

    @Override
    public Campaigns mapRow(ResultSet rs, int i) throws SQLException {

        Image image = new Image();
        image.setId(rs.getString("IMAGE_ID"));

        Campaigns campaigns = new Campaigns();
        campaigns.setCampaignId(rs.getString("CAMPAIGNS_ID"));
        campaigns.setCampaignCode(rs.getString("CAMPAIGNS_CODE"));
        campaigns.setCampaignName(rs.getString("CAMPAIGNS_NAME"));
        campaigns.setCampaignDescription(rs.getString("CAMPAIGNS_DESC"));
        campaigns.setImage(image);
        campaigns.setStartDate(rs.getDate("START_DATE").toLocalDate());
        campaigns.setEndDate(rs.getDate("END_DATE").toLocalDate());
        campaigns.setDiscount(rs.getBigDecimal("DISCOUNT"));
        campaigns.setStatus(rs.getString("STATUS"));

        return campaigns;
    }
}
