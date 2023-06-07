package com.example.smartsitter.ui.profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartsitter.Login;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.smartsitter.R;
import com.example.smartsitter.databinding.FragmentHomeBinding;
import com.example.smartsitter.databinding.FragmentNotificationsBinding;
import com.example.smartsitter.databinding.FragmentProfileBinding;
import com.example.smartsitter.ui.home.HomeViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.play.core.integrity.v;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;


public class ProfileFragment extends Fragment {
private ImageView ProfileImageView, signOut;
private TextView profileName, profileEmail;
private FloatingActionButton button;
private DatabaseReference databaseReference;
private FirebaseAuth mAuth;
private Uri imageUri;
private String myUri="";


private Button SaveButton;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //IMAGE PICKER
        button=view.findViewById(R.id.floatingActionButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(ProfileFragment.this)
                        .cropSquare()                    //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        //binding = FragmentProfileBinding.inflate(inflater, container, false);
        //View root = binding.getRoot();




        //databaseReference = FirebaseDatabase.getInstance().getReference().child("User");
        //StorageProfilePicsRef =FirebaseStorage.getInstance().getReference().child("Profile Pic/");


        signOut = view.findViewById(R.id.signOut);
        profileName = view.findViewById(R.id.profileName);
        profileEmail = view.findViewById(R.id.profileEmail);
        SaveButton = view.findViewById(R.id.SaveButton);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        profileName.setText(currentUser.getDisplayName());
        profileEmail.setText(currentUser.getEmail());

        ProfileImageView = view.findViewById(R.id.profileImage);
        if (currentUser.getPhotoUrl() != null) {
            ProfileImageView.setImageURI(currentUser.getPhotoUrl());
        }

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), Login.class);
                mAuth.signOut();
                startActivity(intent);
            }
        });

        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //uploadProfileImage();
                UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setPhotoUri(imageUri).build();
                currentUser.updateProfile(profileChangeRequest);
                Toast.makeText(getActivity(), "profile edited.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            imageUri = data.getData();
            ImageView imageView = requireView().findViewById(R.id.profileImage); // Replace with your ImageView ID
            if (imageView != null && imageUri != null) {
                imageView.setImageURI(imageUri);
            } else {
                Toast.makeText(requireContext(), "No image selected", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(requireContext(), "No data received", Toast.LENGTH_SHORT).show();
        }
    }
}