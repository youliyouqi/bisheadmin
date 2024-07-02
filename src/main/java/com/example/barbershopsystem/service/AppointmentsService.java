package com.example.barbershopsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.barbershopsystem.domain.Appointments;
import com.example.barbershopsystem.domain.ResponseResult;

import java.util.Date;
import java.util.List;


/**
 * (Appointments)表服务接口
 *
 * @author makejava
 * @since 2024-04-22 18:15:45
 */
public interface AppointmentsService extends IService<Appointments> {

    ResponseResult addAppointmentInfo(Appointments appointments);



    ResponseResult getInfoListByuserId(int pageNum, int pageSize, String userId);

    ResponseResult sendReminderNotification(Long id);

    ResponseResult updateCancle(Long id);

    ResponseResult secondConfirmation(Long id);

    ResponseResult getInfoList(int pageNum, int pageSize);

    ResponseResult fuzzySearch(int pageNum, int pageSize, String name);

    ResponseResult deleteById(Long id);

    ResponseResult getAnnounInfoById(Long id);

    ResponseResult approval(Appointments appointments);

    ResponseResult checkCurrentAppointmentRecord(Long id);

    ResponseResult getInfoListByBarberId(int pageNum, int pageSize, Long userId);
}
