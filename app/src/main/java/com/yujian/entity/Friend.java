package com.yujian.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Friend implements Serializable {
    private String addressDetails;// 地址 ,
    private String advertisement;// 广告 ,
    private String content;// 性别|工作年限 ,
    private String distance;// 距离 ,
    private String icon;// icon(逗号分割) ,
    private String id;// id ,
    private String isHasCard;// 是否有健身卡 1 是 0否 ,
    private String label;// 标签(逗号分割) ,
    private String logo;// 头像 ,
    private String name;// 名称 ,
    private String sex;// 性别 1男 2女 ,
    private String userRole;// 用户角色 1 健身房 2 教练 3 普通用户

//    String t = {"status":"200","message":"成功","rows":
//
//    {
//        "list":[{
//        "id":1, "userRole":1, "logo":
//        "http://pqbf8r76b.bkt.clouddn.com/img6530635677464399872.jpg", "name":
//        "小白房", "addressDetails":"北京市北京市朝阳区北京市朝阳区建外soho西区15号楼二层1527", "distance":
//        "1.2243105E7", "label":"金牌,111,修身,游泳,系统标签", "isHasCard":"1", "advertisement":null, "icon":
//        null, "content":null, "sex":"女"
//    },{
//        "id":2, "userRole":2, "logo":
//        "http://pqbf8r76b.bkt.clouddn.com/9e724g50cikjuarp2ycigo1k83ayuugk.jpg", "name":
//        "白慧", "addressDetails":null, "distance":"1.1939784E7", "label":"测试标签,特色服务,活力", "isHasCard":
//        "0", "advertisement":null, "icon":null, "content":"男|工作3年", "sex":"男"
//    }],"total":2, "pages":1, "pageNum":1
//    }
//}
}
