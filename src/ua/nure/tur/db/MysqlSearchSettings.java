package ua.nure.tur.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class MysqlSearchSettings extends AbstractSearchSettings {


    @Override
    public String buildQueryConditions() {
        StringBuilder queryBuilder = new StringBuilder();

        queryBuilder.append(buildFilterPart());


        if (orderSpecification != null) {
            queryBuilder.append(" ORDER BY ");

            queryBuilder.append(orderSpecification);
            if (desc) {
                queryBuilder.append(" DESC ");
            }
        }

        if (limit != 0) {
            queryBuilder.append(" LIMIT ").append(limit);
        }
        if (offset != 0) {
            queryBuilder.append(" OFFSET ").append(offset);
        }
        return queryBuilder.toString();
    }




}
