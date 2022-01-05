package com.koala.jucsenior.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * day05：
 *      链式调用
 * Create by koala on 2022-01-04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)//链式调用
public class Book
{
    private Integer id;
    private String  bookName;
    private double  price;
    private String  author;
}
