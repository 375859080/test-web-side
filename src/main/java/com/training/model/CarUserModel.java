package com.training.model;

import com.training.domain.Car;
import com.training.domain.User;

public class CarUserModel {
    private User user;
    private Car car;

    public CarUserModel() {
    }

    public CarUserModel(User user, Car car) {
        this.user = user;
        this.car = car;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
