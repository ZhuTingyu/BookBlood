package com.cpigeon.book.test;

import com.base.http.ApiResponse;
import com.base.http.RequestUtil;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Zhu TingYu on 2018/3/2.
 */

public class NumberListModel {
    public static Observable<ApiResponse<List<String>>> getNumber(int page){
        return RequestUtil.<ApiResponse<List<String>>>builder()
                .setToJsonType(new TypeToken<ApiResponse<List<String>>>(){}.getType())
                .addHead("u","KZvtq42TJnoAs/p7JDqLnY16yYcRtK7o2JtPg1WR9mZrFI/WO+qOP84FzmGjZzkgxjrdDPpWNqRQVH0bB6oJtw==")
                .setBaseUrl("http://114.141.132.146:818")
                .headUrl("/CPAPI/V1/")
                .url("SGT_GetUserList")
                .addBody("pi",String.valueOf(page))
                .addBody("ps",String.valueOf(5))
                .request();
    }
}
