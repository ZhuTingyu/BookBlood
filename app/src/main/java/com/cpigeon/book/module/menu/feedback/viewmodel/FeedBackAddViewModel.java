package com.cpigeon.book.module.menu.feedback.viewmodel;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.base.util.Lists;
import com.cpigeon.book.model.FeedbackModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;

/**
 * Created by Zhu TingYu on 2018/8/23.
 */

public class FeedBackAddViewModel extends BaseViewModel {
    public List<String> images = Lists.newArrayList();
    String phone;
    String content;
    //获取  意见反馈 提交
    public void addFadeBack() {
        submitRequestThrowError(FeedbackModel.getZGW_Users_Feedback_Add(content, phone, setImageMap()), r -> {
            if (r.isOk()) {
                normalResult.setValue(r.msg);
            } else throw new HttpErrorException(r);
        });
    }


    public HashMap<String,String> setImageMap() {
        HashMap<String, String> map = new HashMap<>();
        for (int i = 0,len = images.size(); i < len; i++) {
             map.put("pic"+ i, images.get(i));
        }
        return map;
    }

    public Consumer<String> setPhone(){
        return s -> {
          phone = s;
        };
    }
    public Consumer<String> setContent(){
        return s -> {
            content = s;
        };
    }

}
