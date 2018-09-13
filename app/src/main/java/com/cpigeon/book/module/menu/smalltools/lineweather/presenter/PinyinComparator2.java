package com.cpigeon.book.module.menu.smalltools.lineweather.presenter;


import com.base.util.utility.StringUtil;
import com.cpigeon.book.module.menu.smalltools.lineweather.model.bean.ContactModel;

import java.util.Comparator;

/**
 * 
 * @author xiaanming
 *
 */
public class PinyinComparator2 implements Comparator<ContactModel.MembersEntity> {

	public int compare(ContactModel.MembersEntity o1, ContactModel.MembersEntity o2) {
		if(StringUtil.isStringValid(o1.getSortLetters()) && StringUtil.isStringValid(o2.getSortLetters())){
			if (o1.getSortLetters().equals("@")
					|| o2.getSortLetters().equals("#")) {
				return -1;
			} else if (o1.getSortLetters().equals("#")
					|| o2.getSortLetters().equals("@")) {
				return 1;
			} else {
				return o1.getSortLetters().compareTo(o2.getSortLetters());
			}
		}else {
			return -1;
		}

	}

}
