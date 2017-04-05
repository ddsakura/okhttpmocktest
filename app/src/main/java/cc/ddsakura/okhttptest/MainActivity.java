
package cc.ddsakura.okhttptest;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static String uri = "https://api.github.com/";

    private TextView mDescTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDescTv = (TextView) findViewById(R.id.descTv);
        Log.d("OKHTTP", "onCreate: " + uri);
        getRequest();
    }

    public void getRequest() {

        Log.d("OKHTTP", "getRequest: " + uri);
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(uri)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 告知使用者連線失敗
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String json = response.body().string();
                Log.d("OKHTTP", json);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mDescTv.setText(json);
                    }
                });

            }
        });
    }
}
