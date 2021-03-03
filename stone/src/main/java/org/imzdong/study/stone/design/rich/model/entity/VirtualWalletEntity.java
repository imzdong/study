package org.imzdong.study.stone.design.rich.model.entity;

import java.math.BigDecimal;

/**
 * @description:
 * @author: Winter
 * @time: 2020/4/18
 */
public class VirtualWalletEntity {

    private Long walletId;
    private BigDecimal balance;

    public Long getWalletId() {
        return walletId;
    }

    public void setWalletId(Long walletId) {
        this.walletId = walletId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
