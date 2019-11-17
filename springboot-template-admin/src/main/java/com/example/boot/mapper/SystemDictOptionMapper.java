package com.example.boot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.boot.springboottemplatedomain.dict.persistent.SystemDictOption;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author chang_
 * @since 2019-11-13
 */
@Repository
public interface SystemDictOptionMapper extends BaseMapper<SystemDictOption> {

    /**
     * 通过字段ID删除option
     *
     * @param dictId 字典ID
     */
    void deleteAllByDictId(@Param(value = "dictId") Long dictId);
}
