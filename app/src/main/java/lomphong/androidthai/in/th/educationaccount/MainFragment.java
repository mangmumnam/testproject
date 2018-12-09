package lomphong.androidthai.in.th.educationaccount;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.data.SingleRefDataBufferIterator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {


    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        SignUp Controller
        signUpController();

//        signIn Controller
        signInController();

    }  //Main Method

    private void signInController() {
        Button button = getView().findViewById(R.id.buttonSignIn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText emailEditText = getView().findViewById(R.id.editTextEmail);
                EditText passwordEditText = getView().findViewById(R.id.editTextPassword);
                final MyAlert myAlert = new MyAlert(getActivity());

                String emailString = emailEditText.getText().toString().trim();
                String passwordString = passwordEditText.getText().toString().trim();

                if (emailString.isEmpty() || passwordString.isEmpty()) {
                    myAlert.normalDialog(getString(R.string.title_have_space), getString(R.string.masseage_have_space));

                } else {
//                    Check Authen
                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    firebaseAuth.signInWithEmailAndPassword(emailString,passwordString)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                getActivity().getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.contentMainFragment, new ServiceFragment()).commit();
                            } else {
                                myAlert.normalDialog("Authen False" , task.getException().toString());
                            }

                        }
                    });

                }

            }
        });
    }


    private void signUpController() {
        Button button = getView().findViewById(R.id.buttonSignUp);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Replace fragment
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentMainFragment, new RegisterFragment()).addToBackStack(null).commit();

            }
        });
    } //        SignUp Controller

    @Override
    public void onResume() {
        super.onResume();

//        Check Authen
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getUid() != null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contentMainFragment, new ServiceFragment())
                    .commit();
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

}
