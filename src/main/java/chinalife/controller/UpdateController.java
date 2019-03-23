package chinalife.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @BelongsProject: chinalife
 * @BelongsPackage: chinalife.controller
 * @Author: Hinstein
 * @CreateTime: 2019-03-18 08:46
 * @Description:
 */
@Controller
@RequestMapping("/update")
public class UpdateController {

    @GetMapping("")
    public String update(){
        return "/CRUD/update";
    }
}
