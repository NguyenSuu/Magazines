package magazineandnews.controller.admin;


import magazineandnews.model.Category;
import magazineandnews.model.Posts;
import magazineandnews.service.CategoryService;
import magazineandnews.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/admin")
public class AdminTechnologyController extends AdminBaseController {
    private final String ASSIGN="Technology";
    @Autowired
    private PostsService postsService;
    @Autowired
    private CategoryService categoryService;
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);

        if (target.getClass() == Posts.class) {

            // Đăng ký để chuyển đổi giữa các đối tượng multipart thành byte[]
            dataBinder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
        }
    }

    private String getPrincipal() {
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }
    @RequestMapping(value = "/technology", method = RequestMethod.GET)
    public ModelAndView indexAdmin() {
        List<Posts> technology=postsService.getListTechnologyPage();
        ModelAndView modelAndView = new ModelAndView("admin/technology/list");
        modelAndView.addObject("user", getPrincipal());
        modelAndView.addObject("technology", technology);
        modelAndView.addObject("title",TITLE_ADD);
        modelAndView.addObject("assign",ASSIGN);
        return modelAndView;
    }
    @RequestMapping(value = "/technology/add",method = RequestMethod.GET)
    public ModelAndView addNew(){
        Category technologyCategory=categoryService.technologyCategory();
        ModelAndView modelAndView =new ModelAndView("admin/technology/add");
        modelAndView.addObject("technology", new Posts());
        modelAndView.addObject("technologyCategory",technologyCategory);
        modelAndView.addObject("title",TITLE_ADD);
        modelAndView.addObject("assign",ASSIGN);
        modelAndView.addObject("action",ACTION_ADD);
        return modelAndView;
    }
    @RequestMapping(value = "/technology/add", method = RequestMethod.POST)
    public ModelAndView saveAddForm(@ModelAttribute("technologyPage") Posts technologyPage, HttpSession session, HttpServletRequest request) {
        Category technologyCategory=categoryService.technologyCategory();
        String uploadRootPath = request.getServletContext().getRealPath("images");
        File uploadRootDir = new File(uploadRootPath);

        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }
        CommonsMultipartFile[] fileDatas = technologyPage.getFileImage();
        Map<File, String> uploadedFiles = new HashMap();
        for (CommonsMultipartFile fileData : fileDatas) {
            // Tên file gốc tại Client.
            String name = fileData.getOriginalFilename();
//            System.out.println("Client File Name = " + name);
            if (name != null && name.length() > 0) {
                try {
                    // Tạo file tại Server.
                    File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);
                    // Luồng ghi dữ liệu vào file trên Server.
                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                    stream.write(fileData.getBytes());
                    stream.close();
                    technologyPage.setImage(name);
                } catch (Exception e) {
                    System.out.println("Error Write file: " + name);
                }
            }
        }
        technologyPage.setTime(LocalDate.now());
        postsService.save(technologyPage);
        ModelAndView modelAndView = new ModelAndView("admin/technology/add");
        modelAndView.addObject("technology", new Posts());
        modelAndView.addObject("alert",ALERT_SUCCESS);
        modelAndView.addObject("title",TITLE_ADD);
        modelAndView.addObject("technologyCategory",technologyCategory);
        modelAndView.addObject("message", ACTION_ADD_SUCCESS);
        modelAndView.addObject("assign",ASSIGN);
        return modelAndView;
    }
    @RequestMapping(value = "/technology/edit/{id}",method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id")Long id){
        Posts posts=postsService.findById(id);
        Category technologyCategory=categoryService.technologyCategory();
        ModelAndView modelAndView =new ModelAndView("admin/technology/add");
        modelAndView.addObject("technologyCategory",technologyCategory);
        modelAndView.addObject("technology", posts);
        modelAndView.addObject("title",TITLE_EDIT);
        modelAndView.addObject("assign",ASSIGN);
        modelAndView.addObject("action",ACTION_EDIT);
        return modelAndView;
    }
    @RequestMapping(value = "/technology/edit",method = RequestMethod.POST)
    public ModelAndView saveEditForm(HttpServletRequest request, @ModelAttribute("technology") Posts technology){
        //
        String uploadRootPath = request.getServletContext().getRealPath("images");
        File uploadRootDir = new File(uploadRootPath);
        //
        // Tạo thư mục gốc upload nếu nó không tồn tại.
        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }
        CommonsMultipartFile[] fileDatas = technology.getFileImage();
        //
        if (fileDatas!=null){
            Map<File, String> uploadedFiles = new HashMap();
            for (CommonsMultipartFile fileData : fileDatas) {

                // Tên file gốc tại Client.
                String name = fileData.getOriginalFilename();
                System.out.println("Client File Name = " + name);

                if (name != null && name.length() > 0) {
                    try {
                        // Tạo file tại Server.
                        File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);

                        // Luồng ghi dữ liệu vào file trên Server.
                        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                        stream.write(fileData.getBytes());
                        stream.close();
                        //
                        technology.setImage(name);
                        System.out.println("Write file: " + serverFile);
                    } catch (Exception e) {
                        System.out.println("Error Write file: " + name);
                    }
                }
            }
        }
        //
        Category technologyCategory=categoryService.technologyCategory();
        technology.setTime(LocalDate.now());
        technology.setCategory(technologyCategory);
        postsService.save(technology);
        //

        ModelAndView modelAndView = new ModelAndView("admin/technology/add");
        modelAndView.addObject("technology",technology);
        modelAndView.addObject("technologyCategory",technologyCategory);
        modelAndView.addObject("action",ACTION_EDIT);
        modelAndView.addObject("assign",ASSIGN);
        modelAndView.addObject("title",TITLE_EDIT);
        modelAndView.addObject("alert",ALERT_SUCCESS);
        modelAndView.addObject("message", ACTION_EDIT_SUCCESS);
        //
        return  modelAndView;
    }
    @RequestMapping(value = "/technology/delete/{id}",method = RequestMethod.GET)
    public ModelAndView deleteForm(@PathVariable("id")Long id){
        Posts technology=postsService.findById(id);
        if(technology!=null){
            ModelAndView modelAndView =new ModelAndView("admin/technology/delete");
            modelAndView.addObject("technology",technology);
            modelAndView.addObject("action",ACTION_DELETE);
            modelAndView.addObject("assign",ASSIGN);
            modelAndView.addObject("title",TITLE_DELETE);
            return modelAndView;
        }else {
            ModelAndView modelAndView=new ModelAndView("/error.404");
            return modelAndView;
        }
    }
    @RequestMapping(value = "/technology/delete",method = RequestMethod.POST)
    public String delete(@ModelAttribute("technology") Posts technology){
        postsService.remove(technology.getId());
        return "redirect:/admin/technology";
    }
}