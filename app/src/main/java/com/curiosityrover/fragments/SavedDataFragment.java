package com.curiosityrover.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.curiosityrover.R;
import com.curiosityrover.design.CustomAdapter;
import com.curiosityrover.model.CustomImageCard;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SavedDataFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    ListView listView;
    TextView message;

    TextView title;
    ArrayList<CustomImageCard> savedDataList;

    public SavedDataFragment() {

    }

    public static SavedDataFragment newInstance(String param1, String param2) {
        SavedDataFragment fragment = new SavedDataFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_result, container, false);

    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        message = getView().findViewById(R.id.result_message);
        listView = getView().findViewById(R.id.result_list_view);
        listView.setOnItemClickListener(listListener);
        title = getView().findViewById(R.id.result_title);
        title.setText("Saved Data");
        loadData();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    public void loadData() {
        SharedPreferences sp = getActivity().getApplicationContext().getSharedPreferences("ROVER_IMAGE_DATA", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sp.getString("image_data", null);

        Type type = new TypeToken<ArrayList<CustomImageCard>>(){}.getType();
        savedDataList = gson.fromJson(json, type);

        if(savedDataList == null) {
            message.setText("No saved data found...");
        } else if ( savedDataList.size() == 0){
            message.setText("No saved data found...");
            CustomAdapter adapter = new CustomAdapter(getActivity(),savedDataList);
            listView.setAdapter(adapter);
        }  else{
            CustomAdapter adapter = new CustomAdapter(getActivity(),savedDataList);
            listView.setAdapter(adapter);
        }

    }
    AdapterView.OnItemClickListener listListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent descriptionLaunch = new Intent();
            if(!(savedDataList == null || savedDataList.size()==0)) {
                CustomImageCard card = savedDataList.get(position);
                descriptionLaunch.setClassName("com.curiosityrover", "com.curiosityrover.SavedCardDescription");
                descriptionLaunch.putExtra("url", card.getImage_url());
                descriptionLaunch.putExtra("sol", card.getSol());
                descriptionLaunch.putExtra("camera", card.getCamera());
                descriptionLaunch.putExtra("img_id", card.getImage_id());
                descriptionLaunch.putExtra("earth_date", card.getEarth_date());
                descriptionLaunch.putExtra("position", position);
                startActivity(descriptionLaunch);
            }
        }
    };
}