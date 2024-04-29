/**
 * 功能：
 * 作者：JInli
 * 日期： 2024/2/17 21:53
 */
package com.example.springboot.common;

import java.util.List;

public class Page<T> {
    private Integer total;
    private List<T> list;
}
