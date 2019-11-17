package com.example.boot.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.boot.model.ResponseBodyBean;
import com.example.boot.model.dict.payload.CreateDictPLO;
import com.example.boot.model.dict.payload.GetDictTablePLO;
import com.example.boot.model.dict.payload.ModifyDictPLO;
import com.example.boot.service.SystemDictService;
import com.example.boot.springboottemplatedomain.dict.persistent.SystemDict;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author chang_
 * @since 2019-11-13
 */
@Slf4j
@Controller
@RequestMapping(value = "system/dict")
public class SystemDictController {

    private final SystemDictService dictService;

    @Autowired
    public SystemDictController(SystemDictService dictService) {
        this.dictService = dictService;
    }

    @GetMapping(value = "table")
    @ResponseBody
    public ResponseBodyBean<IPage<SystemDict>> table(GetDictTablePLO plo) {
        LambdaQueryWrapper<SystemDict> wrapper = new QueryWrapper<SystemDict>().lambda()
                .eq(false, SystemDict::getDictCode, plo.getDictCode())
                .eq(false, SystemDict::getName, plo.getName()); //查询条件
        Page<SystemDict> page = new Page<>(plo.getPageNo(), plo.getPageSize());
        IPage<SystemDict> table = dictService.page(page, wrapper);

        return ResponseBodyBean.ofSuccess(table);
    }

    @PostMapping(value = "create")
    @ResponseBody
    public ResponseBodyBean create(@RequestBody CreateDictPLO plo) {
        dictService.createDict(plo);
        return ResponseBodyBean.ofSuccess("创建数据字典成功");
    }

    @PutMapping(value = "modify")
    @ResponseBody
    public ResponseBodyBean modify(@RequestBody ModifyDictPLO plo) {
        dictService.modifyDict(plo);
        return ResponseBodyBean.ofSuccess("修改数据字典成功");
    }

    @DeleteMapping(value = "delete")
    @ResponseBody
    public ResponseBodyBean delete(@RequestParam(value = "dict_id") Long dictId) {
        dictService.deleteDict(dictId);
        return ResponseBodyBean.ofSuccess("删除数据字典成功");
    }
}
