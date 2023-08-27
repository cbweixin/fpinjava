package com.fpinjava.functions.monad;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

public class TransactionalTest {

    Account acct1, acct2;

    @Before
    public void setUp(){
        acct1 = new Account(1000);
        acct2 = new Account(0);


    }

    @Test
    public void testMapHappyPath() {
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

    @Test
    public void testMapSadPath(){
        Account fakeAcct1 = mock(Account.class);
        doThrow(new IllegalArgumentException("wrong")).when(fakeAcct1).withdraw(anyInt());

        Account acct3 = new Account(100);

        Transactional.begin()
            .map(txState -> {
                fakeAcct1.withdraw(100);
                return txState;
            }).map(txState -> {
                acct2.deposit(100);
                return txState;
                     }).commit();

        assertEquals(100, acct3.balance);
    }
}