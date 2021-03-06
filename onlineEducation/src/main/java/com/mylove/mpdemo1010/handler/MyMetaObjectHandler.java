package com.mylove.mpdemo1010.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Zeqing Li Email:lizeqing77@163.com
 * @Description
 * @date 2022-05-31 17:30
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    //使用mp实现添加操作时，执行此方法
    @Override
    public void insertFill(MetaObject metaObject) {

        this.setFieldValByName("createTime",new Date(),metaObject);
        this.setFieldValByName("updateTime",new Date(),metaObject);

        this.setFieldValByName("version",1,metaObject);
    }
    //使用mp实现修改操作时，执行此方法
    @Override
    public void updateFill(MetaObject metaObject) {

        this.setFieldValByName("updateTime",new Date(),metaObject);
    }

}
