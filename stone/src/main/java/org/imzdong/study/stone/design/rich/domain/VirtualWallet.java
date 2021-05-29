package org.imzdong.study.stone.design.rich.domain;

import java.math.BigDecimal;

/**
 * @description: 领域驱动模型
 * @author: Winter
 * @time: 2020/4/18
 */
public class VirtualWallet {

    private Long id;
    private Long createTime = System.currentTimeMillis();
    private BigDecimal balance = BigDecimal.ZERO;

    public VirtualWallet(Long preAllocatedId) {
        this.id = preAllocatedId;
    }

    public BigDecimal balance() {
        return this.balance;
    }

    public void debit(BigDecimal amount) {
        if (this.balance.compareTo(amount) < 0) {
            throw new RuntimeException("");
        }
        this.balance.subtract(amount);
    }

    public void credit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException();
        }
        this.balance.add(amount);
    }
}
