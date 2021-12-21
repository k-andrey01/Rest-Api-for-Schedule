package rssapijava.repository;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import rssapijava.entity.Schedule;

import java.sql.Types;
import java.util.ArrayList;

@Repository
public class ScheduleRepository implements IRestRepository<Schedule>
{
    protected final JdbcOperations jdbcOperations;

    private static String selectQuery = "SELECT \"id\", \"place_id\", \"performance_id\", \"date_time\" "
            + "FROM \"schedule\" "
            + "ORDER BY \"id\"";

    private static String selectByIdQuery = "SELECT \"id\", \"place_id\", \"performance_id\", \"date_time\""
            + "FROM \"schedule\" "
            + "WHERE \"id\"=?";

    private static String selectByPlaceIdQuery = "SELECT \"id\", \"place_id\", \"performance_id\", \"date_time\""
            + "FROM \"schedule\" "
            + "WHERE \"place_id\"=?";

    private static String selectByPerformanceIdQuery = "SELECT \"id\", \"place_id\", \"performance_id\", \"date_time\""
            + "FROM \"schedule\" "
            + "WHERE \"performance_id\"=?";

    private static String insertQuery = "INSERT INTO \"schedule\"(\"place_id\", \"performance_id\", \"date_time\") " +
            "VALUES (?, ?, ?) " +
            "RETURNING \"id\", \"place_id\", \"performance_id\", \"date_time\"";

    private static String updateQuery = "UPDATE \"schedule\" " +
            "SET \"place_id\"=?, \"performance_id\"=?, \"date_time\"=?" +
            "WHERE \"id\" = ? " +
            "RETURNING \"id\", \"place_id\", \"performance_id\", \"date_time\"";

    private static String deleteQuery = "DELETE FROM \"schedule\" " +
            "WHERE \"id\" = ? " +
            "RETURNING \"id\", \"place_id\", \"performance_id\", \"date_time\"";

    public ScheduleRepository(JdbcOperations JdbcOperations)
    {
        this.jdbcOperations = JdbcOperations;
    }

    @Override
    public Schedule[] select()
    {
        ArrayList<Schedule> values = new ArrayList<Schedule>();
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectQuery);
        while(rowSet.next())
        {
            values.add(new Schedule(
                    rowSet.getInt(1),
                    rowSet.getInt(2),
                    rowSet.getInt(3),
                    rowSet.getTimestamp(4)));
        }
        Schedule[] result = new Schedule[values.size()];
        result = values.toArray(result);
        return result;
    }

    @Override
    public Schedule select(Integer id)
    {
        Object[] params = new Object[] {id};
        int[] types = new int[] {Types.INTEGER};
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByIdQuery, params, types);
        if(!rowSet.next())
        {
            return null;
        }
        return new Schedule(
                rowSet.getInt(1),
                rowSet.getInt(2),
                rowSet.getInt(3),
                rowSet.getTimestamp(4)
        );
    }

    public Schedule[] selectByPlaceId(Integer placeId)
    {
        ArrayList<Schedule> values = new ArrayList<Schedule>();
        Object[] params = new Object[] { placeId };
        int[] types = new int[] { Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByPlaceIdQuery, params, types);
        while (rowSet.next()) {
            values.add(new Schedule(
                    rowSet.getInt(1),
                    rowSet.getInt(2),
                    rowSet.getInt(3),
                    rowSet.getTimestamp(4)
            ));
        }
        Schedule[] result = new Schedule[values.size()];
        result = values.toArray(result);
        return result;
    }

    public Schedule[] selectByPerformanceId(Integer performanceId)
    {
        ArrayList<Schedule> values = new ArrayList<Schedule>();
        Object[] params = new Object[] { performanceId };
        int[] types = new int[] { Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByPerformanceIdQuery, params, types);
        while (rowSet.next()) {
            values.add(new Schedule(
                    rowSet.getInt(1),
                    rowSet.getInt(2),
                    rowSet.getInt(3),
                    rowSet.getTimestamp(4)
            ));
        }
        Schedule[] result = new Schedule[values.size()];
        result = values.toArray(result);
        return result;
    }

    @Override
    public Schedule insert(Schedule entity)
    {
        Object[] params = new Object[] { entity.getPlaceId(), entity.getPerformanceId(), entity.getDate() };
        int[] types = new int[] { Types.INTEGER, Types.INTEGER, Types.TIMESTAMP };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(insertQuery, params, types);
        if (!rowSet.next())
        {
            return null;
        }
        return new Schedule(
                rowSet.getInt(1),
                rowSet.getInt(2),
                rowSet.getInt(3),
                rowSet.getTimestamp(4)
        );
    }

    @Override
    public Schedule update(Integer id, Schedule entity)
    {
        Object[] params = new Object[] { entity.getPlaceId(), entity.getPerformanceId(), entity.getDate(), id };
        int[] types = new int[] { Types.INTEGER, Types.INTEGER, Types.TIMESTAMP, Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(updateQuery, params, types);
        if (!rowSet.next())
        {
            return null;
        }
        return new Schedule(
                rowSet.getInt(1),
                rowSet.getInt(2),
                rowSet.getInt(3),
                rowSet.getTimestamp(4)
        );
    }

    @Override
    public Schedule delete(Integer id)
    {
        Object[] params = new Object[] { id };
        int[] types = new int[] {Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(deleteQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Schedule(
                rowSet.getInt(1),
                rowSet.getInt(2),
                rowSet.getInt(3),
                rowSet.getTimestamp(4)
        );
    }
}
