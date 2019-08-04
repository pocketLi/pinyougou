package cn.itcast.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/mq")
@RestController
public class MQController {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    /**
     * 发送消息到mq的队列
     * @return 操作完成结果
     */
    @GetMapping("/send")
    public String sendMsg(){
        Map<String, Object> map = new HashMap<>();
        map.put("name", "黑马");
        map.put("age", 13);
        //参数1：队列名称，参数2：发送的数据
        jmsMessagingTemplate.convertAndSend("spring.boot.mq.queue", map);
        return "success；已经发送队列消息，队列名称spring.boot.mq.queue";
    }

    /**
     * 发送消息到mq的队列
     * @return 操作完成结果
     */
    @GetMapping("/sendmq")
    public String sendMqMsg(){
        Map<String, Object> map = new HashMap<>();
        map.put("mobile", "13711347349");
        map.put("signName", "黑马");
        map.put("templateCode", "SMS_125018593");
        map.put("templateParam", "{\"code\":654321}");
        //参数1：队列名称，参数2：发送的数据
        jmsMessagingTemplate.convertAndSend("itcast_sms_queue", map);
        return "success；已经发送队列消息，队列名称itcast_sms_queue";
    }
}
