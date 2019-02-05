package com.johnyhawkdesigns.a55_childhealthapp_1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.johnyhawkdesigns.a55_childhealthapp_1.R;
import com.johnyhawkdesigns.a55_childhealthapp_1.database.ChildViewModel;
import com.johnyhawkdesigns.a55_childhealthapp_1.model.Child;
import com.johnyhawkdesigns.a55_childhealthapp_1.util.AppUtils;

import java.util.List;

public class ChildListAdapter extends RecyclerView.Adapter<ChildListAdapter.ChildViewHolder>{

    private static final String TAG = ChildListAdapter.class.getSimpleName();
    Context mContext;
    private final LayoutInflater mInflator;
    private List<Child> mChilds;

    // interface implemented by MainActivity to respond when the user touches an item in the RecyclerView.
    public interface ChildClickListener{
        void onClick(int chID);
    }

    private final ChildClickListener childClickListener;

    //Constructor for WordListAdapter
    public ChildListAdapter(Context context, ChildClickListener childClickListener){
        mContext = context;
        this.childClickListener = childClickListener;
        mInflator = LayoutInflater.from(context);
    }

    // Instead of using childList in a constructor, we use it here so we can use in MainActivity's observer.
    public void setChildList(List<Child> children){
        this.mChilds = children;
        notifyDataSetChanged();
    }

    //ChildViewHolder class
    class ChildViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final TextView chID, chName, gender, age;
        private final ImageView childIcon;

        public ChildViewHolder(@NonNull View itemView) {
            super(itemView);
            chID = itemView.findViewById(R.id.chID);
            chName = itemView.findViewById(R.id.chName);
            gender = itemView.findViewById(R.id.gender);
            age = itemView.findViewById(R.id.age);
            childIcon = itemView.findViewById(R.id.childIcon);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Child child = mChilds.get(getAdapterPosition());
            String chName = child.getName();
            int chID = child.getChID();
            AppUtils.showMessage(mContext, "Clicked on child with name = " + chName + ", chID = " + chID);
            Log.d(TAG, "onClick: Clicked on child with name = " + chName + ", chID = " + chID);

            // This interface method sends chID to MainActivity's ChildListAdapter constructor method.
            childClickListener.onClick(chID);

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
        childViewHolder.childIcon.setImageResource(R.drawable.random_baby);
        childViewHolder.chID.setText(String.valueOf(currentChild.getChID()));
        childViewHolder.gender.setText(currentChild.getGender());
        childViewHolder.chName.setText(currentChild.getName());
        String ageStr = String.valueOf(currentChild.getAge());
        childViewHolder.age.setText(ageStr + " years");

        Log.d(TAG, "onBindViewHolder: currentChild.getChID() = " + currentChild.getChID() + ", currentChild.getChID() = " + currentChild.getName());
    }

    // getItemCount() is called many times, and when it is first called,
    // mChilds has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        return mChilds.size();
    }



}
