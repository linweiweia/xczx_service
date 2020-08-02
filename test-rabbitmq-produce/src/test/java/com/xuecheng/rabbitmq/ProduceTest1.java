package com.xuecheng.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author linweiwei
 * @version 1.0
 * @date 2020-07-29 20:06
 * @describe:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProduceTest1 {
    //队列名称
    public static final String Queue = "QueueName";

    @Test
    public static void main(String[] args) throws IOException, TimeoutException {
        //通过工厂创建和mq连接
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");

        Connection connection = null;
        Channel channel = null;

        try {
            //通过工厂生产连接
            connection = factory.newConnection();
            //使用连接生成channel（生产者与mq消息通信都通过channel）
            channel = connection.createChannel();
            //使用channel声明队列
            /**
             *  函数：queueDeclare(queue, durable, exclusive, autoDelete, arguments)
             *  参数：队列名称 是否辞旧话 是否独占 是否自动删除 扩展参数
             *  参数明细
             *      1、queue 队列名称
             *      2、durable 是否持久化，如果持久化，mq重启后队列还在
             *      3、exclusive 是否独占连接，队列只允许在该连接中访问，如果connection连接关闭队列则自动删除,如果将此参数设置true可用于临时队列的创建
             *      4、autoDelete 自动删除，队列不再使用时是否自动删除此队列，如果将此参数和exclusive参数设置为true就可以实现临时队列（队列不用了就自动删除）
             *      5、arguments 参数，可以设置一个队列的扩展参数，比如：可设置存活时间
             */
            channel.queueDeclare(Queue, true, false, false, null);
            //使用channel发送消息
            /**
             * 参数：String exchange, String routingKey, BasicProperties props, byte[] body
             * 参数明细：
             * 1、exchange，交换机，如果不指定将使用mq的默认交换机（设置为""）
             * 2、routingKey，路由key，交换机根据路由key来将消息转发到指定的队列，如果使用默认交换机，routingKey设置为队列的名称
             * 3、props，消息的属性
             * 4、body，消息内容
             */
            //消息内容
            String message = "我是发送的消息";
            channel.basicPublish("", Queue, null, message.getBytes());
            System.out.println("send to mq" + message);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }finally {
            channel.close();
            connection.close();
        }

    }
}
