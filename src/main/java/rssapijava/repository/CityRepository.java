package rssapijava.repository;

import org.springframework.stereotype.Repository;
import rssapijava.entity.City;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import java.sql.Types;
import java.util.ArrayList;

@Repository
public class CityRepository implements IRestRepository<City> {
    protected final JdbcOperations jdbcOperations;

    private static String selectQuery = "SELECT \"id\", \"name\" "
            + "FROM \"city\" "
            + "ORDER BY \"id\"";

    private static String selectByIdQuery = "SELECT \"id\", \"name\" "
            + "FROM \"city\" "
            + "WHERE \"id\" = ?";

    private static String insertQuery = "INSERT INTO \"city\"(\"name\") "
            + "VALUES (?) "
            + "RETURNING \"id\", \"name\"";

    private static String updateQuery = "UPDATE \"city\" "
            + "SET \"name\" = ?"
            + "WHERE \"id\" = ? "
            + "RETURNING \"id\", \"name\"";

    private static String deleteQuery = "DELETE FROM \"city\" "
            + "WHERE \"id\" = ? "
            + "RETURNING \"id\", \"name\"";

    public CityRepository(JdbcOperations JdbcOperations) {
        this.jdbcOperations = JdbcOperations;
    }

    @Override
    public City[] select() {
        ArrayList<City> values = new ArrayList<City>();
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectQuery);
        while (rowSet.next()) {
            values.add(new City(
                    rowSet.getInt(1),
                    rowSet.getString(2)));
        }
        City[] result = new City[values.size()];
        result = values.toArray(result);
        return result;
    }

    @Override
    public City select(Integer id) {
        Object[] params = new Object[]{id};
        int[] types = new int[]{Types.INTEGER};
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByIdQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new City(
                rowSet.getInt(1),
                rowSet.getString(2)
        );
    }

    @Override
    public City insert(City entity) {
        Object[] params = new Object[]{entity.getName()};
        int[] types = new int[]{Types.VARCHAR};
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(insertQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new City(
                rowSet.getInt(1),
                rowSet.getString(2)
        );
    }

    @Override
    public City update(Integer id, City entity) {
        Object[] params = new Object[]{entity.getName(), id};
        int[] types = new int[]{Types.VARCHAR, Types.INTEGER};
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(updateQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new City(
                rowSet.getInt(1),
                rowSet.getString(2)
        );
    }

    @Override
    public City delete(Integer id) {
        Object[] params = new Object[]{id};
        int[] types = new int[]{Types.INTEGER};
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(deleteQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new City(
                rowSet.getInt(1),
                rowSet.getString(2)
        );
    }
}
