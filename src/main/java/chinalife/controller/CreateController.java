package chinalife.controller;

import chinalife.entity.Insurance;
import chinalife.entity.User;
import chinalife.service.InsuranceService;
import chinalife.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @BelongsProject: chinalife
 * @BelongsPackage: chinalife.controller
 * @Author: Hinstein
 * @CreateTime: 2019-03-18 08:46
 * @Description: 创建权限控制层
 */
@Controller
@RequestMapping("/create")
public class CreateController {

    @Autowired
    InsuranceService insuranceService;

    @Autowired
    UserService userService;

    /**
     * 来到创建保单页面
     *
     * @return 创建保单页面
     */
    @GetMapping("")
    public String create() {
        return "/CRUD/create";
    }

    /**
     * 异步获取保存保单信息
     *
     * @param insurance
     * @param session
     * @return json数据
     */
    @ResponseBody
    @PostMapping("/insurance")
    public Map<String, Object> createInsurance(Insurance insurance, HttpSession session) {

        //从session中获取当前用户信息
        User user = (User) session.getAttribute("user");
        //生成uuid给保单编号
        insurance.setBaodanNo(java.util.UUID.randomUUID().toString());
        //生成系统时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        insurance.setInforceTime(df.format(new Date()));
        //保存保单的员工号
        insurance.setClerkId(user.getId());
        //保存保单
        insuranceService.save(insurance);
        //用户表中保单数加一
        userService.baodanNumber(user.getId());
        //返回json数据
        Map<String, Object> map = new HashMap<>();
        map.put("success", "添加保单成功，正在跳转我的保单页面！");
        return map;
    }
}
