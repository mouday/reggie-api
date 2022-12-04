package com.github.mouday.reggie.controller;


import com.github.mouday.reggie.common.CustomException;
import com.github.mouday.reggie.common.R;
import com.github.mouday.reggie.entity.User;
import com.github.mouday.reggie.service.UserService;
import com.github.mouday.reggie.utils.SMSUtils;
import com.github.mouday.reggie.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.xml.ws.Holder;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * 发送验证码
     *
     * @param user
     * @param session
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> sendMessage(@RequestBody User user, HttpSession session) {
        String phone = user.getPhone();

        if (StringUtils.isEmpty(phone)) {
            return R.error("手机号不能为空");
        }

        // 生成4位随机验证码
        String code = ValidateCodeUtils.generateValidateCode(4).toString();
        log.info("code: {}", code);

        // 阿里云短信平台发送验证码
        // SMSUtils.sendMessage();

        // 存入session
        session.setAttribute(phone, code);

        return R.success("发送成功");
    }

    /**
     * 手机号+验证码登录
     *
     * @param map
     * @param session
     * @return
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session) {
        log.info("map: {}", map.toString());

        // 获取手机号
        String phone = map.get("phone").toString();
        if (StringUtils.isEmpty(phone)) {
            throw new CustomException("手机号不能为空");
        }

        // 获取验证码
        String code = map.get("code").toString();
        if (StringUtils.isEmpty(code)) {
            throw new CustomException("验证码不能为空");
        }

        // 从session中获取手机号对应的验证码
        Object sessionCode = session.getAttribute(phone);
        if (sessionCode == null) {
            throw new CustomException("未发送验证码");
        }

        // 比对验证码
        if (!sessionCode.equals(code)) {
            throw new CustomException("验证码不正确");
        }

        // 登录成功
        // 如果手机号不存在就自动注册新用户
        User user = userService.getUserByPhone(phone);
        if (user == null) {
            user = new User();
            user.setPhone(phone);
            user.setStatus(1);
            userService.save(user);
        }

        session.setAttribute("user", user.getId());
        return R.success(user);

    }
}
