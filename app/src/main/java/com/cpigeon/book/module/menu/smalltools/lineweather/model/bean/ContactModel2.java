/**
 * created by jiang, 16/2/2
 * Copyright (c) 2016, jyuesong@gmail.com All Rights Reserved.
 * *                #                                                   #
 * #                       _oo0oo_                     #
 * #                      o8888888o                    #
 * #                      88" . "88                    #
 * #                      (| -_- |)                    #
 * #                      0\  =  /0                    #
 * #                    ___/`---'\___                  #
 * #                  .' \\|     |# '.                 #
 * #                 / \\|||  :  |||# \                #
 * #                / _||||| -:- |||||- \              #
 * #               |   | \\\  -  #/ |   |              #
 * #               | \_|  ''\---/''  |_/ |             #
 * #               \  .-\__  '-'  ___/-. /             #
 * #             ___'. .'  /--.--\  `. .'___           #
 * #          ."" '<  `.___\_<|>_/___.' >' "".         #
 * #         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       #
 * #         \  \ `_.   \_ __\ /__ _/   .-` /  /       #
 * #     =====`-.____`.___ \_____/___.-`___.-'=====    #
 * #                       `=---='                     #
 * #     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   #
 * #                                                   #
 * #               佛祖保佑         永无BUG              #
 * #                                                   #
 */

package com.cpigeon.book.module.menu.smalltools.lineweather.model.bean;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiang on 16/2/2.
 */
public class ContactModel2 {


    private List<MembersEntity2> members = new ArrayList<>();

    public void setMembers(List<MembersEntity2> members) {
        this.members = members;
    }


    public List<MembersEntity2> getMembers() {
        return members;
    }

    public static class MembersEntity2<T> implements Indexable, Serializable {

        private String id;

        private String username;

        private String mobile;

        private T data;

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public boolean isChooseVisible;

        public boolean isChoose;

        public String getSortLetters() {
            return sortLetters;
        }

        @Override
        public String getIndex() {
            return sortLetters;
        }

        public void setSortLetters(String sortLetters) {
            this.sortLetters = sortLetters;
        }

        private String sortLetters;

        public void setId(String id) {
            this.id = id;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getId() {
            return id;
        }

        public String getUsername() {
            return username;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public boolean isChooseVisible() {
            return isChooseVisible;
        }

        public void setChooseVisible(boolean chooseVisible) {
            isChooseVisible = chooseVisible;
        }

        public boolean isChoose() {
            return isChoose;
        }

        public void setChoose(boolean choose) {
            isChoose = choose;
        }
    }
}
