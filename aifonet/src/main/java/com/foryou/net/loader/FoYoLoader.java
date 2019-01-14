package com.foryou.net.loader;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

import com.foryou.net.R;

/**
 * Created by moxiaohao on 2017/4/2
 */
public class FoYoLoader {

    private static final int LOADER_SIZE_SCALE = 8;
    private static final int LOADER_OFFSET_SCALE = 10;

    private static final ArrayList<AppCompatDialog> LOADERS = new ArrayList<>();
    private static final String DEFAULT_LOADER = LoaderStyle.BallClipRotatePulseIndicator.name();

    public static void showLoading(Context context, Enum<LoaderStyle> type) {
        showLoading(context, type.name());
    }

    private static void showLoading(Context context, String type) {

        try{
            final AppCompatDialog dialog = new AppCompatDialog(context, R.style.dialog);
            final AVLoadingIndicatorView avLoadingIndicatorView = LoaderCreator.create(type, context);
            dialog.setContentView(avLoadingIndicatorView);

            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            int deviceWidth =  dm.widthPixels;
            int deviceHeight = dm.heightPixels;

            final Window dialogWindow = dialog.getWindow();

            if (dialogWindow != null) {
                final WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                lp.width = deviceWidth / LOADER_SIZE_SCALE;
                lp.height = deviceHeight / LOADER_SIZE_SCALE;
                lp.height = lp.height + deviceHeight / LOADER_OFFSET_SCALE;
                lp.gravity = Gravity.CENTER;
            }
            LOADERS.add(dialog);
            dialog.show();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void showLoading(Context context) {
        showLoading(context, DEFAULT_LOADER);
    }

    public static void stopLoading() {

        for (AppCompatDialog dialog : LOADERS) {
            if (dialog != null) {
                if (dialog.isShowing()) {
                    dialog.cancel();
                }
            }
        }
        LOADERS.clear();
    }

}
