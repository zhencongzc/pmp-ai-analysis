package com.pmp.mapper;

import com.pmp.domain.auth.UserDO;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface AuthMapper {

    UserDO findUserById(Long userId);

    UserDO findByUsername(String username);

}