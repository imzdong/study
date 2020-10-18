package org.imzdong.study.design.poor.service;

import org.imzdong.study.design.poor.model.bo.VirtualWalletBo;
import org.imzdong.study.design.poor.model.entity.VirtualWalletEntity;
import org.imzdong.study.design.poor.model.entity.VirtualWalletTransactionEntity;
import org.imzdong.study.design.poor.repo.VirtualWalletRepository;
import org.imzdong.study.design.poor.repo.VirtualWalletTransactionRepository;

import java.math.BigDecimal;

public class VirtualWalletService {
    // 通过构造函数或者IOC框架注入
    private VirtualWalletRepository walletRepo;
    private VirtualWalletTransactionRepository transactionRepo;

    public VirtualWalletBo getVirtualWallet(Long walletId) {
        VirtualWalletEntity walletEntity = walletRepo.getVirtualWalletEntity(walletId);
        VirtualWalletBo walletBo = null;//convert(walletEntity);
        return walletBo;
    }

    public BigDecimal getBalance(Long walletId) {
        return walletRepo.getBalance(walletId);
    }

    public void debit(Long walletId, BigDecimal amount) {
        VirtualWalletEntity walletEntity = walletRepo.getVirtualWalletEntity(walletId);
        BigDecimal balance = walletEntity.getBalance();
        if (balance.compareTo(amount) < 0) {
            throw new RuntimeException("");
        }
        walletRepo.updateBalance(walletId, balance.subtract(amount));
    }

    public void credit(Long walletId, BigDecimal amount) {
        VirtualWalletEntity walletEntity = walletRepo.getVirtualWalletEntity(walletId);
        BigDecimal balance = walletEntity.getBalance();
        walletRepo.updateBalance(walletId, balance.add(amount));
    }

    public void transfer(Long fromWalletId, Long toWalletId, BigDecimal amount) {
        VirtualWalletTransactionEntity transactionEntity = new VirtualWalletTransactionEntity();
        /*transactionEntity.setAmount(amount);
        transactionEntity.setCreateTime(System.currentTimeMillis());
        transactionEntity.setFromWalletId(fromWalletId);
        transactionEntity.setToWalletId(toWalletId);
        transactionEntity.setStatus(Status.TO_BE_EXECUTED);
        Long transactionId = transactionRepo.saveTransaction(transactionEntity);*/
        try {
            debit(fromWalletId, amount);
            credit(toWalletId, amount);
        } catch (Exception e){

        }
        //transactionRepo.updateStatus(transactionId, Status.EXECUTED);
    }
}