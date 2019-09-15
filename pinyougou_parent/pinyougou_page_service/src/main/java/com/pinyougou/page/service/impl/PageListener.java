package com.pinyougou.page.service.impl;

import com.pinyougou.page.service.ItemPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/*
监听类(用于生成网页)
 */
@Component
public class PageListener implements MessageListener {

    @Autowired
    private ItemPageService itemPageService ;

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage)message ;
        try {
            // 获取商品Id
            String text = textMessage.getText();
            System.out.println("接收到消息"+text);
            // 生成HTML文件(成功返回true,失败则false)
            boolean b = itemPageService.genItemHtml(Long.parseLong(text));
            System.out.println("网页生成结果:"+b);
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
