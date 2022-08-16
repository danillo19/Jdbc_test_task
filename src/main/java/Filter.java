
import controllers.SearchController;
import controllers.StatsController;
import exceptions.BadCriteriaException;
import exceptions.InvalidCriteriaValuesException;
import exceptions.InvalidDateFormatException;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.Context;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Filter {
    private static final String CRITERIAS = "criterias";
    private static final String SEARCH = "search";
    private static final String STATS = "stats";
    private static final String ERROR = "error";
    private static final String MESSAGE = "message";
    private static final String START_DATE = "startDate";
    private static final String END_DATE = "endDate";
    private static final String TYPE = "type";
    private static final String RESULTS = "result";


    private final SearchController searchController;
    private final StatsController statsController;
    private final Context context;

    public Filter(SearchController searchController, StatsController statsController) throws SQLException, ClassNotFoundException {
        this.searchController = searchController;
        this.statsController = statsController;
        this.context = new Context();
    }

    public void handleRequest(String inputJson, String outputJson, String operation) throws IOException, SQLException {
        String json = new String(Files.readAllBytes(new File(inputJson).toPath()));
        JSONObject input = new JSONObject(json);

        JSONArray results = new JSONArray();
        JSONObject output = new JSONObject();
        try {
            if (operation.equals(SEARCH) && input.has(CRITERIAS)) {
                handleSearch(results, input);
                output.put(RESULTS, results);
                output.put(TYPE, SEARCH);
            } else if (operation.equals(STATS) && input.has(START_DATE) && input.has(END_DATE)) {
                handleStats(results,input);
                output.put(RESULTS, results);
                output.put("totalDays",handleCountOfDays(input));
                output.put(TYPE, STATS);

            } else {
                output.put(MESSAGE, "Bad input json format");
                output.put(TYPE, ERROR);
            }

            context.closeConnection();
        } catch (BadCriteriaException | InvalidCriteriaValuesException | InvalidDateFormatException e) {
            output.put(TYPE, ERROR);
            output.put(MESSAGE, e.getMessage());
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            output.put(TYPE, ERROR);
            output.put(MESSAGE, "Internal database error");
        }

        FileWriter writer = new FileWriter(outputJson);
        writer.write(output.toString());
        writer.flush();
        context.closeConnection();
    }

    private void handleSearch(JSONArray results, JSONObject input) throws SQLException, BadCriteriaException, InvalidCriteriaValuesException {
        JSONArray arr = input.getJSONArray(CRITERIAS);
        for (int i = 0; i < arr.length(); i++) {
            JSONObject criteria = (JSONObject) arr.get(i);
            JSONObject response = new JSONObject();
            JSONArray customers = new JSONArray(searchController.searchCustomers(criteria, context));
            response.put(RESULTS, customers);
            response.put("criteria", criteria);
            results.put(response);
        }
    }

    private void handleStats(JSONArray results, JSONObject input) throws SQLException, InvalidDateFormatException {
        String dateFrom = (String) input.get(START_DATE);
        String dateTo = (String) input.get(END_DATE);

        if (!isDateValid(dateFrom, dateTo)) {
            throw new InvalidDateFormatException("Bad date format");
        }
        results.put(statsController.getStats(dateFrom, dateTo, context));
    }

    private int handleCountOfDays(JSONObject input) throws SQLException {
        String dateFrom = (String) input.get(START_DATE);
        String dateTo = (String) input.get(END_DATE);
        return statsController.getCountDaysWithoutWeekends(dateFrom,dateTo,context);
    }

    private boolean isDateValid(String dateFrom, String dateTo) {
        if (dateFrom == null || !dateFrom.matches("\\d{4}-[01]\\d-[0-3]\\d") ||
                dateTo == null || !dateTo.matches("\\d{4}-[01]\\d-[0-3]\\d"))
            return false;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setLenient(false);
        try {
            df.parse(dateFrom);
            df.parse(dateTo);
            return true;
        } catch (ParseException ex) {
            return false;
        }
    }

}
