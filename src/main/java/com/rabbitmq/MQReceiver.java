package com.rabbitmq;

import com.entity.MiaoshaOrder;
import com.entity.MiaoshaProduct;
import com.entity.User;
import com.redis.RedisService;
import com.service.MiaoshaService;
import com.service.ProductAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

//@Service
@Slf4j
public class MQReceiver {

	@Autowired
	MiaoshaService miaoshaService;
	@Autowired
	ProductAdminService productAdminService;

	//监听秒杀队列
	@RabbitListener(queues = MQConfig.MIAOSHA_QUEUE)
	public void receiveTopic1(String message) {
		log.info(" topic  queue1 message:" + message);
		MiaoshaMessage mm = RedisService.stringToBean(message, MiaoshaMessage.class);
		User user = mm.getUser();
		int goodsId = mm.getGoodsId();
		MiaoshaProduct miaoshaProduct = miaoshaService.selectDetailById(goodsId);
		int stock = miaoshaProduct.getStock();
		if (stock <= 0) {
			return;
		}
		MiaoshaOrder miao = new MiaoshaOrder();
		miao.setUserId(user.getId());
		miao.setProductId(goodsId);
		//查询有没有该用户该商品有没有秒杀过
		MiaoshaOrder miaoshaOrder = miaoshaService.find(miao);
		if (miaoshaOrder != null) {
			return;
		}
		//减库存,下订单，写入秒杀订单
		miaoshaService.add(miao, miaoshaProduct);
	}

}
