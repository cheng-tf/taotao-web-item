package com.taotao.shop.web.item.listener;

import com.taotao.shop.item.domain.pojo.TbItemDesc;
import com.taotao.shop.item.export.ItemResource;
import com.taotao.shop.web.item.domain.vo.Item;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
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
public class ItemAddMesssageListener implements MessageListener {

    @Autowired
    private ItemResource itemResource;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Value("${HTML_OUT_PATH}")
    private String HTML_OUT_PATH;


    @Override
    public void onMessage(Message message) {
        try{
            // 从消息中获取商品ID
            TextMessage textMessage = (TextMessage) message;
            String strItemId = textMessage.getText();
            Long itemId = Long.parseLong(strItemId);
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
            // Writer out = new FileWriter(new File(HTML_OUT_PATH + strItemId + ".html"));
            Writer out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(new File(HTML_OUT_PATH + strItemId + ".html")),"UTF-8"));
            // 5.生成静态页面
            template.process(data, out);
            // 关闭流
            out.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


}

