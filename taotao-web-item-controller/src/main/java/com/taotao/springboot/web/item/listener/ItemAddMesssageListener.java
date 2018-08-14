package com.taotao.springboot.web.item.listener;

import com.taotao.springboot.item.domain.pojo.TbItemDesc;
import com.taotao.springboot.item.export.ItemResource;
import com.taotao.springboot.web.item.domain.vo.Item;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title: ItemAddMesssageListener</p>
 * <p>Description: </p>
 * <p>Company: bupt.edu.cn</p>
 * <p>Created: 2018-05-06 17:33</p>
 * @author ChengTengfei
 * @version 1.0
 */
@Component
@RabbitListener(queues = "item-add.freemarker")//基于RabbitMQ：使用RabbitListener配置监听的队列
public class ItemAddMesssageListener {

    @Autowired
    private ItemResource itemResource;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Value("${HTML_OUT_PATH}")
    private String HTML_OUT_PATH;

    @RabbitHandler
    public void consume(String message) {
        try{
            // 从消息中获取商品ID
            Long itemId = Long.parseLong(message);
            // 等待事务提交
            Thread.sleep(1000);
            // 根据商品ID查询商品信息、及商品描述
            Item item = new Item(itemResource.getItemById(itemId));
            TbItemDesc itemDesc = itemResource.getItemDescById(itemId);
            // 使用FreeMarker生成静态页面
            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            // 1.创建模板
            // 2.加载模板对象
            Template template = configuration.getTemplate("item.ftl");
            // 3.准备模板需要的数据
            Map<String, Object> data = new HashMap<>();
            data.put("item", item);
            data.put("itemDesc", itemDesc);
            // 4.指定输出的目录及文件名
            //Writer out = new BufferedWriter(new OutputStreamWriter(//之前
                    //new FileOutputStream(new File(HTML_OUT_PATH + itemId + ".html")),"UTF-8"));
            // #4.1 获取根目录
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            if(!path.exists()) {
                path = new File("");
            }
            // #4.2 获取静态资源目录
            File upload = new File(path.getAbsolutePath(),"static/");
            if(!upload.exists()) {
                upload.mkdirs();
            }
            Writer out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(new File(upload.getAbsolutePath() + "/" + itemId + ".html")),"UTF-8"));
            // 5.生成静态页面
            template.process(data, out);
            // 关闭流
            out.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


}

