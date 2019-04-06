package chinalife.controller;

import chinalife.entity.Photo;
import chinalife.entity.User;
import chinalife.service.PhotoService;
import chinalife.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @BelongsProject: chinalife
 * @BelongsPackage: chinalife.controller
 * @Author: Hinstein
 * @CreateTime: 2019-03-26 08:44
 * @Description: 用户更改信息控制层
 */
@Controller
@RequestMapping(value = "/editor")
public class EditorController {

    @Autowired
    UserService userService;

    @Autowired
    PhotoService photoService;

    /**
     * 来到修改密码页面
     *
     * @return 密码修改页面
     */
    @GetMapping("/password")
    public String editorPassword() {
        return "/editor/password";
    }

    /**
     * 来到修改信息页面
     *
     * @param session
     * @param model
     * @return 信息修改页面
     */
    @GetMapping("/information")
    public String editorInformation(HttpSession session, Model model) {
        //通过session查看当前登录的用户信息
        User user = (User) session.getAttribute("user");
        //给前端添加用户信息
        model.addAttribute("user", user);
        return "/editor/information";
    }

    /**
     * 异步保存修改的密码
     *
     * @param request
     * @param session
     * @return
     */
    @ResponseBody
    @PutMapping("/changePassword")
    public Map<String, Object> changePassword(HttpServletRequest request, HttpSession session) {
        //从前端获取旧密码和新密码
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        Map<String, Object> map = new HashMap<>();
        //通过session查看当前登录的用户信息
        User user = (User) session.getAttribute("user");
        //如果用户密码与前端输入的旧密码相同
        if (user.getPassword().equals(oldPassword)) {
            //设置密码更新时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = df.format(new Date());
            //保存用户更改后的密码
            userService.changePassword(newPassword, user.getId(), time);
            //查找修改后的员工信息
            User user1 = userService.findById(user.getId());
            //重新在session中更新信息
            session.setAttribute("user", user1);
            //返回json数据
            map.put("success", "更换密码成功！");
        }
        //如果用户密码与前端输入的旧密码不相同
        else {
            //返回json数据
            map.put("error", "旧密码输入错误");
        }
        //返回json数据
        return map;
    }

    /**
     * 来到更新头像页面
     *
     * @param session
     * @param model
     * @return 头像页面
     */
    @GetMapping("/headPhoto")
    public String editorHeadPhoto(HttpSession session, Model model) {
        //通过session查看当前登录的用户信息
        User user = (User) session.getAttribute("user");
        //通过当前用户获取头像细腻
        Photo photo = photoService.findByUserId(user.getId());
        //如果用户设置过头像
        if (photo != null) {
            //返回该头像的url
            model.addAttribute("src", photo.getRelativePath());
        }
        //如果用户没有设置过头像
        else {
            //返回默认头像url
            model.addAttribute("src", "/images/head.png");
        }
        return "/editor/headPhoto";
    }

    /**
     * 异步保存头像
     *
     * @param file
     * @param session
     * @param request
     * @return json数据
     */
    @ResponseBody
    @PostMapping("/addPhoto")
    public Map<String, Object> addPhoto(@RequestParam("file") MultipartFile file, HttpSession session, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        //通过session查看当前登录的用户信息
        User user = (User) session.getAttribute("user");
        try {
            //如果文件不为空
            if (null != file) {
                //生成uuid作为文件名称
                String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                //获得文件类型（判断如果不是图片文件类型，则禁止上传）
                String contentType = file.getContentType();
                //获得文件后缀名称
                String imageName = contentType.substring(contentType.indexOf("/") + 1);
                //获取文件的项目路径
                String filePath = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "static/images/";
                //根据日期来创建对应的文件夹
                String datePath = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
                //根据id分类来创建对应的文件夹
                String leagueIdPath = user.getId() + "/";
                //userId
                String path = filePath + leagueIdPath;
                //如果不存在，则创建新文件夹
                File f = new File(path);
                if (!f.exists()) {
                    f.mkdirs();
                }
                //新生成的文件名称
                String fileName = uuid + "." + imageName;
                //图片保存的完整路径
                String pathName = path + fileName;
                //图片保存的相对路径
                String relativePath = "/images/" + leagueIdPath + fileName;
                //将图片从源位置复制到目标位置
                file.transferTo(new File(pathName));

                //设置photo实体类的数据
                Photo photo = new Photo();
                photo.setUserId(user.getId());
                photo.setDatePath(datePath);
                photo.setPathName(pathName);
                photo.setFileName(fileName);
                photo.setRelativePath(relativePath);

                //如果用户有头像
                if (photoService.findByUserId(user.getId()) != null) {
                    //更新头像路径
                    photoService.update(photo);
                }
                //如果用户没有头像
                else {
                    //新建路径
                    photoService.save(photo);
                }
                //返回json数据
                map.put("code", 0);
                map.put("msg", "上传成功！");
                map.put("relativePath", relativePath);
                map.put("data", pathName);
            } else {
                System.out.println("文件为空");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 异步修改个人信息
     *
     * @param request
     * @param session
     * @return json数据
     */
    @ResponseBody
    @PostMapping("/information/save")
    public Map<String, Object> information(HttpServletRequest request, HttpSession session) {
        //通过前端获取姓名
        String username = request.getParameter("username");
        //将前端获取的id转为int类型
        int id = Integer.valueOf(request.getParameter("id"));
        //数据库修改信息
        userService.changeInformation(username, id);
        //在数据库查找新的信息
        User user = userService.findById(id);
        //session中更新用户信息
        session.setAttribute("user", user);
        //返回json数据
        Map<String, Object> map = new HashMap<>();
        map.put("success", "修改成功！");
        return map;
    }
}
