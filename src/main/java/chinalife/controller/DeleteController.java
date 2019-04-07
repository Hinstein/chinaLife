package chinalife.controller;

import chinalife.entity.Insurance;
import chinalife.entity.Recycle;
import chinalife.entity.User;
import chinalife.repository.InsuranceRepository;
import chinalife.service.InsuranceService;
import chinalife.service.RecycleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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

    @Autowired
    RecycleService recycleService;

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
    public Map<String, Object> deleteInsurance(@PathVariable("id") int id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if(user.getId()!=10000000) {
            Insurance insurance = insuranceService.findById(id);
            Recycle recycle = new Recycle();
            //将insurance对象复制到recycleBin对象
            BeanUtils.copyProperties(insurance, recycle);
            //回收站存入该保单信息
            recycleService.save(recycle);
        }
        //从数据库中删除该保单信息
        insuranceService.deleteById(id);
        //返回json数据
        HashMap<String, Object> map = new HashMap<>();
        map.put("success", "删除成功");
        return map;
    }

}
