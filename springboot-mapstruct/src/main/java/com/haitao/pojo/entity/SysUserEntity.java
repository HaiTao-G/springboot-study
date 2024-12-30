package com.haitao.pojo.entity;

import com.haitao.pojo.vo.SysUserVo;
import io.github.linpeilie.annotations.AutoMapper;
import io.github.linpeilie.annotations.AutoMappers;
import io.github.linpeilie.annotations.AutoMapping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AutoMapper(target = SysUserVo.class)
public class SysUserEntity {
//    @AutoMapping(target = "userId")
    private String userId;
//    @AutoMapping(target = "userName")
    private String userName;

}
