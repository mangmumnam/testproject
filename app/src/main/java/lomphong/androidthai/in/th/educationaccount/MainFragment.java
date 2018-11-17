package lomphong.androidthai.in.th.educationaccount;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


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
        Button button = getView().findViewById(R.id.buttonSignUp);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Replace fragment
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentMainFragment, new RegisterFragment()).addToBackStack(null).commit();

            }
        });

    }  //Main Method

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

}
