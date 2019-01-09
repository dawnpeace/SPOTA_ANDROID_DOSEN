package com.example.dawnpeace.spota_android_dosen.SpinnerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dawnpeace.spota_android_dosen.Model.Lecturer;
import com.example.dawnpeace.spota_android_dosen.R;

import java.util.List;

public class LecturerAdapter extends BaseAdapter {
    private Context context;
    private List<Lecturer> lecturers;

    public LecturerAdapter(Context context, List<Lecturer> lecturers) {
        this.context = context;
        this.lecturers = lecturers;
    }

    @Override
    public int getCount() {
        return lecturers.size();
    }

    @Override
    public Object getItem(int position) {
        return lecturers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Lecturer lecturer = lecturers.get(position);
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.lecturer_spinner_layout,parent,false);
            TextView tv_name = convertView.findViewById(R.id.tv_lecturer_name);
            TextView tv_identity_number = convertView.findViewById(R.id.tv_lecturer_id);
            tv_name.setText(lecturer.getName());
            tv_identity_number.setText(lecturer.getIdentity_number());
        }
        return convertView;
    }
}
