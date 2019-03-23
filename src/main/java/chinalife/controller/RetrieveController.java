package chinalife.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @BelongsProject: chinalife
 * @BelongsPackage: chinalife.controller
 * @Author: Hinstein
 * @CreateTime: 2019-03-18 08:47
 * @Description:
 */
@Controller
@RequestMapping("/retrieve")
public class RetrieveController {
    @GetMapping("")
    public String retrieve(){
        return "/CRUD/retrieve";
    }
}
