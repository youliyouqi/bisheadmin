package com.example.barbershopsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.barbershopsystem.domain.LeaveApplication;
import com.example.barbershopsystem.domain.ResponseResult;


/**
 * 请假申请表(LeaveApplication)表服务接口
 *
 * @author makejava
 *
 */
public interface LeaveApplicationService extends IService<LeaveApplication> {

    ResponseResult getInfoList(int pageNum, int pageSize);

    ResponseResult getInfoById(Long id);

    ResponseResult fuzzySearch(int pageNum, int pageSize, String name);

    ResponseResult approval(LeaveApplication leaveApplication);

    ResponseResult deleteById(Long id);

    ResponseResult add(LeaveApplication leaveApplication);

    ResponseResult cancleApplication(Long id);
}
