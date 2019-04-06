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
 * @Description: 删除权限控制层
 */
@Controller
@RequestMapping("/delete")
public class DeleteController {

    @Autowired
    InsuranceService insuranceService;

    /**
     * 来到删除页面
     *
     * @return 删除页面
     */
    @GetMapping("")
    public String deleteIndex() {
        return "/CRUD/delete";
    }

    /**
     * 异步删除保单信息
     *
     * @param id
     * @return json数据
     */
    @ResponseBody
    @DeleteMapping("/insurance/{id}")
    public Map<String, Object> deleteInsurance(@PathVariable("id") int id) {
        //从数据库中删除该保单信息
        insuranceService.deleteById(id);
        //返回json数据
        HashMap<String, Object> map = new HashMap<>();
        map.put("success", "删除成功");
        return map;
    }

}
