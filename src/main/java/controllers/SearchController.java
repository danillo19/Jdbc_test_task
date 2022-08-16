package controllers;

import dto.CustomerDto;
import exceptions.BadCriteriaException;
import exceptions.InvalidCriteriaValuesException;
import org.json.JSONObject;
import services.CustomerService;
import services.PurchasesService;
import utils.Context;

import java.sql.SQLException;
import java.util.List;

public class SearchController {
    private static final String LAST_NAME = "lastName";
    private static final String PRODUCT_NAME = "productName";
    private static final String MIN_TIMES = "minTimes";
    private static final String MIN_EXPENSES = "minExpenses";
    private static final String MAX_EXPENSES = "maxExpenses";
    private static final String BAD_CUSTOMERS = "badCustomers";

    private PurchasesService purchasesService;
    private CustomerService customerService;

    public SearchController(PurchasesService purchasesService, CustomerService customerService) {
        this.purchasesService = purchasesService;
        this.customerService = customerService;
    }

    public List<CustomerDto> searchCustomers(JSONObject criteria, Context ctx) throws SQLException, BadCriteriaException, InvalidCriteriaValuesException {

        if(criteria.has(LAST_NAME)) {
            String lastName = (String) criteria.get(LAST_NAME);
            return customerService.findByLastName(lastName,ctx);
        }
        else if(criteria.has(PRODUCT_NAME) && criteria.has(MIN_TIMES)) {
            try {
                String productName = (String) criteria.get(PRODUCT_NAME);
                Integer lowerBound = (Integer) criteria.get(MIN_TIMES);
                if(lowerBound < 0) throw new InvalidCriteriaValuesException("Min times number should be >= 0");
                return purchasesService.findCustomersByProductNameWithMinPurchases(productName,lowerBound,ctx);
            }
            catch (ClassCastException ex) {
                throw new InvalidCriteriaValuesException("Bad integer value for min times: " + criteria);
            }
        }
        else if(criteria.has(MIN_EXPENSES) && criteria.has(MAX_EXPENSES)) {
            try {
                Integer lowerBound = (Integer) criteria.get(MIN_EXPENSES);
                Integer upperBound = (Integer) criteria.get(MAX_EXPENSES);
                if(lowerBound < 0 || upperBound < 0) throw new InvalidCriteriaValuesException("Min and max expenses should be >= 0");
                return purchasesService.findCustomersByExpensesRange(lowerBound,upperBound,ctx);
            }
            catch (ClassCastException ex) {
                throw new InvalidCriteriaValuesException("Bad integer value for min/max expenses: " + criteria);
            }
        }
        else if(criteria.has(BAD_CUSTOMERS)) {
            try {
                Integer limitCustomers = (Integer) criteria.get(BAD_CUSTOMERS);
                if(limitCustomers < 0) throw new InvalidCriteriaValuesException("Bad customers number should be >= 0");
                return purchasesService.getPassiveCustomers(limitCustomers,ctx);
            }
            catch (ClassCastException ex) {
                throw new InvalidCriteriaValuesException("Bad integer value bad customers: " + criteria);
            }
        }
        else {
            throw new BadCriteriaException("No such criteria: " + criteria);
        }
    }
}
