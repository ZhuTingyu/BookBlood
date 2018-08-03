package com.base.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.base.http.R;
import com.base.util.dialog.DialogUtils;


/**
 *  Created by TingYu Zhu on 2017/10/30.
 */

public class PermissionUtils {

    private static final String SCHEME = "package";
    public static final int PERMISSION_CODE = 0X234;

    Activity context;

    public PermissionUtils(Activity context){
        this.context = context;
    }

    public void requestPermissions(String... permissions){

        if(checkPermissions(permissions)){
            createHintSetPermissionsDialog();
        }
    }

    private boolean checkPermissions(String... permissions) {
        for (String permission : permissions) {
            if(lacksPermission(permission)){
                return true;
            }
        }
        return false;
    }

    private boolean lacksPermission(String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED;
    }

    @TargetApi(Build.VERSION_CODES.N)
    private void createHintSetPermissionsDialog(){
        DialogUtils.createNotCancelableDialog(context, R.string.message_permission_hint, sweetAlertDialog -> {
                    ActivityCompat.finishAffinity(context);
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    context.startActivity(intent);
        }
        ,sweetAlertDialog -> {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    Uri uri = Uri.fromParts(SCHEME, context.getPackageName(), null);
                    intent.setData(uri);
                    context.startActivityForResult(intent, PERMISSION_CODE);

                });
    }

}
