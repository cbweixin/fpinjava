package com.fpinjava.functions.monad;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;


public class MOptionalTest {

    class Account {

        public Account(Address address) {
            this.address = address;
        }

        public Address getAddress() {
            return address;
        }

        public Account setAddress(Address address) {
            this.address = address;
            return this;
        }

        Address address;

    }

    class Address {

        public Address() {
        }

        public Address(City city) {
            this.city = city;
        }

        public City getCity() {
            return city;
        }

        public Address setCity(City city) {
            this.city = city;
            return this;
        }

        City city;

    }

    class City {

        public City(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public City() {
        }

        public City setName(String name) {
            this.name = name;
            return this;
        }

        String name;
    }

    Account acct;
    Account acct2;

    @Before
    public void setUp() throws Exception {
        final City puqi = new City("puqi");
        final Address address = new Address(puqi);
        acct = new Account(address);

        final City nullCity = new City();
        final Address address2 = new Address(nullCity);
        acct2 = new Account(address);
    }

    @Test
    public void testMapHappPath() {
        String name = new MOptional<>(acct).map(Account::getAddress)
                                           .map(Address::getCity)
                                           .map(City::getName)
                                           .OrElse("Unknown");

        assertEquals("puqi", name);

    }

    public void testMapWithNullCity() {
        String name = new MOptional<>(acct2).map(Account::getAddress)
                                            .map(Address::getCity)
                                            .map(City::getName)
                                            .OrElse("Unknown");
        assertEquals("Unknown", name);
    }

}