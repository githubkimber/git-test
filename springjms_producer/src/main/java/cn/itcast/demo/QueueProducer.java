package cn.itcast.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;


import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;


@Component  // 建立bean类名首字母默认小写为Id
public class QueueProducer {

    @Autowired  // jms模板
    private JmsTemplate jmsTemplate;

    @Autowired  // 文本队列
    private Destination queueTextDestination;

    /*
    发送文本消息
     */
    public void sendTextMessage(final String text){
        jmsTemplate.send(queueTextDestination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {

                return session.createTextMessage(text);
            }
        });
    }
}
