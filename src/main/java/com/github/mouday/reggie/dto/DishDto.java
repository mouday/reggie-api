package com.github.mouday.reggie.dto;


import com.github.mouday.reggie.entity.Dish;
import com.github.mouday.reggie.entity.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
