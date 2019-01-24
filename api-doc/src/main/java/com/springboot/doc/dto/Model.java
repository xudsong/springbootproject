package com.springboot.doc.dto;

import lombok.Data;

import java.util.List;

@Data
public class Model {
    /**
     * 类型
     */
    private String type;
    /**
     * 属性
     */
    private List<Field> properties;
    /**
     * 必填参数
     */
    private List<String> required;
    /**
     * 对象名
     */
    private String title;
}
