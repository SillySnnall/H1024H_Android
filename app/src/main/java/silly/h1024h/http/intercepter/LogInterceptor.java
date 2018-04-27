package silly.h1024h.http.intercepter;

import android.util.Log;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import silly.h1024h.BuildConfig;

/**
 * 网络日志
 * Created by SillySnnall on 2018/3/22.
 */

public class LogInterceptor implements Interceptor {
    public static String TAG = "LogInterceptor";

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long startTime = System.currentTimeMillis();
        okhttp3.Response response = chain.proceed(chain.request());
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        if (BuildConfig.DEBUG) Log.d(TAG, "\n");
        if (BuildConfig.DEBUG) Log.d(TAG, "----------Start----------------");
        String requestS = request.toString();
        int tag = requestS.indexOf("tag");
        if (tag != -1) {
            requestS = requestS.substring(0, tag - 2) + "}";
        }
        if (BuildConfig.DEBUG) Log.d(TAG, "| " + requestS);
        String method = request.method();
        if ("POST".equals(method)) {
            StringBuilder sb = new StringBuilder();
            if (request.body() instanceof FormBody) {
                FormBody body = (FormBody) request.body();
                for (int i = 0; i < (body != null ? body.size() : 0); i++) {
                    sb.append(body.encodedName(i)).append("=").append(body.encodedValue(i)).append(",");
                }
                if (sb.length() != 0) sb.delete(sb.length() - 1, sb.length());
                if (BuildConfig.DEBUG) Log.d(TAG, "| RequestParams:{" + sb.toString() + "}");
            }
        }
        if (BuildConfig.DEBUG) Log.d(TAG, "| Response:" + content);
        Log.d(TAG, "----------End:" + duration + "毫秒----------");
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    }
}
