import controllers.SearchController;
import controllers.StatsController;
import repo.CustomersRepository;
import repo.PurchasesRepository;
import services.CustomerService;
import services.PurchasesService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class Main {
    private static final Integer INPUT_INDEX = 1;
    private static final Integer OUTPUT_INDEX = 2;
    private static final Integer OPERATION_INDEX = 0;

    public static void main(String[] args) throws IOException {
        String operation = args[OPERATION_INDEX];
        String input = args[INPUT_INDEX];
        String output = args[OUTPUT_INDEX];

        CustomersRepository customersRepository = new CustomersRepository();
        PurchasesRepository purchasesRepository = new PurchasesRepository();

        CustomerService customerService = new CustomerService(customersRepository);
        PurchasesService purchasesService = new PurchasesService(purchasesRepository);

        SearchController searchController = new SearchController(purchasesService, customerService);
        StatsController statsController = new StatsController(purchasesService);
        try {
            Filter filter = new Filter(searchController, statsController);
            filter.handleRequest(input, output, operation);
        } catch (SQLException sqlException) {
            System.err.println("No database connection");
            sqlException.printStackTrace();
        }
    }
}
