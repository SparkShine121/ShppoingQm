package com.controller;

import com.common.ApiRestResponse;
import com.common.Constant;
import com.entity.Notice;
import com.entity.Product;
import com.entity.Shop;
import com.entity.User;
import com.github.pagehelper.PageInfo;
import com.service.ProductAdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin")
@Slf4j
public class ProductAdminController {

    @Autowired
    ProductAdminService productAdminService;

    /**
     * 商家后台
     */
    @RequestMapping("/index")
    public ModelAndView houru() {
        ModelAndView modelAndView = new ModelAndView("/index");//设置对应JSP的模板文件
        //modelAndView.addObject("name", "miyue");
        return modelAndView;
    }

    @RequestMapping("/form")
    public ModelAndView form() {

        return new ModelAndView("/form");
    }

    @PostMapping("/shengqing")
    @ResponseBody
    public ApiRestResponse shengqing(@Param("shopName") String shopName, @Param("shopAddr") String shopAddr,
                                     @Param("shopDetail") String shopDetail, @Param("shopPhone") String shopPhone,
                                     @Param("shopType") String shopType, @Param("shopImg") String shopImg, HttpSession session) {
        Shop shop = new Shop();
        shop.setShopName(shopName);
        shop.setShopAddr(shopAddr);
        shop.setShopPhone(shopPhone);
        shop.setShopDetail(shopDetail);
        shop.setShopType(shopType);
        shop.setShopImg(shopImg);
        User user = (User) session.getAttribute(Constant.MALL_USER);
        productAdminService.shengqing(shop, user);
        return ApiRestResponse.success();
    }

    @RequestMapping("/notice")
    public ModelAndView notice() {
        //进入页面，由页面主动请求获取数据
        return new ModelAndView("/notice");
    }

    @GetMapping("/notice/list")
    @ResponseBody
    public ApiRestResponse noticeList(HttpSession session) {
        User user = (User) session.getAttribute(Constant.MALL_USER);
        List<Notice> noticeList = productAdminService.selectNotice(user);
        return ApiRestResponse.success(noticeList);
    }

    //进入新增商品页面
    @RequestMapping("/insertProduct")
    public ModelAndView insertProduct() {

        return new ModelAndView("/insertproduct");
    }

    //新增商品
    @PostMapping("/saveProduct")
    @ResponseBody
    public ApiRestResponse saveProduct(@Param("productImage") String productImage, @Param("productName") String productName,
                                       @Param("price") double price, @Param("detail") String detail,
                                       @Param("stock") int stock, HttpSession session) {
        Product product = new Product();
        product.setProductImage(productImage);
        product.setStock(stock);
        product.setPrice(price);
        product.setStatus(0);
        product.setProductName(productName);
        product.setDetail(detail);
        product.setUpdateTime(new Date());
        User user = (User) session.getAttribute(Constant.MALL_USER);
        Shop shop = productAdminService.selectByID(user);
        product.setShopId(shop.getId());
        productAdminService.insertProduct(product);
        return ApiRestResponse.success();
    }

    /**
     * 显示商品列表
     */
    @GetMapping("/productList")
    public ModelAndView selectList(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                   @RequestParam(value = "pageSize", required = false, defaultValue = "4") Integer pageSize,
                                   HttpSession session) {

        Product product = new Product();
        User user = (User) session.getAttribute(Constant.MALL_USER);
        Shop shop = productAdminService.selectByID(user);
        product.setShopId(shop.getId());
        PageInfo pageInfo = productAdminService.listForAdmin(pageNum, pageSize, product);
        ModelAndView mav = new ModelAndView("/adminShowProduct");
        mav.addObject("pageinfo", pageInfo);
        return mav;
    }

    @GetMapping("/updateProductYm/{id}")
    public ModelAndView updateProduct(@PathVariable("id") int id) {
        Product product = productAdminService.selectById(id);
        ModelAndView mav = new ModelAndView("/updateProduct");
        mav.addObject("product", product);
        return mav;
    }

    @PostMapping("/updateProduct")
    @ResponseBody
    public ApiRestResponse update(@RequestParam("productImage") String productImage, @RequestParam("productName") String productName,
                                  @RequestParam("price") double price, @RequestParam("detail") String detail,
                                  @RequestParam("stock") int stock, @RequestParam("id") int id) {
        Product product = new Product();
        product.setId(id);
        product.setProductName(productName);
        product.setDetail(detail);
        product.setProductImage(productImage);
        product.setPrice(price);
        product.setUpdateTime(new Date());
        product.setStock(stock);
        productAdminService.update(product);
        return ApiRestResponse.success();
    }
}
