package org.lxrssdev.taquia.app.dto;

import java.math.BigDecimal;

public record StatsDTO(
        long ordersToday,
        BigDecimal totalSales,
        long pendingCount,
        long preparingCount,
        long readyCount
){}
