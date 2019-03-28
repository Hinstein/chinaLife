package chinalife.controller;

import chinalife.entity.Insurance;
import chinalife.service.InsuranceService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @BelongsProject: chinalife
 * @BelongsPackage: chinalife.controller
 * @Author: Hinstein
 * @CreateTime: 2019-03-18 08:47
 * @Description:
 */
@Controller
@RequestMapping("/retrieve")
public class RetrieveController {

    @Autowired
    InsuranceService insuranceService;

    @GetMapping("")
    public String retrieve(){
        return "/CRUD/retrieve";
    }

//    @ResponseBody
//    @GetMapping("/insurance/data")
//    public Map<String, Object> insuranceData(HttpServletRequest request) {
//        int pageSize = Integer.parseInt(request.getParameter("limit"));
//        int pageNumber = Integer.parseInt(request.getParameter("page"));
//        Page<Insurance> insurances = insuranceService.findAll(pageNumber, pageSize);
//        Map<String, Object> result = new HashMap<String, Object>();
//        result.put("code", 0);
//        result.put("msg", "");
//        result.put("count", insurances.getTotalElements());
//        JSONArray json = JSONArray.fromObject(insurances.getContent());
//        result.put("data", json);
//        return result;
//    }
}
