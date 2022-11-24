package com.github.mouday.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mouday.reggie.common.R;
import com.github.mouday.reggie.entity.Employee;
import com.github.mouday.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

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
        Employee employeeRow = this.employeeService.getOne(queryWrapper);

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

    /**
     * 添加员工账号
     * 清除session
     *
     * @return R
     */
    @PostMapping
    public R<String> addEmployee(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("添加员工账号: {}", employee.toString());

        // 设置默认密码，并使用md5加密处理
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        // employee.setCreateTime(LocalDateTime.now());
        // employee.setUpdateTime(LocalDateTime.now());

        // 当前登录的用户ID
        // Long employeeId = (Long) request.getSession().getAttribute("employee");
        // employee.setCreateUser(employeeId);
        // employee.setUpdateUser(employeeId);

        this.employeeService.save(employee);

        return R.success(null);
    }


    /**
     * 分页查询员工信息
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> getEmployeeList(int page, int pageSize, String name) {
        log.info("page: {}, pageSize: {}, name: {}", page, pageSize, name);

        // 条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();

        // 添加过滤条件
        if (StringUtils.isNotEmpty(name)) {
            queryWrapper.like(Employee::getName, name);
        }

        // 添加排序
        queryWrapper.orderByDesc(Employee::getUpdateTime);

        // 分页
        Page pageInfo = new Page(page, pageSize);

        // 执行查询
        this.employeeService.page(pageInfo, queryWrapper);

        return R.success(pageInfo);
    }

    /**
     * 更新员工信息
     *
     * @return
     */
    @PutMapping
    public R<String> updateEmployee(HttpServletRequest request, @RequestBody Employee employee) {
        // log.info("id: {}, status: {}", employee.getId(), employee.getStatus());

        // 当前登录用户
        // Long employeeId = (Long) request.getSession().getAttribute("employee");
        // employee.setUpdateUser(employeeId);

        // 设置更新时间
        // employee.setUpdateTime(LocalDateTime.now());

        long id = Thread.currentThread().getId();
        log.info("Thread id: {}", id);

        this.employeeService.updateById(employee);
        return R.success("更新成功");
    }


    /**
     * 根据id查询员工信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = this.employeeService.getById(id);

        if (employee != null) {
            return R.success(employee);
        } else {
            return R.error("数据不存在");
        }

    }
}
