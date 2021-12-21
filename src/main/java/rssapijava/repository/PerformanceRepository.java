package rssapijava.repository;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import rssapijava.entity.Performance;

import java.sql.Types;
import java.util.ArrayList;

@Repository
public class PerformanceRepository implements IRestRepository<Performance>
{
    protected final JdbcOperations jdbcOperations;

    private static String selectQuery = "SELECT \"id\", \"type_id\", \"language_id\", \"name\" "
            + "FROM \"performance\" "
            + "ORDER BY \"id\"";

    private static String selectByIdQuery = "SELECT \"id\", \"type_id\", \"language_id\", \"name\""
            + "FROM \"performance\" "
            + "WHERE \"id\"=?";

    private static String selectByTypeIdQuery = "SELECT \"id\", \"type_id\", \"language_id\", \"name\""
            + "FROM \"performance\" "
            + "WHERE \"type_id\"=?";

    private static String selectByLanguageIdQuery = "SELECT \"id\", \"type_id\", \"language_id\", \"name\""
            + "FROM \"performance\" "
            + "WHERE \"language_id\"=?";

    private static String insertQuery = "INSERT INTO \"performance\"(\"type_id\", \"language_id\", \"name\") " +
            "VALUES (?, ?, ?) " +
            "RETURNING \"id\", \"type_id\", \"language_id\", \"name\"";

    private static String updateQuery = "UPDATE \"performance\" " +
            "SET \"type_id\"=?, \"language_id\"=?, \"name\"=? " +
            "WHERE \"id\" = ? " +
            "RETURNING \"id\", \"type_id\", \"language_id\", \"name\"";

    private static String deleteQuery = "DELETE FROM \"performance\" " +
            "WHERE \"id\" = ? " +
            "RETURNING \"id\", \"type_id\", \"language_id\", \"name\"";

    public PerformanceRepository(JdbcOperations JdbcOperations)
    {
        this.jdbcOperations = JdbcOperations;
    }

    @Override
    public Performance[] select()
    {
        ArrayList<Performance> values = new ArrayList<Performance>();
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectQuery);
        while(rowSet.next())
        {
            values.add(new Performance(
                    rowSet.getInt(1),
                    rowSet.getInt(2),
                    rowSet.getInt(3),
                    rowSet.getString(4)));
        }
        Performance[] result = new Performance[values.size()];
        result = values.toArray(result);
        return result;
    }

    @Override
    public Performance select(Integer id)
    {
        Object[] params = new Object[] {id};
        int[] types = new int[] {Types.INTEGER};
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByIdQuery, params, types);
        if(!rowSet.next())
        {
            return null;
        }
        return new Performance(
                rowSet.getInt(1),
                rowSet.getInt(2),
                rowSet.getInt(3),
                rowSet.getString(4)
        );
    }

    public Performance[] selectByTypeId(Integer typeId)
    {
        ArrayList<Performance> values = new ArrayList<Performance>();
        Object[] params = new Object[] { typeId };
        int[] types = new int[] { Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByTypeIdQuery, params, types);
        while (rowSet.next()) {
            values.add(new Performance(
                    rowSet.getInt(1),
                    rowSet.getInt(2),
                    rowSet.getInt(3),
                    rowSet.getString(4)
            ));
        }
        Performance[] result = new Performance[values.size()];
        result = values.toArray(result);
        return result;
    }

    public Performance[] selectByLanguageId(Integer languageId)
    {
        ArrayList<Performance> values = new ArrayList<Performance>();
        Object[] params = new Object[] { languageId };
        int[] types = new int[] { Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByLanguageIdQuery, params, types);
        while (rowSet.next()) {
            values.add(new Performance(
                    rowSet.getInt(1),
                    rowSet.getInt(2),
                    rowSet.getInt(3),
                    rowSet.getString(4)
            ));
        }
        Performance[] result = new Performance[values.size()];
        result = values.toArray(result);
        return result;
    }

    @Override
    public Performance insert(Performance entity)
    {
        Object[] params = new Object[] { entity.getTypeId(), entity.getLanguageId(), entity.getName() };
        int[] types = new int[] { Types.INTEGER, Types.INTEGER, Types.VARCHAR };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(insertQuery, params, types);
        if (!rowSet.next())
        {
            return null;
        }
        return new Performance(
                rowSet.getInt(1),
                rowSet.getInt(2),
                rowSet.getInt(3),
                rowSet.getString(4)
        );
    }

    @Override
    public Performance update(Integer id, Performance entity)
    {
        Object[] params = new Object[] { entity.getTypeId(), entity.getLanguageId(), entity.getName(), id };
        int[] types = new int[] { Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(updateQuery, params, types);
        if (!rowSet.next())
        {
            return null;
        }
        return new Performance(
                rowSet.getInt(1),
                rowSet.getInt(2),
                rowSet.getInt(3),
                rowSet.getString(4)
        );
    }

    @Override
    public Performance delete(Integer id)
    {
        Object[] params = new Object[] { id };
        int[] types = new int[] {Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(deleteQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Performance(
                rowSet.getInt(1),
                rowSet.getInt(2),
                rowSet.getInt(3),
                rowSet.getString(4)
        );
    }
}
