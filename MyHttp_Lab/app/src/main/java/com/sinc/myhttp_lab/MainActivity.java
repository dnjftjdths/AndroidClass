package com.sinc.myhttp_lab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    private Button btn;

    public void connect(View view){
        if(view.getId() == R.id.conBtn){
            httpWeb();
        }
    }

    public void printToast(String result) {
        Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
    }

    public void httpWeb(){
        String SERVER_URL="http://10.149.179.116:8088/android.sinc"; // 서버 주소
        HttpUtil hu = new HttpUtil(MainActivity.this);
        String recordStr = "id:jslim,pwd:jslim1234,name:subsubnim";
        StringTokenizer token = new StringTokenizer(recordStr , ",");
        String[] params = {SERVER_URL,
                token.nextToken() , token.nextToken(), token.nextToken() } ;

        hu.execute(params);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
