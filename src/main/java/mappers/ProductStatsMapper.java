package mappers;

import dto.ProductStatsDto;

public class ProductStatsMapper {
    public static ProductStatsDto toDto(String productName, Integer expenses) {
        ProductStatsDto productStatsDto = new ProductStatsDto();
        productStatsDto.setName(productName);
        productStatsDto.setExpenses(expenses);
        return productStatsDto;
    }
}
