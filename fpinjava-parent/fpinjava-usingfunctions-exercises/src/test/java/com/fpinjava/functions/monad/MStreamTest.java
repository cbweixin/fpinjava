package com.fpinjava.functions.monad;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class MStreamTest {

    final List<Account> accounts = new ArrayList<>();
    Account acct;
    Account acct2;

    Account acct3;

    @Before
    public void setUp() throws Exception {
        final Phone phone1 = new Phone("111");
        final Phone phone2 = new Phone("112");
        final Phone phone3 = new Phone("113");
        final City puqi = new City("puqi");
        final Address address = new Address(puqi);
        acct = new Account(address);
        acct.setPhones(asList(phone1, phone2, phone3));

        final City nullCity = new City();
        final Address address2 = new Address(nullCity);
        final Phone phone4 = new Phone("114");
        final Phone phone5 = new Phone("115");
        final Phone phone6 = new Phone("116");
        acct2 = new Account(address2);
        acct2.setPhones(asList(phone4, phone5, phone6));

        final Phone phone7 = new Phone("117");
        final Address address3 = new Address();
        acct3 = new Account(address3);
        acct3.setPhones(asList(phone7));

        this.accounts.add(acct);
        this.accounts.add(acct2);
        this.accounts.add(acct3);
    }


    @Test
    public void testFlatMap() {
        List<String> phoneNums = new MStream<>(accounts).flatMap(acct -> new MStream<>(acct.getPhones()))
                                                        .map(Phone::getPhoneNum)
                                                        .toList();
        assertArrayEquals(new String[]{"111", "112", "113", "114", "115", "116", "117"}, phoneNums.toArray());
    }

    @Test
    public void testFilter() {
        List<String> phoneNums = new MStream<>(accounts).flatMap(acct -> new MStream<>(acct.getPhones()))
                                                        .map(Phone::getPhoneNum)
                                                        .filter(pnum -> {
                                                            int n = Integer.parseInt(pnum);
                                                            return !((n & 1) > 0);
                                                        })
                                                        .toList();
        assertArrayEquals(new String[]{"112", "114", "116"}, phoneNums.toArray());
    }
}