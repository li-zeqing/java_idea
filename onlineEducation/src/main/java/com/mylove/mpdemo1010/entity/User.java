package com.mylove.mpdemo1010.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.util.Date;

/**
 * @author Zeqing Li Email:lizeqing77@163.com
 * @Description
 * @date 2022-05-31 15:50
 */

@Data
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;

    //create_time
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    //update_time
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    //版本号
    @Version
    @TableField(fill = FieldFill.INSERT)
    private Integer version;

    //逻辑删除
    @TableLogic
    private Integer deleted;
}
