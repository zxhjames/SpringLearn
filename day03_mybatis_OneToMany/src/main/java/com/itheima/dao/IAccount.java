package com.itheima.dao;

import com.itheima.domain.Account;
import com.itheima.domain.AccountUser;

import java.util.List;

public interface IAccount {
    List<AccountUser> findAll();

    List<Account> findAllAccount();
}
