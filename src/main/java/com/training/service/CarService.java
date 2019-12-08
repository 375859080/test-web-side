package com.training.service;

import com.training.domain.Car;
import com.training.response.ResponseResult;

import java.util.List;

/**
 * by Huang
 */
public interface CarService {
    ResponseResult getCars();
    ResponseResult getCarById(Long id);
    ResponseResult save(Car car);
    ResponseResult update(Car car);
    ResponseResult delete(Long id);
}
