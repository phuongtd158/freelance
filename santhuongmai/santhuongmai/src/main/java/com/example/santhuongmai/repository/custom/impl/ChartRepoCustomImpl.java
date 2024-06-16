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
        String sqlQuery = getSqlCommon(sdi) +
                "SELECT date_format(date, :formatField) as field, " +
                "       coalesce(sum(o.total_price), 0) as value,\n" +
                "       d.date                          as dateStr\n" +
                "FROM dates d\n" +
                "         left join orders o on d.date = date_format(o.create_at, :formatDate) and o.status = 9\n" +
                "group by d.date";

        return executeQuery(sdi, sqlQuery);
    }

    @Override
    public List<ChartSdo> searchProduct(ChartSdi sdi) {
        String sqlQuery = getSqlCommon(sdi) +
                "SELECT date_format(date, :formatField) as field, " +
                "       date                            as dateStr,\n" +
                "       coalesce(sum(od.soluong), 0)    as value\n" +
                "FROM dates d\n" +
                "         left join orders o on d.date = date_format(o.create_at, :formatDate) and o.status = 9\n" +
                "         left join order_detail od on o.id = od.order_id\n" +
                "group by d.date";

        return executeQuery(sdi, sqlQuery);
    }

    @Override
    public List<ChartSdo> searchProductDetail(ChartSdi sdi) {
        String sqlQuery = "SELECT od.name                      as field,\n" +
                "       coalesce(sum(od.soluong), 0) as value\n" +
                "FROM orders o\n" +
                "         inner join order_detail od on o.id = od.order_id\n" +
                "where o.status = 9\n" +
                "  and date_format(o.create_at, :formatDate) = :dateStr\n" +
                "group by od.product_id\n" +
                "order by value desc";

        Query query = em.createNativeQuery(sqlQuery, Tuple.class);
        query.setParameter("dateStr", sdi.getToDate());
        query.setParameter("formatDate", getFormatDate(sdi));

        List<Tuple> results = query.getResultList();
        return DataUtil.convertFromQueryResult(results, ChartSdo.class);
    }

    @Override
    public List<ChartSdo> searchProductV2(ChartSdi sdi) {
        String sqlQuery = getSqlCommon(sdi) +
                "select od.name                      as field,\n" +
                "       coalesce(sum(od.soluong), 0) as value,\n" +
                "       od.product_id                as productId\n" +
                "FROM dates d\n" +
                "         inner join orders o on d.date = date_format(o.create_at, :formatDate) and o.status = 9\n" +
                "         inner join order_detail od on o.id = od.order_id\n" +
                "group by od.product_id\n" +
                "order by value desc";

        Query query = em.createNativeQuery(sqlQuery, Tuple.class);
        query.setParameter("fromDate", sdi.getFromDate());
        query.setParameter("toDate", sdi.getToDate());
        query.setParameter("formatDate", getFormatDate(sdi));

        List<Tuple> results = query.getResultList();
        return DataUtil.convertFromQueryResult(results, ChartSdo.class);
    }

    @Override
    public List<ChartSdo> searchProductV2Detail(ChartSdi sdi) {
        String sqlQuery = getSqlCommon(sdi) +
                "select date_format(date, :formatField) as field,\n" +
                "       coalesce(sum(od.soluong), 0)    as value\n" +
                "from dates d\n" +
                "         inner join orders o on d.date = date_format(o.create_at, :formatDate) and o.status = 9\n" +
                "         inner join order_detail od on o.id = od.order_id and od.product_id = :productId\n" +
                "group by d.date";

        Query query = em.createNativeQuery(sqlQuery, Tuple.class);
        query.setParameter("fromDate", sdi.getFromDate());
        query.setParameter("toDate", sdi.getToDate());
        query.setParameter("formatDate", getFormatDate(sdi));
        query.setParameter("formatField", getFormatField(sdi));
        query.setParameter("productId", sdi.getProductId());

        List<Tuple> results = query.getResultList();
        return DataUtil.convertFromQueryResult(results, ChartSdo.class);
    }

    @Override
    public Integer getSumQuantityProduct(ChartSdi sdi) {
        String sqlQuery = getSqlCommon(sdi) +
                "select coalesce(sum(od.soluong), 0)\n" +
                "from dates d\n" +
                "         inner join orders o on d.date = date_format(o.create_at, :formatDate) and o.status = 9\n" +
                "         inner join order_detail od on o.id = od.order_id";

        return DataUtil.safeToInt(executeQuery2(sdi, sqlQuery));
    }

    @Override
    public BigDecimal getSumRevenue(ChartSdi sdi) {
        String sqlQuery = getSqlCommon(sdi) +
                "select coalesce(sum(o.total_price), 0)\n" +
                "from dates d\n" +
                "         inner join orders o on d.date = date_format(o.create_at, :formatDate) and o.status = 9";

        return DataUtil.safeToBigDecimal(executeQuery2(sdi, sqlQuery));
    }

    @Override
    public Integer getSumOrderDone(ChartSdi sdi) {
        String sqlQuery = getSqlCommon(sdi) +
                "select count(*)\n" +
                "from dates d\n" +
                "         inner join orders o on d.date = date_format(o.create_at, :formatDate) and o.status = 9";

        return DataUtil.safeToInt(executeQuery2(sdi, sqlQuery));
    }

    private List<ChartSdo> executeQuery(ChartSdi sdi, String sqlQuery) {
        Query query = em.createNativeQuery(sqlQuery, Tuple.class);
        query.setParameter("fromDate", sdi.getFromDate());
        query.setParameter("toDate", sdi.getToDate());
        query.setParameter("formatDate", getFormatDate(sdi));
        query.setParameter("formatField", getFormatField(sdi));

        List<Tuple> results = query.getResultList();
        return DataUtil.convertFromQueryResult(results, ChartSdo.class);
    }

    private Object executeQuery2(ChartSdi sdi, String sqlQuery) {
        Query query = em.createNativeQuery(sqlQuery);
        query.setParameter("fromDate", sdi.getFromDate());
        query.setParameter("toDate", sdi.getToDate());
        query.setParameter("formatDate", getFormatDate(sdi));

        List<Tuple> results = query.getResultList();
        return results.get(0);
    }

    private String getSqlCommon(ChartSdi sdi) {
        return "WITH RECURSIVE dates(date) AS (SELECT DATE :fromDate\n" +
                "                               UNION ALL\n" +
                "                               SELECT date_add(date, interval 1 " + sdi.getCycleType() + ")\n" +
                "                               FROM dates\n" +
                "                               WHERE date < DATE :toDate)\n";
    }

    private String getFormatDate(ChartSdi sdi) {
        if (sdi.getCycleType().equalsIgnoreCase("YEAR")) {
            return "%Y-01-01";
        } else if (sdi.getCycleType().equalsIgnoreCase("MONTH")) {
            return "%Y-%m-01";
        } else {
            return "%Y-%m-%d";
        }
    }

    private String getFormatField(ChartSdi sdi) {
        if (sdi.getCycleType().equalsIgnoreCase("YEAR")) {
            return "%Y";
        } else if (sdi.getCycleType().equalsIgnoreCase("MONTH")) {
            return "%m-%Y";
        } else {
            return "%d-%m";
        }
    }
}
