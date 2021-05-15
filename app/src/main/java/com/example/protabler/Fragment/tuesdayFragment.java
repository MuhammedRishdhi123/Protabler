package com.example.protabler.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.protabler.API.API;
import com.example.protabler.API.API_BASE_URL;
import com.example.protabler.Adapter.moduleListAdapter;
import com.example.protabler.Adapter.sessionListAdapter;
import com.example.protabler.Dto.SessionDTO;
import com.example.protabler.JsonList.SessionList;
import com.example.protabler.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link tuesdayFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link tuesdayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class tuesdayFragment extends ListFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    API api;
    public SharedPreferences sharedPreferences;
    private String url;

    private OnFragmentInteractionListener mListener;

    public tuesdayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment tuesdayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static tuesdayFragment newInstance(String param1, String param2) {
        tuesdayFragment fragment = new tuesdayFragment();
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
        API_BASE_URL base_url=new API_BASE_URL();
        url=base_url.getURL();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api=retrofit.create(API.class);
        sharedPreferences=getActivity().getSharedPreferences("session", Context.MODE_PRIVATE);
        if(sharedPreferences.getString("role","none").equalsIgnoreCase("Student")) {
            try {
                Call<SessionList> call = api.getTimetable(sharedPreferences.getInt("userId", -1));

                call.enqueue(new Callback<SessionList>() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onResponse(Call<SessionList> call, Response<SessionList> response) {
                        SessionList list = response.body();
                        List<SessionDTO> sessions = list.getSessionList();
                        List<SessionDTO> mondaySessions = new ArrayList<>();
                        for (SessionDTO s : sessions) {
                            if (s.getDay().equalsIgnoreCase("TUESDAY")) {
                                mondaySessions.add(s);
                            }
                        }
                        ArrayAdapter adapter = new sessionListAdapter(getActivity(), R.layout.day_info_single_item, mondaySessions);
                        setListAdapter(adapter);

                    }

                    @Override
                    public void onFailure(Call<SessionList> call, Throwable t) {
                        Toast.makeText(getActivity(), "Something went wrong !" + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            } catch (Exception e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        else if(sharedPreferences.getString("role","none").equalsIgnoreCase("Lecturer")){
            try {
                Call<SessionList> call = api.getTimetableLecturer(sharedPreferences.getInt("userId", -1));

                call.enqueue(new Callback<SessionList>() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onResponse(Call<SessionList> call, Response<SessionList> response) {
                        SessionList list = response.body();
                        List<SessionDTO> sessions = list.getSessionList();
                        List<SessionDTO> mondaySessions = new ArrayList<>();
                        for (SessionDTO s : sessions) {
                            if (s.getDay().equalsIgnoreCase("TUESDAY")) {
                                mondaySessions.add(s);
                            }
                        }
                        ArrayAdapter adapter = new sessionListAdapter(getActivity(), R.layout.day_info_single_item, mondaySessions);
                        setListAdapter(adapter);

                    }

                    @Override
                    public void onFailure(Call<SessionList> call, Throwable t) {
                        Toast.makeText(getActivity(), "Something went wrong !" + t.getCause(), Toast.LENGTH_LONG).show();
                    }
                });
            } catch (Exception e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        return inflater.inflate(R.layout.fragment_tuesday, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
