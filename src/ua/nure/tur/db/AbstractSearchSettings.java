package ua.nure.tur.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSearchSettings implements SearchSettings {

    protected List<SearchSpecification> searchSpecifications;

    protected String orderSpecification;

    protected boolean desc;

    protected int limit;

    protected int offset;

    public void setSearchSpecifications(List<SearchSpecification> searchSpecifications) {
        this.searchSpecifications = searchSpecifications;
    }

    public void setOrderSpecification(String orderSpecification) {
        this.orderSpecification = orderSpecification;
    }

    public void setDesc(boolean desc) {
        this.desc = desc;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void addSearchSpecification(SearchSpecification item) {
        if (searchSpecifications == null) {
            searchSpecifications = new ArrayList<>();
        }
        searchSpecifications.add(item);
    }

    @Override
    public String buildFilterPart() {
        StringBuilder queryBuilder = new StringBuilder();
        if (searchSpecifications != null) {
            for (int i = 0; i < searchSpecifications.size(); i++) {
                if (i == 0) {
                    queryBuilder.append(" WHERE ");
                } else {
                    queryBuilder.append(" AND ");
                }
                queryBuilder.append(searchSpecifications.get(i).getCondition());
            }
        }
        return queryBuilder.toString();
    }

    @Override
    public void prepareStatement(PreparedStatement statement) throws SQLException {
        if (searchSpecifications != null) {
            for (int i = 0; i < searchSpecifications.size(); i++) {
                searchSpecifications.get(i).prepareStatement(statement, i + 1);
            }
        }
    }


}
