package silly.h1024h.http.httputil;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.File;

import okhttp3.Call;
import silly.h1024h.R;
import silly.h1024h.utils.Init;


/**
 * 图片加载
 * Created by SillySnnall on 2018/7/17.
 */

public class ImageloadUtil {
    public static final int Default = 0;
    public static final int Fillet = 1;

    private static ImageloadUtil imageloadUtil;

    public static ImageloadUtil get() {
        if (imageloadUtil == null) {
            imageloadUtil = new ImageloadUtil();
        }
        return imageloadUtil;
    }

    private ImageloadUtil() {
    }

    private String getDir(String url) {
        String fileName = "img.jpg";
        if (url.contains("https://")) fileName = url.replace("https://", "");
        if (url.contains("http://")) fileName = url.replace("http://", "");
        fileName = fileName.substring(fileName.indexOf("/") + 1);
        return fileName.substring(0, fileName.lastIndexOf("/"));
    }

    private String getName(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    private boolean isFile(String file) {
        return new File(file).exists();
    }

    public void imageload(final ImageView imageView, String url, final int type, final OnLoadListener listener) {
        File file = new File(getDir(url), getName(url));
        if (isFile(file.getAbsolutePath())) {
            if (type == Fillet) {
                Glide.with(Init.ctx).load(file.getAbsolutePath()).centerCrop().placeholder(R.color.color_bfbfbf).listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        if (listener != null) listener.onFailed(e);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        if (listener != null) listener.onSuccess();
                        return false;
                    }
                }).into(imageView);
            } else {
                Glide.with(Init.ctx).load(file.getAbsolutePath()).listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        if (listener != null) listener.onFailed(e);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        if (listener != null) listener.onSuccess();
                        return false;
                    }
                }).into(imageView);
            }
        } else {
            DownloadUtil.get().download(url, Init.ctx.getExternalFilesDir("img/" + getDir(url)).getAbsolutePath(), "", new DownloadUtil.OnDownloadListener() {
                @Override
                public void onDownloadSuccess(String filePath) {
                    if (type == Fillet) {
                        Glide.with(Init.ctx).load(filePath).centerCrop().placeholder(R.color.color_bfbfbf).listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                if (listener != null) listener.onFailed(e);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                if (listener != null) listener.onSuccess();
                                return false;
                            }
                        }).into(imageView);
                    } else {
                        Glide.with(Init.ctx).load(filePath).listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                if (listener != null) listener.onFailed(e);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                if (listener != null) listener.onSuccess();
                                return false;
                            }
                        }).into(imageView);
                    }
                }

                @Override
                public void onDownloading(int progress) {
                    if (listener != null) listener.onLoading(progress);
                }

                @Override
                public void onDownloadFailed(Call call, Exception e) {
                    if (listener != null) listener.onFailed(e);
                }
            });
        }
    }

    public interface OnLoadListener {
        /**
         * 加载成功
         */
        void onSuccess();

        /**
         * @param progress 加载进度
         */
        void onLoading(int progress);

        /**
         * 加载失败
         */
        void onFailed(Exception e);
    }
}
