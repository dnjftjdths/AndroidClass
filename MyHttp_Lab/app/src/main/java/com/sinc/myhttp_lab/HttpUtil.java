package com.sinc.myhttp_lab;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil extends AsyncTask<String, String, String> {
    private Context context;

    public HttpUtil(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        // 호출 전
        System.out.println("************************************************* 서버 호출 선행" );
    }


    @Override
    public String doInBackground(String...params) {
        System.out.println("************************************************* 서버 호출" );
        String paramString = "";
        String url = params[0];
        String param1Key = params[1].split(":")[0];
        String param1Value = params[1].split(":")[1];
        String param2Key = params[2].split(":")[0];
        String param2Value = params[2].split(":")[1];
        String param3Key = params[3].split(":")[0];
        String param3Value = params[3].split(":")[1];
        System.out.println("************************************************* 서버 호출 url : " + url);

        paramString = param1Key  + "=" + param1Value + "&" + param2Key  + "=" + param2Value + "&" + param3Key  + "=" + param3Value ;

        try {
            URL obj = new URL(url + "?" + paramString);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");

            byte[] outputInBytes = params[0].getBytes("UTF-8");
            OutputStream os = conn.getOutputStream();
            os.write( outputInBytes );
            os.close();

            int retCode = conn.getResponseCode();

            System.out.println("************************************************* 서버 호출 결과 코드 : " + retCode );
            if (retCode == HttpURLConnection.HTTP_OK) {
                InputStream is = conn.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                char[] buff = new char[512];
                int len = -1;
                String line;
                StringBuffer response = new StringBuffer();
                while((len = br.read(buff)) != -1) {
                    response.append(new String(buff, 0, len));
                }
                br.close();
                System.out.println("************************************************* 서버 호출 결과 text : " + response.toString());

                return response.toString();
            }else{
                System.out.println("************************************************* 서버 호출 실패 code : " + retCode );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 호출이 끝난 후
    @Override
    protected void onPostExecute(String result) {
        JSONObject object = null;
        try {
            object = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("************************************************* 서버 호출 후행");
        String rtn = "";
        try {
            // return 받은 Json 데이터
            // rtn = URLDecoder.decode(object.getString("DATA"), "UTF-8");
            ((MainActivity) context).printToast(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
