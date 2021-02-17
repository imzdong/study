package org.imzdong.study.spring.tx.service;

import org.imzdong.study.spring.tx.dao.TxDao;

public class TxServiceImpl implements  TxService{

    private TxDao txDao;

    @Override
    public int updateAgeService(int id, int age) {
        return txDao.updateAge(id, age);
    }

    public void setTxDao(TxDao txDao) {
        this.txDao = txDao;
    }
}
