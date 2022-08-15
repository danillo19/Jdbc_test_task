package dto;

import java.util.ArrayList;
import java.util.List;

public class CustomerStatsDto {
    private String name;
    private List<ProductStatsDto> productStats;
    private Integer totalExpenses;

    public Integer getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(Integer totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProductStatsDto> getProductStats() {
        return productStats;
    }

    public void setProductStats(List<ProductStatsDto> productStats) {
        this.productStats = productStats;
    }
}
