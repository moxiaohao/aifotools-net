package io.aifo.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import java.util.WeakHashMap;
import com.foryou.net.FoYoNet;
import com.foryou.net.callback.IFailure;
import com.foryou.net.callback.ISuccess;
import com.foryou.net.http.IMethod;
import io.reactivex.Observable;

public class ExampleActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.findViewById(R.id.tv_hello).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request();
            }
        });
    }

    void request() {

        FoYoNet.builder()
                .params("uname", "zhangsan")
                .loader(this)
                .service(CommonService.class)
                .method(new IMethod<CommonService>() {
                    @Override
                    public Observable ob(CommonService service, WeakHashMap params) {
                        return service.login(params);
                    }
                })
                .success(new ISuccess<SingleEntity>() {
                    @Override
                    public void onSuccess(SingleEntity response) {
                        Toast.makeText(ExampleActivity.this, "成功", Toast.LENGTH_SHORT).show();
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure(int code, String desc) {
                        Toast.makeText(ExampleActivity.this, desc, Toast.LENGTH_SHORT).show();
                    }
                })
                .build()
                .excute();
    }

}
