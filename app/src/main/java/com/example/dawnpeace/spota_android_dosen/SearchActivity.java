package com.example.dawnpeace.spota_android_dosen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dawnpeace.spota_android_dosen.Model.Praoutline;
import com.example.dawnpeace.spota_android_dosen.MyRecyclerViewAdapters.PraoutlineDraftAdapter;
import com.example.dawnpeace.spota_android_dosen.RetrofitInterface.PraoutlineInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity {
    private EditText et_search;
    private ImageButton ib_search;
    private SharedPrefHelper sharedpref;
    private RecyclerView rv_preoutlines;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        try{
            getSupportActionBar().setTitle("Praoutline");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException e){
            e.printStackTrace();
        }
        initView();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void initView(){
        et_search = findViewById(R.id.et_search);
        ib_search = findViewById(R.id.ib_search);
        sharedpref = SharedPrefHelper.getInstance(this);

        ib_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });

        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search();
                    InputMethodManager imm  = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    View view = getCurrentFocus();
                    if(view == null){
                        view = new View(SearchActivity.this);
                    }
                    if(!et_search.getText().toString().isEmpty()){
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    public void search(){
        String query = et_search.getText().toString();
        if(query.isEmpty()){
            Toast.makeText(this, "Kata Kunci Kosong . .", Toast.LENGTH_SHORT).show();
        } else {
            Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(APIUrl.BASE_URL).client(sharedpref.getInterceptor()).build();
            PraoutlineInterface search = retrofit.create(PraoutlineInterface.class);
            Call<List<Praoutline>> call  = search.search(query);
            call.enqueue(new Callback<List<Praoutline>>() {
                @Override
                public void onResponse(Call<List<Praoutline>> call, Response<List<Praoutline>> response) {
                    if(response.isSuccessful()){
                        rv_preoutlines = (RecyclerView) findViewById(R.id.rv_search);
                        PraoutlineDraftAdapter adapter = new PraoutlineDraftAdapter(SearchActivity.this,response.body());
                        rv_preoutlines.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
                        rv_preoutlines.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(Call<List<Praoutline>> call, Throwable t) {

                }
            });
        }


    }
}
