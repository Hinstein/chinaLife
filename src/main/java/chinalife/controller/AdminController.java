package chinalife.controller;

import chinalife.entity.Insurance;
import chinalife.entity.Permission;
import chinalife.entity.User;
import chinalife.service.InsuranceService;
import chinalife.service.PermissionService;
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
 * @Description:
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

    @GetMapping("/allUser")
    public String allUser() {
        return "/admin/allUser";
    }

    @GetMapping("/createUser")
    public String createUser() {
        return "/admin/createUser";
    }

    @ResponseBody
    @PostMapping("/createUser/save")
    public Map<String, String> createUser(User user, HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        user.setAddTime(df.format(new Date()));
        user.setPasswordTime(df.format(new Date()));
        userService.save(user);
        User newUser = userService.findById(user.getId());
        String create = request.getParameter("create");
        String delete = request.getParameter("delete");
        String retrieve = request.getParameter("retrieve");
        String update = request.getParameter("update");
        if (create != null) {
            Permission permission = new Permission();
            permission.setPerms("create");
            permission.setUserId(newUser.getId());
            permsService.save(permission);
        }
        if (delete != null) {
            Permission permission = new Permission();
            permission.setPerms("delete");
            permission.setUserId(newUser.getId());
            permsService.save(permission);
        }
        if (retrieve != null) {
            Permission permission = new Permission();
            permission.setPerms("retrieve");
            permission.setUserId(newUser.getId());
            permsService.save(permission);
        }
        if (update != null) {
            Permission permission = new Permission();
            permission.setPerms("update");
            permission.setUserId(newUser.getId());
            permsService.save(permission);
        }
        map.put("success", "成功添加员工，工号为" + newUser.getId());
        return map;
    }

    @ResponseBody
    @GetMapping("/user/data")
    public Map<String, Object> userData(HttpServletRequest request, @RequestParam(value = "content", required = false) String content) {
        int pageSize = Integer.parseInt(request.getParameter("limit"));
        int pageNumber = Integer.parseInt(request.getParameter("page"));
        Map<String, Object> result = new HashMap<String, Object>();
        if (content != null) {
            Page<User> users = userService.findByContent(content, pageNumber, pageSize);
            result.put("count", users.getTotalElements());
            JSONArray json = JSONArray.fromObject(users.getContent());
            result.put("data", json);
        } else {
            Page<User> users = userService.findAll(pageNumber, pageSize);
            result.put("count", users.getTotalElements());
            JSONArray json = JSONArray.fromObject(users.getContent());
            result.put("data", json);
        }
        result.put("code", 0);
        result.put("msg", "");

        return result;
    }

    @GetMapping("/user/editor/{id}")
    public String userEditor(@PathVariable("id") int id, Model model) {
        User user = userService.findById(id);
        List<Permission> perms = permsService.findByUserId(id);
        for (Permission p : perms) {
            if (p.getPerms().equals("create")) {
                model.addAttribute("create", 1);
            } else if (p.getPerms().equals("delete")) {
                model.addAttribute("delete", 1);
            } else if (p.getPerms().equals("retrieve")) {
                model.addAttribute("retrieve", 1);
            } else if (p.getPerms().equals("update")) {
                model.addAttribute("update", 1);
            }
        }
        model.addAttribute("user", user);
        return "/admin/userEditor";
    }

    @ResponseBody
    @PostMapping("/userEditor/save")
    public Map<String, Object> loginPost(User user, HttpServletRequest request) {
        String create = request.getParameter("create");
        String delete = request.getParameter("delete");
        String retrieve = request.getParameter("retrieve");
        String update = request.getParameter("update");
        if (create != null) {
            if (permsService.findByUserIdAndPerms(user.getId(), "create") == null) {
                Permission permission = new Permission();
                permission.setPerms("create");
                permission.setUserId(user.getId());
                permsService.save(permission);
            }
        } else {
            if (permsService.findByUserIdAndPerms(user.getId(), "create") != null) {
                permsService.deleteByUserIdAndPerms(user.getId(), "create");
            }
        }
        if (delete != null) {
            if (permsService.findByUserIdAndPerms(user.getId(), "delete") == null) {
                Permission permission = new Permission();
                permission.setPerms("delete");
                permission.setUserId(user.getId());
                permsService.save(permission);
            }
        } else {
            if (permsService.findByUserIdAndPerms(user.getId(), "delete") != null) {
                permsService.deleteByUserIdAndPerms(user.getId(), "delete");
            }
        }
        if (retrieve != null) {
            if (permsService.findByUserIdAndPerms(user.getId(), "retrieve") == null) {
                Permission permission = new Permission();
                permission.setPerms("retrieve");
                permission.setUserId(user.getId());
                permsService.save(permission);
            }
        } else {
            if (permsService.findByUserIdAndPerms(user.getId(), "retrieve") != null) {
                permsService.deleteByUserIdAndPerms(user.getId(), "retrieve");
            }
        }
        if (update != null) {
            if (permsService.findByUserIdAndPerms(user.getId(), "update") == null) {
                Permission permission = new Permission();
                permission.setPerms("update");
                permission.setUserId(user.getId());
                permsService.save(permission);
            }
        } else {
            if (permsService.findByUserIdAndPerms(user.getId(), "update") != null) {
                permsService.deleteByUserIdAndPerms(user.getId(), "update");
            }
        }
        Map<String, Object> map = new HashMap<>(10);
        map.put("success", "保存成功！");
        return map;
    }

    @ResponseBody
    @PostMapping("/user/delete/{id}")
    public Map<String, String> userDelete(@PathVariable("id") int id) {
        userService.deleteById(id);
        permsService.deleteByUserId(id);
        Map<String, String> map = new HashMap<>();
        map.put("success", "删除成功！");
        return map;
    }

    @GetMapping("/insurance")
    public String insurance() {
        return "/admin/insurance/allInsurance";
    }

    @ResponseBody
    @GetMapping("/insurance/data")
    public Map<String, Object> insuranceData(HttpServletRequest request, @RequestParam(value = "content", required = false) String content,
                                             @RequestParam(value = "fitter", required = false) String t) {
        int pageSize = Integer.parseInt(request.getParameter("limit"));
        int pageNumber = Integer.parseInt(request.getParameter("page"));
        Map<String, Object> result = new HashMap<String, Object>();
        int type=4;
        if (t!=null){  type=Integer.valueOf(t);}
        if (content != null || type != 4) {
            if ((content != null && type != 4)) {
                Page<Insurance> insurances = insuranceService.findPolNameFitterAndContentByAdmin(type, content, pageNumber, pageSize);
                result.put("count", insurances.getTotalElements());
                JSONArray json = JSONArray.fromObject(insurances.getContent());
                result.put("data", json);
            } else if (content != null) {
                Page<Insurance> insurances = insuranceService.findByContent(content, pageNumber, pageSize);
                result.put("count", insurances.getTotalElements());
                JSONArray json = JSONArray.fromObject(insurances.getContent());
                result.put("data", json);
            } else {
                Page<Insurance> insurances = insuranceService.findPolNameFitterByAdmin(type, pageNumber, pageSize);
                result.put("count", insurances.getTotalElements());
                JSONArray json = JSONArray.fromObject(insurances.getContent());
                result.put("data", json);
            }
        } else {
            Page<Insurance> insurances = insuranceService.findAll(pageNumber, pageSize);
            result.put("count", insurances.getTotalElements());
            JSONArray json = JSONArray.fromObject(insurances.getContent());
            result.put("data", json);
        }
        result.put("code", 0);
        result.put("msg", "");
        return result;
    }
}
