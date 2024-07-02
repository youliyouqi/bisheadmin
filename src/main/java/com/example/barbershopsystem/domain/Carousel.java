package com.example.barbershopsystem.domain;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 轮播图(Carousel)表实体类
 *
 * @author makejava
 * @since 2024-04-11 13:31:10
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("carousel")
public class Carousel  {
    //主键@TableId
    private Long id;

    //轮播图名称
    private String ImgName;
    //轮播图路径
    private String value;
    //逻辑删除 0存在 1删除
    private Integer delFlag;



}
