package chinalife.controller;

import chinalife.entity.User;
import chinalife.service.UserService;
import net.bytebuddy.implementation.bytecode.assign.TypeCasting;
import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        model.addAttribute("user", user);
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
            userService.changePassword(newPassword, user.getId());
        } else {
            map.put("error", "旧密码输入错误");
        }
        return map;
    }

    @GetMapping("/headPhoto")
    public String editorHeadPhoto() {
        return "/editor/headPhoto";
    }

    @ResponseBody
    @PostMapping("/addPhoto")
    public Map<String,Object> addPhoto(@RequestParam("file") MultipartFile file, HttpSession session,   HttpServletRequest request) {
//        Photo photo = new Photo();
        HashMap<String, Object> map = new HashMap<>();
        String str = "";
        String league_id="2";

        System.out.println("联赛id：" + league_id);

        String url = request.getRequestURI();
        System.out.println("URL:" + url);

        try {
            if (null != file) {
                //获得当前项目所在路径
                String pathRoot = request.getSession().getServletContext().getRealPath("");
                System.out.println("当前项目所在路径：" + pathRoot);

                //生成uuid作为文件名称
                String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                System.out.println("文件名称：" + uuid);

                //获得文件类型（判断如果不是图片文件类型，则禁止上传）
                String contentType = file.getContentType();
                System.out.println("文件类型：" + contentType);

                //获得文件后缀名称
                String imageName = contentType.substring(contentType.indexOf("/") + 1);
                System.out.println("文件后缀名称：" + imageName);
                String filePath = "D:/temp-rainy/";

                //根据日期来创建对应的文件夹
                String datePath = new SimpleDateFormat("yyyy/MM/dd/").format(new Date());
                System.out.println("日期：" + datePath);

                //根据id分类来创建对应的文件夹
                String leagueIdPath = league_id + "/";

                //日期
                /*String path=filePath+datePath;*/
                //联赛id
                String path = filePath + leagueIdPath;

                //如果不存在，则创建新文件夹
                File f = new File(path);
                if (!f.exists()) {
                    f.mkdirs();
                }

                //新生成的文件名称
                String fileName = uuid + "." + imageName;
                System.out.println("新生成的文件名称：" + fileName);
                session.setAttribute("fileName", fileName);

                //图片保存的完整路径
                String pathName = path + fileName;
                System.out.println("图片保存的完整路径"+pathName);

                //获取所属联赛ID
                int leagueID = Integer.parseInt(league_id);

                //图片的静态资源路径
                String staticPath = "/upload/images/" + leagueID + "/" + fileName;
                System.out.println("静态资源路径：" + staticPath);

                //复制操作
                //将图片从源位置复制到目标位置
                file.transferTo(new File(pathName));

                HashMap<String,String> srcmap =new HashMap<>();
                map.put("conde",0);
                map.put("msg","上传成功！");
                srcmap.put("src",pathName);
                map.put("data",srcmap);

//                photo.setStaticPath(staticPath);    //将图片保存的静态资源路径插入数据库
//                photo.setLeagueID(leagueID);        //将所属联赛ID插入数据库
//                photo.setUploadDate(datePath);      //将上传日期插入数据库
//                photo.setDestFile(pathName);        //将完整路径插入数据库
//                photo.setFileName(fileName);        //将最后保存的完整文件名插入数据库
//                photoRepository.save(photo);
            } else {
                System.out.println("文件为空");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

}
