package android.javapapers.com.majorproject.Fragments;

import android.content.Context;
import android.content.Intent;
import android.javapapers.com.majorproject.R;
import android.javapapers.com.majorproject.Activities.verfication_of_otp;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentSecond.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentSecond#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSecond extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private EditText number,name;
    private Button send;

    private String phonenumber,person_name;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragmentSecond() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentSecond.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentSecond newInstance(String param1, String param2) {
        FragmentSecond fragment = new FragmentSecond();
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
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_second, container, false);
        number=view.findViewById(R.id.phonenumber);
        name=view.findViewById(R.id.name);
        send=view.findViewById(R.id.sendotp);




        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                phonenumber=number.getText().toString().trim();
                person_name=name.getText().toString().trim();

                Log.d("pn",phonenumber);
                if(phonenumber.isEmpty()||number.getText().toString().trim().length()<10){
                    Toast.makeText(getContext(), "Enter a valid phone number", Toast.LENGTH_SHORT).show();
                    number.requestFocus();
                    return;
                }
                else{
                    Intent i =new Intent(getActivity(), verfication_of_otp.class);
                    i.putExtra("phonenumber",phonenumber);
                    i.putExtra("name",person_name);
                    getFragmentManager().beginTransaction().remove(FragmentSecond.this).commit();
                    startActivity(i);
                }
            }
        });



        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

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
