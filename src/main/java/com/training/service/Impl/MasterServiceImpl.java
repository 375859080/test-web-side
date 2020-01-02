package com.training.service.Impl;


import com.training.domain.Master;
import com.training.model.AuditModel;
import com.training.repository.CarRepository;
import com.training.repository.MasterRepository;
import com.training.repository.RouteRepository;
import com.training.repository.UserRepository;
import com.training.response.ResponseResult;
import com.training.service.MasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MasterServiceImpl implements MasterService {

    @Autowired
    MasterRepository masterRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CarRepository carRepository;
    @Autowired
    RouteRepository routeRepository;

    @Override
    public ResponseResult findAllMasters() {
        List<Master> Masters = masterRepository.findAll();
        if (Masters.size() == 0)
            return new ResponseResult(500,"管理员不存在!");
        return new ResponseResult(Masters);
    }

    @Override
    public ResponseResult findMastersByName(String newname) {
        List<Master> Masters =  masterRepository.findAllByName(newname);
        if (Masters.size() == 0)
            return new ResponseResult(501,"name不存在!");
        return new ResponseResult(Masters);
    }

    @Override
    public ResponseResult saveMaster(Master master) {
        try {
            Master m =  masterRepository.save(master);
            return new ResponseResult(m);
        }
        catch (Exception e){
            return new ResponseResult(503,"插入失败!");
        }
    }

    @Override
    public void deleteMaster(Master master) {
        masterRepository.delete(master);
    }

    @Override
    public ResponseResult deleteMasterById(Long id) {
        try {
            masterRepository.deleteById(id);
            return new ResponseResult();
        }
        catch (Exception e){
            return new ResponseResult(504,"删除失败!");
        }
    }

    @Override
    public ResponseResult updateMaster(Master master) {
        try {
            Master m =  masterRepository.save(master);
            return new ResponseResult(m);
        }
        catch (Exception e){
            return new ResponseResult(503,"更新失败!");
        }
    }

    @Override
    public ResponseResult findMasterById(Long id) {
        Master Master =  masterRepository.findById(id).get();
        if (Master == null)
            return new ResponseResult(501,"id不存在!");
        return new ResponseResult(Master);
    }

    @Override
    public ResponseResult updateNameOfMastersById(Long id,String name){
        try {
            Master m = masterRepository.findById(id).get();
            m.setName(name);
            return new ResponseResult(m);
        }
        catch (Exception e){
            return new ResponseResult(503,"更新失败!");
        }

    }

    @Override
    public ResponseResult loginByMasterName(String mastername,String password) {
//        Master master = masterRepository.findMasterByMasterName(mastername);
//        System.out.println(master == null);
//        if (master == null)
//            return new ResponseResult(500,"管理员不存在!");
//        if (master.getPassword().equals(password))
//            return new ResponseResult(200,"登录成功！",master);
//        return new ResponseResult(506,"密码错误!");
        return null;
    }

    @Override
    public ResponseResult getAuditNum() {
        AuditModel auditModel=new AuditModel(userRepository.getUsersByCheckStatus(0),carRepository.findByIsUse(1),routeRepository.findRoutesByStatus(0),routeRepository.findRoutesByStatusAndIsReimburse(3,2));
        return new ResponseResult(auditModel);
    }


}
