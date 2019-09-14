package cn.itcast.demo;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class consumer {

    // 监听itcaist队列
    @JmsListener(destination = "itcast")
    public void readMessage(String  text){

        System.out.println("接收消息"+text);
    }

    // 监听itcaist_Map队列
    @JmsListener(destination = "itcast_Map")
    public void readMapMessage(Map map){
        System.out.println(map);
    }
}
