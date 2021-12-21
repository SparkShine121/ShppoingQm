package com.controller;

import com.entity.Product;
import com.service.ProductAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件上传
 */
@Controller
public class FileController {

    @Autowired
    ProductAdminService productAdminService;

    @RequestMapping(value = "/file")
    public ModelAndView file() {
        return new ModelAndView("/file");
    }

    @GetMapping(value = "/updateFile")
    public ModelAndView updateFile(@RequestParam("id") int id) {
        ModelAndView modelAndView = new ModelAndView("/updateFile");
        modelAndView.addObject("id", id);
        return modelAndView;
    }

    @PostMapping(value = "/fileUpload")
    public ModelAndView fileUpload(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request) {
        if (file.isEmpty()) {
            System.out.println("文件为空空");
        }
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        String filePath = "D://temp-rainy//"; // 上传后的路径
        fileName = UUID.randomUUID() + suffixName; // 新文件名
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ModelAndView model = new ModelAndView("/insertproduct");
        String filename = "/temp-rainy/" + fileName;
        model.addObject("filename", filename);
        return model;
    }

    @PostMapping(value = "/updateFileUpload")
    public ModelAndView updateFileUpload(@RequestParam("id") String id, @RequestParam(value = "file") MultipartFile file, HttpServletRequest request) {
        int id1 = Integer.parseInt(id);
        if (file.isEmpty()) {
            System.out.println("文件为空空");
        }
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        String filePath = "D://temp-rainy//"; // 上传后的路径
        fileName = UUID.randomUUID() + suffixName; // 新文件名
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ModelAndView model = new ModelAndView("/updateProduct");
        String filename = "/temp-rainy/" + fileName;
        model.addObject("filename", filename);
        Product product = productAdminService.selectById(id1);
        model.addObject("product", product);
        return model;
    }
}
