package services;

import dto.CustomerDto;
import dto.CustomerStatsDto;
import dto.ProductStatsDto;
import mappers.CustomerMapper;
import mappers.CustomerStatsMapper;
import mappers.ProductStatsMapper;
import repo.PurchasesRepository;
import utils.Context;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PurchasesService {
    private PurchasesRepository purchasesRepository;

    public PurchasesService(PurchasesRepository purchasesRepository) {
        this.purchasesRepository = purchasesRepository;
    }

    public List<CustomerDto> findCustomersByProductNameWithMinPurchases(String productName, Integer lowerBoundPurchases, Context ctx) throws SQLException {
        ResultSet resultSet = purchasesRepository.findCustomersByProductNameWithMinPurchases(productName,lowerBoundPurchases,ctx);
        return CustomerMapper.getCustomersDtoListFromSqlResult(resultSet);

    }

    public List<CustomerDto> findCustomersByExpensesRange(Integer lowerBound, Integer upperBound, Context ctx) throws SQLException {
        ResultSet resultSet = purchasesRepository.findCustomersByExpensesRange(lowerBound,upperBound,ctx);
        return CustomerMapper.getCustomersDtoListFromSqlResult(resultSet);
    }

    public List<CustomerDto> getPassiveCustomers(Integer limitCustomers, Context ctx) throws SQLException {
        ResultSet resultSet = purchasesRepository.getPassiveCustomers(limitCustomers,ctx);
        return CustomerMapper.getCustomersDtoListFromSqlResult(resultSet);
    }

    public List<CustomerStatsDto> getStats(String dateFrom, String dateTo, Context ctx) throws SQLException {
        ResultSet resultSet = purchasesRepository.getStatisticsForEveryCustomer(dateFrom,dateTo, ctx);
        List<CustomerStatsDto> customerStats = new ArrayList<>();
        String currentCustomer = null;
        Integer currentTotalExpenses = null;
        List<ProductStatsDto> productStats = null;

        while(resultSet.next()) {
            String customer = resultSet.getString("customer_name") + " " + resultSet.getString("customer_surname");
            String productName = resultSet.getString("product_name");
            Integer expenses = resultSet.getInt("product_expenses");
            if(!customer.equals(currentCustomer)) {
                if(productStats != null) {
                    customerStats.add(CustomerStatsMapper.toDto(currentCustomer,productStats,currentTotalExpenses));
                }
                productStats = new ArrayList<>();
                currentCustomer = customer;
                currentTotalExpenses = resultSet.getInt("total");
            }

            productStats.add(ProductStatsMapper.toDto(productName,expenses));
        }
        if(currentCustomer != null) {
            customerStats.add(CustomerStatsMapper.toDto(currentCustomer,productStats,currentTotalExpenses));
        }

        return customerStats;
    }

    public Integer getCountDayWithoutWeekends(String dateFrom, String dateTo, Context ctx) throws SQLException {
        ResultSet resultSet = purchasesRepository.getCountDaysWithoutWeekends(dateFrom,dateTo,ctx);
        resultSet.next();
        return resultSet.getInt("count_days_no_weekend");
    }


}
