package ua.nure.tur.db;

import ua.nure.tur.db.spesifications.NameSpecification;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public interface SearchSettings {

    String buildQueryConditions();

    String buildFilterPart();

    void prepareStatement(PreparedStatement statement) throws SQLException;

    void setSearchSpecifications(List<SearchSpecification> specifications);

    void setOrderSpecification(String summary_rating);
    
    void setDesc(boolean b);

    void setLimit(int limit);

    void addSearchSpecification(SearchSpecification nameSpecification);

    void setOffset(int offset);
}
