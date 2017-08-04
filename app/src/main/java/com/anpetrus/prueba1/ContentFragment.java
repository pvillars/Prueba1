package com.anpetrus.prueba1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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
        final RadioGroup genderGrb = view.findViewById(R.id.genderGroupRb);

        genderGrb.setVisibility(View.GONE);
        nameEt.setVisibility(View.VISIBLE);
        backBtn.setVisibility(View.GONE);
        step = Step.NAME;

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (step) {
                    case Step.NAME:
                        if (!nameEt.getText().toString().trim().equals("")) {
                            nameEt.setVisibility(View.GONE);
                            genderGrb.setVisibility(VISIBLE);
                            nextBtn.setText(R.string.finish_button);
                            backBtn.setVisibility(VISIBLE);
                            step = Step.GENDER;
                            try {
                                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                            } catch (Exception e) {
                                Log.e("ERROR", e.getMessage());
                            }
                        } else {
                            Toast.makeText(getContext(), R.string.please_enter_name, Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case Step.GENDER:
                        int genderId = genderGrb.getCheckedRadioButtonId();
                        if(genderId > -1 && genderId <= 2) {
                            Intent intent = new Intent(getActivity(), ResultActivity.class);
                            intent.putExtra(Constants.EXTRA_NAME, nameEt.getText().toString());
                            intent.putExtra(Constants.EXTRA_GENDER_SELECTED, genderId);
                            startActivity(intent);
                        }else if(genderId > 2){
                            Toast.makeText(getContext(), R.string.problem_with_selection, Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getContext(), R.string.please_select_gender, Toast.LENGTH_SHORT).show();
                        }
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
                        genderGrb.setVisibility(View.GONE);
                        nextBtn.setText(R.string.next_button);
                        backBtn.setVisibility(View.GONE);
                        step = Step.NAME;
                        break;
                }
            }
        });
    }


    private static class Step {
        private static final int NAME = 1;
        private static final int GENDER = 2;
    }

}
