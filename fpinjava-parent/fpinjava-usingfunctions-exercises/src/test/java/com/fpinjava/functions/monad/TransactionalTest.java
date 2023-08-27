package com.fpinjava.functions.monad;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TransactionalTest {

    Account acct1, acct2;

    @Before
    public void setUp() throws Exception {
        acct1 = new Account(1000);
        acct2 = new Account(0);

    }

    @Test
    public void testMap() {
        Transactional.begin()
                     .map(txState -> {
                         acct1.withdraw(500);
                         return txState;
                     })
                     .map(txState -> {
                         acct2.deposit(500);
                         return txState;
                     })
                     .commit();

        assertEquals(500, acct1.balance);
        assertEquals(500, acct2.balance);

    }
}