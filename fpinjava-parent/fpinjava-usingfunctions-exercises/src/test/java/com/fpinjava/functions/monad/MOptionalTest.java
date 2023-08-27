package com.fpinjava.functions.monad;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;


public class MOptionalTest {


    Account acct;
    Account acct2;

    Account acct3;

    @Before
    public void setUp() throws Exception {
        final City puqi = new City("puqi");
        final Address address = new Address(puqi);
        acct = new Account(address);

        final City nullCity = new City();
        final Address address2 = new Address(nullCity);
        acct2 = new Account(address2);

        final Address address3 = new Address();
        acct3 = new Account(address3);
    }

    @Test
    public void testMapHappPath() {
        String name = new MOptional<>(acct).map(Account::getAddress)
                                           .map(Address::getCity)
                                           .map(City::getName)
                                           .OrElse("Unknown");

        assertEquals("puqi", name);

    }

    @Test
    public void testMapWithNullCity() {
        String name = new MOptional<>(acct2).map(Account::getAddress)
                                            .map(Address::getCity)
                                            .map(City::getName)
                                            .OrElse("Unknown");
        assertEquals("Unknown", name);
    }

    public void testMapWithNullAddr() {
        String name = new MOptional<>(acct3).map(Account::getAddress)
                                            .map(Address::getCity)
                                            .map(City::getName)
                                            .OrElse("Unknown");
        assertEquals("Unknown", name);

    }



}