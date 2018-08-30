//package com.base.util;
//
//import android.app.Activity;
//
//import com.umeng.socialize.ShareAction;
//import com.umeng.socialize.bean.SHARE_MEDIA;
//import com.umeng.socialize.media.UMImage;
//import com.umeng.socialize.media.UMWeb;
//
///**分享工具
// * Created by Zhu TingYu on 2018/3/2.
// */
//
//public class ShareManager {
//
//
//    private static final String TITLE = "中鸽网";
//    public static final String CONTENT_TYPE_URL = "CONTENT_TYPE_URL";
//
//    public static final String CONTENT_TYPE_IMAGE = "CONTENT_TYPE_IMAGE";
//
//    public static final SHARE_MEDIA PLATFORM_TYPE_QQ = SHARE_MEDIA.QQ;
//
//    public static final SHARE_MEDIA PLATFORM_TYPE_WX = SHARE_MEDIA.WEIXIN;
//
//    private String contentType;
//    private String shareContent;
//    private String description;
//
//    private static ShareAction shareAction;
//
//    private static Activity mActivity;
//
//
//    public static ShareManager builder(Activity activity){
//        ShareManager shareManager = new ShareManager();
//        shareAction = new ShareAction(activity);
//        mActivity = activity;
//        return shareManager;
//    }
//
//    /**
//     * 设置分享平台
//     * @param platformType
//     * @return
//     */
//
//    public ShareManager setPlatformType(SHARE_MEDIA platformType){
//        shareAction.setPlatform(platformType);
//        return this;
//    }
//
//    /**
//     * 分享内容的类型
//     * @param contentType
//     * @return
//     */
//
//    public ShareManager setPcontentType(String contentType){
//        this.contentType = contentType;
//        return this;
//    }
//
//    /**
//     * 分享内容的描述
//     * @param description
//     * @return
//     */
//
//    public ShareManager setDescription(String description){
//        this.description = description;
//        return this;
//    }
//
//    /**
//     * 分享的内容
//     * @param description
//     * @return
//     */
//
//    public ShareManager setShareContent(String description){
//        this.description = description;
//        return this;
//    }
//
//
//    public void share(){
//        if(CONTENT_TYPE_URL.equals(contentType)){
//            UMWeb web = new UMWeb(shareContent);
//            web.setTitle(TITLE);//标题
//            web.setDescription(description);//描述
//            shareAction.withMedia(web);
//        }else if(CONTENT_TYPE_IMAGE.equals(contentType)){
//            UMImage image = new UMImage(mActivity, shareContent);//网络图片
//            image.setTitle(TITLE);
//            shareAction.withText(description);
//            shareAction.withMedia(image);
//        }
//
//        shareAction.share();
//    }
//
//}
