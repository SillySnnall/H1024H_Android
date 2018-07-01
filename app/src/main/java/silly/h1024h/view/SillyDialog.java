package silly.h1024h.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Field;

import silly.h1024h.BuildConfig;


/**
 * SillyDialog
 * Created by StringBOX on 2016/10/20.
 */

public class SillyDialog extends AlertDialog {

    private static final String TAG = "SillyDialog";

    private Context mContext;
    private int layoutResID;
    private View layoutView;
    private final SparseArray<View> mViews;     //android推荐使用的键值对数组
    private boolean mIsFinish = false;
    private BxDialogInterf mStatusListener;
    private CanceledOnTouchOutsideInterf mCanceledListener;

    public interface BxDialogInterf {
        /**
         * dialog显示
         */
        void onShow();
        /**
         * dialog消失
         */
        void onDismiss();
    }

    public interface CanceledOnTouchOutsideInterf {
        void CanceledListener();
    }

    public SillyDialog(Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        mContext = context;
        mViews = new SparseArray<>();
    }

    public SillyDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        mContext = context;
        mViews = new SparseArray<>();
    }

    /**
     * 传入布局文件
     *
     * @param layoutResID
     * @return GCDialog
     * <br/>
     * RelativeLayout <br/>
     * &nbsp match_parent <br/>
     * &nbsp match_parent <br/>
     * &nbsp&nbsp&nbsp RelativeLayout <br/>
     * &nbsp&nbsp&nbsp &nbsp&nbsp&nbsp 在第二个Layout中写代码，类似activity中的xml文件 <br/>
     * &nbsp&nbsp&nbsp /RelativeLayout <br/>
     * /RelativeLayout
     */
    public SillyDialog loadLayout(int layoutResID) {
        this.layoutResID = layoutResID;
        show();
        dismiss();
        return this;
    }

    public SillyDialog loadLayout(View layoutView) {
        this.layoutView = layoutView;
        show();
        dismiss();
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (layoutView != null)
            setContentView(layoutView);
        else
            setContentView(layoutResID);
        setCanceledOnTouchOutside(false);
        matchWidth();
    }

    /**
     * 宽度填充屏幕
     */
    private void matchWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = dm.widthPixels;
//        p.height = dm.heightPixels - getStatusBarHeight() - getNavigationBarHeight(mContext);
        getWindow().setAttributes(p);
    }

    /**
     * 获取NavigationBar高度
     *
     * @param context 上下文
     * @return NavigationBar高度
     */
    public int getNavigationBarHeight(Context context) {
        int navigationBarHeight = 0;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("navigation_bar_height", "dimen", "android");
        if (id > 0) {
            navigationBarHeight = rs.getDimensionPixelSize(id);
        }
        return navigationBarHeight;
    }

    /**
     * 获取状态栏高度
     *
     * @return 状态栏高度
     */
    public int getStatusBarHeight() {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = getContext().getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return sbar;
    }

    /**
     * 点击外部是否消失GCDialog
     *
     * @param cancel true可以消失,false不能消失,默认为false
     * @return
     */
    public SillyDialog setGCCanceledOnTouchOutside(boolean cancel) {
        try {
            setCanceledOnTouchOutside(cancel);
            View inflate;
            if (layoutView != null) {
                inflate = layoutView;
            } else {
                inflate = getLayoutInflater().inflate(layoutResID, null);
            }
            if (cancel) {
                setOnClickListener(inflate.getId(), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                        if (mCanceledListener != null) {
                            mCanceledListener.CanceledListener();
                        }
                    }
                });
            }
        } catch (Exception e) {
            if (BuildConfig.DEBUG) Log.e(TAG, "跟布局缺少ID,请在布局文件中给根布局加上ID");
        }
        return this;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /* 触摸外部弹窗 */
        if (isOutOfBounds(getContext(), event)) {
            if (mCanceledListener != null) {
                mCanceledListener.CanceledListener();
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 判断是否触摸到空白区域
     *
     * @param context
     * @param event
     * @return
     */
    private boolean isOutOfBounds(Context context, MotionEvent event) {
        final int x = (int) event.getX();
        final int y = (int) event.getY();
        final int slop = ViewConfiguration.get(context).getScaledWindowTouchSlop();
        final View decorView = getWindow().getDecorView();
        return (x < -slop) || (y < -slop) || (x > (decorView.getWidth() + slop))
                || (y > (decorView.getHeight() + slop));
    }

    /**
     * 设置dialog点击外部区域的监听
     *
     * @param canceledListener 外部区域监听回调
     * @return
     */
    public SillyDialog setCanceledListener(CanceledOnTouchOutsideInterf canceledListener) {
        mCanceledListener = canceledListener;
        return this;
    }

    /**
     * 彻底清除GCDialog
     *
     * @return
     */
    public SillyDialog dismissCancel() {
        dismiss();
        cancel();
        return this;
    }

    /**
     * 设置dialog状态监听
     *
     * @param statusListener 状态监听回调
     * @return
     */
    public SillyDialog setStatusListener(BxDialogInterf statusListener) {
        mStatusListener = statusListener;
        return this;
    }

    @Override
    public void show() {
        try {
            super.show();
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            if (mStatusListener != null) {
                mStatusListener.onShow();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (BuildConfig.DEBUG) Log.e("SillyDialog", e.toString());
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (mStatusListener != null) {
            mStatusListener.onDismiss();
        }
    }

    /**
     * 手机返回键按钮监听
     *
     * @param OnclickListener
     * @return
     */
    public SillyDialog setKeyCodeBack(OnclickListener OnclickListener) {
        if (OnclickListener != null) {
            this.OnclickListener = OnclickListener;
        }
        mIsFinish = true;
        return this;
    }

    private OnclickListener OnclickListener;

    public interface OnclickListener {
        void onBackClick(Context context);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mIsFinish) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (OnclickListener != null) {
                    OnclickListener.onBackClick(mContext);
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public <TView extends View> TView getView(int id) {
        View view = mViews.get(id);
        if (view == null) {
            view = findViewById(id);
            mViews.put(id, view);
        }
        return (TView) view;
    }

    public SillyDialog setText(int viewId, CharSequence value) {
        TextView view = getView(viewId);
        view.setText(value);
        return this;
    }

    public SillyDialog setImageURI(int viewId, Uri uri) {
        ImageView view = getView(viewId);
        view.setImageURI(uri);
        return this;
    }

    public SillyDialog setTextColor(int viewId, int textColor) {
        TextView view = getView(viewId);
        view.setTextColor(textColor);
        return this;
    }

    public SillyDialog setTextColorRes(int viewId, int textColorRes) {
        TextView view = getView(viewId);
        view.setTextColor(mContext.getResources().getColor(textColorRes));
        return this;
    }

    public SillyDialog setImageResource(int viewId, int imageResId) {
        ImageView view = getView(viewId);
        view.setImageResource(imageResId);
        return this;
    }

    public SillyDialog setBackgroundColor(int viewId, int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public SillyDialog setBackgroundResource(int viewId, int backgroundRes) {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    public SillyDialog setVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    public SillyDialog setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public SillyDialog setOnTouchListener(int viewId, View.OnTouchListener listener) {
        View view = getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    public SillyDialog setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }
}
