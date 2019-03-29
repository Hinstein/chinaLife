package chinalife.controller;

import chinalife.repository.InsuranceRepository;
import chinalife.service.InsuranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
@RequestMapping("/delete")
public class DeleteController {

    @Autowired
    InsuranceService insuranceService;

    @GetMapping("")
    public String deleteIndex(){
        return "/CRUD/delete";
    }

    @ResponseBody
    @DeleteMapping("/insurance/{id}")
    public Map<String,Object> deleteInsurance(@PathVariable("id")int id){
        HashMap<String, Object> map = new HashMap<>();
        insuranceService.deleteById(id);
        map.put("success","删除成功");
        return map;
    }

}
