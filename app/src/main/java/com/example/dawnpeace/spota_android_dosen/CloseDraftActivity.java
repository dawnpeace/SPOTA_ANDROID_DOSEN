package com.example.dawnpeace.spota_android_dosen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dawnpeace.spota_android_dosen.Model.Expertise;
import com.example.dawnpeace.spota_android_dosen.Model.Lecturer;
import com.example.dawnpeace.spota_android_dosen.Model.Statistic.PraoutlineInfo;
import com.example.dawnpeace.spota_android_dosen.RetrofitInterface.PraoutlineInterface;
import com.example.dawnpeace.spota_android_dosen.SpinnerAdapter.ExpertiseAdapter;
import com.example.dawnpeace.spota_android_dosen.SpinnerAdapter.LecturerAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CloseDraftActivity extends AppCompatActivity {

    private Spinner first_supervisor_spinner;
    private Spinner second_supervisor_spinner;
    private Spinner first_examiner_spinner;
    private Spinner second_examiner_spinner;
    private Spinner result_spinner;
    private Spinner expertise_spinner;
    private TextView tv_title;
    private EditText et_title;
    private EditText et_notes;
    private Button bt_submit;
    private SharedPrefHelper mSharedPref;

    private int preoutline_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close_draft);
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(getString(R.string.draft_closing));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Bundle bundle = getIntent().getExtras();
        preoutline_id = bundle.getInt("preoutline_id");

        mSharedPref = SharedPrefHelper.getInstance(this);
        initView();
        loadData();
    }

    private void initView(){
        first_supervisor_spinner = findViewById(R.id.first_supervisor_spinner);
        second_supervisor_spinner = findViewById(R.id.second_supervisor_spinner);
        first_examiner_spinner = findViewById(R.id.first_examiner_spinner);
        second_examiner_spinner = findViewById(R.id.second_examiner_spinner);
        expertise_spinner = findViewById(R.id.expertise_spinner);
        tv_title = findViewById(R.id.tv_closing_draft_title);
        et_title = findViewById(R.id.et_draft_final_title);
        et_notes = findViewById(R.id.et_draft_notes);
        bt_submit = findViewById(R.id.btn_submit_result);


        result_spinner = findViewById(R.id.results_choices_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.draft_result,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        result_spinner.setAdapter(adapter);
    }


    private void loadData(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL)
                    .client(mSharedPref.getInterceptor())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        PraoutlineInterface praoutlineInterface = retrofit.create(PraoutlineInterface.class);
        Call<PraoutlineInfo> call = praoutlineInterface.getDraftInfo(preoutline_id);
        call.enqueue(new Callback<PraoutlineInfo>() {
            @Override
            public void onResponse(Call<PraoutlineInfo> call, Response<PraoutlineInfo> response) {
                if(response.isSuccessful()){
                    setView(response.body());
                } else {
                    Toast.makeText(CloseDraftActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PraoutlineInfo> call, Throwable t) {

            }
        });
    }

    private void setView(PraoutlineInfo info){
        List<Lecturer> lecturers = info.getLecturers();
        String draft_title = info.getPreoutline_title() == null ? "" : info.getPreoutline_title();
        et_title.setText(draft_title);
        tv_title.setText(draft_title);


        if(lecturers != null){
            List<Lecturer> lecturerList1 = new ArrayList<>();
            lecturerList1.addAll(lecturers);
            LecturerAdapter adapter1 = new LecturerAdapter(CloseDraftActivity.this,lecturerList1);
            first_supervisor_spinner.setAdapter(adapter1);
            second_supervisor_spinner.setAdapter(adapter1);


            Lecturer empty = new Lecturer(0,"<Kosong>","<kosong>");
            List<Lecturer> lecturerList2 = new ArrayList<>();
            lecturerList2.addAll(lecturers);
            lecturerList2.add(0,empty);
            LecturerAdapter adapter2 = new LecturerAdapter(CloseDraftActivity.this,lecturerList2);
            first_examiner_spinner.setAdapter(adapter2);
            second_examiner_spinner.setAdapter(adapter2);

            bt_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    inputSubmission();
                }
            });
        }

        List<Expertise> expertises = info.getExpertises();
        if(expertises != null){
            ExpertiseAdapter adapter = new ExpertiseAdapter(CloseDraftActivity.this,expertises);
            expertise_spinner.setAdapter(adapter);
        }
    }

    private void inputSubmission(){
        String results = result_spinner.getSelectedItem().toString().toLowerCase().trim();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL)
                .client(mSharedPref.getInterceptor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PraoutlineInterface praoutlineInterface = retrofit.create(PraoutlineInterface.class);

        if(results.equals("diterima")){
            Lecturer first_supervisor = (Lecturer) first_supervisor_spinner.getSelectedItem();
            Lecturer second_supervisor = (Lecturer) second_supervisor_spinner.getSelectedItem();
            Lecturer first_examiner = (Lecturer) first_examiner_spinner.getSelectedItem();
            Lecturer second_examiner = (Lecturer) second_examiner_spinner.getSelectedItem();
            Expertise expertise = (Expertise) expertise_spinner.getSelectedItem();

            int first_supervisor_id = first_supervisor.getId();
            int second_supervisor_id = second_supervisor.getId();
            int first_examiner_id = first_examiner.getId();
            int second_examiner_id = second_examiner.getId();
            int expertise_id = expertise.getId();

            if(first_supervisor == second_supervisor){
                Toast.makeText(this, getString(R.string.supervisor_closing_failure), Toast.LENGTH_SHORT).show();
                return;
            }

            if((first_supervisor_id == first_examiner_id) ||
                    (first_supervisor_id == second_examiner_id) ||
                    (second_supervisor_id == first_examiner_id) ||
                    (second_supervisor_id == second_examiner_id)
                    ){
                Toast.makeText(this, getString(R.string.supervisor_examiner_closing_failure), Toast.LENGTH_SHORT).show();
                return;
            }

            if(!(first_examiner_id == 0 && second_examiner_id == 0)){
                if(first_examiner_id == second_examiner_id){
                    Toast.makeText(this, getString(R.string.examiner_closing_faulure), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            String final_title = et_title.getText().toString();
            if(final_title.isEmpty()){
                Toast.makeText(this, getString(R.string.final_title_failure), Toast.LENGTH_SHORT).show();
                finish();
            }

            Call<Void> call = praoutlineInterface.approveDraft(preoutline_id,results,first_supervisor_id,second_supervisor_id,first_examiner_id,second_examiner_id,expertise_id,et_notes.getText().toString(),final_title);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(CloseDraftActivity.this, "Berhasil !", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(CloseDraftActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(CloseDraftActivity.this, "Terjadi Kesalahan !", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Call<Void> call = praoutlineInterface.closeDraft(preoutline_id,results);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(CloseDraftActivity.this, "Berhasil !", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(CloseDraftActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(CloseDraftActivity.this, "Terjadi Kesalahan !", Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
