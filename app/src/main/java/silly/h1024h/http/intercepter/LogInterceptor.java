package silly.h1024h.http.intercepter;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import silly.h1024h.BuildConfig;
import silly.h1024h.utils.DesUtil;

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
        Log.d(TAG, "\n");
        Log.d(TAG, "----------Start----------------");
        String requestS = request.toString();
        int tag = requestS.indexOf("tag");
        if (tag != -1) {
            requestS = requestS.substring(0, tag - 2) + "}";
        }
        Log.d(TAG, "| " + requestS);
        String method = request.method();
        if ("POST".equals(method)) {
            StringBuilder sb = new StringBuilder();
            if (request.body() instanceof FormBody) {
                FormBody body = (FormBody) request.body();
                String sign = "";
                String timestamp = "";
                for (int i = 0; i < (body != null ? body.size() : 0); i++) {
                    if ("sign".equals(body.encodedName(i))) {
                        sign = Uri.decode(body.encodedValue(i));
                    }
                    if ("timestamp".equals(body.encodedName(i))) {
                        timestamp = Uri.decode(body.encodedValue(i));
                    }
                }
                Log.d(TAG, "| RequestParams:{" + decryptData(sign, timestamp) + "}");
            }
        }
        try {
            Log.d(TAG, "| Response:" + DesUtil.decrypt(content));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "----------End:" + duration + "毫秒----------");
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    }

    /**
     * 解密数据
     */
    private String decryptData(String sign, String timestamp) {
        if (sign.isEmpty() || timestamp.isEmpty()) {
            return "";
        }
        try {
            String decrypt = DesUtil.decrypt(sign);
            decrypt = DesUtil.decrypt(decrypt, timestamp);
            return decrypt;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
