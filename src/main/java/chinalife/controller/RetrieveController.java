package chinalife.controller;

import chinalife.entity.Insurance;
import chinalife.entity.User;
import chinalife.service.InsuranceService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    public String retrieve() {
        return "/CRUD/retrieve";
    }

    @ResponseBody
    @GetMapping("/insurance/data")
    public Map<String, Object> insuranceData(HttpServletRequest request, HttpSession session, @RequestParam(value = "content", required = false) String content,
                                             @RequestParam(value = "fitter", required = false) String t) {
        User user = (User) session.getAttribute("user");
        int pageSize = Integer.parseInt(request.getParameter("limit"));
        int pageNumber = Integer.parseInt(request.getParameter("page"));
        Map<String, Object> result = new HashMap<String, Object>();
        int type=4;
        if (t!=null){  type=Integer.valueOf(t);}
        if (content != null || type != 4) {
            if ((content != null && type != 4)) {
                Page<Insurance> insurances = insuranceService.findByPolNameFitterAndContent(type, user.getId(), content, pageNumber, pageSize);
                result.put("count", insurances.getTotalElements());
                JSONArray json = JSONArray.fromObject(insurances.getContent());
                result.put("data", json);
            } else if (content != null) {
                Page<Insurance> insurances = insuranceService.findByClerkIdAndContent(content, user.getId(), pageNumber, pageSize);
                result.put("count", insurances.getTotalElements());
                JSONArray json = JSONArray.fromObject(insurances.getContent());
                result.put("data", json);
            } else {
                Page<Insurance> insurances = insuranceService.findByPolNameFitter(type, user.getId(), pageNumber, pageSize);
                result.put("count", insurances.getTotalElements());
                JSONArray json = JSONArray.fromObject(insurances.getContent());
                result.put("data", json);
            }
        } else {
            Page<Insurance> insurances = insuranceService.findAllByClerkId(user.getId(), pageNumber, pageSize);
            result.put("count", insurances.getTotalElements());
            JSONArray json = JSONArray.fromObject(insurances.getContent());
            result.put("data", json);
        }
        result.put("code", 0);
        result.put("msg", "");
        return result;
    }
}
