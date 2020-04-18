package org.imzdong.study.rich.repo;

import org.imzdong.study.rich.model.entity.VirtualWalletEntity;

import java.math.BigDecimal;

/**
 * @description: 数据库交互
 * @author: Winter
 * @time: 2020/4/18
 */
public class VirtualWalletRepository {

    public BigDecimal getBalance(Long walletId){
        return new BigDecimal(1000);
    }

    public VirtualWalletEntity getVirtualWalletEntity(Long walletId){
        return new VirtualWalletEntity();
    }

    public VirtualWalletEntity updateBalance(Long walletId, BigDecimal balance){
        return new VirtualWalletEntity();
    }
}
