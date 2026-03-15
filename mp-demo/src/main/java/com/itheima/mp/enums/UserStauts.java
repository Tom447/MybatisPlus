package com.itheima.mp.enums;


import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;




@Getter
public enum UserStauts {
    NORMAL(1, "正常"),
    FREZZE(2, "冻结"),
    ;
    @EnumValue
    private final int value;
    private final String desc;

    UserStauts(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
