package com.example.springliquidbase.domain.user;

import com.example.springliquidbase.domain.common.PageModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPageModel extends PageModel {

    private String nameFilter;
}
