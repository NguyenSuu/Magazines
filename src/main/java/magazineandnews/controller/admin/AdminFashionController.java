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
public class AdminFashionController extends AdminBaseController {
    private final String ASSIGN = "Fashion";
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

    @RequestMapping(value = "/fashion", method = RequestMethod.GET)
    public ModelAndView indexAdmin() {
        List<Posts> fashion = postsService.getListFashionPage();
        ModelAndView modelAndView = new ModelAndView("admin/fashion/list");
        modelAndView.addObject("user", getPrincipal());
        modelAndView.addObject("fashion", fashion);
        modelAndView.addObject("title", TITLE_ADD);
        modelAndView.addObject("assign", ASSIGN);
        return modelAndView;
    }

    @RequestMapping(value = "/fashion/add", method = RequestMethod.GET)
    public ModelAndView addNew() {
        Category fashionCategory = categoryService.fashionCategory();
        ModelAndView modelAndView = new ModelAndView("admin/fashion/add");
        modelAndView.addObject("fashion", new Posts());
        modelAndView.addObject("fashionCategory", fashionCategory);
        modelAndView.addObject("title", TITLE_ADD);
        modelAndView.addObject("assign", ASSIGN);
        modelAndView.addObject("action", ACTION_ADD);
        return modelAndView;
    }

    @RequestMapping(value = "/fashion/add", method = RequestMethod.POST)
    public ModelAndView saveAddForm(@ModelAttribute("fashion") Posts fashion, HttpSession session, HttpServletRequest request) {
        Category fashionCategory = categoryService.fashionCategory();
        String uploadRootPath = request.getServletContext().getRealPath("images");
        File uploadRootDir = new File(uploadRootPath);

        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }
        CommonsMultipartFile[] fileDatas = fashion.getFileImage();
        Map<File, String> uploadedFiles = new HashMap();
        for (CommonsMultipartFile fileData : fileDatas) {
            String name = fileData.getOriginalFilename();
            if (name != null && name.length() > 0) {
                try {
                    File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);
                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                    stream.write(fileData.getBytes());
                    stream.close();
                    fashion.setImage(name);
                } catch (Exception e) {
                    System.out.println("Error Write file: " + name);
                }
            }
        }

        postsService.save(fashion);
        ModelAndView modelAndView = new ModelAndView("admin/fashion/add");
        modelAndView.addObject("fashion", new Posts());
        modelAndView.addObject("alert", ALERT_SUCCESS);
        modelAndView.addObject("fashionCategory", fashionCategory);
        modelAndView.addObject("title", TITLE_ADD);
        modelAndView.addObject("message", ACTION_ADD_SUCCESS);
        modelAndView.addObject("assign", ASSIGN);
        return modelAndView;
    }

    @RequestMapping(value = "/fashion/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id) {
        Posts posts = postsService.findById(id);
        Category fashionCategory = categoryService.fashionCategory();
        ModelAndView modelAndView = new ModelAndView("admin/fashion/add");
        modelAndView.addObject("fashionCategory", fashionCategory);
        modelAndView.addObject("fashion", posts);
        modelAndView.addObject("title", TITLE_EDIT);
        modelAndView.addObject("assign", ASSIGN);
        modelAndView.addObject("action", ACTION_EDIT);
        return modelAndView;
    }

    @RequestMapping(value = "/fashion/edit", method = RequestMethod.POST)
    public ModelAndView saveEditForm(HttpServletRequest request, @ModelAttribute("fashion") Posts fashion) {
        //
        String uploadRootPath = request.getServletContext().getRealPath("images");

        File uploadRootDir = new File(uploadRootPath);
        //
        // Tạo thư mục gốc upload nếu nó không tồn tại.
        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }
        CommonsMultipartFile[] fileDatas = fashion.getFileImage();
        //
        if (fileDatas != null) {
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
                        fashion.setImage(name);
                        System.out.println("Write file: " + serverFile);
                    } catch (Exception e) {
                        System.out.println("Error Write file: " + name);
                    }
                }
            }
        }
        //
        Category fashionCategory = categoryService.fashionCategory();
        fashion.setTime(LocalDate.now());
        fashion.setCategory(fashionCategory);
        postsService.save(fashion);
        //
        ModelAndView modelAndView = new ModelAndView("admin/fashion/add");
        modelAndView.addObject("fashion", fashion);
        modelAndView.addObject("fashionCategory", fashionCategory);
        modelAndView.addObject("action", ACTION_EDIT);
        modelAndView.addObject("assign", ASSIGN);
        modelAndView.addObject("title", TITLE_EDIT);
        modelAndView.addObject("alert", ALERT_SUCCESS);
        modelAndView.addObject("message", ACTION_EDIT_SUCCESS);
        //
        return modelAndView;
    }

    @RequestMapping(value = "/fashion/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteForm(@PathVariable("id") Long id) {
        Posts fashion = postsService.findById(id);
        if (fashion != null) {
            ModelAndView modelAndView = new ModelAndView("admin/fashion/delete");
            modelAndView.addObject("fashion", fashion);
            modelAndView.addObject("action", ACTION_DELETE);
            modelAndView.addObject("assign", ASSIGN);
            modelAndView.addObject("title", TITLE_DELETE);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @RequestMapping(value = "/fashion/delete", method = RequestMethod.POST)
    public String delete(@ModelAttribute("fashion") Posts fashion) {
        postsService.remove(fashion.getId());
        return "redirect:/admin/fashion";
    }
}