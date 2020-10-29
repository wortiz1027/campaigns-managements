package co.edu.javeriana.campaigns.infraestructure.repository.campaignsproduct;

import co.edu.javeriana.campaigns.domain.CampaignProduct;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CampaignsProductsRowMapper implements RowMapper<CampaignProduct> {


    @Override
    public CampaignProduct mapRow(ResultSet rs, int i) throws SQLException {
        CampaignProduct products = new CampaignProduct();

        products.setCampaignProductId(rs.getString("CAMPAIGNS_PRODUCTS_ID"));
        products.setCampaignId(rs.getString("CAMPAIGNS_ID"));
        products.setProductId(rs.getString("PRODUCT_ID"));

        return products;
    }
}
