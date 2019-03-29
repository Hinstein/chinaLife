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
 * @Description:
 */
@Controller
@RequestMapping("/update")
public class UpdateController {

    @Autowired
    InsuranceService insuranceService;

    @GetMapping("")
    public String update(){
        return "/CRUD/update";
    }

    @GetMapping("/insurance/{id}")
    public String insuranceEditor(@PathVariable("id")int id,Model model){
        Insurance insurance = insuranceService.findById(id);
        model.addAttribute("insurance",insurance);
        return "/CRUD/editor";
    }

    @ResponseBody
    @PostMapping("/insurance/save")
    public Map<String,Object> insuranceSave(Insurance insurance){
        insuranceService.update(insurance);
        HashMap<String, Object> map = new HashMap<>();
        map.put("success","保存成功！");
        return map;
    }
}
