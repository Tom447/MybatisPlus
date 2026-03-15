package com.itheima.mp.domain.vo;

import com.itheima.mp.domain.po.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor(staticName = "of")
public class PageVO<T> {
   private Long total;
   private Long pages;
   private List<T> list;
}
