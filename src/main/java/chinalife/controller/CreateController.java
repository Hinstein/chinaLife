package chinalife.controller;

import chinalife.entity.Insurance;
import chinalife.entity.User;
import chinalife.service.InsuranceService;
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
 * @Description:
 */
@Controller
@RequestMapping("/create")
public class CreateController {

    @Autowired
    InsuranceService insuranceService;

    @GetMapping("")
    public String create() {
        return "/CRUD/create";
    }

    @ResponseBody
    @PostMapping("/insurance")
    public Map<String, Object> createInsurance(Insurance insurance, HttpSession session) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        User user = (User) session.getAttribute("user");
        insurance.setBaodanNo(java.util.UUID.randomUUID().toString());
        insurance.setInforceTime(df.format(new Date()));
        insurance.setClerkId(user.getId());
        insuranceService.save(insurance);
        Map<String, Object> map = new HashMap<>();
        map.put("success", "添加保单成功！");
        return map;
    }
}
