package com.curiosityrover.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.curiosityrover.R;
import com.curiosityrover.design.CustomAdapter;
import com.curiosityrover.model.CustomImageCard;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;



public class ResultFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    ListView listView;
    TextView message;
    String inputUrl;


    ArrayList<CustomImageCard> imageLists;

    public ResultFragment() {
        // Required empty public constructor
    }


    public static ResultFragment newInstance(String param1, String param2) {
        ResultFragment fragment = new ResultFragment();
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
        if(savedInstanceState != null){
            inputUrl = savedInstanceState.getString("url");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        message = getView().findViewById(R.id.result_message);

        listView = getView().findViewById(R.id.result_list_view);
        listView.setOnItemClickListener(listListener);

        if(inputUrl != null){
            getResult();
        }else {
            getParentFragmentManager().setFragmentResultListener("imageLists", getActivity(), new FragmentResultListener() {
                @Override
                public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                    inputUrl = result.getString("url");
                        message.setText("");
                        getResult();
                }
            });
        }

    }

    public void getResult(){
        imageLists = new ArrayList<>() ;
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, inputUrl,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray res = response.getJSONArray("photos");

                            for (int i=0; i< res.length(); i++){
                                imageLists.add(new CustomImageCard(res.getJSONObject(i)));
                            }
                            if(imageLists.size() != 0) {
                                setListAdapter(imageLists);
                            } else {
                                setListAdapter(new ArrayList<CustomImageCard>());
                                message.setText("No data available for the given input");
                            }
                        } catch (Exception e) {
                            setListAdapter(new ArrayList<CustomImageCard>());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setListAdapter(new ArrayList<CustomImageCard>());
                message.setText("Incorrect input\nTry again...");
            }
        });
        queue.add(request);

    }

    AdapterView.OnItemClickListener listListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent descriptionLaunch = new Intent();
            CustomImageCard card = imageLists.get(position);
            descriptionLaunch.setClassName("com.curiosityrover", "com.curiosityrover.CardDescription");
            descriptionLaunch.putExtra("url", card.getImage_url());
            descriptionLaunch.putExtra("sol", card.getSol());
            descriptionLaunch.putExtra("camera", card.getCamera());
            descriptionLaunch.putExtra("img_id", card.getImage_id());
            descriptionLaunch.putExtra("earth_date", card.getEarth_date());
            startActivity(descriptionLaunch);
        }
    };

    public void setListAdapter (ArrayList<CustomImageCard> imageLists){
        CustomAdapter adapter = new CustomAdapter(getActivity(), imageLists);
        listView.setAdapter(adapter);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("url", inputUrl);
    }
}