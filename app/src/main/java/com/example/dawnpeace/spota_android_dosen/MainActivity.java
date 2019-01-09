package com.example.dawnpeace.spota_android_dosen;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.dawnpeace.spota_android_dosen.Model.User;
import com.example.dawnpeace.spota_android_dosen.RetrofitInterface.Auth;
import com.example.dawnpeace.spota_android_dosen.TabFragment.AnnouncementFragment;
import com.example.dawnpeace.spota_android_dosen.TabFragment.ConsultationFragment;
import com.example.dawnpeace.spota_android_dosen.TabFragment.DraftFragment;
import com.example.dawnpeace.spota_android_dosen.TabFragment.ReviewedDraftFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private BottomNavigationView mBottomNav;
    private FrameLayout mFrame;
    private SharedPrefHelper mSharedPref;
    private NavigationView navDrawer;
    private ImageView iv_nav_header;
    private TextView tv_nav_name;
    private TextView tv_nav_identity_number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSharedPref = SharedPrefHelper.getInstance(this);
        checkTokenAvailability();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        mDrawerLayout = findViewById(R.id.main_drawer);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navDrawer = findViewById(R.id.nav_view);

        View header = navDrawer.getHeaderView(0);
        iv_nav_header = header.findViewById(R.id.iv_header_pp);
        tv_nav_name = header.findViewById(R.id.tv_username);
        tv_nav_identity_number = header.findViewById(R.id.tv_user_identity_number);

        navDrawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_notification_list:
                        Intent intent1 = new Intent(MainActivity.this,NotificationActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.nav_statistic:
                        Intent intent2 = new Intent(MainActivity.this,StatisticActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.nav_schedule:
                        Intent intent3 = new Intent(MainActivity.this,ScheduleActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.nav_search:
                        Intent intent4 = new Intent(MainActivity.this,SearchActivity.class);
                        startActivity(intent4);
                        break;
                    case R.id.nav_profile:
                        Intent intent5 = new Intent(MainActivity.this,ProfileActivity.class);
                        startActivity(intent5);
                        break;
                    case R.id.nav_logout:
                        logout();
                        break;
                }
                return true;
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_layout, new DraftFragment()).commit();
        mBottomNav = (BottomNavigationView) findViewById(R.id.bottom_nav);
        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment mFragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.new_preoutline_menu:
                        mFragment = new DraftFragment();
                        break;
                    case R.id.reviewed_preoutline_menu:
                        mFragment = new ReviewedDraftFragment();
                        break;
                    case R.id.consultation_menu:
                        mFragment = new ConsultationFragment();
                        break;
                    case R.id.announcement_menu:
                        mFragment = new AnnouncementFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_layout, mFragment).commit();
                return true;
            }
        });


        if (mSharedPref.getToken() != null) {
            getAuthUser();
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAuthUser();
    }

    private void getAuthUser() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL)
                .client(mSharedPref.getInterceptor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Auth auth = retrofit.create(Auth.class);
        Call<User> call = auth.getUser();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    mSharedPref.storeUser(response.body());
                    if(mSharedPref.getUser() != null){
                        Glide.with(MainActivity.this)
                                .load(mSharedPref.getUser().getPictureUrl())
                                .apply(RequestOptions.circleCropTransform())
                                .apply(new RequestOptions()
                                        .error(R.drawable.ic_person_grey_24dp)
                                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                                        .skipMemoryCache(true))
                                .into(iv_nav_header);

                        tv_nav_name.setText(mSharedPref.getUser().getName());
                        tv_nav_identity_number.setText(mSharedPref.getUser().getIdentity_number());

                        if(!mSharedPref.getUser().isMajorheadmaster()){
                            navDrawer.getMenu().getItem(2).setVisible(false);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });


    }

    private void logout() {
        AlertDialog.Builder logoutAlert = new AlertDialog.Builder(this,R.style.AlertDialog);

        logoutAlert.setMessage("Apakah anda yakin untuk keluar ?")
                .setCancelable(true)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL)
                                .client(mSharedPref.getInterceptor())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        Auth auth = retrofit.create(Auth.class);
                        Call<Void> call = auth.destroyFcmToken();
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if(response.isSuccessful()){
                                    mSharedPref.logout();
                                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                    new LogoutTask().execute();
                                    startActivity(intent);
                                    finish();
                                } else {
                                    response.message();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(MainActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).create();
        logoutAlert.show();

    }

    private void checkTokenAvailability(){
        if(mSharedPref.getFirebaseToken() != null ){
            Log.d("FCMTOKEN", "onResponse: "+mSharedPref.getFirebaseToken());
            if(!mSharedPref.issetFCMToken()){
                Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL)
                        .client(mSharedPref.getInterceptor())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                Auth auth = retrofit.create(Auth.class);
                Call<Void> call = auth.storeFCMToken(mSharedPref.getFirebaseToken());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            mSharedPref.setFcmTokenAvailability(true);
                            Log.d("FCMTOKEN", "onResponse: "+mSharedPref.getFirebaseToken());
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        }
    }



}
