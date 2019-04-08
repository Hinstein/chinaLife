package chinalife.controller;

import chinalife.entity.Insurance;
import chinalife.entity.Recycle;
import chinalife.entity.User;
import chinalife.service.InsuranceService;
import chinalife.service.RecycleService;
import net.sf.json.JSONArray;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @BelongsProject: chinalife
 * @BelongsPackage: chinalife.controller
 * @Author: Hinstein
 * @CreateTime: 2019-04-07 12:58
 * @Description:
 */
@Controller
@RequestMapping("/recycle")
public class RecycleController {
    @Autowired
    RecycleService recycleService;

    @Autowired
    InsuranceService insuranceService;

    /**
     * 来到回收站主页
     *
     * @return
     */
    @GetMapping("")
    public String recycle() {
        return "/CRUD/recycle";
    }

    /**
     * 异步获取回收站信息
     *
     * @param request
     * @param session
     * @return
     */
    @ResponseBody
    @GetMapping("/insurance")
    public Map<String, Object> recycleData(HttpServletRequest request, HttpSession session) {

        //通过session查找当前登录的用户
        User user = (User) session.getAttribute("user");
        //前端传来每页显示的保单数
        int pageSize = Integer.parseInt(request.getParameter("limit"));
        //当前第几页
        int pageNumber = Integer.parseInt(request.getParameter("page"));
        Map<String, Object> result = new HashMap<String, Object>();
        Page<Recycle> recycles = recycleService.findAllByClerkId(user.getId(), pageNumber, pageSize);
        result.put("count", recycles.getTotalElements());
        JSONArray json = JSONArray.fromObject(recycles.getContent());
        result.put("data", json);
        //返回json数据
        result.put("code", 0);
        result.put("msg", "");
        return result;
    }

    /**
     * 恢复保单信息
     *
     * @param id
     * @return
     */
    @ResponseBody
    @PostMapping("/recover/{id}")
    public Map<String, Object> recoverInsurance(@PathVariable("id") int id) {
        Recycle recycle = recycleService.findById(id);
        Insurance insurance = new Insurance();
        //将recycleBin对象复制到insurance对象
        BeanUtils.copyProperties(recycle, insurance);
        //回收站存入该保单信息
        insuranceService.save(insurance);
        //从数据库中删除该保单信息
        recycleService.deleteById(id);
        //返回json数据
        HashMap<String, Object> map = new HashMap<>();
        map.put("success", "恢复成功");
        return map;
    }

    /**
     * 删除保单信息
     *
     * @param id
     * @return
     */
    @ResponseBody
    @DeleteMapping("/delete/{id}")
    public Map<String, Object> deleteRecycle(@PathVariable("id") int id) {
        recycleService.deleteById(id);
        //返回json数据
        HashMap<String, Object> map = new HashMap<>();
        map.put("success", "删除成功");
        return map;
    }

    /**
     * 查看保单信息
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/check/{id}")
    public String insuranceEditor(@PathVariable("id") int id, Model model) {
        //通过保单id查找保单
        Recycle recycle = recycleService.findById(id);
        //视图层显示
        model.addAttribute("recycle", recycle);
        return "/CRUD/check";
    }
}
