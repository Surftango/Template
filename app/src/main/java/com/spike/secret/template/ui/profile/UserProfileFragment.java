package com.spike.secret.template.ui.profile;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.spike.secret.template.R;
import com.spike.secret.template.model.Consumer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Shyam on 2/5/17.
 */

public class UserProfileFragment extends Fragment implements ProfileContract.View, View.OnClickListener {

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;

    ViewGroup profileView;
    ViewGroup loginView;
    TextInputLayout emailView,passwordView;

    ProfileContract.Presenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter(new ProfilePresenter(this,getContext()));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_profile, container, false);
        profileView = (ViewGroup)rootView.findViewById(R.id.profile_view);
        loginView = (ViewGroup)rootView.findViewById(R.id.login_view);
        emailView = (TextInputLayout) rootView.findViewById(R.id.input_email);
        passwordView = (TextInputLayout) rootView.findViewById(R.id.input_password);
        rootView.findViewById(R.id.btn_login).setOnClickListener(this);
        if(presenter.token()!=null){
            loginView.setVisibility(View.GONE);
            presenter.getConsumerProfile();
        }
        return rootView;
    }


    @Override
    public void onClick(View v) {
        //hide Keyboard
        try{
            if (v != null) {
                ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).
                        hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }catch (Exception supress){}

        emailView = null;
        //Validate
        String username = emailView.getEditText().getText().toString();
        String password = passwordView.getEditText().getText().toString();
        if (!validateEmail(username)) {
            emailView.setError("Not a valid email address!");
        } else if (!validatePassword(password)) {
            passwordView.setError("Not a valid password!");
        } else {
            //perform operation
            emailView.setErrorEnabled(false);
            passwordView.setErrorEnabled(false);
            presenter.authenticateAndRetrieve(username,password);
        }
    }

    public boolean validateEmail(String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean validatePassword(String password) {
        return password.length() > 6;
    }

    @Override
    public void updateView(Consumer consumer) {
        try {
            ((TextView)profileView.findViewById(R.id.phonenumber)).setText(consumer.getPhone_number());
            ((TextView)profileView.findViewById(R.id.name)).setText(consumer.getFirst_name()+" "+consumer.getLast_name());
            ((TextView)profileView.findViewById(R.id.email)).setText(consumer.getEmail());
        }catch (Exception supress){}
        loginView.setVisibility(View.GONE);
        profileView.setVisibility(View.VISIBLE);

    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(loginView,message,Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void setPresenter(@NonNull ProfileContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("JWT",presenter.token());
        super.onSaveInstanceState(outState);
    }
}
