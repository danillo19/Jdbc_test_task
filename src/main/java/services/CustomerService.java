package services;

import dto.CustomerDto;
import mappers.CustomerMapper;
import repo.CustomersRepository;
import utils.Context;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CustomerService {
    private CustomersRepository customersRepository;

    public CustomerService(CustomersRepository customersRepository) {
        this.customersRepository = customersRepository;
    }

    public List<CustomerDto> findByLastName(String lastName, Context ctx) throws SQLException {
        ResultSet resultSet = customersRepository.findByLastName(lastName,ctx);
        return CustomerMapper.getCustomersDtoListFromSqlResult(resultSet);

    }
}
