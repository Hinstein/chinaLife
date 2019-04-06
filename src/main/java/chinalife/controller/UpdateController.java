package chinalife.controller;

import chinalife.entity.Insurance;
import chinalife.service.InsuranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @BelongsProject: chinalife
 * @BelongsPackage: chinalife.controller
 * @Author: Hinstein
 * @CreateTime: 2019-03-18 08:46
 * @Description: 更新权限控制层
 */
@Controller
@RequestMapping("/update")
public class UpdateController {

    @Autowired
    InsuranceService insuranceService;

    /**
     * 来到更新保单页面
     *
     * @return 更新保单页面
     */
    @GetMapping("")
    public String update() {
        return "/CRUD/update";
    }

    /**
     * 获取保单信息
     *
     * @param id
     * @param model
     * @return 保单信息
     */
    @GetMapping("/insurance/{id}")
    public String insuranceEditor(@PathVariable("id") int id, Model model) {
        //通过保单id查找保单
        Insurance insurance = insuranceService.findById(id);
        //视图层显示
        model.addAttribute("insurance", insurance);
        return "/CRUD/editor";
    }

    /**
     * 异步保存保单信息
     *
     * @param insurance
     * @return json数据
     */
    @ResponseBody
    @PostMapping("/insurance/save")
    public Map<String, Object> insuranceSave(Insurance insurance) {
        //更新保单信息
        insuranceService.update(insurance);
        //返回json格式
        HashMap<String, Object> map = new HashMap<>();
        map.put("success", "保存成功！");
        return map;
    }
}
