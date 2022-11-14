package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.common.contants.Contants;
import com.bjpowernode.crm.common.domain.ReturnObject;
import com.bjpowernode.crm.common.utils.DateUtils;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (C), 2017-2022
 * <author>          <time>              <version>
 * 冯俊        2022/8/23 11:30    since 1.0.0
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("/settings/qx/user/toLogin.do")
    public String toLogin(){
        return "settings/qx/user/login";
    }

    /**
     * 验证用户登录的方法
     * @return
     */
    @RequestMapping("/settings/qx/user/login.do")
    public @ResponseBody Object login(String loginAct, String loginPwd, String isRemPwd, HttpServletRequest request,HttpServletResponse response, HttpSession session){
        //封装参数
        Map<String,Object> map=new HashMap<>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);
        //调用service层方法，查询用户
        User user = userService.queryUserByLoginActAndPwd(map);
        ReturnObject retObj= new ReturnObject();
        if (user==null){
            //用户名或密码错误
            retObj.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            retObj.setMessage("用户名或密码错误");
        }else {//进一步判断
            if (user.getLockState().equals("0")){
                //账户被锁定
                retObj.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                retObj.setMessage("账户被锁定");
            }else if (!user.getAllowIps() .contains(request.getRemoteAddr())){
                //用户登录ip不在范围类 ip受限
                retObj.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                retObj.setMessage("ip受限");
            }else if(!(user.getExpireTime().compareTo(DateUtils.formatDateTime(new Date()))>0)){//账户过期时间与当前时间进行比较
                //账户已过期
                retObj.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                retObj.setMessage("账户已过期");
            }else {
                //登录成功
                retObj.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                retObj.setMessage("登录成功");
                session.setAttribute(Contants.SESSION_USER,user);
                //如果选择需要记住密码 则往外写cookie
                if("true".equals(isRemPwd)){
                    Cookie c1 = new Cookie("loginAct", user.getLoginAct());
                    Cookie c2=new Cookie("loginPwd",user.getLoginPwd());
                    c1.setMaxAge(10*24*60*60);//设置有效期
                    c2.setMaxAge(10*24*60*60);//设置有效期
                    response.addCookie(c1);
                    response.addCookie(c2);
                }else {
                    Cookie c1 = new Cookie("loginAct", null);
                    Cookie c2=new Cookie("loginPwd",null);
                    c1.setMaxAge(0);//设置有效期
                    c2.setMaxAge(0);//设置有效期
                    response.addCookie(c1);
                    response.addCookie(c2);
                }
            }
        }
        return retObj;
    }
    @RequestMapping("/settings/qx/user/logout.do")
    public String logout(HttpServletResponse response,HttpSession session){
        //清空cookie
        Cookie c1 = new Cookie("loginAct", "1");
        Cookie c2=new Cookie("loginPwd","1");
        c1.setMaxAge(0);//设置有效期
        c2.setMaxAge(0);//设置有效期
        response.addCookie(c1);
        response.addCookie(c2);
        //销毁session
        session.invalidate();
        return "redirect:/";
    }
}
