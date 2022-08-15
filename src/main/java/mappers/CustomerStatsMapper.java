package mappers;

import dto.CustomerStatsDto;
import dto.ProductStatsDto;

import java.util.List;

public class CustomerStatsMapper {
    public static CustomerStatsDto toDto(String name, List<ProductStatsDto> productStats, Integer totalExpenses) {
        CustomerStatsDto customerStatsDto = new CustomerStatsDto();
        customerStatsDto.setName(name);
        customerStatsDto.setProductStats(productStats);
        customerStatsDto.setTotalExpenses(totalExpenses);
        return customerStatsDto;
    }
}
