package com.johnyhawkdesigns.a55_childhealthapp_1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.johnyhawkdesigns.a55_childhealthapp_1.R;
import com.johnyhawkdesigns.a55_childhealthapp_1.model.Child;

import java.util.List;

public class ChildListAdapter extends RecyclerView.Adapter<ChildListAdapter.ChildViewHolder>{

    private static final String TAG = ChildListAdapter.class.getSimpleName();
    Context mContext;
    private final LayoutInflater mInflator;
    private List<Child> mChilds;
    private int mSelectedItemIndex;

    //Constructor for WordListAdapter
    public ChildListAdapter(Context context){
        mContext = context;
        mInflator = LayoutInflater.from(context);
    }

    // Instead of using childList in a constructor, we use it here so we can use in MainActivity's observer.
    public void setChildList(List<Child> children){
        this.mChilds = children;
    }

    //ChildViewHolder class
    class ChildViewHolder extends RecyclerView.ViewHolder{

        private final TextView chID, chName, gender, age;

        public ChildViewHolder(@NonNull View itemView) {
            super(itemView);
            chID = itemView.findViewById(R.id.chID);
            chName = itemView.findViewById(R.id.chName);
            gender = itemView.findViewById(R.id.gender);
            age = itemView.findViewById(R.id.age);

        }
    }


    @NonNull
    @Override
    public ChildViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = mInflator.inflate(R.layout.recycler_item, viewGroup, false);
        return new ChildViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildViewHolder childViewHolder, int position) {
        Child currentChild = mChilds.get(position);
        childViewHolder.chID.setText(currentChild.getChID());
        childViewHolder.gender.setText(currentChild.getGender());
        childViewHolder.chName.setText(currentChild.getName());
        String ageStr = String.valueOf(currentChild.getAge());
        childViewHolder.age.setText(ageStr);
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        return mChilds.size();
    }


}
