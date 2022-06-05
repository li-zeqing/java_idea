package com.mylove.servicebase.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * @author Zeqing Li Email:lizeqing77@163.com
 * @Description 自定义异常类
 * @date 2022-06-05 20:30
 */
@Data
@AllArgsConstructor //生成全参构造方法
@NoArgsConstructor //生成无参构造方法
public class MyException extends RuntimeException {

    private Integer code;//状态码

    private String msg;//异常信息
}
