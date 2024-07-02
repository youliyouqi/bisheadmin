package com.example.barbershopsystem.controller;



import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.barbershopsystem.domain.*;
import com.example.barbershopsystem.handle.ExcelExportHandler;
import com.example.barbershopsystem.mapper.BarberSalaryMapper;
import com.example.barbershopsystem.service.BarberSalaryService;
import com.example.barbershopsystem.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 理发师工资表(BarberSalary)表控制层
 *
 * @author makejava
 * @since 2024-04-30 02:31:31
 */
@RestController
@RequestMapping("barberSalary")
public class BarberSalaryController  {
    /**
     * 服务对象
     */
    @Resource
    private BarberSalaryService barberSalaryService;
    @Autowired
    private BarberSalaryMapper barberSalaryMapper;
    @Resource
    private ExcelExportHandler excelExportHandler;



    @GetMapping("getInfoList")
    public ResponseResult getInfoList(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize){
        return barberSalaryService.getInfoList(pageNum,pageSize);
    }
    @GetMapping("getInfoListBymanager")
    public ResponseResult getInfoListBymanager(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize){
        return barberSalaryService.getInfoListBymanager(pageNum,pageSize);
    }
    @PostMapping("/addInfo")
    public ResponseResult addInfo(@RequestBody BarberSalary barberSalary){
        return barberSalaryService.addInfo(barberSalary);
    }

    @GetMapping("/getInfoById/{id}")
    public ResponseResult getAnnounInfoById(@PathVariable Long id){
        return barberSalaryService.getInfoById(id);
    }

    @PostMapping("/update")
    public ResponseResult update(@RequestBody BarberSalary barberSalary){

        return barberSalaryService.update(barberSalary);
    }
    @GetMapping("/deleteById/{id}")
    public ResponseResult deleteById(@PathVariable Long id){
        return barberSalaryService.deleteById(id);
    }
    @GetMapping("/likeSelect")
    public ResponseResult fuzzySearch(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize ,String title ){
        return barberSalaryService.fuzzySearch(pageNum,pageSize,title);
    }
    @GetMapping("/getInfoByBaberId")
    public ResponseResult getInfoByBaberId(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize ,String userId ){
        return barberSalaryService.getInfoByBaberId(pageNum,pageSize,userId);
    }
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {
        //获取需要导出的数据

        List<BarberSalary> barberSalaries = barberSalaryMapper.selectList(null);
        List<ExcelBarberSalaryVo> excelBarberSalaryVos = BeanCopyUtils.copyBeanList(barberSalaries, ExcelBarberSalaryVo.class);
        excelExportHandler.export(response, "人员表", excelBarberSalaryVos, ExcelBarberSalaryVo.class);
    }
}

