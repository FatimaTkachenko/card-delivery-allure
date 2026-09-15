package ru.netology.carddelivery;

public class UserInfo {
    private final String city;
    private final String name;
    private final String phone;
    private final String date;

    public UserInfo(String city, String name, String phone, String date) {
        this.city = city;
        this.name = name;
        this.phone = phone;
        this.date = date;
    }

    public String getCity() {
        return city;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getDate() {
        return date;
    }
}