package com.jgkj.grb.utils;

/**
 * 银行卡信息工具
 * Created by jugekeji on 2018/10/29.
 */

public class BankCardUtil {
    //  根据卡号查询卡信息：阿里 api
    // https://ccdcapi.alipay.com/validateAndCacheCardInfo.json?_input_charset=utf-8&cardBinCheck=true&cardNo=cardNo
    // {"cardType":"DC","bank":"CMB","key":"cardNo","messages":[],"validated":true,"stat":"ok"}

    // 根据 "bank":"CMB" 获取银行 logo
    // https://apimg.alipay.com/combo.png?d=cashier&t=AYCB

    public static final String ALIPAY_CARD_INFO = "https://ccdcapi.alipay.com/validateAndCacheCardInfo.json";
    public static final String ALIPAY_CARD_LOGO = "https://apimg.alipay.com/combo.png";


    //  http://www.mob.com/  ：Mob api ，get 请求方式
    // http://apicloud.mob.com/appstore/bank/card/query?key=key&card=card
    // {"msg":"success","result":{"bank":"招商银行","bin":"000000","binNumber":6,"cardName":"银联IC普卡","cardNumber":16,"cardType":"借记卡"},"retCode":"200"}
    // {"msg":"卡号有误,或者旧银行卡，暂时没有收录","retCode":"21401"}

    public static final String APICLOUD_CARD_INFO_APPKEY = "287a1fc29304f";
    public static final String APICLOUD_CARD_INFO = "http://apicloud.mob.com/appstore/bank/card/query";
}
