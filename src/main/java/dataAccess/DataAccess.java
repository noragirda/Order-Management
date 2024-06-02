
package dataAccess;

import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * This class is responsible for managing the data access operations.
 * @param <T> The type of the object to be managed.
 */
public abstract class DataAccess<T> {
    protected Connection connection;
    /**
     * Creates a new instance of the DataAccess class.
     */
    public DataAccess() {
        this.connection = ConnectionFactory.getConnection();
    }
    /**
     * Inserts a new object into the database.
     * @param obj The object to be inserted.
     */
    public void insert(T obj) {
        String query = createInsertQuery(obj);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setParameters(statement, obj);
            statement.executeUpdate();
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    /**
     * Updates an object in the database.
     * @param obj The updated object.
     * @param id The ID of the object to be updated.
     */
    public void update(T obj, int id) {
        String query = createUpdateQuery(obj, id);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setParameters(statement, obj);
            statement.executeUpdate();
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    /**
     * Deletes an object from the database.
     * @param clazz The class of the object to be deleted.
     * @param id The ID of the object to be deleted.
     */
    public void delete(Class<T> clazz, int id) {
        String query = createDeleteQuery(clazz, id);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Retrieves an object by its ID.
     * @param clazz The class of the object to be retrieved.
     * @param id The ID of the object to be retrieved.
     * @return The object with the specified ID.
     */
    public T findById(Class<T> clazz, int id) {
        String query = createFindByIdQuery(clazz, id);
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return mapResultSetToObject(clazz, resultSet);
            }
        } catch (SQLException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * Retrieves all objects from the database.
     * @param clazz The class of the objects to be retrieved.
     * @return A list of all objects.
     */
    public List<T> findAll(Class<T> clazz) {
        String query = createFindAllQuery(clazz);
        List<T> list = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                list.add(mapResultSetToObject(clazz, resultSet));
            }
        } catch (SQLException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return list;
    }
    /**
     * Creates an SQL query for inserting an object into the database.
     * @param obj The object to be inserted.
     * @return The SQL query for inserting the object.
     */
    private String createInsertQuery(T obj) {
        StringBuilder query = new StringBuilder("INSERT INTO ");
        query.append(obj.getClass().getSimpleName().toLowerCase()).append(" (");
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            query.append(field.getName()).append(",");
        }
        query.deleteCharAt(query.length() - 1);
        query.append(") VALUES (");
        for (Field field : fields) {
            query.append("?,");
        }
        query.deleteCharAt(query.length() - 1);
        query.append(")");
        return query.toString();
    }
    /**
     * Creates an SQL query for updating an object in the database.
     * @param obj The object to be updated.
     * @param id The ID of the object to be updated.
     * @return The SQL query for updating the object.
     */
   private String createUpdateQuery(T obj, int id)
   {
        StringBuilder query = new StringBuilder("UPDATE ");
        query.append(obj.getClass().getSimpleName().toLowerCase()).append(" SET ");
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (!field.getName().equalsIgnoreCase("id")) {
                query.append(field.getName()).append("=?,");
            }
        }
        query.deleteCharAt(query.length() - 1);
        query.append(" WHERE ").append(obj.getClass().getSimpleName().toLowerCase()).append("Id=").append(id);
        return query.toString();
    }
    /**
     * Creates an SQL query for deleting an object from the database.
     * @param clazz The class of the object to be deleted.
     * @param id The ID of the object to be deleted.
     * @return The SQL query for deleting the object.
     */
    private String createDeleteQuery(Class<T> clazz, int id) {
        return "DELETE FROM " + clazz.getSimpleName().toLowerCase() + " WHERE " + clazz.getSimpleName().toLowerCase() + "Id=" + id;
    }
    /**
     * Creates an SQL query for retrieving an object by its ID.
     * @param clazz The class of the object to be retrieved.
     * @param id The ID of the object to be retrieved.
     * @return The SQL query for retrieving the object.
     */
    private String createFindByIdQuery(Class<T> clazz, int id) {
        return "SELECT * FROM " + clazz.getSimpleName().toLowerCase() + " WHERE " + clazz.getSimpleName().toLowerCase() + "Id=" + id;
    }
    /**
     * Creates an SQL query for retrieving all objects from the database.
     * @param clazz The class of the objects to be retrieved.
     * @return The SQL query for retrieving all objects.
     */
    private String createFindAllQuery(Class<T> clazz) {
        return "SELECT * FROM " + clazz.getSimpleName().toLowerCase();
    }
    /**
     * Sets the parameters of a prepared statement.
     * @param statement The prepared statement.
     * @param obj The object from which to set the parameters.
     * @throws SQLException
     * @throws IllegalAccessException
     */
    private void setParameters(PreparedStatement statement, T obj) throws SQLException, IllegalAccessException {
        Field[] fields = obj.getClass().getDeclaredFields();
        int index = 1;
        for (Field field : fields) {
            field.setAccessible(true);
            statement.setObject(index++, field.get(obj));
        }
    }
    /**
     * Maps a result set to an object.
     * @param clazz The class of the object to be mapped.
     * @param resultSet The result set to be mapped.
     * @return The object mapped from the result set.
     * @throws SQLException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private T mapResultSetToObject(Class<T> clazz, ResultSet resultSet) throws SQLException, IllegalAccessException, InstantiationException {
        T obj = clazz.newInstance();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            field.set(obj, resultSet.getObject(field.getName()));
        }
        return obj;
    }
}
