package cn.itcast.demo;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/*
发布订阅模式(消费者)
 */
public class TopicConsumer {
    public static void main(String[] args) throws JMSException,IOException {
        // 1.创建连接工厂6
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.129:61616");
        // 2.创建连接
        Connection connection = connectionFactory.createConnection();
        // 3.启动连接
        connection.start();
        // 4.获取session(会话对象) 参数1:是否启用事务; 参数2:消息的确认方式(自动确认)
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 5.创建队列对象
        Topic topic = session.createTopic("test-topic");
        // 6.创建消息消费者对象
        MessageConsumer consumer = session.createConsumer(topic);
        // 7.设置监听
        consumer.setMessageListener(new MessageListener(){
            // 匿名类
            public void onMessage(Message message){
                TextMessage textMessage = (TextMessage)message;
                try {
                    System.out.println("提取消息:"+textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        // 8.等待键盘输入
        System.in.read();
        // 9.关闭资源
        consumer.close();
        session.close();
        connection.close();

    }
}
