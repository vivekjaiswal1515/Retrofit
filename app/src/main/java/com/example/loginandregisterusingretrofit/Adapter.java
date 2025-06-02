package com.example.loginandregisterusingretrofit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginandregisterusingretrofit.ModelClass.FetchModel;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{
    ArrayList<FetchModel> fetchModelArrayList;
    Context context;

    public void SetFilterList(ArrayList<FetchModel> Filter){
        this.fetchModelArrayList=Filter;
        notifyDataSetChanged();
    }

    public Adapter(ArrayList<FetchModel> fetchModelArrayList, Context context) {
        this.fetchModelArrayList = fetchModelArrayList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.details_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FetchModel registerModel = fetchModelArrayList.get(position);
        holder.name.setText(registerModel.getName());
        holder.email.setText(registerModel.getEmail());
        holder.password.setText(registerModel.getPassword());
        holder.gender.setText(registerModel.getGender());
        holder.course.setText(registerModel.getCourse());
        holder.location.setText(registerModel.getLocation());
        holder.phone.setText(registerModel.getPhone());
    }

    @Override
    public int getItemCount() {
        return fetchModelArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
           TextView name,email,password,gender,course,location,phone;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_tv);
            email = itemView.findViewById(R.id.email_tv);
            password = itemView.findViewById(R.id.password_tv);
            gender = itemView.findViewById(R.id.gender_tv);
            course = itemView.findViewById(R.id.course_tv);
            location = itemView.findViewById(R.id.location_tv);
            phone = itemView.findViewById(R.id.phone_tv);
        }
    }
}
