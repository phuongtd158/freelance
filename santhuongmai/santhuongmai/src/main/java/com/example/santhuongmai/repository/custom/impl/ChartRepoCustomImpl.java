package com.example.santhuongmai.repository.custom.impl;

import com.example.santhuongmai.dto.sdi.ChartSdi;
import com.example.santhuongmai.dto.sdo.ChartSdo;
import com.example.santhuongmai.repository.custom.ChartRepoCustom;
import com.example.santhuongmai.util.DataUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;
import java.math.BigDecimal;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChartRepoCustomImpl implements ChartRepoCustom {
    private final EntityManager em;

    @Override
    public List<ChartSdo> searchRevenue(ChartSdi sdi) {
        String formatDate;
        String formatField;
        if (sdi.getCycleType().equalsIgnoreCase("YEAR")) {
            formatDate = "%Y-01-01";
            formatField = "%Y";
        } else if (sdi.getCycleType().equalsIgnoreCase("MONTH")) {
            formatDate = "%Y-%m-01";
            formatField = "%m-%Y";
        } else {
            formatDate = "%Y-%m-%d";
            formatField = "%d-%m";
        }

        String sqlQuery = "WITH RECURSIVE dates(date) AS (SELECT DATE :fromDate\n" +
                "                               UNION ALL\n" +
                "                               SELECT date_add(date, interval 1 " + sdi.getCycleType() + ")\n" +
                "                               FROM dates\n" +
                "                               WHERE date < DATE :toDate)\n" +
                "select date_format(revenue.date, :formatField) as field,\n" +
                "       revenue.value                           as value,\n" +
                "       product.value                           as amount\n" +
                "from (SELECT d.date,\n" +
                "             coalesce(sum(o.total_price), 0) as value\n" +
                "      FROM dates d\n" +
                "               left join orders o on d.date = date_format(o.create_at, :formatDate) and o.status = 9\n" +
                "      group by d.date) revenue\n" +
                "         inner join\n" +
                "     (SELECT d.date,\n" +
                "             coalesce(sum(od.soluong), 0) as value\n" +
                "      FROM dates d\n" +
                "               left join orders o on d.date = date_format(o.create_at, :formatDate) and o.status = 9\n" +
                "               left join order_detail od on o.id = od.order_id\n" +
                "      group by d.date) product on product.date = revenue.date";

        Query query = em.createNativeQuery(sqlQuery, Tuple.class);
        query.setParameter("fromDate", sdi.getFromDate());
        query.setParameter("toDate", sdi.getToDate());
        query.setParameter("formatDate", formatDate);
        query.setParameter("formatField", formatField);

        List<Tuple> results = query.getResultList();
        return DataUtil.convertFromQueryResult(results, ChartSdo.class);
    }

    @Override
    public List<ChartSdo> searchProduct(ChartSdi sdi) {
        return null;
    }

    @Override
    public Integer getSumQuantityProduct() {
        return DataUtil.safeToInt(
                em.createNativeQuery("select sum(quantity) from product").getResultList().get(0)
        );
    }

    @Override
    public BigDecimal getSumRevenue() {
        return DataUtil.safeToBigDecimal(
                em.createNativeQuery("select sum(total_price) from orders where status = 9").getResultList().get(0)
        );
    }

    @Override
    public Integer getSumOrderDone() {
        return DataUtil.safeToInt(
                em.createNativeQuery("select count(1) from orders").getResultList().get(0)
        );
    }

    @Override
    public Integer getSumProductSell() {
        return DataUtil.safeToInt(
                em.createNativeQuery("select sum(quantitybuy) from product").getResultList().get(0)
        );
    }
}
