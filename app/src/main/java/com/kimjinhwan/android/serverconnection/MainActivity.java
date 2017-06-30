package com.kimjinhwan.android.serverconnection;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kimjinhwan.android.serverconnection.domain.Data;

import org.androidannotations.annotations.EActivity;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class MainActivity extends AppCompatActivity {
    OkHttpClient client = new OkHttpClient();

    RecyclerView recycler;
    CustomAdapter adapter;

    //리모트 관련 설정 (주소는 바뀌지 않으니 final 처리를 함)
    final String DOMAIN = "http://192.168.10.248:8080";
    final String SERVER_PATH = "/bbs/json/list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycler = (RecyclerView) findViewById(R.id.recycler);
        adapter = new CustomAdapter();
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        load();

    }
    //화면 xml의 속성 추가 : android:onClick="btnPost"라고 적으면 아래 함수가 자동으로 호출됨.
    public void btnPost(){
        Intent intent = new Intent(this, WriteActivity.class);
        startActivity(intent);
    }


    private void load() {
        run(DOMAIN + SERVER_PATH);
    }


    private void run(String url) {
        //서브 thread에서 실행
        //onDoingBackground의 parameter, onDoingBackground의 리턴값, onPostExecute의 파라미터.
        new AsyncTask<String, Void, String>() {
            String result = null;

            @Override
            protected String doInBackground(String... params) {

                try {
                    result = getData(params[0]);
                } catch (Exception e) {
                    Log.e("MainActivity", e.getMessage());
                }
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                //결과값인 json 스트링을 객체로 변환
                Log.e("Result", result);
                Gson gson = new Gson();
                Data data = gson.fromJson(result, Data.class);
                //listView의 Adapter에 세팅
                adapter.setData(data.bbsList);
                //listView notify
                adapter.notifyDataSetChanged();
            }

        }.execute(url);
    }

    //OkHttp 작동을 위해 만든 함수
    public String getData(String url) throws IOException {
        //request : 요청정보를 담고 있는 객체
        Request request = new Request.Builder()
                .url(url)
                .build();
        //response : 응답정보를 담고 있는 객체

        Response response = null;
        //newCall을 통해 request 객체를 담아서 넘기고 실행하면 response로 return 해줌
        //서버로 요청
        response = client.newCall(request).execute();
        //응답 객체에서 실제 데이터만 추출.
        ResponseBody resBody = response.body();
        //데이터를 스트링 타입으로 변환하여 리턴
        return resBody.string();
    }


}
