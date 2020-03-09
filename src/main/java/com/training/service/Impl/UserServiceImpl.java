package com.training.service.Impl;


import com.training.domain.User;
import com.training.repository.UserRepository;
import com.training.response.ResponseResult;
import com.training.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames="user")
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Cacheable(key="user-all")
    public ResponseResult getUsers(){
        return new ResponseResult(userRepository.findAll());
    }

    @Override
    @Cacheable(key="'user'+ #p0")
    public ResponseResult getUserById(Long id) {
        return new ResponseResult(userRepository.findById(id).get());
    }

    @Override
    @CachePut(key="'user'+ #p0.id")
    public ResponseResult save(User user){
        return new ResponseResult(userRepository.save(user));
    }

    @Override
    public ResponseResult update(User user) {
        Optional<User> optional = userRepository.findById(user.getId());

        if(optional.isPresent()==false){
            return new ResponseResult(500,"找不到用户",null);
        }

        User user1 =optional.get();
        user.setPhoneNumber(user1.getPhoneNumber());
        user.setAccountId(user1.getAccountId());
        user.setUserName(user1.getUserName());
        //以上三条信息无法更新！需要走其它通道！
        return new ResponseResult(userRepository.save(user));
    }

    @Override
    @CacheEvict(key="'car'+ #p0")
    public ResponseResult delete(Long id){
        User user = userRepository.findById(id).get();
        userRepository.deleteById(id);
        return new ResponseResult(user);
    }

    @Override
    public ResponseResult getUserByPhone(String s) {
        User user = userRepository.findByPhoneNumber(s);
        return new ResponseResult(user);
    }

    @Override
    public ResponseResult getUserByAccount(Long id) {
        User user = userRepository.findByAccount(id);
        return new ResponseResult(user);
    }

    @Override
    public ResponseResult getUsersByCheckStatus(int status) {
        List<User> users = userRepository.getUsersByCheckStatus(status);
        return new ResponseResult(users);
    }

    @Override
    public ResponseResult updateFDUser(User user){
        return new ResponseResult(userRepository.save(user));
    }

    @Override
    public ResponseResult getUsersByCompanyId(Long companyId){
        List<User> users = userRepository.getUserByCompanyId(companyId);
        return new ResponseResult(users);
    }
}
