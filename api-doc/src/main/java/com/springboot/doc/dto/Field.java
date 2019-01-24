package com.springboot.doc.dto;

import lombok.Data;

@Data
public class Field {
    /**
     * 参数名
     */
    private String name;
    /**
     * 参数数据类型
     */
    private String type;
    /**
     * 数据格式
     */
    private String format;
    /**
     * 是否必填
     */
    private String required;
    /**
     * 参考值
     */
    private String example;
    /**
     * 描述
     */
    private String description;
    /**
     * 枚举类型
     */
    private String enumstr;
}
