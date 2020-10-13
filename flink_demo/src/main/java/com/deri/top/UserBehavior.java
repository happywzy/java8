package com.deri.top;

/**
 * @ClassName: UserBehavior
 * @Description: TODO
 * @Author: wuzhiyong
 * @Time: 2020/3/24 11:20
 * @Version: v1.0
 **/
public class UserBehavior {
    public long userId;         // 用户ID
    public long itemId;         // 商品ID
    public int categoryId;      // 商品类目ID
    public String behavior;     // 用户行为, 包括("pv", "buy", "cart", "fav")
    public long timestamp;      // 行为发生的时间戳，单位秒
}
