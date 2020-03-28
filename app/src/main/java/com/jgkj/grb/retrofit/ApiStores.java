package com.jgkj.grb.retrofit;

import com.jgkj.grb.BuildConfig;
import com.jgkj.grb.ui.bean.CentreRecordModel;
import com.jgkj.grb.ui.bean.PersonalTransferFriendsModel;
import com.jgkj.grb.ui.bean.UserRatioModel;
import com.jgkj.grb.ui.gujujin.bean.AgentLevelModel;
import com.jgkj.grb.ui.mvp.ApplyAfterDetailModel;
import com.jgkj.grb.ui.mvp.BankCardInfoModel;
import com.jgkj.grb.ui.mvp.CollectModel;
import com.jgkj.grb.ui.mvp.ConventionCentreModel;
import com.jgkj.grb.ui.mvp.FlashSaleModel;
import com.jgkj.grb.ui.mvp.FlashSalePageModel;
import com.jgkj.grb.ui.mvp.IndexPlaceGoodsModel;
import com.jgkj.grb.ui.mvp.IndexPlaceModel;
import com.jgkj.grb.ui.mvp.OrderDetailsModel;
import com.jgkj.grb.ui.mvp.OrderManagementModel;
import com.jgkj.grb.ui.mvp.OrderSubOrderModel;
import com.jgkj.grb.ui.mvp.SettlementModel;
import com.jgkj.grb.ui.mvp.cloud.CloudWarehouseRecordModel;
import com.jgkj.grb.ui.mvp.find.FindDetailsModel;
import com.jgkj.grb.ui.mvp.find.FindModel;
import com.jgkj.grb.ui.mvp.find.FindPageModel;
import com.jgkj.grb.ui.mvp.index.IndexCatesChildListModel;
import com.jgkj.grb.ui.mvp.index.IndexCatesChildPageModel;
import com.jgkj.grb.ui.mvp.index.IndexCatesListModel;
import com.jgkj.grb.ui.mvp.index.IndexSearchModel;
import com.jgkj.grb.ui.mvp.luckdraw.LuckDrawIndexModel;
import com.jgkj.grb.ui.mvp.luckdraw.LuckDrawRecordModel;
import com.jgkj.grb.ui.mvp.message.MessageDetailModel;
import com.jgkj.grb.ui.mvp.message.MessageModel;
import com.jgkj.grb.ui.mvp.personal.AddresManagementModel;
import com.jgkj.grb.ui.mvp.personal.CouponModel;
import com.jgkj.grb.ui.mvp.personal.PersonalEvaluationModel;
import com.jgkj.grb.ui.mvp.personal.PersonalGRBCashModel;
import com.jgkj.grb.ui.mvp.personal.PersonalGRBModel;
import com.jgkj.grb.ui.mvp.personal.PersonalHeadModel;
import com.jgkj.grb.ui.mvp.personal.PersonalIncomeModel;
import com.jgkj.grb.ui.mvp.personal.PersonalIncomeRecordModel;
import com.jgkj.grb.ui.mvp.personal.PersonalRechargeRecordModel;
import com.jgkj.grb.ui.mvp.personal.PersonalTracksModel;
import com.jgkj.grb.ui.mvp.personal.PersonalTransferRecordModel;
import com.jgkj.grb.ui.mvp.personal.PowderModel;
import com.jgkj.grb.ui.mvp.personal.RankFarmAgentModel;
import com.jgkj.grb.ui.mvp.product.ProductDetailsModel;
import com.jgkj.grb.ui.mvp.product.ProductEvaluationModel;
import com.jgkj.grb.ui.mvp.shopcart.CartIndexModel;
import com.jgkj.grb.utils.BankCardUtil;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;

/**
 * Android-Retrofit+RxJava+OkHttp：https://www.jianshu.com/p/0d1fb9f08a34
 */
public interface ApiStores {

    String TOKEN_KEY = "authtoken";

    // 环信客服
    boolean KEFU_CHANNEL_DEBUG = BuildConfig.KEFU_CHANNEL_DEBUG;
    // API BaseUrl
    String API_SERVER_URL = BuildConfig.API_SERVER_URL;
    // API GameUrl
    String API_SERVER_URL_GAME = BuildConfig.API_SERVER_URL_GAME;
    // API ShareWeb
    String API_SERVER_URL_SHARE = BuildConfig.API_SERVER_URL_SHARE;

    // 在线客服
    String API_SERVER_SERVICE = "http://p.qiao.baidu.com/cps/chat?siteId=14016818&userId=29150210&cp=hnyqwlkj.com&cr=hnyqwlkj.com&cw=%E6%B2%B3%E5%8D%97%E6%89%AC%E5%A5%87%E7%BD%91%E7%BB%9C%E7%A7%91%E6%8A%80%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8";
    // 下载 QuantBroker
    String API_SERVER_QUANTBROKER = "https://quantbroker.info/appDownload.html?nsukey=OmuylgdIF7ehdS2eclAKM4a2nNPN73%2BleEJW2RO%2FkM5ZAott6UPK83m2Uwzq59c%2B8i9x3%2Fi2f59Ww2msMnxPUO8WKlIsw%2BI2xGeLfzg33o%2FAaJHpi03zdmbDWvBg4FBU5jVPCngnCyowDdRCS6ho5UJl8X6o8IUcFaSMrtI4iwI9NSaH8Ur7qrYy4%2Foa0UDA9DEOewh0fePwxPVl%2FNEtaQ%3D%3D";

    /**
     * 登录：us_tel  password
     */
    String API_SERVER_LOGIN = "/index/login/index";

    @GET(API_SERVER_LOGIN)
    Observable<String> login(@QueryMap Map<String, Object> body);

    /**
     * 商品详情
     */
    String API_SERVER_GOODS_DETAIL = "/index/index/goodsdetail";

    @FormUrlEncoded
    @POST(API_SERVER_GOODS_DETAIL)
    Observable<ProductDetailsModel> goodsDetail(@Field("pd_id") Object pdid, @Field("us_id") Object usid);

    /**
     * 商品详情：收藏
     */
    String API_SERVER_GOODS_COLLECTION = "/index/index/Collection";

    @FormUrlEncoded
    @POST(API_SERVER_GOODS_COLLECTION)
    Observable<String> goodsCollection(@Field("pd_id") Object pdid, @Field("us_id") Object usid);

    /**
     * 商品详情：取消收藏
     */
    String API_SERVER_GOODS_COLLECTION_CANCEL = "/index/index/cancel";

    @FormUrlEncoded
    @POST(API_SERVER_GOODS_COLLECTION_CANCEL)
    Observable<String> goodsCollectionCancel(@Field("pd_id") Object pdid, @Field("us_id") Object usid);

    /**
     * 商品详情：添加购物车，pd_id 商品id，sku_id 规格id，num 商品数量，pd_spec  规格名称
     */
    String API_SERVER_CART_ADD = "/index/cart/add";

    @FormUrlEncoded
    @POST(API_SERVER_CART_ADD)
    Observable<String> userCartAdd(@Header(TOKEN_KEY) String header, @FieldMap Map<String, Object> body);

    /**
     * 首页：分类信息，主分类列表
     */
    String API_SERVER_INDEX_CATES = "/index/index/cates";

    @GET(API_SERVER_INDEX_CATES)
    Observable<IndexCatesListModel> indexCates();

    /**
     * 首页：分类信息，主分类下的子分类列表
     */
    String API_SERVER_INDEX_INDEX = "/index/index/index";

    @FormUrlEncoded
    @POST(API_SERVER_INDEX_INDEX)
    Observable<IndexCatesChildListModel> indexIndex(@Field("ca_id") Object caid);

    /**
     * 首页：分类信息，子分类下的商品数据列表
     */
    String API_SERVER_INDEX_CATEGOODS = "/index/index/categoods";

    @FormUrlEncoded
    @POST(API_SERVER_INDEX_CATEGOODS)
    Observable<IndexCatesChildPageModel> indexCateGoods(@Field("ca_id") Object caid);

    /**
     * 我的收藏商品
     */
    String API_SERVER_USER_COLLECTION = "/index/user/collection";

    @GET(API_SERVER_USER_COLLECTION)
    Observable<CollectModel> userCollection(@Header(TOKEN_KEY) String header);

    /**
     * 限时抢购：标签
     */
    String API_SERVER_LIMIT_PRODUCT = "/index/index/limitproduct";

    @GET(API_SERVER_LIMIT_PRODUCT)
    Observable<FlashSaleModel> limitProduct();

    /**
     * 限时抢购：page
     */
    String API_SERVER_LIMITS = "/index/index/limits";

    @FormUrlEncoded
    @POST(API_SERVER_LIMITS)
    Observable<FlashSalePageModel> limits(@Field("id") Object id);

    /**
     * 主页：发现接口
     */
    String API_SERVER_CONSULTATION = "/index/index/consultation";

    @GET(API_SERVER_CONSULTATION)
    Observable<FindModel> consultation();

    /**
     * 主页：发现里面的新闻列表接口
     */
    String API_SERVER_CONSULTATION_LISTNEWS = "/index/index/listnews";

    @FormUrlEncoded
    @POST(API_SERVER_CONSULTATION_LISTNEWS)
    Observable<FindPageModel> consultationListNews(@Field("ca_id") Object caid);

    /**
     * 主页：发现里面的新闻详情接口
     */
    String API_SERVER_CONSULTATION_DETAILNEWS = "/index/index/detailnews";

    @FormUrlEncoded
    @POST(API_SERVER_CONSULTATION_DETAILNEWS)
    Observable<FindDetailsModel> consultationDetailNews(@Header(TOKEN_KEY) String header, @Field("id") Object id);

    /**
     * 我的足迹：我的浏览历史
     */
    String API_SERVER_USER_SKIM = "/index/user/skim";

    @GET(API_SERVER_USER_SKIM)
    Observable<PersonalTracksModel> userSkim(@Header(TOKEN_KEY) String header);

    /**
     * 个人中心：GRB，主页面
     */
    String API_SERVER_USER_GRB = "/index/user/grb";

    @GET(API_SERVER_USER_GRB)
    Observable<PersonalGRBModel> userGRB(@Header(TOKEN_KEY) String header);

    /**
     * 个人中心：GRB，通证明细，year ：年     month ：月   type:1：grc，2：grb，3：账户余额明细，4 gra   page:第几页  status：0 支出，1 收入
     */
    String API_SERVER_USER_MYGRBCASH = "/index/user/mygrbcash";

    @FormUrlEncoded
    @POST(API_SERVER_USER_MYGRBCASH)
    Observable<PersonalGRBCashModel> userGRBCash(@Header(TOKEN_KEY) String header, @FieldMap Map<String, Object> body);

    /**
     * 个人中心：GRB，转让记录，year ：年     month ：月   type：1：好友转让 2：公共市场转让   page:第几页
     */
    String API_SERVER_USER_MYGRBTURN = "/index/user/mygrbturn";

    @FormUrlEncoded
    @POST(API_SERVER_USER_MYGRBTURN)
    Observable<PersonalTransferRecordModel> userGRBTurn(@Header(TOKEN_KEY) String header, @FieldMap Map<String, Object> body);

    /**
     * 抽奖主页：
     */
    String API_SERVER_LOTTERY_INDEX = "/index/lottery/index";

    @GET(API_SERVER_LOTTERY_INDEX)
    Observable<LuckDrawIndexModel> lotteryIndex(@Header(TOKEN_KEY) String header);

    /**
     * 抽奖主页：支付 GRB 获取抽奖次数
     */
    String API_SERVER_LOTTERY_GETNUM = "/index/lottery/getnum";

    @GET(API_SERVER_LOTTERY_GETNUM)
    Observable<String> lotteryGetNum(@Header(TOKEN_KEY) String header);

    /**
     * 抽奖主页：抽奖接口
     */
    String API_SERVER_LOTTERY_EDIT = "/index/lottery/edit";

    @GET(API_SERVER_LOTTERY_EDIT)
    Observable<LuckDrawIndexModel.DataBean.DataResultBean> lotteryEdit(@Header(TOKEN_KEY) String header);

    /**
     * 抽奖主页：我的奖品
     */
    String API_SERVER_LOTTERY_MYPRIZE = "/index/lottery/myprize";

    @GET(API_SERVER_LOTTERY_MYPRIZE)
    Observable<LuckDrawRecordModel> lotteryMyPrize(@Header(TOKEN_KEY) String header);

    /**
     * 个人中心：我的地址
     */
    String API_SERVER_USER_MYADDR = "/index/user/myAddr";

    @GET(API_SERVER_USER_MYADDR)
    Observable<AddresManagementModel> userAddr(@Header(TOKEN_KEY) String header);

    /**
     * 个人中心：我的地址，添加地址
     */
    String API_SERVER_USER_ADD_ADDR = "/index/user/addAddr";

    @FormUrlEncoded
    @POST(API_SERVER_USER_ADD_ADDR)
    Observable<String> userAddAddr(@Header(TOKEN_KEY) String header, @FieldMap Map<String, Object> body, @Field("addr_code[]") List<String> addrCode);

    /**
     * 个人中心：我的地址，修改地址
     */
    String API_SERVER_USER_ADD_EDIT = "/index/user/editAddr";

    @FormUrlEncoded
    @POST(API_SERVER_USER_ADD_EDIT)
    Observable<String> userAddEdit(@Header(TOKEN_KEY) String header, @FieldMap Map<String, Object> body, @Field("addr_code[]") List<String> addrCode);

    /**
     * 个人中心：我的地址，删除地址
     */
    String API_SERVER_USER_ADD_DELETE = "/index/user/delAddr";

    @FormUrlEncoded
    @POST(API_SERVER_USER_ADD_DELETE)
    Observable<String> userAddDelete(@Header(TOKEN_KEY) String header, @Field("id") Object id);

    /**
     * 个人中心：我的地址，地址详情
     */
    String API_SERVER_USER_ADD_DETAIL = "/index/user/addrDetail";

    @FormUrlEncoded
    @POST(API_SERVER_USER_ADD_DETAIL)
    Observable<String> userAddDetail(@Header(TOKEN_KEY) String header, @Field("id") Object id);

    /**
     * 购物车列表：
     */
    String API_SERVER_CART_INDEX = "/index/cart/index";

    @GET(API_SERVER_CART_INDEX)
    Observable<CartIndexModel> userCartIndex(@Header(TOKEN_KEY) String header);

    /**
     * 购物车列表：购物车数量+1   参数 cart_id
     */
    String API_SERVER_CART_ADDNUM = "/index/cart/addnum";

    @FormUrlEncoded
    @POST(API_SERVER_CART_ADDNUM)
    Observable<String> userCartAddnum(@Header(TOKEN_KEY) String header, @Field("cart_id") Object cartId);

    /**
     * 购物车列表：购物车数量-1   参数 cart_id
     */
    String API_SERVER_CART_CUTNUM = "/index/cart/cutnum";

    @FormUrlEncoded
    @POST(API_SERVER_CART_CUTNUM)
    Observable<String> userCartCutnum(@Header(TOKEN_KEY) String header, @Field("cart_id") Object cartId);

    /**
     * 购物车列表：批量收藏    参数id[]
     */
    String API_SERVER_CART_COLLECTION = "/index/cart/collection";

    @FormUrlEncoded
    @POST(API_SERVER_CART_COLLECTION)
    Observable<String> userCartCollection(@Header(TOKEN_KEY) String header, @Field("id[]") List<String> id);

    /**
     * 购物车列表：批量删除    参数id[]
     */
    String API_SERVER_CART_DELCART = "/index/cart/delcart";

    @FormUrlEncoded
    @POST(API_SERVER_CART_DELCART)
    Observable<String> userCartDelete(@Header(TOKEN_KEY) String header, @Field("id[]") List<String> id);

    /**
     * 结算页面：购物车
     */
    String API_SERVER_CART_SETTLEMENT = "/index/cart/settlement";

    @FormUrlEncoded
    @POST(API_SERVER_CART_SETTLEMENT)
    Observable<SettlementModel> userCartSettlement(@Header(TOKEN_KEY) String header, @Field("id[]") List<String> id);

    /**
     * 结算页面：单商品    pd_id    sku_id    pd_num      商品id，规格id，商品数量
     */
    String API_SERVER_ORDER_SETTLEMENT = "/index/order/settlement";

    @FormUrlEncoded
    @POST(API_SERVER_ORDER_SETTLEMENT)
    Observable<SettlementModel> userOrderSettlement(@Header(TOKEN_KEY) String header, @Field("pd_id") Object pdId, @Field("sku_id") Object skuId, @Field("pd_num") Object pdNum);

    /**
     * 结算页面：查询运费，addr_id
     */
    String API_SERVER_ORDER_FINDFEE = "/index/order/findfee";

    @FormUrlEncoded
    @POST(API_SERVER_ORDER_FINDFEE)
    Observable<String> userOrderFindfee(@Header(TOKEN_KEY) String header, @Field("addr_id") Object addrId);

    /**
     * 结算页面：购物车下单    参数   id   数组； type：1:商城订单，2：云仓库订单，3：抢购订单；   addr_id ：  地址id ，不是云仓库订单必须传这个参数
     */
    String API_SERVER_CART_ADDORDER = "/index/cart/addOrder";

    @FormUrlEncoded
    @POST(API_SERVER_CART_ADDORDER)
    Observable<String> userCartAddOrder(@Header(TOKEN_KEY) String header, @Field("id[]") List<String> id, @Field("type") Object type,
                                        @Field("addr_id") Object addrId, @Field("or_remark") Object orRemark);

    /**
     * 结算页面：单商品下单     参数   pd_id    sku_id    pd_num  type   addr_id       商品id，规格id，商品数量   订单类型    地址id
     */
    String API_SERVER_ORDER_ADDORDER = "/index/order/addOrder";

    @FormUrlEncoded
    @POST(API_SERVER_ORDER_ADDORDER)
    Observable<String> userOrderAddOrder(@Header(TOKEN_KEY) String header, @Field("pd_id") Object pdId, @Field("sku_id") Object skuId,
                                         @Field("pd_num") Object pdNum, @Field("type") Object type, @Field("addr_id") Object addrId, @Field("or_remark") Object orRemark);

    @FormUrlEncoded
    @POST(API_SERVER_ORDER_ADDORDER)
    Observable<String> userOrderAddOrder(@Header(TOKEN_KEY) String header, @Field("pd_id") Object pdId, @Field("sku_id") Object skuId,
                                         @Field("pd_num") Object pdNum, @Field("type") Object type, @Field("type4") Object type4,
                                         @Field("addr_id") Object addrId, @Field("or_remark") Object orRemark);

    /**
     * 结算页面：云仓库提货，支付(运费)     参数 c_id  pd_id    sku_id    pd_num  type   addr_id      列表id 商品id，规格id，商品数量   订单类型    地址id  type2 云仓库提货订单 固定值 2
     */
    @FormUrlEncoded
    @POST(API_SERVER_ORDER_ADDORDER)
    Observable<String> userOrderAddOrder(@Header(TOKEN_KEY) String header, @Field("c_id") Object cId, @Field("pd_id") Object pdId, @Field("sku_id") Object skuId,
                                         @Field("pd_num") Object pdNum, @Field("type") Object type, @Field("addr_id") Object addrId, @Field("type2") Object type2, @Field("paytype") Object paytype);

    /**
     * 支付订单：支付页面    or_id   订单id
     */
    String API_SERVER_ORDER_SUBORDER = "/index/order/subOrder";

    @FormUrlEncoded
    @POST(API_SERVER_ORDER_SUBORDER)
    Observable<OrderSubOrderModel> userOrderSubOrder(@Header(TOKEN_KEY) String header, @Field("or_id") Object orId);

    /**
     * 会议：
     */
    String API_SERVER_MEETING_INDEX = "/index/meeting/index";

    @GET(API_SERVER_MEETING_INDEX)
    Observable<ConventionCentreModel> indexMeetingIndex(@Header(TOKEN_KEY) String header);

    /**
     * 会议：报名   name   tel   card_id   m_id    sex     姓名  电话   身份证号  会议id    性别：1男，2女
     */
    String API_SERVER_MEETING_SIGNUP = "/index/meeting/signUp";

    @FormUrlEncoded
    @POST(API_SERVER_MEETING_SIGNUP)
    Observable<String> indexMeetingSignUp(@Header(TOKEN_KEY) String header, @Field("name") Object name, @Field("tel") Object tel,
                                          @Field("card_id") Object cardId, @Field("m_id") Object mId, @Field("sex") Object sex);

    /**
     * 会议：签到   会议id    经纬度(经,纬)
     */
    String API_SERVER_MEETING_SIGNIN = "/index/meeting/signin";

    @FormUrlEncoded
    @POST(API_SERVER_MEETING_SIGNIN)
    Observable<String> indexMeetingSignIn(@Header(TOKEN_KEY) String header, @Field("m_id") Object mId, @Field("coordinate") Object coordinate);

    /**
     * 会议：支付报名费  id:提交成功返回的id；type 支付类型；pwd 密码
     */
    String API_SERVER_MEETING_PAYFEE = "/index/meetingpay/meetingfee";

    @FormUrlEncoded
    @POST(API_SERVER_MEETING_PAYFEE)
    Observable<String> indexMeetingPayFee(@Header(TOKEN_KEY) String header, @Field("id") Object id, @Field("type[]") List<Object> type, @Field("pwd") Object pwd);

    /**
     * 订单列表：   type  1:商城订单，2：云仓库订单，3：抢购订单       status  1，待付款，3待发货，4 待收货  5 待评价       page  页码
     */
    String API_SERVER_ORDER_INDEX = "/index/order/index";

    @FormUrlEncoded
    @POST(API_SERVER_ORDER_INDEX)
    Observable<OrderManagementModel> indexOrderIndex(@Header(TOKEN_KEY) String header, @Field("type") Object type, @Field("status") Object status, @Field("page") Object page, @Field("size") Object size);

    /**
     * 订单列表：订单详情    or_id  订单id
     */
    String API_SERVER_ORDER_ORDERDETAIL = "/index/order/orderDetail";

    @FormUrlEncoded
    @POST(API_SERVER_ORDER_ORDERDETAIL)
    Observable<OrderDetailsModel> indexOrderDetail(@Header(TOKEN_KEY) String header, @Field("or_id") Object orId);

    /**
     * 订单列表：确认收货    or_id  订单id
     */
    String API_SERVER_ORDER_MAKESURE = "/index/order/makesure";

    @FormUrlEncoded
    @POST(API_SERVER_ORDER_MAKESURE)
    Observable<String> indexOrderMakesure(@Header(TOKEN_KEY) String header, @Field("or_id") Object orId);

    /**
     * 订单列表：申请退款   or_id   订单id； or_son_id   订单 detail 里面的 id；  note   申请原因；content   说明；type 1 退款，2 退款退货，3 换货
     */
    String API_SERVER_ORDER_BACKGOODS = "/index/order/backgoods";

    @FormUrlEncoded
    @POST(API_SERVER_ORDER_BACKGOODS)
    Observable<String> indexOrderBackgoods(@Header(TOKEN_KEY) String header, @Field("or_id") Object orId,
                                           @Field("or_son_id") Object or_son_id, @Field("note") Object note, @Field("content") Object content, @Field("type") Object type);

    /**
     * 上传单张及多张图片与文本
     * 订单列表：申请退货退款、申请换货   or_id   订单id； or_son_id   订单 detail 里面的 id；  note   申请原因；content   说明；type 1 退款，2 退款退货，3 换货   pic
     */
    @Multipart
    @POST(API_SERVER_ORDER_BACKGOODS)
    Observable<String> indexOrderBackgoods(@Header(TOKEN_KEY) String header, @Part List<MultipartBody.Part> body);

    /**
     * 订单列表：退款/售后列表    type：1 ，售后中，2已完成，3 申请失败   page
     */
    String API_SERVER_ORDER_BACKLIST = "/index/order/backlist";

    @FormUrlEncoded
    @POST(API_SERVER_ORDER_BACKLIST)
    Observable<OrderManagementModel> indexOrderBacklist(@Header(TOKEN_KEY) String header, @Field("type") Object type, @Field("page") Object page, @Field("size") Object size);

    /**
     * 订单列表：退款/售后列表，售后详情    back_id    售后列表里面的 id
     */
    String API_SERVER_ORDER_BACKDETAIL = "/index/order/backdetail";

    @FormUrlEncoded
    @POST(API_SERVER_ORDER_BACKDETAIL)
    Observable<ApplyAfterDetailModel> indexOrderBackdetail(@Header(TOKEN_KEY) String header, @Field("back_id") Object backId);

    /**
     * 订单列表：退款/售后列表，填写物流单号     back_id  退货的id ，express_name 物流公司，express_num 物流单号
     * 只传参数back_id 的时候返回已有物流信息
     */
    String API_SERVER_ORDER_BACKEXPRESS = "/index/order/backexpress";

    @FormUrlEncoded
    @POST(API_SERVER_ORDER_BACKEXPRESS)
    Observable<String> indexOrderBackexpress(@Header(TOKEN_KEY) String header, @Field("back_id") Object backId, @Field("express_name") Object expressName, @Field("express_num") Object expressNum);

    /**
     * 基地商品分类：
     */
    String API_SERVER_INDEX_PLACEGOODS = "/index/index/placeGoods";

    @GET(API_SERVER_INDEX_PLACEGOODS)
    Observable<IndexPlaceModel> indexPlaceGoods();

    /**
     * 基地商品分类：分类对应的商品    area_id   区域id
     */
    String API_SERVER_INDEX_AREAGOODS = "/index/index/areagoods";

    @FormUrlEncoded
    @POST(API_SERVER_INDEX_AREAGOODS)
    Observable<IndexPlaceGoodsModel> indexAreaGoods(@Field("area_id") Object areaId, @Field("page") Object page, @Field("size") Object size);

    /**
     * 专供商品：    cate ： 1，人气，2 销量，3 新品 4 价格     type  1：为升序，2为降序
     */
    String API_SERVER_INDEX_SPECIALGOODS = "/index/index/Specialgoods";

    @FormUrlEncoded
    @POST(API_SERVER_INDEX_SPECIALGOODS)
    Observable<IndexPlaceGoodsModel> indexSpecialGoods(@Field("cate") Object cate, @Field("type") Object type, @Field("page") Object page, @Field("size") Object size);

    /**
     * 云仓库列表
     */
    String API_SERVER_ORDER_CLOUDLIST = "/index/order/cloudlist";

    @FormUrlEncoded
    @POST(API_SERVER_ORDER_CLOUDLIST)
    Observable<OrderManagementModel> orderCloudlist(@Header(TOKEN_KEY) String header, @Field("page") Object page, @Field("size") Object size);

    /**
     * 取消订单    参数 or_id
     */
    String API_SERVER_ORDER_CANCEL = "/index/order/cancel";

    @FormUrlEncoded
    @POST(API_SERVER_ORDER_CANCEL)
    Observable<String> orderCancel(@Header(TOKEN_KEY) String header, @Field("or_id") Object orId);

    /**
     * 消息列表：type 1：系统消息，2：商城活动，3：代理消息，4：首页公告弹框
     */
    String API_SERVER_INDEX_NEWSLIST = "/index/index/newslist";

    @FormUrlEncoded
    @POST(API_SERVER_INDEX_NEWSLIST)
    Observable<MessageModel> indexNewslist(@Header(TOKEN_KEY) String header, @Field("type") Object type, @Field("page") Object page, @Field("size") Object size);

    @FormUrlEncoded
    @POST(API_SERVER_INDEX_NEWSLIST)
    Observable<String> indexNewHome(@Header(TOKEN_KEY) String header, @Field("type") Object type, @Field("page") Object page, @Field("size") Object size);

    /**
     * 消息列表：消息详情  参数 id
     */
    String API_SERVER_INDEX_NEWSDETAIL = "/index/index/newsDetail";

    @FormUrlEncoded
    @POST(API_SERVER_INDEX_NEWSDETAIL)
    Observable<MessageDetailModel> indexNewsDetail(@Header(TOKEN_KEY) String header, @Field("id") Object id);

    /**
     * 实名认证：us_card_front_pic 身份证正面；us_card_pic 手持身份证；us_card_reverse_pic 身份证反面；
     * us_name 真实姓名；us_id_card 身份证号；us_tel  绑定手机号；us_addr_code 区域信息；code 验证码
     */
    String API_SERVER_USER_GETREAL = "/index/user/getreal";

    @Multipart
    @POST(API_SERVER_USER_GETREAL)
    Observable<String> userGetreal(@Header(TOKEN_KEY) String header, @Part List<MultipartBody.Part> body);

    /**
     * 绑定支付宝：参数 account
     */
    String API_SERVER_USER_BINDALIPAY = "/index/user/bindAlipay";

    @FormUrlEncoded
    @POST(API_SERVER_USER_BINDALIPAY)
    Observable<String> userBindAlipay(@Header(TOKEN_KEY) String header, @Field("account") Object account);

    /**
     * 绑定云闪付：参数 account
     */
    String API_SERVER_USER_FLASHOVER = "/index/user/flashover";

    @FormUrlEncoded
    @POST(API_SERVER_USER_FLASHOVER)
    Observable<String> userBindFlashover(@Header(TOKEN_KEY) String header, @Field("account") Object account);

    /**
     * 忘记登录密码：us_tel  手机号；code  验证码；us_pwd  新密码
     */
    String API_SERVER_EVERY_FORGET = "/index/Every/forget";

    @FormUrlEncoded
    @POST(API_SERVER_EVERY_FORGET)
    Observable<String> userEveryForget(@Header(TOKEN_KEY) String header, @Field("us_tel") Object usTel, @Field("code") Object code, @Field("us_pwd") Object usPwd);

    /**
     * 修改登录密码：us_tel  手机号；code  验证码；us_pwd  新密码
     */
    String API_SERVER_USER_CHANGEPWD = "/index/user/changePwd";

    @FormUrlEncoded
    @POST(API_SERVER_USER_CHANGEPWD)
    Observable<String> userChangePwd(@Header(TOKEN_KEY) String header, @Field("us_tel") Object usTel, @Field("code") Object code, @Field("us_pwd") Object usPwd);

    /**
     * 修改支付密码：us_tel  手机号；code  验证码；us_safe_pwd  新支付密码
     */
    String API_SERVER_USER_CHANGESAFE = "/index/user/changeSafe";

    @FormUrlEncoded
    @POST(API_SERVER_USER_CHANGESAFE)
    Observable<String> userChangeSafe(@Header(TOKEN_KEY) String header, @Field("us_tel") Object usTel, @Field("code") Object code, @Field("us_safe_pwd") Object usSafePwd);

    /**
     * 注册：us_tel  手机号；ptel  推荐人；us_pwd  密码； code  验证码
     */
    String API_SERVER_EVERY_REGISTER = "/index/every/register";

    @FormUrlEncoded
    @POST(API_SERVER_EVERY_REGISTER)
    Observable<String> everyRegister(@Header(TOKEN_KEY) String header, @Field("ptel") Object pTel, @Field("us_nickname") Object usNickname, @Field("us_tel") Object usTel, @Field("code") Object code, @Field("us_pwd") Object usPwd);

    /**
     * 绑定银行卡：bank_account 银行卡号；us_bank  银行名称；us_name  真实姓名；us_id_card 身份证号；us_tel  手机号；code  验证码；
     */
    String API_SERVER_USER_BINDBANK = "/index/user/blindBank";

    @FormUrlEncoded
    @POST(API_SERVER_USER_BINDBANK)
    Observable<String> userBindBank(@Header(TOKEN_KEY) String header, @Field("us_name") Object usName,
                                    @Field("us_id_card") Object usIdCard, @Field("bank_account") Object bankAccount,
                                    @Field("us_bank") Object usBank, @Field("us_tel") Object usTel, @Field("code") Object code);

    /**
     * 银行卡信息：_input_charset=utf-8    cardBinCheck=true    cardNo=cardNo
     */
    @FormUrlEncoded
    @POST(BankCardUtil.ALIPAY_CARD_INFO)
    Observable<BankCardInfoModel> cardInfo(@Field("_input_charset") Object inputCharset, @Field("cardBinCheck") Object cardBinCheck, @Field("cardNo") Object cardNo);

    /**
     * 主页搜索：keyword  搜索词；
     */
    String API_SERVER_INDEX_SEARCHGOODS = "/index/index/searchgoods";

    @FormUrlEncoded
    @POST(API_SERVER_INDEX_SEARCHGOODS)
    Observable<IndexSearchModel> indexSearchGoods(@Header(TOKEN_KEY) String header, @Field("keyword") Object keyword, @Field("cate") Object cate, @Field("type") Object type, @Field("page") Object page, @Field("size") Object size);

    /**
     * 发现资讯搜索：keyword  搜索词；
     */
    String API_SERVER_INDEX_SEARCHM = "/index/index/searchm";

    @FormUrlEncoded
    @POST(API_SERVER_INDEX_SEARCHM)
    Observable<FindPageModel> indexSearchm(@Header(TOKEN_KEY) String header, @Field("keyword") Object keyword);

    /**
     * 订单搜索：keyword  订单号或者商品名称，搜索
     */
    String API_SERVER_ORDER_SEARCH = "/index/order/search";

    @FormUrlEncoded
    @POST(API_SERVER_ORDER_SEARCH)
    Observable<OrderManagementModel> orderSearch(@Header(TOKEN_KEY) String header, @Field("keyword") Object keyword, @Field("type") Object type);

    /**
     * 客服与帮助：id
     */
    String API_SERVER_INDEX_HELP = "/index/index/help";

    @FormUrlEncoded
    @POST(API_SERVER_INDEX_HELP)
    Observable<String> indexHelp(@Header(TOKEN_KEY) String header, @Field("id") Object id);

    /**
     * 代理等级：id
     */
    String API_SERVER_INDEX_AGENCYLEVEL = "/index/index/agencylevel";

    @GET(API_SERVER_INDEX_AGENCYLEVEL)
    Observable<RankFarmAgentModel> indexAgencylevel(@Header(TOKEN_KEY) String header);

    /**
     * 预售商品：
     */
    String API_SERVER_INDEX_WILLGOODS = "/index/index/willgoods";

    @FormUrlEncoded
    @POST(API_SERVER_INDEX_WILLGOODS)
    Observable<IndexPlaceGoodsModel> indexWillgoods(@Header(TOKEN_KEY) String header, @Field("page") Object page, @Field("size") Object size);

    /**
     * 我的宝粉：
     */
    String API_SERVER_USER_MYTEAM = "/index/user/myTeam";

    @FormUrlEncoded
    @POST(API_SERVER_USER_MYTEAM)
    Observable<PowderModel> userMyTeam(@Header(TOKEN_KEY) String header, @Field("page") Object page, @Field("size") Object size);

    /**
     * 我的优惠券：
     */
    String API_SERVER_USER_MYCOUPON = "/index/user/mycoupon";

    @FormUrlEncoded
    @POST(API_SERVER_USER_MYCOUPON)
    Observable<CouponModel> userMyCoupon(@Header(TOKEN_KEY) String header, @Field("status") Object status, @Field("page") Object page, @Field("size") Object size);

    /**
     * 好友转让：account  账号；num  转让数量；password  密码
     */
    String API_SERVER_USER_TURNTO = "/index/user/turnto";

    @FormUrlEncoded
    @POST(API_SERVER_USER_TURNTO)
    Observable<String> userTurnto(@Header(TOKEN_KEY) String header, @Field("type") Object type, @Field("num") Object num, @Field("account") Object account, @Field("password") Object password);

    /**
     * 好友转让：兑换信息
     */
    String API_SERVER_USER_EXCHANGE = "/index/user/exchange";

    @GET(API_SERVER_USER_EXCHANGE)
    Observable<PersonalTransferFriendsModel> userExchange(@Header(TOKEN_KEY) String header);

    /**
     * 好友转让：兑换
     */
    String API_SERVER_USER_GRBTOGRA = "/index/user/grbtogra";

    @FormUrlEncoded
    @POST(API_SERVER_USER_GRBTOGRA)
    Observable<String> userExchangeGRBToGRA(@Header(TOKEN_KEY) String header, @Field("type") Object type, @Field("account") Object account, @Field("password") Object password);

    /**
     * 好友转让：兑换
     */
    String API_SERVER_USER_CASHTOGRA = "/index/user/cashtogra";

    @FormUrlEncoded
    @POST(API_SERVER_USER_CASHTOGRA)
    Observable<String> userExchangeCashToGRA(@Header(TOKEN_KEY) String header, @Field("type") Object type, @Field("account") Object account, @Field("password") Object password);

    /**
     * 个人中心：
     */
    String API_SERVER_USER_MYHEAD = "/index/user/myHead";

    @GET(API_SERVER_USER_MYHEAD)
    Observable<PersonalHeadModel> userMyHead(@Header(TOKEN_KEY) String header);

    /**
     * 发布商品评价：订单商品评论： or_id 订单id；or_son_id  订单detail里面id；content 评论内容； level 评价等级：1好评，2中评，3差评  pic  图片数组  anonymous 1：匿名 2显示名字
     */
    String API_SERVER_ORDER_COMMENT = "/index/order/comment";

    @Multipart
    @POST(API_SERVER_ORDER_COMMENT)
    Observable<String> orderComment(@Header(TOKEN_KEY) String header, @Part List<MultipartBody.Part> body);

    /**
     * 我的评价：
     */
    String API_SERVER_USER_MYCOMMENT = "/index/user/myComment";

    @FormUrlEncoded
    @POST(API_SERVER_USER_MYCOMMENT)
    Observable<PersonalEvaluationModel> userMyComment(@Header(TOKEN_KEY) String header, @Field("page") Object page, @Field("size") Object size);

    /**
     * 我的评价：删除我的评论   c_id   评论id
     */
    String API_SERVER_USER_DELCOMMENT = "/index/user/delComment";

    @FormUrlEncoded
    @POST(API_SERVER_USER_DELCOMMENT)
    Observable<String> userDelComment(@Header(TOKEN_KEY) String header, @Field("c_id") Object cId);

    /**
     * 充值记录：
     */
    String API_SERVER_USER_RECHARGEINDEX = "/index/user/RechargeIndex";

    @FormUrlEncoded
    @POST(API_SERVER_USER_RECHARGEINDEX)
    Observable<PersonalRechargeRecordModel> userRechargeIndex(@Header(TOKEN_KEY) String header, @Field("page") Object page, @Field("size") Object size);

    /**
     * 订单支付： 参数： or_id  订单id； pwd  安全密码；  type：支付方式数组  1 现金余额，2 GRB，3 支付宝 4 微信 5 云闪付
     */
    String API_SERVER_ORDER_SUBORDERTOPAY = "/index/Orderpay/subOrderToPay";/*"/index/order/subOrderToPay";*/

    @FormUrlEncoded
    @POST(API_SERVER_ORDER_SUBORDERTOPAY)
    Observable<String> orderSubOrderToPay(@Header(TOKEN_KEY) String header, @Field("or_id") Object orId, @Field("pwd") Object pwd, @Field("type[]") List<Object> type);

    /**
     * 商品评价： id  商品id；level    1好评，2中评，3差评;  page  页码
     */
    String API_SERVER_INDEX_GOODSCOMMENT = "/index/index/goodsComment";

    @FormUrlEncoded
    @POST(API_SERVER_INDEX_GOODSCOMMENT)
    Observable<ProductEvaluationModel> indexGoodsComment(@Header(TOKEN_KEY) String header, @Field("id") Object id, @Field("level") Object level, @Field("page") Object page, @Field("size") Object size);

    /**
     * 我的足迹：清除浏览历史
     */
    String API_SERVER_USER_CLEAN = "/index/user/clean";

    @GET(API_SERVER_USER_CLEAN)
    Observable<String> userClean(@Header(TOKEN_KEY) String header);

    /**
     * 抽奖主页：获取抽奖消息   new_num   index里面的new_num;
     */
    String API_SERVER_LOTTERY_GETNEWS = "/index/lottery/getnews";

    @FormUrlEncoded
    @POST(API_SERVER_LOTTERY_GETNEWS)
    Observable<String> lotteryGetnews(@Header(TOKEN_KEY) String header, @Field("new_num") Object newNum);

    /**
     * 抽奖主页：奖品领取   id  奖品id；name  姓名；tel  电话； address  地址；
     */
    String API_SERVER_LOTTERY_RECEIVE = "/index/lottery/receive";

    @FormUrlEncoded
    @POST(API_SERVER_LOTTERY_RECEIVE)
    Observable<String> lotteryReceive(@Header(TOKEN_KEY) String header, @Field("id") Object id, @Field("name") Object name, @Field("tel") Object tel, @Field("address") Object address);

    /**
     * 云仓库：提货记录
     */
    String API_SERVER_ORDER_TAKEGOODS = "/index/order/takegoods";

    @FormUrlEncoded
    @POST(API_SERVER_ORDER_TAKEGOODS)
    Observable<CloudWarehouseRecordModel> orderTakegoods(@Header(TOKEN_KEY) String header, @Field("page") Object page, @Field("size") Object size);

    /**
     * 收益明细：农场代理区奖
     */
    String API_SERVER_USER_FARMAGENT = "/index/user/farmAgent";

    @GET(API_SERVER_USER_FARMAGENT)
    Observable<PersonalIncomeModel> userFarmAgent(@Header(TOKEN_KEY) String header);

    /**
     * 收益明细：农场代理区奖：收益明细   参数 type  根据农场代理区奖 传值
     */
    String API_SERVER_USER_FARMLIST = "/index/user/farmlist";

    @FormUrlEncoded
    @POST(API_SERVER_USER_FARMLIST)
    Observable<PersonalIncomeRecordModel> userFarmList(@Header(TOKEN_KEY) String header, @Field("type") Object type, @Field("page") Object page, @Field("size") Object size);

    /**
     * 修改昵称：   参数 us_nickname
     */
    String API_SERVER_USER_CHANGENICK = "/index/user/changeNick";

    @FormUrlEncoded
    @POST(API_SERVER_USER_CHANGENICK)
    Observable<String> userChangeNick(@Header(TOKEN_KEY) String header, @Field("us_nickname") Object usNickname);

    /**
     * 修改用户图像   参数 us_head_pic 文件类型
     */
    String API_SERVER_USER_CHANGEHEAD = "/index/user/changeHead";

    @Multipart
    @POST(API_SERVER_USER_CHANGEHEAD)
    Observable<String> userChangeHead(@Header(TOKEN_KEY) String header, @Part List<MultipartBody.Part> body);

    /**
     * 绑定手机号：   us_tel   code
     */
    String API_SERVER_USER_BLINDTEL = "/index/user/blindTel";

    @FormUrlEncoded
    @POST(API_SERVER_USER_BLINDTEL)
    Observable<String> userBlindTel(@Header(TOKEN_KEY) String header, @Field("us_tel") Object usTel, @Field("code") Object code);

    /**
     * 删除我的收藏：参数 id
     */
    String API_SERVER_USER_DELCOLL = "/index/user/delcoll";

    @FormUrlEncoded
    @POST(API_SERVER_USER_DELCOLL)
    Observable<String> userDelcoll(@Header(TOKEN_KEY) String header, @Field("id") Object id);

    /**
     * 清空已使用优惠券：
     */
    String API_SERVER_USER_CLEANCOPON = "/index/user/cleanCopon";

    @GET(API_SERVER_USER_CLEANCOPON)
    Observable<String> userCleanCopon(@Header(TOKEN_KEY) String header);

    /**
     * 基地：产品搜索，可以搜索产品名称和地区 keyword
     */
    String API_SERVER_INDEX_SEARCHPLACE = "/index/index/searchPlace";

    @FormUrlEncoded
    @POST(API_SERVER_INDEX_SEARCHPLACE)
    Observable<IndexPlaceGoodsModel> indexSearchPlace(@Header(TOKEN_KEY) String header, @Field("keyword") Object keyword, @Field("page") Object page, @Field("size") Object size);

    /**
     * 谷聚金：代理等级介绍
     */
    String API_SERVER_VALLEY_AGENT = "/index/valley/agent";

    @GET(API_SERVER_VALLEY_AGENT)
    Observable<AgentLevelModel> indexValleyAgent(@Header(TOKEN_KEY) String header);

    /**
     * 谷聚金：详情   跟商品详情一样   不用传参数
     */
    String API_SERVER_VALLEY_GOODS = "/index/valley/goods";

    @GET(API_SERVER_VALLEY_GOODS)
    Observable<ProductDetailsModel> indexValleyGoods(@Header(TOKEN_KEY) String header);

    /**
     * 谷聚金：申请代理   手机号检测获取推荐人  参数us_tel
     */
    String API_SERVER_VALLEY_GETNAME = "/index/valley/getname";

    @FormUrlEncoded
    @POST(API_SERVER_VALLEY_GETNAME)
    Observable<String> indexValleyGetName(@Header(TOKEN_KEY) String header, @Field("us_tel") Object usTel);

    /**
     * 谷聚金：申请代理   选择级别
     */
    String API_SERVER_VALLEY_CONTENT = "/index/valley/content";

    @GET(API_SERVER_VALLEY_CONTENT)
    Observable<String> indexValleyContent(@Header(TOKEN_KEY) String header);

    /**
     * 谷聚金：申请代理   填写资料   type 代理级别  us_tel 手机号  code 验证码  name 姓名  card_id 省份证  wechat 微信   sex 性别   birthday 出生年月  province_code city_code town_code 地区
     */
    String API_SERVER_VALLEY_APPLY = "/index/valley/apply";

    @FormUrlEncoded
    @POST(API_SERVER_VALLEY_APPLY)
    Observable<String> indexValleyApply(@Header(TOKEN_KEY) String header, @Field("type") Object type,
                                        @Field("us_tel") Object usTel, @Field("code") Object code,
                                        @Field("name") Object name, @Field("card_id") Object cardId,
                                        @Field("wechat") Object wechat, @Field("sex") Object sex,
                                        @Field("birthday") Object birthday, @Field("province_code") Object provinceCode,
                                        @Field("city_code") Object cityCode, @Field("town_code") Object townCode);

    /**
     * 谷聚金：申请代理   首次操作
     */
    String API_SERVER_VALLEY_SHOW = "/index/valley/show";

    @FormUrlEncoded
    @POST(API_SERVER_VALLEY_SHOW)
    Observable<String> indexValleyShow(@Header(TOKEN_KEY) String header, @Field("id") Object id);

    /**
     * 谷聚金：申请代理   首次操作    省、市、区/县代理 申请支付  a_id  是index/valley/apply 申请成功时返回的id；v_id  是index/valley/show 这个接口返回的id；password   支付密码   支付方式  type  数组类型
     */
    String API_SERVER_VALLEY_APPLYPAY = "/index/valley/applyPay";

    @FormUrlEncoded
    @POST(API_SERVER_VALLEY_APPLYPAY)
    Observable<String> indexValleyApplyPay(@Header(TOKEN_KEY) String header, @Field("a_id") Object aId,
                                           @Field("v_id") Object vId, @Field("password") Object password, @Field("type[]") List<Object> type);

    /**
     * 谷聚金：申请代理   首次操作   个人代理 结算页面
     */
    String API_SERVER_VALLEY_SETTLEMENT = "/index/valley/settlement";

    @GET(API_SERVER_VALLEY_SETTLEMENT)
    Observable<SettlementModel> indexValleySettlement(@Header(TOKEN_KEY) String header);

    /**
     * 谷聚金：申请代理   首次操作   个人代理 下单
     */
    String API_SERVER_VALLEY_ADDORDER = "/index/valley/addOrder";

    @FormUrlEncoded
    @POST(API_SERVER_VALLEY_ADDORDER)
    Observable<String> indexValleyAddOrder(@Header(TOKEN_KEY) String header, @Field("pd_id") Object pdId,
                                           @Field("sku_id") Object skuId, @Field("pd_num") Object pdNum,
                                           @Field("type") Object type, @Field("type4") Object type4,
                                           @Field("addr_id") Object addrId);

    /**
     * 谷聚金：进货 下单  发货人 type3 1 ：平台发货；2：区县发货
     */
    @FormUrlEncoded
    @POST(API_SERVER_VALLEY_ADDORDER)
    Observable<String> indexValleyPurchaseAddOrder(@Header(TOKEN_KEY) String header, @Field("pd_id") Object pdId,
                                                   @Field("sku_id") Object skuId, @Field("pd_num") Object pdNum,
                                                   @Field("type") Object type, @Field("type3") Object type3, @Field("type4") Object type4,
                                                   @Field("addr_id") Object addrId);

    /**
     * 谷聚金：申请代理   首次操作   个人代理 支付   or_id  订单id； type  支付方式（数组）；pwd  支付密码；
     */
    String API_SERVER_VALLEY_ORDERPAY = "/index/valley/orderPay";

    @FormUrlEncoded
    @POST(API_SERVER_VALLEY_ORDERPAY)
    Observable<String> indexValleyOrderPay(@Header(TOKEN_KEY) String header, @Field("or_id") Object orId, @Field("pwd") Object pwd, @Field("type[]") List<Object> type);

    /**
     * 谷聚金：订单列表   status 订单状态；type3   1:总部发货 2：县代发货；page  页码
     */
    String API_SERVER_VALLEY_ORDER = "/index/valley/order";

    @FormUrlEncoded
    @POST(API_SERVER_VALLEY_ORDER)
    Observable<OrderManagementModel> indexValleyOrder(@Header(TOKEN_KEY) String header, @Field("type3") Object type, @Field("status") Object status, @Field("page") Object page, @Field("size") Object size);

    /**
     * 谷聚金：订单列表   上传凭证  订单信息
     */
    String API_SERVER_ORDER_TOTAL = "/index/order/ordertotal";

    @FormUrlEncoded
    @POST(API_SERVER_ORDER_TOTAL)
    Observable<String> indexOrderTotal(@Header(TOKEN_KEY) String header, @Field("or_id") Object orId);

    /**
     * 谷聚金：订单列表   上传凭证  上传支付凭证  or_num   or_voucher
     */
    String API_SERVER_ORDER_UPLOAD = "/index/order/uploadOrder";

    @Multipart
    @POST(API_SERVER_ORDER_UPLOAD)
    Observable<String> indexOrderUpload(@Header(TOKEN_KEY) String header, @Part List<MultipartBody.Part> body);

    /**
     * 谷聚金：发货列表
     */
    String API_SERVER_ORDER_SENDLIST = "/index/order/sendlist";

    @FormUrlEncoded
    @POST(API_SERVER_ORDER_SENDLIST)
    Observable<OrderManagementModel> indexOrderSendlist(@Header(TOKEN_KEY) String header, @Field("or_status") Object orStatus, @Field("page") Object page, @Field("size") Object size);

    /**
     * 谷聚金：发货功能    参数：  or_id  订单id ; pd_id 商品；or_pd_num   商品数量
     */
    String API_SERVER_ORDER_SENDPRODUCT = "/index/order/sendproduct";

    @FormUrlEncoded
    @POST(API_SERVER_ORDER_SENDPRODUCT)
    Observable<String> indexOrderSendProduct(@Header(TOKEN_KEY) String header, @Field("or_id") Object orId, @Field("pd_id") Object pdId, @Field("or_pd_num") Object orPdNum);

    /**
     * 个人中心：充值    num  充值金额；type 支付类型 3 支付宝，4 微信，5 云闪付，68 测试直接充值
     */
    String API_SERVER_USER_PAY_RECHARGE = "/index/user/pay_recharge";

    @FormUrlEncoded
    @POST(API_SERVER_USER_PAY_RECHARGE)
    Observable<String> userPayRecharge(@Header(TOKEN_KEY) String header, @Field("num") Object money, @Field("type") Object type);

    /**
     * 公共市场转让：获取 GRB 可兑换 GRA 金额
     */
    String API_SERVER_USER_RATIO = "/index/User/ratio";

    @GET(API_SERVER_USER_RATIO)
    Observable<UserRatioModel> userRatio(@Header(TOKEN_KEY) String header);

    /**
     * 公共市场转让：公共市场转出      address：钱包地址      gra：转出的GRA数量
     */
    String API_SERVER_USER_WITHDRAW = "/index/User/withdraw";

    @FormUrlEncoded
    @POST(API_SERVER_USER_WITHDRAW)
    Observable<String> userWithdraw(@Header(TOKEN_KEY) String header, @Field("gra") Object money, @Field("address") Object address);

    /**
     * 公共市场转让：获取钱包地址     address  ：地址文本      iban   ：二维码文本
     */
    String API_SERVER_USER_ETHADDRESS = "/index/User/ethAddress";

    @GET(API_SERVER_USER_ETHADDRESS)
    Observable<String> userEthAddress(@Header(TOKEN_KEY) String header);

    /**
     * 实时查询用户余额：
     */
    String API_SERVER_USER_MYLAST = "/index/user/mylast";

    @GET(API_SERVER_USER_MYLAST)
    Observable<String> userMyLast(@Header(TOKEN_KEY) String header);

    /**
     * 会议报名记录：
     */
    String API_SERVER_MEETING_MYMEETING = "/index/meeting/mymeeting";

    @GET(API_SERVER_MEETING_MYMEETING)
    Observable<CentreRecordModel> userMeeting(@Header(TOKEN_KEY) String header);

    /**
     * 资讯评论：a_id   咨询id，coutent   内容
     */
    String API_SERVER_USER_ADDCOMMENT = "/index/user/addComment";

    @FormUrlEncoded
    @POST(API_SERVER_USER_ADDCOMMENT)
    Observable<String> userAddComment(@Header(TOKEN_KEY) String header, @Field("a_id") Object aId, @Field("coutent") Object content);

    /**
     * 资讯评论：点赞  id  : 评论id
     */
    String API_SERVER_USER_THUMB = "/index/user/thumb";

    @FormUrlEncoded
    @POST(API_SERVER_USER_THUMB)
    Observable<String> userThumb(@Header(TOKEN_KEY) String header, @Field("id") Object id);

    /**
     * 绑定微信账号
     */
    String API_SERVER_USER_BINDWECHAT = "/index/user/bindWechat";

    @FormUrlEncoded
    @POST(API_SERVER_USER_BINDWECHAT)
    Observable<String> userBindWechat(@Header(TOKEN_KEY) String header, @Field("type") Object type, @Field("account") Object data);

    /**
     * 限时抢购：设置提醒  RegistrationId：注册id，pd_id :商品id；us_id :用户id
     */
    String API_SERVER_NEWS_REMIND = "/index/news/remind";

    @FormUrlEncoded
    @POST(API_SERVER_NEWS_REMIND)
    Observable<String> newsRemind(@Header(TOKEN_KEY) String header, @Field("RegistrationId") Object registrationId,
                                  @Field("pd_id") Object pdId, @Field("us_id") Object usId);

    /**
     * 客服与帮助：
     */
    String API_SERVER_INDEX_GETINFO = "/index/index/getinfo";

    @GET(API_SERVER_INDEX_GETINFO)
    Observable<String> appGetInfo();

    /**
     * APP 版本控制：
     */
    String API_SERVER_APP_CONTROL = "/game/Every/appControl";

    @GET(API_SERVER_APP_CONTROL)
    Observable<String> appControl();

    /**
     * 发送验证码接口：us_tel  手机号；code  验证码；us_safe_pwd  新支付密码
     */
    String API_SERVER_EVERY_SEND = "/index/every/send";

    @FormUrlEncoded
    @POST(API_SERVER_EVERY_SEND)
    Observable<String> everySend(@Header(TOKEN_KEY) String header, @Field("us_tel") Object usTel);

    /**
     * 城市列表
     */
    String API_SERVER_EVERY_GETREGION = "/index/every/getregion";

    @GET(API_SERVER_EVERY_GETREGION)
    Observable<String> everyGetRegion();

}
