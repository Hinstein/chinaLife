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
 * @Description: 查看权限控制层
 */
@Controller
@RequestMapping("/retrieve")
public class RetrieveController {

    @Autowired
    InsuranceService insuranceService;

    /**
     * 来到查看页面
     *
     * @return 查看页面
     */
    @GetMapping("")
    public String retrieve() {
        return "/CRUD/retrieve";
    }

    /**
     * 异步获取保单信息
     *
     * @param request
     * @param session
     * @param content
     * @param t
     * @return json数据
     */
    @ResponseBody
    @GetMapping("/insurance/data")
    public Map<String, Object> insuranceData(HttpServletRequest request, HttpSession session, @RequestParam(value = "content", required = false) String content,
                                             @RequestParam(value = "fitter", required = false) String t) {
        //通过session查找当前登录的用户
        User user = (User) session.getAttribute("user");
        //前端传来每页显示的保单数
        int pageSize = Integer.parseInt(request.getParameter("limit"));
        //当前第几页
        int pageNumber = Integer.parseInt(request.getParameter("page"));
        Map<String, Object> result = new HashMap<String, Object>();
        //默认分类为全部保单
        int type = 4;
        //如果搜索不为空
        if (t != null) {
            //将前端传来的字符串转成int类型
            type = Integer.valueOf(t);
        }
        //如果搜索框或者分类框不为空
        if (content != null || type != 4) {
            //如果两个选项都不为空
            if ((content != null && type != 4)) {
                //通过分类和搜索框查找保单
                Page<Insurance> insurances = insuranceService.findByPolNameFitterAndContent(type, user.getId(), content, pageNumber, pageSize);
                result.put("count", insurances.getTotalElements());
                JSONArray json = JSONArray.fromObject(insurances.getContent());
                result.put("data", json);
            }
            //如果搜索框不为空
            else if (content != null) {
                //通过搜索框查找保单
                Page<Insurance> insurances = insuranceService.findByClerkIdAndContent(content, user.getId(), pageNumber, pageSize);
                result.put("count", insurances.getTotalElements());
                JSONArray json = JSONArray.fromObject(insurances.getContent());
                result.put("data", json);
            }
            //如果分类框不为空
            else {
                //通过分类框查找保单
                Page<Insurance> insurances = insuranceService.findByPolNameFitter(type, user.getId(), pageNumber, pageSize);
                result.put("count", insurances.getTotalElements());
                JSONArray json = JSONArray.fromObject(insurances.getContent());
                result.put("data", json);
            }
        }
        //如果两者都为空
        else {
            //查找所有表单信息
            Page<Insurance> insurances = insuranceService.findAllByClerkId(user.getId(), pageNumber, pageSize);
            result.put("count", insurances.getTotalElements());
            JSONArray json = JSONArray.fromObject(insurances.getContent());
            result.put("data", json);
        }
        //返回json数据
        result.put("code", 0);
        result.put("msg", "");
        return result;
    }
}
