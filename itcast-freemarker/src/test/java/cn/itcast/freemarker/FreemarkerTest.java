package cn.itcast.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class FreemarkerTest {

    @Test
    public void test() throws Exception {
        //- 创建freemarker配置对象
        Configuration configuration = new Configuration(Configuration.getVersion());
        //- 设置配置对象的文件编码、模版路径
        configuration.setDefaultEncoding("utf-8");
        configuration.setClassForTemplateLoading(FreemarkerTest.class, "/ftl");
        //- 获取到具体的模版
        Template template = configuration.getTemplate("test.ftl");
        //- 创建数据
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("name", "heima");
        dataModel.put("message", "i_am_ljb can we chat");

        List<Map<String, Object>> goodsList = new ArrayList<>();

        Map<String, Object> map1 = new HashMap<>();
        map1.put("name", "苹果");
        map1.put("price", 20.0);
        goodsList.add(map1);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("name", "香蕉");
        map2.put("price", 4.0);
        goodsList.add(map2);

        dataModel.put("goodsList", goodsList);

        dataModel.put("today", new Date());
        dataModel.put("number", 123456789L);


        //- 创建文件输出对象
        FileWriter fileWriter = new FileWriter("D:\\itcast\\test\\test.html");

        //- 使用模版结合数据输出文件
        template.process(dataModel, fileWriter);

        fileWriter.close();
    }
}
