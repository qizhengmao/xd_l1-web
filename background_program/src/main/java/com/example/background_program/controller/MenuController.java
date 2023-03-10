package com.example.background_program.controller;
import com.example.background_program.common.Result;
import com.example.background_program.entity.Menu;
import com.example.background_program.service.IMenuService;
import com.example.background_program.service.IRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2023-02-20
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private IMenuService imenuService;

    @GetMapping("/list")
    public Result list(@RequestParam String roleId){
        List list = imenuService.lambdaQuery().like(Menu::getMenuright,roleId).list();
        return Result.suc(list);
    }
}
