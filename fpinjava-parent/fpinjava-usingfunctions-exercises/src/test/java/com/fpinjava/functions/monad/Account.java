package com.fpinjava.functions.monad;

import java.util.List;

class Account {

    public Account(int balance) {
        this.balance = balance;
    }

    public int balance;
    public Address address;

    public List<Phone> phones;

    public List<Phone> getPhones() {
        return phones;
    }

    public Account setPhones(List<Phone> phones) {
        this.phones = phones;
        return this;
    }


    public void withdraw(int amount) {
        this.balance -= amount;
        if (this.balance < 0) {
            this.balance += amount;
            throw new RuntimeException("insufficient fund...");
        }
    }

    public void deposit(int amount) {
        this.balance += amount;
    }

    public Account(Address address) {
        this.address = address;
        this.balance = 0;
    }

    public Address getAddress() {
        return address;
    }

    public Account setAddress(Address address) {
        this.address = address;
        return this;
    }

    public MOptional<City> city() {
        return new MOptional<>(address).map(Address::getCity);
    }

    /**
     * before use `flatMap`, we need call nested Moptional map. not very convenient
     *
     * @param
     * @return
     */
    public String getCityName() {
        final MOptional<Account> optAcct = new MOptional<>(this);
        // nested Moptional, so it is not that convinient
        final MOptional<MOptional<City>> optOptCity = optAcct.map(account -> account.city());
        final MOptional<String> optName = optOptCity.map(optCity -> optCity.map(City::getName))
                                                    .OrElse(null);
        return optName.OrElse("Unknown");
    }

    public String getCityNameWithFlatMap() {
        final MOptional<Account> optAcct = new MOptional<>(this);
        String cityName = optAcct.flatMap(optacct -> optacct.city())
                                 .map(City::getName)
                                 .OrElse("Unknown");
        return cityName;
    }
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

class Phone {

    public Phone(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public Phone setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
        return this;
    }

    String phoneNum;

}
