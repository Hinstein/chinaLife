package chinalife.controller;


import chinalife.entity.User;
import chinalife.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
 * @Description:
 */

@Controller
public class MainController {

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @GetMapping("/exit")
    public String exit() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/login";
    }

    @ResponseBody
    @PostMapping("/login/post")
    public Map<String, Object> loginPost(User user, HttpServletRequest request, HttpSession session) {
        String rightCode = (String) request.getSession().getAttribute("rightCode");
        String tryCode = request.getParameter("tryCode");
        Map<String, Object> map = new HashMap<>(10);

        //1.获取subject
        Subject subject = SecurityUtils.getSubject();

        //2.封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(user.getId().toString(), user.getPassword());

        //3.判断验证码是否正确
        if (tryCode.equals(rightCode)) {
            //4.执行登录方法
            try {
                subject.login(token);
                User user1 =userService.findById(user.getId());
                session.setAttribute("user",user1);
                map.put("success", "登录成功");
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

    @GetMapping("/noAuth")
    public String unAuth() {
        return "/noAuth";
    }

    @GetMapping("/index")
    public String index() {
        return "/index";
    }
}
