package com.taotao.shop.web.item.controller;

import com.taotao.shop.web.item.domain.vo.Item;
import com.taotao.springboot.item.domain.pojo.TbItem;
import com.taotao.springboot.item.domain.pojo.TbItemDesc;
import com.taotao.springboot.item.export.ItemResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>Title: ItemController</p>
 * <p>Description: 商品详情页面展示Controller</p>
 * <p>Company: bupt.edu.cn</p>
 * <p>Created: 2018-05-06 17:38</p>
 * @author ChengTengfei
 * @version 1.0
 */
@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemResource itemResource;

    @RequestMapping("/{itemId}")
    public String showItem(@PathVariable long itemId, Model model) {
        // 获取商品基本信息
        TbItem tbItem = itemResource.getItemById(itemId);
        Item item = new Item(tbItem);
        // 获取商品详情
        TbItemDesc tbItemDesc = itemResource.getItemDescById(itemId);
        // 将数据传递给页面
        model.addAttribute("item", item);
        model.addAttribute("itemDesc", tbItemDesc);
        // 返回逻辑视图
        return "item";
    }

}
