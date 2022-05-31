package com.mylove.mpdemo1010.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mylove.mpdemo1010.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author Zeqing Li Email:lizeqing77@163.com
 * @Description
 * @date 2022-05-31 15:53
 */

@Repository
public interface UserMapper extends BaseMapper<User> {
}
