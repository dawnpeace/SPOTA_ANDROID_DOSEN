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
import com.example.dawnpeace.spota_android_dosen.Model.Praoutline;
import com.example.dawnpeace.spota_android_dosen.MyRecyclerViewAdapters.PraoutlineDraftAdapter;
import com.example.dawnpeace.spota_android_dosen.R;
import com.example.dawnpeace.spota_android_dosen.RetrofitInterface.PraoutlineInterface;
import com.example.dawnpeace.spota_android_dosen.SharedPrefHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DraftFragment extends Fragment {
    private RecyclerView rv_unreviewed_draft;
    private View mainView;
    private SharedPrefHelper mSharedPref;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RelativeLayout rl_progressbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_draft,container,false);
        rv_unreviewed_draft = mainView.findViewById(R.id.rv_unreviewed_draft);
        swipeRefreshLayout = mainView.findViewById(R.id.sr_draft);
        rl_progressbar = mainView.findViewById(R.id.rl_progress_bar);
        mSharedPref = SharedPrefHelper.getInstance(getActivity());
        loadDrafts();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadDrafts();
            }
        });
        return mainView;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadDrafts();
    }

    private void loadDrafts(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL)
                .client(mSharedPref.getInterceptor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PraoutlineInterface drafts = retrofit.create(PraoutlineInterface.class);
        Call<List<Praoutline>> call = drafts.getNewDrafts();
        call.enqueue(new Callback<List<Praoutline>>() {
            @Override
            public void onResponse(Call<List<Praoutline>> call, Response<List<Praoutline>> response) {
                if(response.isSuccessful()){
                    swipeRefreshLayout.setVisibility(View.VISIBLE);
                    rl_progressbar.setVisibility(View.GONE);
                    PraoutlineDraftAdapter adapter = new PraoutlineDraftAdapter(getActivity(),response.body());
                    rv_unreviewed_draft.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rv_unreviewed_draft.setAdapter(adapter);
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    swipeRefreshLayout.setVisibility(View.VISIBLE);
                    rl_progressbar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);

                }
            }

            @Override
            public void onFailure(Call<List<Praoutline>> call, Throwable t) {
                swipeRefreshLayout.setVisibility(View.VISIBLE);
                rl_progressbar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Terjadi Kesalahan !", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
