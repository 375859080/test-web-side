package com.training.service.Impl;

import com.training.domain.Settlement;
import com.training.repository.SettlementRepository;
import com.training.response.ResponseResult;
import com.training.service.SettlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettlementServiceImpl implements SettlementService {

    @Autowired
    SettlementRepository settlementRepository;

    @Override
    public ResponseResult findAllSettlement() {
        List<Settlement> settlements = settlementRepository.findAll();
        if (settlements.size() == 0)
            return new ResponseResult(500,"结算不存在!");
        return new ResponseResult(settlements);
    }

    @Override
    public ResponseResult findSettlementById(Long id) {
        Settlement settlement = settlementRepository.findById(id).get();
        if (settlement == null)
            return new ResponseResult(501,"id不存在!");
        return new ResponseResult(settlement);
    }

    @Override
    public ResponseResult findSettlementByRouteId(Long routeid) {
        List<Settlement> settlements = settlementRepository.findSettlementByRouteId(routeid);
        if (settlements.size() == 0)
            return new ResponseResult(502,"routeid不存在!");
        return new ResponseResult(settlements);
    }

    @Override
    public ResponseResult findSettlementBySecRouteId(Long secrouteid) {
        List<Settlement> settlements = settlementRepository.findSettlementBySecRouteId(secrouteid);
        if (settlements.size() == 0)
            return new ResponseResult(504,"secrouteid不存在!");
        return new ResponseResult(settlements);
    }

    @Override
    public ResponseResult findSettlementByUserId(Long userId) {
        List<Settlement> settlements = settlementRepository.findSettlementBySecRouteId(userId);
        if (settlements.size() == 0)
            return new ResponseResult(503,"userId不存在!");
        return new ResponseResult(settlements);
    }

    @Override
    public ResponseResult saveSettlement(Settlement settlement) {
        try {
            Settlement s = settlementRepository.save(settlement);
            return new ResponseResult(s);
        }
        catch (Exception e){
            return new ResponseResult(505,"插入失败!");
        }
    }

    @Override
    public ResponseResult updateSettlement(Settlement settlement) {
        try {
            Settlement s = settlementRepository.save(settlement);
            return new ResponseResult(s);
        }
        catch (Exception e){
            return new ResponseResult(506,"更新失败!");
        }
    }
}
