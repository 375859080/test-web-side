package com.training.service.Impl;

import com.training.domain.Car;
import com.training.domain.Message;
import com.training.domain.User;
import com.training.dto.AuditCarDTO;
import com.training.model.CarUserModel;
import com.training.model.SelectCarModel;
import com.training.repository.CarRepository;
import com.training.response.ResponseResult;
import com.training.service.CarService;
import com.training.service.MasterService;
import com.training.service.MessageService;
import com.training.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * by Huang
 */
@Service
@CacheConfig(cacheNames="car")
public class CarServiceImpl implements CarService {
    @Autowired
    CarRepository carRepository;
    @Autowired
    MasterService masterService;
    @Autowired
    UserService userService;
    @Autowired
    MessageService messageService;

    @Override
    public ResponseResult getCars(Long companyId){
        List<Car> cars = carRepository.findAllByCompany(companyId);
        if (cars.size() == 0)
            return new ResponseResult(500,"不存在任何车辆!");
        return new ResponseResult(cars);
    }

    @Override
    @Cacheable(key="'car'+ #p0")
    public ResponseResult getCarById(Long id) {
        try {
            Car car = carRepository.findById(id).get();
            return new ResponseResult(car);
        }
        catch (Exception e) {
            return new ResponseResult(501, "id不存在!");
        }
    }

    @Override
    @CachePut(key="'car'+ #result.data.id")
    @CacheEvict(key="'car-all'")
    public ResponseResult save(Car car){
        try {
            Car c = carRepository.save(car);
            return new ResponseResult(c);
        }
        catch (Exception e){
            return new ResponseResult(502,"新建失败!");
        }
    }

    @Override
    @CachePut(key="'car'+ #result.data.id")
    public ResponseResult update(Car car) {
        try {
            Car c = carRepository.save(car);
            return new ResponseResult(c);
        }
        catch (Exception e){
            return new ResponseResult(503,"更新失败!");
        }
    }

    @Override
    @CacheEvict(key="'car'+ #p0")
    public ResponseResult delete(Long id){
        try {
            carRepository.deleteById(id);
            return new ResponseResult(200,"删除成功!");
        }
        catch (Exception e){
            return new ResponseResult(504,"删除失败!");
        }
    }

//        }
//        return new ResponseResult(cars);
//    }
//    @Override
//    public ResponseResult findByIsDeleted(int isDeleted) {
//        List<Car> cars = carRepository.findByIsDeleted(isDeleted);
//        if (cars.size() == 0){
//            switch (isDeleted){
//                case 0:return new ResponseResult(511,"不存在有效的车辆!");
//                case -1:return new ResponseResult(512,"没有已删除的车!");
//                default:return new ResponseResult(507,"无效的识别码!");
//            }
//        }
//        return new ResponseResult(cars);
//    }

    @Override
    public ResponseResult findByTimeAndUserID(SelectCarModel model, Long userId,Long companyId) {
        List<Car> cars;
        String startTime=model.getStartTime();
        String endTime=model.getEndTime();
        int isMine=model.getIsMine();
        if(isMine==1){
            cars = carRepository.findByTimeAndMy(startTime,endTime,userId);
        }else {
            cars = carRepository.findByTimeAndNotMy(startTime, endTime, userId,companyId);
        }
        if (cars.size() == 0){
            return new ResponseResult(505,"不存在该时间段可用的车辆!");
        }
        return new ResponseResult(getUserOfCars(cars));
    }

    //使用状态（0 空闲；1 审核中；2 使用中 ；审核不通过-1）
    @Override
    public ResponseResult updateCarIsUseOrNot(Long carId,int status) {
        try {
            Car car = carRepository.findById(carId).get();
            car.setIsUse(status);
            carRepository.save(car);
            return new ResponseResult(200,"更新车辆状态成功");
        }
        catch (Exception e){
            return new ResponseResult(506,"更新车辆状态失败!");
        }
    }

    @Override
    public ResponseResult findCarWaitForCheck(Long companyId) {
        List<Car> cars = carRepository.findCarWaitForCheck(companyId);
        if (cars.size() == 0)
            return new ResponseResult(507,"不存在待审核的车辆!");
        return new ResponseResult(cars);
    }

    @Override
    public ResponseResult findMyCarByIsPublicAndUserID(int isPublic, Long userId) {
        List<Car> cars = carRepository.findByIsPublicAndUserId(isPublic,userId);
        if (cars.size() == 0){
            return new ResponseResult(508,"您没有该状态的车辆!");
        }
        return new ResponseResult(cars);
    }

    @Override
    public ResponseResult findCarByIsUse(int isUse, Long companyId) {
        List<Car> cars = carRepository.findCarByIsUse(isUse,companyId);
        if (cars.size() == 0){
            return new ResponseResult(508,"没有该状态的车辆!");
        }
        return new ResponseResult(cars);
    }

    @Override
    public ResponseResult findCarPassed(Long companyId) {
        List<Car> cars = carRepository.findCarPassed(companyId);
        if (cars.size() == 0){
            return new ResponseResult(508,"没有该状态的车辆!");
        }
        return new ResponseResult(cars);
    }

    //获取所有车辆 by pja
    @Override
    @Cacheable(key="'car-all'")
    public ResponseResult findAllCars(){
        return new ResponseResult(carRepository.findAll());
    }

    @Override
    public ResponseResult findCarsByUserId(Long userId) {
        return new ResponseResult(carRepository.findCarsByUserId(userId));
    }

    @Override
    public ResponseResult passAddCarOrNot(AuditCarDTO auditCarDTO) {
        Car car = (Car) getCarById(auditCarDTO.getCarId()).getData();
        if(car == null) return new ResponseResult(501,"id不存在！");
        car.setIsUse(auditCarDTO.getIsAccept());
        carRepository.save(car);
        if(auditCarDTO.getIsAccept()==-1){
            Message message= new Message("car",auditCarDTO.getCarId(),auditCarDTO.getReason());
            messageService.save(message);
        }
        return new ResponseResult(200,"更新车辆状态成功");
    }

    private List<CarUserModel> getUserOfCars(List<Car> cars){
        List<CarUserModel> carUserModels=new ArrayList<>();
        for(Car car:cars){
            carUserModels.add(getUserOfCar(car));
        }
        return carUserModels;
    }

    private CarUserModel getUserOfCar(Car car){
        User user=(User) userService.getUserById(car.getUserId()).getData();
        return new CarUserModel(user,car);
    }

    @Override
    public ResponseResult findCarByIsPublicAndCompanyId(int isPublic, Long companyId) {
        List<Car> cars = carRepository.findCarByIsPublicAndCompanyId(isPublic,companyId);
        return new ResponseResult(cars);
    }
}
