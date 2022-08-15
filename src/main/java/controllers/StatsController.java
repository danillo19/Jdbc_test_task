package controllers;

import dto.CustomerStatsDto;
import services.PurchasesService;
import utils.Context;

import java.sql.SQLException;
import java.util.List;

public class StatsController {
    private PurchasesService purchasesService;

    public StatsController(PurchasesService purchasesService) {
        this.purchasesService = purchasesService;
    }

    public List<CustomerStatsDto> getStats(String dateFrom, String dateTo, Context ctx) throws SQLException {
        return purchasesService.getStats(dateFrom, dateTo, ctx);
    }

    public Integer getCountDaysWithoutWeekends(String dateFrom, String dateTo, Context ctx) throws SQLException {
        return purchasesService.getCountDayWithoutWeekends(dateFrom,dateTo,ctx);
    }
}
