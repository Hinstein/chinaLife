package chinalife.controller;

import chinalife.entity.Insurance;
import chinalife.entity.Permission;
import chinalife.entity.User;
import chinalife.service.InsuranceService;
import chinalife.service.PermissionService;
import chinalife.service.PhotoService;
import chinalife.service.UserService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @BelongsProject: chinalife
 * @BelongsPackage: chinalife.controller
 * @Author: Hinstein
 * @CreateTime: 2019-03-18 08:53
 * @Description: 管理员权限控制层
 */

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    PermissionService permsService;

    @Autowired
    InsuranceService insuranceService;

    @Autowired
    PhotoService photoService;

    /**
     * 查看所有用户
     *
     * @return
     */
    @GetMapping("/allUser")
    public String allUser() {
        return "/admin/allUser";
    }

    /**
     * 创建用户
     *
     * @return
     */
    @GetMapping("/createUser")
    public String createUser() {
        return "/admin/createUser";
    }

    /**
     * 异步保存创建的用户
     *
     * @param user
     * @param request
     * @return jason数据
     */
    @ResponseBody
    @PostMapping("/createUser/save")
    public Map<String, String> createUser(User user, HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        //设置用户创建时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //为用户设置创建时间与密码修改时间
        user.setAddTime(df.format(new Date()));
        user.setPasswordTime(df.format(new Date()));
        //保存用户
        userService.save(user);
        //通过id查找到用户
        User newUser = userService.findById(user.getId());
        //从视图层获取权限信息
        String create = request.getParameter("create");
        String delete = request.getParameter("delete");
        String retrieve = request.getParameter("retrieve");
        String update = request.getParameter("update");
        //如果用户有创建权限
        if (create != null) {
            //新增创建权限
            Permission permission = new Permission();
            permission.setPerms("create");
            permission.setUserId(newUser.getId());
            permsService.save(permission);
        }
        //如果用户有删除权限
        if (delete != null) {
            //新增删除权限
            Permission permission = new Permission();
            permission.setPerms("delete");
            permission.setUserId(newUser.getId());
            permsService.save(permission);
        }
        //如果用户有删除权限
        if (retrieve != null) {
            //新增删除权限
            Permission permission = new Permission();
            permission.setPerms("retrieve");
            permission.setUserId(newUser.getId());
            permsService.save(permission);
        }
        //如果用户有删除权限
        if (update != null) {
            //新增删除权限
            Permission permission = new Permission();
            permission.setPerms("update");
            permission.setUserId(newUser.getId());
            permsService.save(permission);
        }
        //若创建成功返回json数据
        map.put("success", "成功添加员工，工号为" + newUser.getId());
        return map;
    }

    /**
     * layui数据表格异步获取用户信息
     *
     * @param request
     * @param content
     * @return json数据
     */
    @ResponseBody
    @GetMapping("/user/data")
    public Map<String, Object> userData(HttpServletRequest request, @RequestParam(value = "content", required = false) String content) {
        //从视图层获取每一页显示的用户数量
        int pageSize = Integer.parseInt(request.getParameter("limit"));
        //从视图层获取当前第几页
        int pageNumber = Integer.parseInt(request.getParameter("page"));
        Map<String, Object> result = new HashMap<String, Object>();
        //如果没有搜索栏没有内容
        if (content == "" || content == null) {
            //查找所有用户
            Page<User> users = userService.findAll(pageNumber, pageSize);
            result.put("count", users.getTotalElements());
            JSONArray json = JSONArray.fromObject(users.getContent());
            result.put("data", json);
        }
        //如果搜索栏有内容
        else {
            //查找相关用户
            Page<User> users = userService.findByContent(content, pageNumber, pageSize);
            result.put("count", users.getTotalElements());
            JSONArray json = JSONArray.fromObject(users.getContent());
            result.put("data", json);
        }
        //返回layui数据表格需要的json数据
        result.put("code", 0);
        result.put("msg", "");
        return result;
    }

    /**
     * 管理员获取用户信息
     *
     * @param id
     * @param model
     * @return 用户信息
     */
    @GetMapping("/user/editor/{id}")
    public String userEditor(@PathVariable("id") int id, Model model) {
        //通过url中的id查找用户信息
        User user = userService.findById(id);
        //通过用户id查找他拥有的权限
        List<Permission> perms = permsService.findByUserId(id);
        //循环获得当前用户所有的权限
        for (Permission p : perms) {
            //如果权限中存在添加权限
            if (p.getPerms().equals("create")) {
                //前端显示拥有添加权限
                model.addAttribute("create", 1);
            }
            //如果权限中有删除权限
            else if (p.getPerms().equals("delete")) {
                //前端显示删除添加权限
                model.addAttribute("delete", 1);
            }
            //如果权限中存在查看权限
            else if (p.getPerms().equals("retrieve")) {
                //前端显示拥有查看权限
                model.addAttribute("retrieve", 1);
            }
            //如果权限中存在更新权限
            else if (p.getPerms().equals("update")) {
                //前端显示拥有更新权限
                model.addAttribute("update", 1);
            }
        }
        //返回数据到前端页面
        model.addAttribute("user", user);
        return "/admin/userEditor";
    }

    /**
     * 保存用户修改信息
     *
     * @param user
     * @param request
     * @return json数据
     */
    @ResponseBody
    @PostMapping("/userEditor/save")
    public Map<String, Object> loginPost(User user, HttpServletRequest request) {
        //从前端获取权限
        String create = request.getParameter("create");
        String delete = request.getParameter("delete");
        String retrieve = request.getParameter("retrieve");
        String update = request.getParameter("update");
        //如果拥有添加权限
        if (create != null) {
            //如果数据库没有存在该权限
            if (permsService.findByUserIdAndPerms(user.getId(), "create") == null) {
                //权限添加进入数据库
                Permission permission = new Permission();
                permission.setPerms("create");
                permission.setUserId(user.getId());
                permsService.save(permission);
            }
        }
        //如果没有添加权限
        else {
            //如果数据库存在该权限
            if (permsService.findByUserIdAndPerms(user.getId(), "create") != null) {
                //数据库删除该权限
                permsService.deleteByUserIdAndPerms(user.getId(), "create");
            }
        }
        //如果拥有删除权限
        if (delete != null) {
            //如果数据库没有存在该权限
            if (permsService.findByUserIdAndPerms(user.getId(), "delete") == null) {
                //权限添加进入数据库
                Permission permission = new Permission();
                permission.setPerms("delete");
                permission.setUserId(user.getId());
                permsService.save(permission);
            }
        }
        //如果没有删除权限
        else {
            //如果数据库存在该权限
            if (permsService.findByUserIdAndPerms(user.getId(), "delete") != null) {
                //数据库删除该权限
                permsService.deleteByUserIdAndPerms(user.getId(), "delete");
            }
        }
        //如果拥有查看权限
        if (retrieve != null) {
            //如果数据库没有存在该权限
            if (permsService.findByUserIdAndPerms(user.getId(), "retrieve") == null) {
                //权限添加进入数据库
                Permission permission = new Permission();
                permission.setPerms("retrieve");
                permission.setUserId(user.getId());
                permsService.save(permission);
            }
        }
        //如果没有查看权限
        else {
            //如果数据库存在该权限
            if (permsService.findByUserIdAndPerms(user.getId(), "retrieve") != null) {
                //数据库删除该权限
                permsService.deleteByUserIdAndPerms(user.getId(), "retrieve");
            }
        }
        //如果有更新权限
        if (update != null) {
            //如果数据库没有存在该权限
            if (permsService.findByUserIdAndPerms(user.getId(), "update") == null) {
                //权限添加进入数据库
                Permission permission = new Permission();
                permission.setPerms("update");
                permission.setUserId(user.getId());
                permsService.save(permission);
            }
        }
        //如果有更新权限
        else {
            //如果数据库存在该权限
            if (permsService.findByUserIdAndPerms(user.getId(), "update") != null) {
                //数据库删除该权限
                permsService.deleteByUserIdAndPerms(user.getId(), "update");
            }
        }
        //返回json数据
        Map<String, Object> map = new HashMap<>(10);
        userService.save(user);
        map.put("success", "保存成功！");
        return map;
    }

    /**
     * 异步删除用户
     *
     * @param id
     * @return json数据
     */
    @ResponseBody
    @PostMapping("/user/delete/{id}")
    public Map<String, String> userDelete(@PathVariable("id") int id) {
        //用户表中删除
        userService.deleteById(id);
        //权限表中删除
        permsService.deleteByUserId(id);
        //头像表中删除
        photoService.deleteByUserId(id);
        //返回json数据
        Map<String, String> map = new HashMap<>();
        map.put("success", "删除成功！");
        return map;
    }

    /**
     * 管理员查看保单
     *
     * @return
     */
    @GetMapping("/insurance")
    public String insurance() {
        return "/admin/insurance/allInsurance";
    }

    /**
     * 查看所有保单信息
     *
     * @param request
     * @param content
     * @param t
     * @return json数据
     */
    @ResponseBody
    @GetMapping("/insurance/data")
    public Map<String, Object> insuranceData(HttpServletRequest request, @RequestParam(value = "content", required = false) String content,
                                             @RequestParam(value = "fitter", required = false) String t) {
        //从前端获取每个页面显示的保单数
        int pageSize = Integer.parseInt(request.getParameter("limit"));
        //从前端获取当前第几页
        int pageNumber = Integer.parseInt(request.getParameter("page"));
        Map<String, Object> result = new HashMap<String, Object>();
        //如果查看的是全部种类的保单信息
        int type = 4;
        //如果分类查看
        if (t != null) {
            //将前端传来的字符串转成int类型
            type = Integer.valueOf(t);
        }
        //如果搜索框或者分类框不为空
        if (content != null || type != 4) {
            //如果两个选项都不为空
            if ((content != null && type != 4)) {
                //通过分类和搜索框查找保单
                Page<Insurance> insurances = insuranceService.findPolNameFitterAndContentByAdmin(type, content, pageNumber, pageSize);
                result.put("count", insurances.getTotalElements());
                JSONArray json = JSONArray.fromObject(insurances.getContent());
                result.put("data", json);
            }
            //如果搜索框不为空
            else if (content != null) {
                //通过搜索框查找保单
                Page<Insurance> insurances = insuranceService.findByContent(content, pageNumber, pageSize);
                result.put("count", insurances.getTotalElements());
                JSONArray json = JSONArray.fromObject(insurances.getContent());
                result.put("data", json);
            }
            //如果分类框不为空
            else {
                //通过分类框查找保单
                Page<Insurance> insurances = insuranceService.findPolNameFitterByAdmin(type, pageNumber, pageSize);
                result.put("count", insurances.getTotalElements());
                JSONArray json = JSONArray.fromObject(insurances.getContent());
                result.put("data", json);
            }
        }
        //如果两者都为空
        else {
            //查找所有表单信息
            Page<Insurance> insurances = insuranceService.findAll(pageNumber, pageSize);
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
