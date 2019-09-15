package cn.itcast.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/*
消息生产者
 */
@RestController
public class QueueController {

    // 发送MQ消息的模板
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @RequestMapping("/send")
    public  void send(String text){
        jmsMessagingTemplate.convertAndSend("itcast",text);
    }

    @RequestMapping("/sendMap")
    public  void sendMap(){
        System.out.println("你好!!!----11---!!");
        Map map = new HashMap<>();
        map.put("mobile","132965653554");
        map.put("template_code","SMS_168586741");
        map.put("sign_name","黑马课堂");
        map.put("param","{\"number\":\"165645\"}");

        jmsMessagingTemplate.convertAndSend("sms",map);
    }

}
