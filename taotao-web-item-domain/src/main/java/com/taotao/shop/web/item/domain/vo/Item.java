package com.taotao.shop.web.item.domain.vo;

import com.taotao.shop.item.domain.pojo.TbItem;

/**
 * <p>Title: Item</p>
 * <p>Description: </p>
 * <p>Company: bupt.edu.cn</p>
 * <p>Created: 2018-05-06 17:35</p>
 * @author ChengTengfei
 * @version 1.0
 */
public class Item extends TbItem {

    public Item() {
    }

    public Item(TbItem tbItem) {
        // 初始化属性
        this.setId(tbItem.getId());
        this.setTitle(tbItem.getTitle());
        this.setSellPoint(tbItem.getSellPoint());
        this.setPrice(tbItem.getPrice());
        this.setNum(tbItem.getNum());
        this.setBarcode(tbItem.getBarcode());
        this.setImage(tbItem.getImage());
        this.setCid(tbItem.getCid());
        this.setStatus(tbItem.getStatus());
        this.setCreated(tbItem.getCreated());
        this.setUpdated(tbItem.getUpdated());
    }

    public String[] getImages() {
        if (this.getImage() != null && !"".equals(this.getImage())) {
            String images = this.getImage();
            return images.split(",");
        }
        return null;
    }

}
