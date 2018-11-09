package com.base.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;

import com.base.BaseFragment;
import com.base.FragmentParentActivity;

import com.base.http.R;

import java.io.Serializable;
import java.util.ArrayList;


public class IntentBuilder {

    public static final String KEY_TYPE = "KEY_TYPE";
    public static final String KEY_TYPE_2 = "KEY_TYPE_2";
    public static final String KEY_TITLE = "KEY_TITLE";
    public static final String KEY_BOOLEAN = "KEY_BOOLEAN";
    public static final String KEY_BOOLEAN_2 = "KEY_BOOLEAN_2";
    public static final String KEY_DATA = "KEY_DATA";
    public static final String KEY_DATA_2 = "KEY_DATA_2";
    public static final String KEY_DATA_3 = "KEY_DATA_3";


    private Intent intent;

    private Context mContext;

    private int enter = R.anim.anim_right_in;
    private int exit = R.anim.anim_left_out;


    public static IntentBuilder Builder() {
        return new IntentBuilder();
    }

    public static IntentBuilder Builder(Intent intent) {
        return new IntentBuilder(intent);
    }

    public static IntentBuilder Builder(String action) {
        return new IntentBuilder(action);
    }

    public static IntentBuilder Builder(String action, Uri uri) {
        return new IntentBuilder(action, uri);
    }

    public static IntentBuilder Builder(Context packageContext, Class<?> cls) {
        return new IntentBuilder(packageContext, cls);
    }

    public static IntentBuilder Builder(String action, Uri uri, Context packageContext, Class<?> cls) {
        return new IntentBuilder(action, uri, packageContext, cls);
    }

    public IntentBuilder() {
        intent = new Intent();
    }

    public IntentBuilder(Intent intent) {
        intent = new Intent(intent);
    }

    public IntentBuilder(String action) {
        intent = new Intent(action);
    }

    public IntentBuilder(String action, Uri uri) {
        intent = new Intent(action, uri);
    }

    public IntentBuilder(Context packageContext, Class<?> cls) {
        mContext = packageContext;
        intent = new Intent(packageContext, cls);
    }

    public IntentBuilder(String action, Uri uri, Context packageContext, Class<?> cls) {
        mContext = packageContext;
        intent = new Intent(action, uri, packageContext, cls);
    }

    public IntentBuilder setData(Uri uri){
        intent.setData(uri);
        return this;
    }

    public Intent getIntent() {
        return intent;
    }

    public String getAction() {
        return intent.getAction();
    }

    public Uri getData() {
        return intent.getData();
    }

    public String getDataString() {
        return intent.getDataString();
    }

    public String getScheme() {
        return intent.getScheme();
    }

    public String getType() {
        return intent.getType();
    }


    public IntentBuilder setClass(Context packageContext, Class<?> cls) {
        mContext = packageContext;
        intent.setClass(mContext, cls);
        return this;
    }

    public IntentBuilder putExtra(String name, boolean value) {
        intent.putExtra(name, value);
        return this;
    }


    public IntentBuilder putExtra(String name, ArrayList value) {
        intent.putParcelableArrayListExtra(name, value);
        return this;
    }

    public IntentBuilder putExtra(String name, byte value) {
        intent.putExtra(name, value);
        return this;
    }

    public IntentBuilder putExtra(String name, char value) {
        intent.putExtra(name, value);
        return this;
    }

    public IntentBuilder putExtra(String name, short value) {
        intent.putExtra(name, value);
        return this;
    }

    public IntentBuilder putExtra(String name, int value) {
        intent.putExtra(name, value);
        return this;
    }

    public IntentBuilder putExtra(String name, long value) {
        intent.putExtra(name, value);
        return this;
    }

    public IntentBuilder putExtra(String name, float value) {
        intent.putExtra(name, value);
        return this;
    }

    public IntentBuilder putExtra(String name, double value) {
        intent.putExtra(name, value);
        return this;
    }

    public IntentBuilder putExtra(String name, String value) {
        intent.putExtra(name, value);
        return this;
    }

    public IntentBuilder putExtra(String name, CharSequence value) {
        intent.putExtra(name, value);
        return this;
    }

    public IntentBuilder putExtra(String name, Parcelable value) {
        intent.putExtra(name, value);
        return this;
    }

    public IntentBuilder putExtra(String name, Parcelable[] value) {
        intent.putExtra(name, value);
        return this;
    }

    public IntentBuilder putParcelableArrayListExtra(String name, ArrayList<? extends Parcelable> value) {
        intent.putExtra(name, value);
        return this;
    }

    public IntentBuilder putSerializableArrayListExtra(String name, ArrayList<? extends Serializable> value) {
        intent.putExtra(name, value);
        return this;
    }

    public IntentBuilder putIntegerArrayListExtra(String name, ArrayList<Integer> value) {
        intent.putExtra(name, value);
        return this;
    }

    public IntentBuilder putStringArrayListExtra(String name, ArrayList<String> value) {
        intent.putExtra(name, value);
        return this;
    }

    public IntentBuilder putCharSequenceArrayListExtra(String name, ArrayList<CharSequence> value) {
        intent.putExtra(name, value);
        return this;
    }

    public IntentBuilder putExtra(String name, Serializable value) {
        intent.putExtra(name, value);
        return this;
    }

    public IntentBuilder putExtra(String name, boolean[] value) {
        intent.putExtra(name, value);
        return this;
    }

    public IntentBuilder putExtra(String name, byte[] value) {
        intent.putExtra(name, value);
        return this;
    }

    public IntentBuilder putExtra(String name, short[] value) {
        intent.putExtra(name, value);
        return this;
    }

    public IntentBuilder putExtra(String name, char[] value) {
        intent.putExtra(name, value);
        return this;
    }

    public IntentBuilder putExtra(String name, int[] value) {
        intent.putExtra(name, value);
        return this;
    }

    public IntentBuilder putExtra(String name, long[] value) {
        intent.putExtra(name, value);
        return this;
    }

    public IntentBuilder putExtra(String name, float[] value) {
        intent.putExtra(name, value);
        return this;
    }

    public IntentBuilder putExtra(String name, double[] value) {
        intent.putExtra(name, value);
        return this;
    }

    public IntentBuilder putExtra(String name, String[] value) {
        intent.putExtra(name, value);
        return this;
    }

    public IntentBuilder putExtra(String name, CharSequence[] value) {
        intent.putExtra(name, value);
        return this;
    }

    public IntentBuilder putExtra(String name, Bundle value) {
        intent.putExtra(name, value);
        return this;
    }

    public IntentBuilder putExtras(Intent src) {
        intent.putExtras(src);
        return this;
    }

    public IntentBuilder putExtras(Bundle extras) {
        intent.putExtras(extras);
        return this;
    }

    public IntentBuilder setAction(String  action) {
        intent.setAction(action);
        return this;
    }

    public IntentBuilder setFlag(int  flag) {
        intent.setFlags(flag);
        return this;
    }

    public IntentBuilder addFlag(int  flag) {
        intent.addFlags(flag);
        return this;
    }

    public IntentBuilder startActivity() {
        if (mContext != null){
            mContext.startActivity(intent);
            ((Activity)mContext).overridePendingTransition(enter, exit);
        }
        return this;
    }

    public IntentBuilder startActivity(int requestCode) {
        if (mContext != null){
            ((Activity)mContext).startActivityForResult(intent, requestCode);
            ((Activity)mContext).overridePendingTransition(enter, exit);
        }
        return this;
    }


    public void startActivity(Activity activity) {
        activity.startActivity(intent);
        activity.overridePendingTransition(enter, exit);
    }

    public void dial(Activity activity, String phone){
        setAction(Intent.ACTION_DIAL);
        setData(Uri.parse("tel:" + phone));
        startActivity(activity);
    }

    public void startActivity(Activity activity, boolean isBack) {
        activity.startActivity(intent);
    }

    public void startParentActivity(Activity context, Class clz) {
        intent.setClass(context, FragmentParentActivity.class);
        intent.putExtra(FragmentParentActivity.KEY_FRAGMENT, clz);
        context.startActivity(intent);
        context.overridePendingTransition(enter, exit);
    }
    public void startParentActivity(Activity context,boolean isHaveToolbar ,Class clz) {
        intent.setClass(context, FragmentParentActivity.class);
        intent.putExtra(FragmentParentActivity.KEY_FRAGMENT, clz);
        intent.putExtra(FragmentParentActivity.KEY_HAVE_TOOL_BAR, isHaveToolbar);
        context.startActivity(intent);
        context.overridePendingTransition(enter, exit);

    }

    public void startParentActivity(Activity context,boolean isHaveToolbar ,Class clz,int requestCode) {
        intent.setClass(context, FragmentParentActivity.class);
        intent.putExtra(FragmentParentActivity.KEY_FRAGMENT, clz);
        intent.putExtra(FragmentParentActivity.KEY_HAVE_TOOL_BAR, isHaveToolbar);
        context.startActivityForResult(intent,requestCode);
        context.overridePendingTransition(enter, exit);

    }

    public void startParentActivity(Activity context, Class clz,int requestCode) {
        intent.setClass(context, FragmentParentActivity.class);
        intent.putExtra(FragmentParentActivity.KEY_FRAGMENT, clz);
        context.startActivityForResult(intent,requestCode);
        context.overridePendingTransition(enter, exit);

    }
    public void startParentActivity(BaseFragment fragment, Class clz, int requestCode) {
        if(fragment.getActivity() != null){
            intent.setClass(fragment.getActivity(), FragmentParentActivity.class);
            intent.putExtra(FragmentParentActivity.KEY_FRAGMENT, clz);
            fragment.startActivityForResult(intent,requestCode);
            fragment.getBaseActivity().overridePendingTransition(enter, exit);
        }else throw new NullPointerException("fragment's activity is null");

    }

    public void finishForResult(Activity activity){
        finishForResult(activity, Activity.RESULT_OK);
    }
    public void finishForResult(Activity activity, int resultCode){
        activity.setResult(resultCode, intent);
        activity.finish();
    }

}
