package com.github.mouday.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.mouday.reggie.common.R;
import com.github.mouday.reggie.entity.Employee;
import com.github.mouday.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 登录逻辑
     * 1、通过username查数据库
     * 2、校验密码password md5加密
     * 3、判断账号是否可用 status
     * 4、登录成功
     *
     * @param employee
     * @return R
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        // 1、通过username查数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee employeeRow = employeeService.getOne(queryWrapper);

        if (employeeRow == null) {
            return R.error("登录失败");
        }

        // 2、校验密码password md5加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        if (!employeeRow.getPassword().equals(password)) {
            return R.error("登录失败");
        }

        // 3、判断账号是否可用 status
        if (employeeRow.getStatus() == 0) {
            return R.error("账号已禁用");
        }

        // 4、登录成功
        request.getSession().setAttribute("employee", employeeRow.getId());

        return R.success(employeeRow);
    }

    /**
     * 退出逻辑
     * 清除session
     *
     * @return R
     */
    @PostMapping("/logout")
    public R<Employee> logout(HttpServletRequest request) {

        request.getSession().removeAttribute("employee");

        return R.success(null);
    }
}
