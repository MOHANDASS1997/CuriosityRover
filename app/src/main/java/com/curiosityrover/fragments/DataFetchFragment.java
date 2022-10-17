package com.curiosityrover.fragments;


import android.app.DatePickerDialog;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;
import com.curiosityrover.R;


public class DataFetchFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;


    String selectedCamera, dataUrl;
    EditText date, pages;
    Button getData;
    ImageButton getInputDate;
    Spinner cameraTypes;



    public DataFetchFragment() {

    }

    public static DataFetchFragment newInstance(String param1, String param2) {
        DataFetchFragment fragment = new DataFetchFragment();
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
        return inflater.inflate(R.layout.fragment_data_fetch, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        date = getView().findViewById(R.id.entry_date);
        pages = getView().findViewById(R.id.entry_page);
        getInputDate = getView().findViewById(R.id.button_date);
        getInputDate.setOnClickListener(datePickListener);

        cameraTypes = getView().findViewById(R.id.entry_camera);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.camera_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        cameraTypes.setAdapter(adapter);
        cameraTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCamera = parent.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedCamera="All";
            }
        });

        getData = getView().findViewById(R.id.get_data_button);
        getData.setOnClickListener(listener);

    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String inputData = "";
            String currentDate = date.getText().toString();
            String currentPage = pages.getText().toString();



            if(!currentDate.equals("")){
                inputData = "earth_date=" + currentDate;

                if(!selectedCamera.equals("All")){
                    inputData = inputData + "&camera=" + selectedCamera;
                }
                if(!currentPage.equals("")){
                    inputData = inputData +"&page=" + currentPage;
                } else {
                    inputData = inputData +"&page=1";
                }
                dataUrl = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?" + inputData+ "&api_key=UH4onJQtjSSDw0SgKrfUOjsBm6Q6RmeNuOV8j95N";
                Bundle result = new Bundle();
                result.putString("url", dataUrl);
                getParentFragmentManager().setFragmentResult("imageLists", result);
                Toast.makeText(getActivity(), "Click on Result tab to view results", Toast.LENGTH_SHORT).show();

//                ResultFragment res = new ResultFragment();
//                FragmentManager manager = getActivity().getSupportFragmentManager();
//                FragmentTransaction transaction = manager.beginTransaction();
//                transaction.replace(R.id.view_pager, new ResultFragment()).addToBackStack(null).commit();


            }
            else {
                Toast.makeText(getActivity(), "Enter mandatory field : Date ", Toast.LENGTH_SHORT).show();
            }

        }
    };

    View.OnClickListener datePickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getActivity(),
                    R.style.MyDatePickerDialogTheme,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            date.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
                        }
                    },year, month, day);
            datePickerDialog.show();
        }
    };



}