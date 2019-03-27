package chinalife.controller;

import chinalife.entity.User;
import chinalife.service.UserService;
import net.bytebuddy.implementation.bytecode.assign.TypeCasting;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @CreateTime: 2019-03-26 08:44
 * @Description:
 */
@Controller
@RequestMapping(value = "/editor")
public class EditorController {

    @Autowired
    UserService userService;

    @GetMapping("/password")
    public String editorPassword() {
        return "/editor/password";
    }

    @GetMapping("/information")
    public String editorInformation(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user",user);
        return "/editor/information";
    }

    @ResponseBody
    @PutMapping("/changePassword")
    public Map<String, Object> changePassword(HttpServletRequest request, HttpSession session) {
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        Map<String, Object> map = new HashMap<>();
        User user = (User) session.getAttribute("user");
        if (user.getPassword().equals(oldPassword)) {
            userService.changePassword(newPassword,user.getId());
        } else {
            map.put("error", "旧密码输入错误");
        }
        return map;
    }
}
