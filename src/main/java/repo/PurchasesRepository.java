package repo;

import utils.Context;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PurchasesRepository implements Repository {
    public ResultSet findCustomersByProductNameWithMinPurchases(String productName, Integer lowerBoundCount, Context ctx) throws SQLException {
        String request = "SELECT c.name, c.surname \n" +
                "from purchases p\n" +
                "         LEFT JOIN customers c on c.id = p.customer_id\n" +
                "         LEFT JOIN products pr on p.product_id = pr.id\n" +
                "WHERE pr.name = " + "'" + productName + "'" + "\n" +
                "GROUP BY (pr.name, c.name, c.surname)\n" +
                "HAVING count(pr.name) > " + lowerBoundCount;

        Statement statement = ctx.getConnection().createStatement();
        return statement.executeQuery(request);
    }

    public ResultSet findCustomersByExpensesRange(Integer lowerBound, Integer upperBound, Context ctx) throws SQLException {
        String request = "SELECT c.name, c.surname\n" +
                "from purchases p\n" +
                "         LEFT JOIN products pr ON p.product_id = pr.id\n" +
                "         LEFT JOIN customers c on p.customer_id = c.id\n" +
                "GROUP BY (c.name, c.surname)\n" +
                "HAVING sum(pr.price) > " + lowerBound + " and sum(pr.price) < " + upperBound;
        Statement statement = ctx.getConnection().createStatement();
        return statement.executeQuery(request);
    }

    public ResultSet getPassiveCustomers(Integer limitRows, Context ctx) throws SQLException {
        String request = "SELECT c.name, c.surname, count(c.id) as count from purchases\n" +
                "LEFT JOIN customers c on purchases.customer_id = c.id\n" +
                "GROUP BY (c.name, c.surname)\n" +
                "ORDER BY (count)\n" +
                "LIMIT " + limitRows;

        Statement statement = ctx.getConnection().createStatement();
        return statement.executeQuery(request);

    }

    public ResultSet getStatisticsForEveryCustomer(String from, String to, Context ctx) throws SQLException {
        String request = "SELECT c.name as customer_name, c.surname customer_surname, pr.name as product_name, " +
                "sum(pr.price) product_expenses,\n" +
                "sum(sum(pr.price)) over (PARTITION BY c.name,c.surname) as total from purchases p\n" +
                "LEFT JOIN products pr on pr.id = p.product_id\n" +
                "LEFT JOIN customers c on p.customer_id = c.id\n" +
                "WHERE extract(isodow from p.date) not in (6,7) and " +
                "p.date > '" + from + "'::date and p.date < '" + to + "'::date\n" +
                "GROUP BY (c.name, c.surname, pr.name)";

        Statement statement = ctx.getConnection().createStatement();
        return statement.executeQuery(request);
    }

    public ResultSet getCountDaysWithoutWeekends(String from, String to, Context ctx) throws SQLException {
        String request = "SELECT count(*) AS count_days_no_weekend\n" +
                "FROM   generate_series(timestamp '" + from + "'\n" +
                "                     , timestamp '" + to + "'\n" +
                "                     , interval  '1 day') the_day\n" +
                "WHERE  extract('ISODOW' FROM the_day) < 6;";
        Statement statement = ctx.getConnection().createStatement();
        return statement.executeQuery(request);
    }
}
