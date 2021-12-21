package com.service.impl;

import com.entity.Notice;
import com.entity.Product;
import com.entity.Shop;
import com.entity.User;
import com.exception.ImoocMallException;
import com.exception.ImoocMallExceptionEnum;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mapper.NoticeMapper;
import com.mapper.ProductMapper;
import com.mapper.ShopMapper;
import com.service.ProductAdminService;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProductAdminServiceImpl implements ProductAdminService {

    @Autowired
    ShopMapper shopMapper;

    @Autowired
    NoticeMapper noticeMapper;

    @Autowired
    UserService userService;

    @Autowired
    ProductMapper productMapper;

    /**
     * 商家申请入驻
     */
    @Override
    public void shengqing(Shop shop, User user) {
        Shop shop1 = shopMapper.selectName(shop);
        if (shop1 != null) {
            throw new ImoocMallException(ImoocMallExceptionEnum.NAME_EXISTED);
        }
        shop.setStatus(0);
        shop.setBusiness(0);
        shop.setCreateDate(new Date());
        shop.setUserId(user.getId());
        shopMapper.insertShop(shop);

        //向申请人发通知
        Notice notice = new Notice();
        notice.setUserId(user.getId());
        notice.setCreateTime(new Date());
        notice.setContent("商铺入驻申请已发出,24小时内会审批完成,请等待");
        noticeMapper.insertNotice(notice);
        //向平台发起通知
        Notice notice1 = new Notice();
        User user1 = userService.selectPt();
        //平台接收人的ID
        notice1.setUserId(user1.getId());
        //申请发起人的id
        notice1.setSqrId(user.getId());
        notice1.setCreateTime(new Date());
        String noticeContent = String.format("%s发起商铺入驻,请审核", user.getUsername());
        notice1.setContent(noticeContent);
        noticeMapper.insertNotice(notice1);
    }

    @Override
    public List<Notice> selectNotice(User user) {
        Notice notice = new Notice();
        notice.setUserId(user.getId());
        return noticeMapper.selectNotice(notice);
    }

    @Override
    public List<Shop> selectShop() {
        List<Shop> list = shopMapper.selectShop();
        return list;
    }

    @Override
    public void tongguo(Shop shop, User user) {
        shop.setStatus(1);
        shopMapper.update(shop);
        Shop shop1 = shopMapper.selectName(shop);
        String shopName = shop1.getShopName();
        int sqrId = shop1.getUserId();
        //向申请人发通知
        Notice notice = new Notice();
        notice.setUserId(sqrId);
        notice.setCreateTime(new Date());
        notice.setContent("你的商铺入驻申请平台管理员已通过");
        noticeMapper.insertNotice(notice);
        //根据sqrId查询出申请人姓名
        User user1 = new User();
        user1.setId(sqrId);
        User user2 = userService.selectById(user1);
        String username = user2.getUsername();
        //向平台发起信息
        Notice notice1 = new Notice();
        notice1.setUserId(user.getId());
        String content = String.format("%s申请%s入驻京西商城,你已经通过该申请", username, shopName);
        notice1.setContent(content);
        notice1.setCreateTime(new Date());
        noticeMapper.insertNotice(notice1);

    }

    @Override
    public void butongguo(Shop shop, User user) {
        shop.setStatus(2);
        shopMapper.update(shop);
        Shop shop1 = shopMapper.selectName(shop);
        String shopName = shop1.getShopName();
        int sqrId = shop1.getUserId();
        //向申请人发通知
        Notice notice = new Notice();
        notice.setUserId(sqrId);
        notice.setCreateTime(new Date());
        notice.setContent("你的商铺入驻申请已被平台管理员驳回");
        noticeMapper.insertNotice(notice);
        //根据sqrId查询出申请人姓名
        User user1 = new User();
        user1.setId(sqrId);
        User user2 = userService.selectById(user1);
        String username = user2.getUsername();
        //向平台发起信息
        Notice notice1 = new Notice();
        notice1.setUserId(user.getId());
        String content = String.format("%s申请%s入驻京西商城,你已驳回该申请", username, shopName);
        notice1.setContent(content);
        notice1.setCreateTime(new Date());
        noticeMapper.insertNotice(notice1);
    }

    @Override
    public void insertProduct(Product product) {
        Product product1 = productMapper.selectTongMing(product);
        if (product1 != null) {
            throw new ImoocMallException(ImoocMallExceptionEnum.NAME_EXISTED);
        }
        productMapper.insertProduct(product);
    }

    @Override
    public Shop selectByID(User user) {
        Shop shop = new Shop();
        shop.setUserId(user.getId());
        List<Shop> shop1 = shopMapper.selectByID(shop);
        return shop1.get(0);

    }

    @Override
    public List<Product> selectList(Product product) {
        productMapper.selectListForAdmin(product);
        //TODO
        return null;
    }

    @Override
    public PageInfo<Product> listForAdmin(Integer pageNum, Integer pageSize, Product product) {
        PageHelper.startPage(pageNum, pageSize);
        List<Product> products = productMapper.selectListForAdmin(product);
        PageInfo<Product> pageInfo = new PageInfo<Product>(products);
        return pageInfo;
    }

    @Override
    public void update(Product product) {
        Product product2 = productMapper.selectTongMing(product);
        if (product2 != null) {
            throw new ImoocMallException(ImoocMallExceptionEnum.NAME_EXISTED);
        }
        productMapper.update(product);
    }

    @Override
    public Product selectById(int id) {
        Product product = productMapper.selectById(id);
        return product;
    }

    @Override
    public void updateStock(Product product) {
        productMapper.updateStock(product);
    }

    @Override
    public Shop selectByShopId(int id) {
        return shopMapper.selectByShopId(id);
    }
}
