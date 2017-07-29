package com.anpetrus.prueba1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import static android.view.View.VISIBLE;


public class ContentFragment extends Fragment {
    public ContentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_content, container, false);
    }

    int step = Step.NAME;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {//Solo vistas del fragmento
        super.onViewCreated(view, savedInstanceState);

        final Button nextBtn = view.findViewById(R.id.nextBtn);
        final Button backBtn = view.findViewById(R.id.backBtn);
        final EditText nameEt = view.findViewById(R.id.nameEt);
        final RadioGroup genderGRB = view.findViewById(R.id.genderGroupRb);
        final RadioButton genderMaleRB = view.findViewById(R.id.genderMaleRb);

        genderGRB.setVisibility(View.GONE);
        nameEt.setVisibility(View.VISIBLE);
        backBtn.setVisibility(View.GONE);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (step) {
                    case Step.NAME:
                        if (!nameEt.getText().toString().trim().equals("")) {
                            //nameEt.animate().translationX(0).setDuration(600);
                            nameEt.setVisibility(View.GONE);
                            genderGRB.setVisibility(VISIBLE);
                            nextBtn.setText(R.string.finish_button);
                            backBtn.setVisibility(VISIBLE);
                            step = Step.GENDER;
                            try {
                                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                            } catch (Exception e) {

                            }
                        } else {
                            Toast.makeText(getContext(), "Favor ingresa tu Nombre", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case Step.GENDER:
                        Intent intent = new Intent(getActivity(), ResultActivity.class);
                        intent.putExtra("NAME", nameEt.getText().toString());
                        intent.putExtra("GENDER_MALE", genderMaleRB.isChecked());
                        startActivity(intent);
                        break;
                }

            }
        });


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (step) {
                    case Step.GENDER:
                        nameEt.setVisibility(View.VISIBLE);
                        genderGRB.setVisibility(View.GONE);
                        nextBtn.setText(R.string.next_button);
                        backBtn.setVisibility(View.GONE);
                        step = Step.NAME;
                        break;
                }
            }
        });

    }

    static class Step {
        private static final int NAME = 1;
        private static final int GENDER = 2;
    }

}
