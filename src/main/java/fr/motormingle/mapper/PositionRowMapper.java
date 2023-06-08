package fr.motormingle.mapper;

import fr.motormingle.model.Position;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class PositionRowMapper implements RowMapper<Position> {
    @Override
    public Position mapRow(ResultSet rs, int rowNum) throws SQLException {
        Position position = new Position();
        position.setLatitude(rs.getDouble("latitude"));
        position.setLongitude(rs.getDouble("longitude"));
        position.setUserId(UUID.fromString(rs.getString("user_id")));
        position.setDate(rs.getTimestamp("date").toLocalDateTime());
        return position;
    }
}
