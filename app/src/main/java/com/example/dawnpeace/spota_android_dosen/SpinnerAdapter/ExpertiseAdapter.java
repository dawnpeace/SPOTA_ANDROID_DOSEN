package com.example.dawnpeace.spota_android_dosen.SpinnerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dawnpeace.spota_android_dosen.Model.Expertise;
import com.example.dawnpeace.spota_android_dosen.R;


import java.util.List;

public class ExpertiseAdapter extends BaseAdapter {
    private Context context;
    private List<Expertise> expertises;

    public ExpertiseAdapter(Context context, List<Expertise> expertises) {
        this.context = context;
        this.expertises = expertises;
    }

    @Override
    public int getCount() {
        return expertises.size();
    }

    @Override
    public Object getItem(int position) {
        return expertises.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Expertise expertise = expertises.get(position);
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.expertise_spinner_layout,parent,false);
            TextView tv_name = convertView.findViewById(R.id.tv_expertise_spinner_name);
            tv_name.setText(expertise.getName());
        }
        return convertView;
    }
}
