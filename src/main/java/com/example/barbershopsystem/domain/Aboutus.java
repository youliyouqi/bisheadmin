package com.example.barbershopsystem.domain;

import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 关于我们(Aboutus)表实体类
 *
 * @author makejava
 * @since 2024-04-16 17:20:34
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("aboutus")
public class Aboutus  {
    //主键@TableId
    private Long id;

    //创建时间
    private Date createTime;
    //标题
    private String title;
    //副标题
    private String subtitle;
    //内容
    private String content;
    //图片1
    private String picture1;



}
