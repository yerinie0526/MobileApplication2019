package ddwu.mobile.final_project.ma02_20170962;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import static android.content.ContentValues.TAG;

public class ShowPlaceListActivity extends Activity {

    EditText etTarget;
    ListView lvList;
    String apiAddress;

    String query;

    MyPlaceAdapter adapter;
    ArrayList<NaverPlaceDTO> resultList;
    NaverPlaceXmlParser parser;
    Intent intent;
    long id2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.placeview_layout);

        etTarget = findViewById(R.id.searchPlace);
        lvList = findViewById(R.id.placeListview);

        resultList = new ArrayList();
        adapter = new MyPlaceAdapter(this, R.layout.place_layout, resultList);
        lvList.setAdapter(adapter);

        intent = getIntent();
        id2 = getIntent().getLongExtra("id", 0);





        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String name = ((NaverPlaceDTO)adapter.getItem(position)).getName();
                String add = ((NaverPlaceDTO)adapter.getItem(position)).getAddress();

                if(Paramenters.addOrUpdaate.equals("update")){
                    Intent intent = new Intent();
                    intent.putExtra("name", name);
                    intent.putExtra("id", id2);
                    intent.putExtra("add", add);
                    setResult(1235, intent);
                }
                else{
                    //Intent intent = new Intent(ShowPlaceListActivity.this, AddFoodActivity.class);
                    Intent intent = new Intent();
                    // putExtra(key, value)
                    intent.putExtra("name", name);
                    intent.putExtra("add", add);
                    //startActivity(intent);
                    setResult(1234, intent);
                }

                finish();

            }
        });

        apiAddress = getResources().getString(R.string.api_url);
        parser = new NaverPlaceXmlParser();
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnSearchPlace:
                query = etTarget.getText().toString();
                try {
                    new NaverAsyncTask().execute(apiAddress + URLEncoder.encode(query, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                break;
        }
    }



    class NaverAsyncTask extends AsyncTask<String, Integer, String> {
        ProgressDialog progressDlg;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDlg = ProgressDialog.show(ShowPlaceListActivity.this, "Wait", "Downloading...");
        }


        @Override
        protected String doInBackground(String... strings) {
            String address = strings[0];
            String result = downloadContents(address);
            if (result == null) return "Error!";
            return result;
        }


        @Override
        protected void onPostExecute(String result) {
            Log.i(TAG, result);

            resultList = parser.parse(result);

            adapter.setList(resultList);
            adapter.notifyDataSetChanged();

            progressDlg.dismiss();
        }




        /* 네트워크 관련 메소드 */
        /* 네트워크 환경 조사 */
        private boolean isOnline() {
            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            return (networkInfo != null && networkInfo.isConnected());
        }



        private InputStream getNetworkConnection(HttpURLConnection conn) throws Exception {

            conn.setReadTimeout(3000);
            conn.setConnectTimeout(3000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setRequestProperty("X-Naver-Client-Id", getResources().getString(R.string.client_id));
            conn.setRequestProperty("X-Naver-Client-Secret", getResources().getString(R.string.client_secret));

            if (conn.getResponseCode() != HttpsURLConnection.HTTP_OK) {
                throw new IOException("HTTP error code: " + conn.getResponseCode());
            }

            return conn.getInputStream();
        }



        protected String readStreamToString(InputStream stream){
            StringBuilder result = new StringBuilder();

            try {
                InputStreamReader inputStreamReader = new InputStreamReader(stream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String readLine = bufferedReader.readLine();

                while (readLine != null) {
                    result.append(readLine + "\n");
                    readLine = bufferedReader.readLine();
                }

                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result.toString();
        }



        protected String downloadContents(String address) {
            HttpURLConnection conn = null;
            InputStream stream = null;
            String result = null;

            try {
                URL url = new URL(address);
                conn = (HttpURLConnection)url.openConnection();
                stream = getNetworkConnection(conn);
                result = readStreamToString(stream);
                if (stream != null) stream.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (conn != null) conn.disconnect();
            }

            return result;
        }

    }
}
