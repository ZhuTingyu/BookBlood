package com.cpigeon.book.module.home.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.base.base.BaseViewModel;
import com.base.http.HttpErrorException;
import com.cpigeon.book.model.IdCardModel;
import com.cpigeon.book.model.entity.UserIdCardEntity;

import io.reactivex.functions.Consumer;

/**
 * Created by Zhu TingYu on 2018/8/9.
 */

public class IdCertificationViewModel extends BaseViewModel {
    public String idCardP;
    public String idCardN;
    private String name;
    private String sex;
    private String nation;
    private String address;
    private String cardNumber;
    private String authority;
    private String inDate;

    public MutableLiveData<UserIdCardEntity> mUserIdCardLiveData = new MutableLiveData<>();

    public void setUserIdCard(){
        submitRequestThrowError(IdCardModel.setUserIdCard(idCardP,idCardN,name,sex,nation,address,cardNumber
                ,authority, inDate),r -> {
            if(r.isOk()){
                normalResult.setValue(r.msg);
            }else throw new HttpErrorException(r);
        });
    }

    public void getUserIdCard(){
        submitRequestThrowError(IdCardModel.getUserIdCard(),r -> {
            if(r.isOk()){
                mUserIdCardLiveData.setValue(r.data);
            }else throw new HttpErrorException(r);
        });
    }


    public Consumer<String>  setName() {
        return s -> {
          this.name = s;
          isCanCommit(name, cardNumber, sex, nation, address, authority, inDate);
        };
    }

    public Consumer<String> setSex() {
        return s -> {
            this.sex = s;
            isCanCommit(name, cardNumber, sex, nation, address, authority, inDate);
        };
    }

    public Consumer<String> setNation() {
        return s -> {
            this.nation = s;
            isCanCommit(name, cardNumber, sex, nation, address, authority, inDate);
        };
    }

    public Consumer<String> setAddress() {
        return s -> {
            this.address = s;
            isCanCommit(name, cardNumber, sex, nation, address, authority, inDate);
        };
    }

    public Consumer<String> setCardNumber() {
        return s -> {
            this.cardNumber = s;
            isCanCommit(name, cardNumber, sex, nation, address, authority, inDate);
        };
    }

    public Consumer<String> setAuthority() {
        return s -> {
            this.authority = s;
            isCanCommit(name, cardNumber, sex, nation, address, authority, inDate);
        };
    }

    public Consumer<String> setInDate() {
        return s -> {
            this.inDate = s;
            isCanCommit(name, cardNumber, sex, nation, address, authority, inDate);
        };
    }
}
