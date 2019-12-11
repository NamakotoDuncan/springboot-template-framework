package com.example.boot.springboottemplatebase.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.boot.springboottemplatebase.domain.systemrole.entity.SystemRoleMenuRefEntity;
import com.example.boot.springboottemplatebase.domain.systemrole.value.SecurityGetRoleMenuListByRoleIdVO;

import java.util.List;

/**
 * @author chang_
 * @since 2019-11-17
 */
public interface SystemRoleMenuRefService extends IService<SystemRoleMenuRefEntity> {

    List<SecurityGetRoleMenuListByRoleIdVO> securityGetRoleMenuListByRoleId(Long roleId);
}
