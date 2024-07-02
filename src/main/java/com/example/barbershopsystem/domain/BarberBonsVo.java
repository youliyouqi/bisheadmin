package com.example.barbershopsystem.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 尤里尤气
 * Created on 2024/5/1-17:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BarberBonsVo {
    private Long barberid;

    private String barbername;
    //奖金提成
    private Double bonusCommission;
}
