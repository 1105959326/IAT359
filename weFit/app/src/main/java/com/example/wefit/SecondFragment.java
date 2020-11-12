package com.example.wefit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SecondFragment extends Fragment implements View.OnClickListener {

    private Button activityButton;


    private RecyclerView mRecyclerView;




    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState

    ) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


//

    }
    public void onStart() {
        super.onStart();

    }
    private class VH extends RecyclerView.ViewHolder {
        TextView tv1;
        TextView tv2;

        public VH(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.tv1);
            tv2 = itemView.findViewById(R.id.tv2);
        }
    }
    private class LetterAdapter extends RecyclerView.Adapter<VH> {

        private List<Character> dataList;

        public LetterAdapter(List<Character> dataList) {
            this.dataList = dataList;
        }

        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.header, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull VH holder, int position) {
            Character c = dataList.get(position);
            holder.tv1.setText(c.toString());
            holder.tv2.setText(String.valueOf(Integer.valueOf(c)));
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }
    }


    @Override
    public void onClick(View v) {

    }
}