package com.pmp.infrastructure.mapper;

import com.pmp.domain.model.auth.UserDO;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface AuthMapper {

    UserDO findUserById(Long userId);

    UserDO findByUsername(String username);

}