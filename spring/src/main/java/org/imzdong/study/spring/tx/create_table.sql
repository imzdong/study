--  Drop table

--  DROP TABLE spring_test.tx;

CREATE TABLE spring_test.tx
(
    age INT NULL COMMENT '年龄',
    id  BIGINT auto_increment NOT NULL,
    CONSTRAINT tx_pk PRIMARY KEY (id)
) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci
COMMENT='spring事务测试表';
