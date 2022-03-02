package org.imzdong.tool.pxml;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author admin
 * @since 2021/10/5 下午4:12
 */
@Data
public class Order {

    String orderId;
    String productId;
    String name;
    String category;
    BigDecimal price;
    Integer saleNum;
    Date saleDate;

}
