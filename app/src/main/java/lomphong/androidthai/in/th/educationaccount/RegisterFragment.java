package lomphong.androidthai.in.th.educationaccount;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

//    Explicit
    private boolean avataABoolean =true;
    private ImageView imageView;
    private Uri uri;
    private String tag = "17novV1";
    private MyAlert myAlert;
    private String nameString,emailString, passwordString, avatarString, uidString;





    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        myAlert = new MyAlert(getActivity());

//        Create Toolbar
        createToolbar();
//        Avatar Controller
        avatarController();

    } //main method

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK) {

            avataABoolean = false;
            uri = data.getData();

            try {

                Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri));
                Bitmap bitmap1 = Bitmap.createScaledBitmap(bitmap, 800, 600, false);
                imageView.setImageBitmap(bitmap1);

            } catch (Exception e) {
                Log.d(tag,"e==>" +e.toString());
            }



        } // if

    }

    private void avatarController() {
        imageView = getView().findViewById(R.id.imageViewAvata);
        imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

//                intent to gallery
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,"Please Choose app"), 1);


            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId()==R.id.itemUpload) {

            checkDataAnUpload();


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void checkDataAnUpload() {

        MyAlert myAlert=new MyAlert(getActivity());
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Upload Avatar Image");
        progressDialog.setMessage("Please Wait Few Minus...");
        progressDialog.show();


// get value from edittext
        EditText  nameEditeText = getView().findViewById(R.id.editTextName);
        EditText emailEdittext = getView().findViewById(R.id.editTextEmail);
        EditText passwordEditText = getView().findViewById(R.id.editTextPassword);
        nameString = nameEditeText.getText().toString().trim();
        emailString = emailEdittext.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();




        if (avataABoolean) {
           // none  Choose avatar
            myAlert.normalDialog("None Choose Avatar","Please Choose Avatar");

        } else if (nameString.isEmpty() || emailString.isEmpty() || passwordString.isEmpty()) {
        //    Have space
            myAlert.normalDialog(getString(R.string.title_have_space), getString(R.string.masseage_have_space));
        } else {
//dataOK
            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
            StorageReference storageReference = firebaseStorage.getReference();
            storageReference.child("Avatar/" + nameString).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getActivity(),"Success Upload Avatar" , Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    findPartAvatar();

                }
            });

        } //if


    } //checkData

    private void findPartAvatar() {

        FirebaseStorage firebaseStorage=FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference().child("Avatar").child(nameString);
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                avatarString = uri.toString();
                Log.d("9decV1","avatarString ==>" +avatarString);
                register();

            }
        });

    }

    private void register() {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(emailString, passwordString)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
//                    Register success;
                    updateDatabase();

                } else {
                    myAlert.normalDialog("Cannot Register ?", task.getException().toString());
                }

            }
        });

    }

    private void updateDatabase() {

//        Find Uid
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        uidString = firebaseAuth.getUid();
        Log.d("9decV1", "uidSring ==>" + uidString);


    }   //updateDatabase

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_register, menu);

    }

    private void createToolbar() {
        Toolbar toolbar = getView().findViewById(R.id.toolbarRegister);
        ((MainActivity)getActivity()).setSupportActionBar(toolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Register");
        ((MainActivity) getActivity()).getSupportActionBar().setSubtitle("Please Fill every Blank");
        ((MainActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

}
