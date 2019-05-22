package chinalife.controller;


import chinalife.entity.User;
import chinalife.service.InsuranceService;
import chinalife.service.PermissionService;
import chinalife.service.UserService;
import org.apache.lucene.index.DocIDMerger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @BelongsProject: chinalife
 * @BelongsPackage: chinalife.controller
 * @Author: Hinstein
 * @CreateTime: 2019-03-12 19:00
 * @Description: 登录和主页的控制层
 */

@Controller
public class MainController {

    @Autowired
    UserService userService;

    @Autowired
    InsuranceService insuranceService;

    @Autowired
    PermissionService permissionService;

    /**
     * 来到登录页面
     *
     * @return 登录页面
     */
    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    /**
     * 退出页面
     *
     * @return 退出页面
     */
    @GetMapping("/exit")
    public String exit(HttpSession session) {
        User user = (User) session.getAttribute("user");
        Subject subject = SecurityUtils.getSubject();
        if (subject.isPermitted("admin")) {
        } else {
            int baodanNumber = Integer.parseInt(session.getAttribute("baodanNumbers").toString());
            userService.changeBaodanNumber(baodanNumber, user.getId());
        }
        subject.logout();
        return "redirect:/login";
    }

    /**
     * 异步登录
     *
     * @param user
     * @param request
     * @param session
     * @return json数据
     */
    @ResponseBody
    @PostMapping("/login/post")
    public Map<String, Object> loginPost(User user, HttpServletRequest request, HttpSession session) {
        //从session中获取kaptcha生成在session中的正确验证码
        String rightCode = (String) request.getSession().getAttribute("rightCode");
        //从前端页面获取用户输入的验证码
        String tryCode = request.getParameter("tryCode");
        Map<String, Object> map = new HashMap<>(10);

        //1.判断验证码是否正确
        if (tryCode.equals(rightCode)) {

            //2.获取subject
            Subject subject = SecurityUtils.getSubject();

            //3.封装用户数据
            UsernamePasswordToken token = new UsernamePasswordToken(user.getId().toString(), user.getPassword());

            //4.执行登录方法
            try {
                subject.login(token);
                User user1 = userService.findById(user.getId());
                session.setAttribute("user", user1);
                map.put("success", "登录成功");
                //判断是否是管理员登录
                if (subject.isPermitted("admin")) {
                    session.setAttribute("baodan0", insuranceService.baodan0());
                    session.setAttribute("baodan1", insuranceService.baodan1());
                    session.setAttribute("baodan2", insuranceService.baodan2());
                    session.setAttribute("baodan3", insuranceService.baodan3());
                    session.setAttribute("baodanNumbers", insuranceService.numbers());
                    session.setAttribute("createCounts", permissionService.createCounts());
                    session.setAttribute("updateCounts", permissionService.updateCounts());
                    session.setAttribute("deleteCounts", permissionService.deleteCounts());
                    session.setAttribute("retrieveCounts", permissionService.retrieveCounts());
                    session.setAttribute("userCounts", userService.numbers());
                } else {
                    userService.active(user1.getId());
                }
                //登录成功
            } catch (UnknownAccountException e) {
                map.put("error", "不存在此用户");
            } catch (IncorrectCredentialsException e) {
                map.put("error", "密码错误");
            }
        } else {
            map.put("error", "验证码错误");
        }
        return map;
    }

    /**
     * 来到未授权页面
     *
     * @return 未授权页面
     */
    @GetMapping("/noAuth")
    public String unAuth() {
        return "/noAuth";
    }

    /**
     * 来到主页
     *
     * @param session
     * @param model
     * @return 主页
     */
    @GetMapping("/index")
    public String index(HttpSession session, Model model) {
        //通过session查看当前登录的用户信息
        User user1 = (User) session.getAttribute("user");
        Subject subject = SecurityUtils.getSubject();
        session.setAttribute("user", userService.findById(user1.getId()));
        //如果是总管理员
        if (subject.isPermitted("admin")) {
            //获取用户活跃数和用户业绩排名
            Page<User> activeRank = userService.activeRank();
            Page<User> baodanRank = userService.baodanRank();
            model.addAttribute("activeRank", activeRank);
            model.addAttribute("baodanRank", baodanRank);

        }
        //如果用户不是总管理员
        else {
            //向session中添加保单的数量信息
            //意外险数量
            session.setAttribute("baodan0", insuranceService.baodan0(user1.getId()));
            //健康险数量
            session.setAttribute("baodan1", insuranceService.baodan1(user1.getId()));
            //补充医疗险数量
            session.setAttribute("baodan2", insuranceService.baodan2(user1.getId()));
            //分红险数量
            session.setAttribute("baodan3", insuranceService.baodan3(user1.getId()));
            //保单总量
            session.setAttribute("baodanNumbers", insuranceService.numbers(user1.getId()));
        }
        return "/index";
    }
}
