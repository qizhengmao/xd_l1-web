package com.example.background_program.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.background_program.common.QueryPageParam;
import com.example.background_program.common.Result;
import com.example.background_program.entity.Menu;
import com.example.background_program.entity.User;
import com.example.background_program.service.IMenuService;
import com.example.background_program.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import javax.websocket.SessionException;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2022-11-16
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IMenuService imenuService;
    @GetMapping("/findByNo")
    public Result findByNo(@RequestParam String no){
        List list = iUserService.lambdaQuery().eq(User::getNo,no).list();
        return list.size()>0?Result.suc(list):Result.fail();
    }
    @RequestMapping("/list")
    public List<User> list(){
        return iUserService.list();
    }
    //新增
    @PostMapping("/save")
    public Result save(@RequestBody User user){
        return iUserService.save(user)?Result.suc():Result.fail();

    }
    //更新
    @PostMapping("/update")
    public Result update(@RequestBody User user){
        return iUserService.updateById(user)?Result.suc():Result.fail();
    }
    //登录
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        List list = iUserService.lambdaQuery().eq(User::getNo,user.getNo()).eq(User::getPassword,user.getPassword()).list();
        if(list.size()>0){
            User user1 = (User)list.get(0);
            List menulist = imenuService.lambdaQuery().like(Menu::getMenuright,user1.getRoleId()).list();
            HashMap res = new HashMap();
            res.put("user",user1);
            res.put("menu",menulist);
            return Result.suc(res);
        }
        return Result.fail();
    }
    @PostMapping("/del")
    public Result del(@RequestParam String id){
        return iUserService.removeById(id)?Result.suc():Result.fail();
    }
    //修改
    @PostMapping("/mod")
    public boolean mod(@RequestBody User user){
        return iUserService.updateById(user);
    }
    //新增或修改
    @PostMapping("/saveorMod")
    public boolean saveorMod(@RequestBody User user){
        return iUserService.saveOrUpdate(user);
    }
    //删除
    @GetMapping("/delete")
    public boolean delete(Integer id){
        return iUserService.removeById(id);
    }
    //查询（模糊，匹配）
    @PostMapping("/listp")
    public Result listp(@RequestBody User user){
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(user.getName())){
            lambdaQueryWrapper.like(User::getName,user.getName());
        }

        return Result.suc(iUserService.list(lambdaQueryWrapper));
    }
    @PostMapping("/listPage")
    public List<User> listPage(@RequestBody QueryPageParam pageParam){
        HashMap param = pageParam.getParam();
        String name = (String) param.get("name");
        System.out.println("param="+param.get("name"));

        Page<User> page = new Page();
        page.setCurrent(pageParam.getPageNum());
        page.setSize(pageParam.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        IPage result= iUserService.page(page,lambdaQueryWrapper);

        System.out.println(result.getTotal());
        return result.getRecords();
    }
    @PostMapping("/listPageC")
    public List<User> listPageC(@RequestBody QueryPageParam pageParam){
        HashMap param = pageParam.getParam();
        String name = (String) param.get("name");

        Page<User> page = new Page();
        page.setCurrent(pageParam.getPageNum());
        page.setSize(pageParam.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.like(User::getName,name);
        //IPage result= iUserService.pageC(page);
        IPage result= iUserService.pageCC(page,lambdaQueryWrapper);
        System.out.println(result.getTotal());
        return result.getRecords();
    }
    @PostMapping("/listPageC1")
    public Result listPageC1(@RequestBody QueryPageParam pageParam) {
        HashMap param = pageParam.getParam();
        String name = (String) param.get("name");
        String sex = (String) param.get("sex");
        String roleId = (String) param.get("roleId");

        Page<User> page = new Page();
        page.setCurrent(pageParam.getPageNum());
        page.setSize(pageParam.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        if (StringUtils.isNotBlank(name) && !"null".equals(name)){
            lambdaQueryWrapper.like(User::getName, name);
        }
        if (StringUtils.isNotBlank(sex)){
            lambdaQueryWrapper.eq(User::getSex, sex);
        }
        if (StringUtils.isNotBlank(roleId)){
            lambdaQueryWrapper.eq(User::getRoleId, roleId);
        }
        //IPage result= iUserService.pageC(page);
        IPage result = iUserService.pageCC(page, lambdaQueryWrapper);

        return Result.suc(result.getRecords(), result.getTotal());
    }
}
