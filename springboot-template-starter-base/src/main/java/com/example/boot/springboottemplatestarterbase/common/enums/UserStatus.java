package com.example.boot.springboottemplatestarterbase.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统用户状态
 * <p>
 * 1：启用
 * 2：禁用
 *
 * @author Chang
 * @date 2019/7/21 19:53
 */
@Getter
@AllArgsConstructor
public enum UserStatus {

    ENABLED("1"), DISABLED("2");

    private String status;
}
