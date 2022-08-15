package repo;

import utils.Context;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomersRepository implements Repository{
    public ResultSet findByLastName(String lastName, Context context) throws SQLException {
        Statement statement = context.getConnection().createStatement();
        return statement.executeQuery("Select customers.name, customers.surname " +
                "from customers where surname = " + "'" + lastName + "'");

    }
}
