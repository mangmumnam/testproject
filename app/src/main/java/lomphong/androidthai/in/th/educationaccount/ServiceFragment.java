package lomphong.androidthai.in.th.educationaccount;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceFragment extends Fragment {
    private String currentUid;


    public ServiceFragment() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        Find Uid;
        findUid();

//        Create Toolbar
        createToolbar();

//        Post Controller
        postController();

    } // Main Method

    private void findUid() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        currentUid = firebaseAuth.getUid();
    }

    private void postController() {
        Button button = getView().findViewById(R.id.buttonPost);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText editText = getView().findViewById(R.id.edittextmassage);
                final String messageString = editText.getText().toString().trim();

                if (messageString.isEmpty()) {
                    MyAlert myAlert = new MyAlert(getActivity());
                    myAlert.normalDialog(getString(R.string.title_have_space),
                            getString(R.string.masseage_have_space));

                } else {

                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = firebaseDatabase.getReference().child("User").child(currentUid);

                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            UserModel userModel = dataSnapshot.getValue(UserModel.class);
                            UserModel userModel1 = new UserModel(
                                    userModel.getAvatarString(),
                                    messageString,
                                    userModel.getNameString(),
                                    userModel.getUidString());

                            replaceMessage(userModel1);



                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    editText.setText("");

                }       //if



            }
        });
    }

    private void replaceMessage(UserModel userModel1) {
        FirebaseDatabase firebaseDatabase =FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("User").child(currentUid);
        databaseReference.setValue(userModel1).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getActivity(), "Upload Meassage OK", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.itemExit) {

            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.signOut();
            getActivity().finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_service,menu);



    }



    private void createToolbar() {
        Toolbar toolbar = getView().findViewById(R.id.toolbarService);

        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_, container, false);
    }

}
