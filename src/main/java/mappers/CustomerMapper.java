package mappers;

import dto.CustomerDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerMapper {
    public static CustomerDto toDto(String name, String surname) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setFirstName(name);
        customerDto.setLastName(surname);
        return customerDto;
    }

    public static List<CustomerDto> getCustomersDtoListFromSqlResult(ResultSet resultSet) throws SQLException {
        List<CustomerDto> customers = new ArrayList<>();
        while(resultSet.next()) {
            customers.add(CustomerMapper.toDto(resultSet.getString("name"), resultSet.getString("surname")));
        }

        return customers;
    }
}
