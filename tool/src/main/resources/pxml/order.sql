CREATE TABLE test.`order` (
                              order_id varchar(100) NOT NULL COMMENT '订单id',
                              product_id varchar(100) NOT NULL COMMENT '产品id',
                              name varchar(100) NOT NULL COMMENT '商品名称',
                              category varchar(50) NOT NULL COMMENT '商品类别',
                              price DECIMAL NOT NULL COMMENT '订单金额',
                              sale_num INTEGER NOT NULL COMMENT '商品数量',
                              sale_date DATE NOT NULL COMMENT '销售日期'
)
    ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;