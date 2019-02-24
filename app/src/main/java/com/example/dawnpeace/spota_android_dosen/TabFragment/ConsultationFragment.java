package com.example.dawnpeace.spota_android_dosen.TabFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.dawnpeace.spota_android_dosen.APIUrl;
import com.example.dawnpeace.spota_android_dosen.Model.Consultation;
import com.example.dawnpeace.spota_android_dosen.MyRecyclerViewAdapters.ConsultationAdapter;
import com.example.dawnpeace.spota_android_dosen.R;
import com.example.dawnpeace.spota_android_dosen.RetrofitInterface.ConsultationInterface;
import com.example.dawnpeace.spota_android_dosen.SharedPrefHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConsultationFragment extends Fragment {
    private RecyclerView rv_consultation;
    private SharedPrefHelper mSharedPref;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RelativeLayout rl_progressbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consultation,container,false);
        mSharedPref = SharedPrefHelper.getInstance(getActivity());
        rv_consultation = view.findViewById(R.id.rv_consultation_list);
        rl_progressbar = view.findViewById(R.id.rl_progress_bar);
        swipeRefreshLayout = view.findViewById(R.id.sr_consultation);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
        loadData();

        return view;
    }


    private void loadData(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL)
                .client(mSharedPref.getInterceptor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ConsultationInterface consultation = retrofit.create(ConsultationInterface.class);
        Call<List<Consultation>> call = consultation.getConsultationList();
        call.enqueue(new Callback<List<Consultation>>() {
            @Override
            public void onResponse(Call<List<Consultation>> call, Response<List<Consultation>> response) {
                if(response.isSuccessful()){
                    swipeRefreshLayout.setVisibility(View.VISIBLE);
                    rl_progressbar.setVisibility(View.GONE);
                    rv_consultation.setLayoutManager(new LinearLayoutManager(getActivity()));
                    ConsultationAdapter adapter = new ConsultationAdapter(getActivity(),response.body());
                    rv_consultation.setAdapter(adapter);
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    swipeRefreshLayout.setVisibility(View.VISIBLE);
                    rl_progressbar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<List<Consultation>> call, Throwable t) {
                swipeRefreshLayout.setVisibility(View.VISIBLE);
                rl_progressbar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Terjadi Kesalahan !", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
