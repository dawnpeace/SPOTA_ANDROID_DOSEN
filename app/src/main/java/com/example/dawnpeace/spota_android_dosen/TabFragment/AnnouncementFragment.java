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
import com.example.dawnpeace.spota_android_dosen.Model.Announcement;
import com.example.dawnpeace.spota_android_dosen.MyRecyclerViewAdapters.AnnouncementAdapter;
import com.example.dawnpeace.spota_android_dosen.R;
import com.example.dawnpeace.spota_android_dosen.RetrofitInterface.AnnouncementInterface;
import com.example.dawnpeace.spota_android_dosen.SharedPrefHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AnnouncementFragment extends Fragment {

    private View mView;
    private RecyclerView rv_announcement;
    private SharedPrefHelper mSharedPref;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RelativeLayout rl_progressbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_announcement,container,false);
        mSharedPref = SharedPrefHelper.getInstance(getActivity());
        rv_announcement = mView.findViewById(R.id.rv_announcement);
        swipeRefreshLayout = mView.findViewById(R.id.sr_announcement);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadAnnouncement();
            }
        });
        rl_progressbar = mView.findViewById(R.id.rl_progress_bar);
        loadAnnouncement();

        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadAnnouncement();
    }

    private void loadAnnouncement(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL)
                .client(mSharedPref.getInterceptor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AnnouncementInterface announcement = retrofit.create(AnnouncementInterface.class);
        Call<List<Announcement>> call = announcement.getAnnouncement();
        call.enqueue(new Callback<List<Announcement>>() {
            @Override
            public void onResponse(Call<List<Announcement>> call, Response<List<Announcement>> response) {
                if(response.isSuccessful()){
                    AnnouncementAdapter adapter = new AnnouncementAdapter(getActivity(),response.body());
                    rv_announcement.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rv_announcement.setAdapter(adapter);
                    swipeRefreshLayout.setVisibility(View.VISIBLE);
                    rl_progressbar.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    swipeRefreshLayout.setVisibility(View.VISIBLE);
                    rl_progressbar.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Announcement>> call, Throwable t) {
                swipeRefreshLayout.setVisibility(View.VISIBLE);
                rl_progressbar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
