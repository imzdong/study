package org.imzdong.study.design.rich.service;

import org.imzdong.study.design.rich.domain.VirtualWallet;
import org.imzdong.study.design.rich.model.entity.VirtualWalletEntity;
import org.imzdong.study.design.rich.repo.VirtualWalletRepository;
import org.imzdong.study.design.rich.repo.VirtualWalletTransactionRepository;

import java.math.BigDecimal;

public class VirtualWalletService {

    // 通过构造函数或者IOC框架注入
    private VirtualWalletRepository walletRepo;
    private VirtualWalletTransactionRepository transactionRepo;

    public VirtualWallet getVirtualWallet(Long walletId) {
        VirtualWalletEntity walletEntity = walletRepo.getVirtualWalletEntity(walletId);
        VirtualWallet wallet = null;//convert(walletEntity);
        return wallet;
    }

    public BigDecimal getBalance(Long walletId) {
        return walletRepo.getBalance(walletId);
    }

    public void debit(Long walletId, BigDecimal amount) {
        VirtualWalletEntity walletEntity = walletRepo.getVirtualWalletEntity(walletId);
        VirtualWallet wallet = null;//convert(walletEntity);
        wallet.debit(amount);
        walletRepo.updateBalance(walletId, wallet.balance());
    }

    public void credit(Long walletId, BigDecimal amount) {
        VirtualWalletEntity walletEntity = walletRepo.getVirtualWalletEntity(walletId);
        VirtualWallet wallet = null;//convert(walletEntity);
        wallet.credit(amount);
        walletRepo.updateBalance(walletId, wallet.balance());
    }

    public void transfer(Long fromWalletId, Long toWalletId, BigDecimal amount) {
        //...跟基于贫血模型的传统开发模式的代码一样...
    }
}