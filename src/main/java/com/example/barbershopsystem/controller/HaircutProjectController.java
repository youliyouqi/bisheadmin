package com.example.barbershopsystem.controller;



import com.example.barbershopsystem.domain.HaircutProject;
import com.example.barbershopsystem.domain.ResponseResult;
import com.example.barbershopsystem.service.HaircutProjectService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 理发项目管理表(HaircutProject)表控制层
 *
 * @author makejava

 */
@RestController
@RequestMapping("haircutProject")
public class HaircutProjectController  {
    /**
     * 服务对象
     */
    @Resource
    private HaircutProjectService haircutProjectService;

    @GetMapping("getInfoList")
    public ResponseResult getInfoList(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize){
        return haircutProjectService.getInfoList(pageNum,pageSize);
    }

    @PostMapping("/addInfo")
    public ResponseResult addInfo(@RequestBody HaircutProject haircutProject){

        return haircutProjectService.addInfo(haircutProject);
    }

    @GetMapping("/getInfoById/{id}")
    public ResponseResult getInfoById(@PathVariable Long id){
        return haircutProjectService.getInfoById(id);
    }
    @PostMapping("/update")
    public ResponseResult update(@RequestBody HaircutProject haircutProject){

        return haircutProjectService.update(haircutProject);
    }
    @GetMapping("/deleteById/{id}")
    public ResponseResult deleteById(@PathVariable Long id){
        return haircutProjectService.deleteById(id);
    }

    @GetMapping("/likeSelect")
    public ResponseResult fuzzySearch(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize ,String title ){
        return haircutProjectService.fuzzySearch(pageNum,pageSize,title);
    }

    @GetMapping("/getInfoByCategoryId")
    public ResponseResult getInfoByCategoryId(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize,String categoryId){
        return haircutProjectService.getInfoByCategoryId(pageNum,pageSize,categoryId);
    }
}

