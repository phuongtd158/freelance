package com.example.santhuongmai.repository.custom.impl;

import com.example.santhuongmai.dto.sdi.FilterProductSdi;
import com.example.santhuongmai.dto.sdo.ProductFilterSdo;
import com.example.santhuongmai.repository.ImageRepository;
import com.example.santhuongmai.repository.custom.ProductRepositoryCustom;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.santhuongmai.util.DataUtil.*;

@RequiredArgsConstructor
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    private final EntityManager em;
    private final ImageRepository imageRepository;

    @Override
    public List<ProductFilterSdo> filter(FilterProductSdi request) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder sql = new StringBuilder(
                "SELECT p.id,\n" +
                        "       p.description,\n" +
                        "       p.name,\n" +
                        "       p.price,\n" +
                        "       p.quantity,\n" +
                        "       p.category_id as categoryId,\n" +
                        "       p.mota,\n" +
                        "       p.quantitybuy as quantityBuy,\n" +
                        "       p.user_id,\n" +
                        "       p.pricesale   as priceSale,\n" +
                        "       p.create_at\n" +
                        "FROM product p\n" +
                        "         left join product_productroom pp on p.id = pp.product_id\n" +
                        "where p.category_id = :categoryId \n"
        );
        params.put("categoryId", request.getCategoryId());
        if (!isNullOrEmpty(request.getCapacity())) {
            sql.append(" and pp.productroom_id = :productRoomId\n");
            params.put("productRoomId", request.getCapacity());
        }
        if (!isNullOrEmpty(request.getPrice())) {
            sql.append(" and (\n" +
                    "    case :price\n" +
                    "        when 'LESS_THAN_10' then p.price < 10000000\n" +
                    "        when 'FROM_10_TO_15' then p.price between 10000000 and 15000000\n" +
                    "        when 'FROM_15_TO_20' then p.price between 15000000 and 20000000\n" +
                    "        else p.price > 20000000\n" +
                    "        end\n" +
                    "    )\n");
            params.put("price", request.getPrice());
        }
        sql.append(" group by p.id\n");
        if (!isNullOrEmpty(request.getOrderPrice())) {
            sql.append(String.format(" order by p.price %s", request.getOrderPrice()));
        }
        Query nativeQuery = em.createNativeQuery(sql.toString(), Tuple.class);
        params.forEach(nativeQuery::setParameter);
        List<Tuple> resultList = nativeQuery.getResultList();

        List<ProductFilterSdo> productFilterSdos = convertFromQueryResult(resultList, ProductFilterSdo.class);
        for (ProductFilterSdo sdo : productFilterSdos) {
            sdo.setImages(imageRepository.getListImgByProductId(sdo.getId()));
        }

        return productFilterSdos;
    }
}
