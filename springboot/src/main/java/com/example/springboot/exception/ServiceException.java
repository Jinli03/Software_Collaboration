/**
 * 功能：
 * 作者：JInli
 * 日期： 2024/2/18 20:52
 */
package com.example.springboot.exception;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException{
    private final String code;
    public ServiceException(String msg) {
        super(msg);
        this.code = "500";
    }

    public ServiceException(String code, String msg){
        super(msg);
        this.code = code;
    }
}
