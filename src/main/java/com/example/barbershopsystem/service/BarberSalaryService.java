package com.example.barbershopsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.barbershopsystem.domain.BarberSalary;
import com.example.barbershopsystem.domain.ResponseResult;


/**
 * 理发师工资表(BarberSalary)表服务接口
 *
 * @author makejava
 * @since 2024-04-30 02:31:31
 */
public interface BarberSalaryService extends IService<BarberSalary> {

    ResponseResult getInfoList(int pageNum, int pageSize);

    ResponseResult addInfo(BarberSalary barberSalary);

    ResponseResult getInfoById(Long id);

    ResponseResult update(BarberSalary barberSalary);

    ResponseResult deleteById(Long id);

    ResponseResult fuzzySearch(int pageNum, int pageSize, String title);

    ResponseResult getInfoByBaberId(int pageNum, int pageSize,String id);

    ResponseResult getInfoListBymanager(int pageNum, int pageSize);
}
