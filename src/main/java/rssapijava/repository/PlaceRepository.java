package rssapijava.repository;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import rssapijava.entity.Place;

import java.sql.Types;
import java.util.ArrayList;

@Repository
public class PlaceRepository implements IRestRepository<Place>
{
    protected final JdbcOperations jdbcOperations;

    private static String selectQuery = "SELECT \"id\", \"city_id\", \"name\", \"street\", \"house\" "
            + "FROM \"place\" "
            + "ORDER BY \"id\"";

    private static String selectByIdQuery = "SELECT \"id\", \"city_id\", \"name\", \"street\", \"house\" "
            + "FROM \"place\" "
            + "WHERE \"id\"=?";

    private static String selectByCityIdQuery = "SELECT \"id\", \"city_id\", \"name\", \"street\", \"house\" "
            + "FROM \"place\" "
            + "WHERE \"city_id\"=?";

    private static String insertQuery = "INSERT INTO \"place\"(\"city_id\", \"name\", \"street\", \"house\") " +
            "VALUES (?, ?, ?, ?) " +
            "RETURNING \"id\", \"city_id\", \"name\", \"street\", \"house\"";

    private static String updateQuery = "UPDATE \"place\" " +
            "SET \"city_id\" = ?, \"name\" = ?, \"street\"=?, \"house\"=? " +
            "WHERE \"id\" = ? " +
            "RETURNING \"id\", \"city_id\", \"name\", \"street\", \"house\"";

    private static String deleteQuery = "DELETE FROM \"place\" " +
            "WHERE \"id\" = ? " +
            "RETURNING \"id\", \"city_id\", \"name\", \"street\", \"house\"";

    public PlaceRepository(JdbcOperations JdbcOperations)
    {
        this.jdbcOperations = JdbcOperations;
    }

    @Override
    public Place[] select()
    {
        ArrayList<Place> values = new ArrayList<Place>();
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectQuery);
        while(rowSet.next())
        {
            values.add(new Place(
                    rowSet.getInt(1),
                    rowSet.getInt(2),
                    rowSet.getString(3),
                    rowSet.getString(4),
                    rowSet.getString(5)));
        }
        Place[] result = new Place[values.size()];
        result = values.toArray(result);
        return result;
    }

    @Override
    public Place select(Integer id)
    {
        Object[] params = new Object[] {id};
        int[] types = new int[] {Types.INTEGER};
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByIdQuery, params, types);
        if(!rowSet.next())
        {
            return null;
        }
        return new Place(
                rowSet.getInt(1),
                rowSet.getInt(2),
                rowSet.getString(3),
                rowSet.getString(4),
                rowSet.getString(5)
        );
    }

    public Place[] selectByCityId(Integer cityId)
    {
        ArrayList<Place> values = new ArrayList<Place>();
        Object[] params = new Object[] { cityId };
        int[] types = new int[] { Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByCityIdQuery, params, types);
        while (rowSet.next()) {
            values.add(new Place(
                    rowSet.getInt(1),
                    rowSet.getInt(2),
                    rowSet.getString(3),
                    rowSet.getString(4),
                    rowSet.getString(5)
            ));
        }
        Place[] result = new Place[values.size()];
        result = values.toArray(result);
        return result;
    }

    @Override
    public Place insert(Place entity)
    {
        Object[] params = new Object[] { entity.getCityId(), entity.getName(), entity.getStreet(), entity.getHouse() };
        int[] types = new int[] { Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(insertQuery, params, types);
        if (!rowSet.next())
        {
            return null;
        }
        return new Place(
                rowSet.getInt(1),
                rowSet.getInt(2),
                rowSet.getString(3),
                rowSet.getString(4),
                rowSet.getString(5)
        );
    }

    @Override
    public Place update(Integer id, Place entity)
    {
        Object[] params = new Object[] { entity.getCityId(), entity.getName(), entity.getStreet(), entity.getHouse(), id };
        int[] types = new int[] { Types.INTEGER, Types.VARCHAR, Types.VARCHAR,Types.VARCHAR, Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(updateQuery, params, types);
        if (!rowSet.next())
        {
            return null;
        }
        return new Place(
                rowSet.getInt(1),
                rowSet.getInt(2),
                rowSet.getString(3),
                rowSet.getString(4),
                rowSet.getString(5)
        );
    }

    @Override
    public Place delete(Integer id)
    {
        Object[] params = new Object[] { id };
        int[] types = new int[] {Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(deleteQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Place(
                rowSet.getInt(1),
                rowSet.getInt(2),
                rowSet.getString(3),
                rowSet.getString(4),
                rowSet.getString(5)
        );
    }
}
