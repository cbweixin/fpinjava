package com.fpinjava.functions.monad;

class Account {

    public Account(int balance) {
        this.balance = balance;
    }

    public int balance;
    public Address address;


    public void withdraw(int amount) {
        this.balance -= amount;
        if(this.balance < 0){
            this.balance += amount;
            throw new RuntimeException("insufficient fund...");
        }
    }

    public void deposit(int amount){
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
