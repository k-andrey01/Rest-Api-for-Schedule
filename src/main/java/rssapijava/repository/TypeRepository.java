package rssapijava.repository;

import org.springframework.stereotype.Repository;
import rssapijava.entity.Type;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import java.sql.Types;
import java.util.ArrayList;

@Repository
public class TypeRepository implements IRestRepository<Type> {
    protected final JdbcOperations jdbcOperations;

    private static String selectQuery = "SELECT \"id\", \"name\" "
            + "FROM \"type\" "
            + "ORDER BY \"id\"";

    private static String selectByIdQuery = "SELECT \"id\", \"name\" "
            + "FROM \"type\" "
            + "WHERE \"id\" = ?";

    private static String insertQuery = "INSERT INTO \"type\"(\"name\") "
            + "VALUES (?) "
            + "RETURNING \"id\", \"name\"";

    private static String updateQuery = "UPDATE \"type\" "
            + "SET \"name\" = ?"
            + "WHERE \"id\" = ? "
            + "RETURNING \"id\", \"name\"";

    private static String deleteQuery = "DELETE FROM \"type\" "
            + "WHERE \"id\" = ? "
            + "RETURNING \"id\", \"name\"";

    public TypeRepository(JdbcOperations JdbcOperations) {
        this.jdbcOperations = JdbcOperations;
    }

    @Override
    public Type[] select() {
        ArrayList<Type> values = new ArrayList<Type>();
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectQuery);
        while (rowSet.next()) {
            values.add(new Type(
                    rowSet.getInt(1),
                    rowSet.getString(2)));
        }
        Type[] result = new Type[values.size()];
        result = values.toArray(result);
        return result;
    }

    @Override
    public Type select(Integer id) {
        Object[] params = new Object[]{id};
        int[] types = new int[]{Types.INTEGER};
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByIdQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Type(
                rowSet.getInt(1),
                rowSet.getString(2)
        );
    }

    @Override
    public Type insert(Type entity) {
        Object[] params = new Object[]{entity.getName()};
        int[] types = new int[]{Types.VARCHAR};
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(insertQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Type(
                rowSet.getInt(1),
                rowSet.getString(2)
        );
    }

    @Override
    public Type update(Integer id, Type entity) {
        Object[] params = new Object[]{entity.getName(), id};
        int[] types = new int[]{Types.VARCHAR, Types.INTEGER};
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(updateQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Type(
                rowSet.getInt(1),
                rowSet.getString(2)
        );
    }

    @Override
    public Type delete(Integer id) {
        Object[] params = new Object[]{id};
        int[] types = new int[]{Types.INTEGER};
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(deleteQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Type(
                rowSet.getInt(1),
                rowSet.getString(2)
        );
    }
}
