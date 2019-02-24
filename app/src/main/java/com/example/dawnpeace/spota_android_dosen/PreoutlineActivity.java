package com.example.dawnpeace.spota_android_dosen;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dawnpeace.spota_android_dosen.Model.Draft;
import com.example.dawnpeace.spota_android_dosen.Model.DraftApproval;
import com.example.dawnpeace.spota_android_dosen.MyRecyclerViewAdapters.DraftViewAdapter;
import com.example.dawnpeace.spota_android_dosen.RetrofitInterface.PraoutlineInterface;
import com.example.dawnpeace.spota_android_dosen.RetrofitInterface.ReviewInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PreoutlineActivity extends AppCompatActivity {
    private TextView tv_title;
    private TextView tv_description;
    private TextView tv_upvote;
    private TextView tv_downvote;
    private TextView tv_status;
    private ImageButton ib_upvote;
    private ImageButton ib_downvote;
    private ImageButton ib_comment;
    private SharedPrefHelper mSharedPref;
    private RecyclerView rv_approval;
    private LinearLayout ll_preoutline;
    private int preoutline_id;
    private Intent review_intent;
    private String url="";
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preoutline);
        getSupportActionBar().setTitle("Praoutline");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSharedPref = SharedPrefHelper.getInstance(this);
        checkPermission();
        Intent retriveIntent = getIntent();
        Bundle bundle = retriveIntent.getExtras();

        if(bundle != null){
            preoutline_id = bundle.getInt("preoutline_id");
            review_intent = new Intent(this,ReviewActivity.class);
            review_intent.putExtra("preoutline_id",preoutline_id);

        } else {
            Toast.makeText(this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
            return;
        }

        initView();
        loadData();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.preoutline_sub_menu,menu);
        return true;
    }

    private void checkPermission(){
        String[] read_external_permission = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, read_external_permission,1);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.preoutline_sub_download:
                if(!url.isEmpty()){
                    if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this, "Pastikan aplikasi memiliki izin mengakses file", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                    Uri uri = Uri.parse(url);

                    DownloadManager.Request request = new DownloadManager.Request(uri);
                    request.setTitle("Draft Praoutline");
                    request.setDescription("Downloading");
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setDestinationInExternalFilesDir(this,"Downloads","Draft.pdf");
                    downloadManager.enqueue(request);
                }
                break;
            case R.id.preoutline_sub_result:
                if(preoutline_id != 0){
                    Intent intent = new Intent(this,CloseDraftActivity.class);
                    intent.putExtra("preoutline_id",preoutline_id);
                    startActivity(intent);
                }
                break;
            default:
                finish();
        }
        return true;
    }


    private void initView(){
        tv_title = findViewById(R.id.tv_draft_title);
        tv_description = findViewById(R.id.tv_draft_content);
        tv_upvote = findViewById(R.id.tv_draft_upvote);
        tv_downvote = findViewById(R.id.tv_draft_downvote);
        ib_upvote = findViewById(R.id.ib_upvote);
        ib_downvote = findViewById(R.id.ib_downvote);
        ib_comment = findViewById(R.id.ib_comment);
        tv_status = findViewById(R.id.tv_draft_status);
        rv_approval = findViewById(R.id.rv_approvals);
        rv_approval.setNestedScrollingEnabled(false);
        ll_preoutline = findViewById(R.id.ll_preoutline_draft);
    }

    private void loadData(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL)
                .client(mSharedPref.getInterceptor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PraoutlineInterface praoutline = retrofit.create(PraoutlineInterface.class);
        Call<Draft> call = praoutline.getDraft(preoutline_id);
        call.enqueue(new Callback<Draft>() {
            @Override
            public void onResponse(Call<Draft> call, Response<Draft> response) {

                if(response.isSuccessful()){
                    if(response.body() != null){
                        setView(response.body());
                        url = response.body().getFile();
                        review_intent.putExtra("preoutline_status",response.body().getStatus());
                        if(response.body().getStatus().equals("open") && mSharedPref.getUser().isMajorheadmaster()){
                            menu.getItem(1).setVisible(true);
                        }
                    }
                } else {
                    Toast.makeText(PreoutlineActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Draft> call, Throwable t) {
                Toast.makeText(PreoutlineActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void setView(final Draft draft) {
        String data = draft.getDescription();  // the html data
        tv_description.setText(Html.fromHtml(data,null,new MyTagHandler()));
        tv_description.setMovementMethod(LinkMovementMethod.getInstance());
        tv_title.setText(draft.getTitle());
        String upvote_count = String.valueOf(draft.getUpvote_count());
        String downvote_count = String.valueOf(draft.getDownvote_count());
        tv_upvote.setText(upvote_count);
        tv_downvote.setText(downvote_count);
        tv_status.setText("STATUS : "+draft.getLocalizedStatus());

        rv_approval.setLayoutManager(new LinearLayoutManager(this));
        DraftViewAdapter adapter = new DraftViewAdapter(this,draft.getApprovals());
        rv_approval.setAdapter(adapter);
        ll_preoutline.setVisibility(View.VISIBLE);

        if(draft.getMy_approval().equals("approve")){
            ib_upvote.setImageResource(R.drawable.ic_arrow_up_blue);

        }

        if(draft.getMy_approval().equals("disapprove")){
            ib_downvote.setImageResource(R.drawable.ic_arrow_pointing_down_blue);
        }

        ib_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                review_intent.putExtra("status",draft.getStatus());
                startActivity(review_intent);
            }
        });

        if(draft.getStatus().equals("open")){
            ib_upvote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    approval("approve");
                }
            });


            ib_downvote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    approval("disapprove");
                }
            });
        }


    }

    private void approval(final String approval){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL)
                .client(mSharedPref.getInterceptor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ReviewInterface review = retrofit.create(ReviewInterface.class);
        Call<DraftApproval> call = review.approve(preoutline_id, approval);
        call.enqueue(new Callback<DraftApproval>() {
            @Override
            public void onResponse(Call<DraftApproval> call, Response<DraftApproval> response) {
                if(response.isSuccessful()){
                    if(approval.equals("approve")){
                        ib_upvote.setImageResource(R.drawable.ic_arrow_up_blue);
                        ib_downvote.setImageResource(R.drawable.ic_downvote);
                        tv_upvote.setText(response.body().getUpvote_count());
                        tv_downvote.setText(response.body().getDownvote_count());

                    } else {
                        ib_downvote.setImageResource(R.drawable.ic_arrow_pointing_down_blue);
                        ib_upvote.setImageResource(R.drawable.ic_upvote);
                        tv_upvote.setText(response.body().getUpvote_count());
                        tv_downvote.setText(response.body().getDownvote_count());
                    }

                    rv_approval.setLayoutManager(new LinearLayoutManager(PreoutlineActivity.this));
                    DraftViewAdapter adapter = new DraftViewAdapter(PreoutlineActivity.this,response.body().getApprovals());
                    rv_approval.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<DraftApproval> call, Throwable t) {

            }
        });
    }


}
