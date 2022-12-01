package com.github.mouday.reggie.dto;


import com.github.mouday.reggie.entity.Setmeal;
import com.github.mouday.reggie.entity.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
