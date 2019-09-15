package cn.itcast.demo;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/*
发布订阅模式(消息生产者)
 */
public class TopicProducer {
    public static void main(String[] args) throws JMSException {
        // 1.创建连接工厂6
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.129:61616");
        // 2.创建连接
        Connection connection = connectionFactory.createConnection();
        // 3.启动连接
        connection.start();
        // 4.获取session(会话对象) 参数1:是否启用事务; 参数2:消息的确认方式(自动确认)
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 5.创建主题对象
        Topic topic = session.createTopic("test-topic") ;
        // 6.创建消息生产者对象
        MessageProducer producer = session.createProducer(topic);
        // 7.创建先息对象(文本消息)
        TextMessage textMassage = session.createTextMessage("欢迎来到品优购购物!!!");
        // 8.发送消息
        producer.send(textMassage);
        // 9.关闭资源
        producer.close();
        session.close();
        connection.close();

    }

}
