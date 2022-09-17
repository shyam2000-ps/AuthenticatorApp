package net.smallacademy.authenticatorapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import javax.annotation.Nullable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeMenuFragment} factory method to
 * create an instance of this fragment.
 */
public class HomeMenuFragment extends Fragment {
    TextView fullName, email, phone, verifyMsg;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    Button resendCode;
    ImageButton livechat;
    Button resetPassLocal, changeProfileImage, btnLogout;
    FirebaseUser user;
    ImageView profileImage;
    StorageReference storageReference;




    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_home_menu, container, false);
        phone = view.findViewById(R.id.profilePhone);
        fullName = view.findViewById(R.id.profileName);
        email = view.findViewById(R.id.profileEmail);
        btnLogout = view.findViewById(R.id.button);
        resetPassLocal = view.findViewById(R.id.resetPasswordLocal);
        profileImage = view.findViewById(R.id.profileImage);
        changeProfileImage = view.findViewById(R.id.changeProfile);
        livechat = view.findViewById(R.id.livechat);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        StorageReference profileRef = storageReference.child("users/" + fAuth.getCurrentUser().getUid() + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess (Uri uri) {
                Picasso.get().load(uri).into(profileImage);
            }
        });


        resendCode = view.findViewById(R.id.resendCode);
        verifyMsg = view.findViewById(R.id.verifyMsg);


        userId = fAuth.getCurrentUser().getUid();
        user = fAuth.getCurrentUser();

        if (!user.isEmailVerified()) {
            verifyMsg.setVisibility(View.VISIBLE);
            resendCode.setVisibility(View.VISIBLE);

            resendCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick (final View v) {

                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess (Void aVoid) {
                            Toast.makeText(v.getContext(), "Verification Email Has been Sent.", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure (@NonNull Exception e) {
                            Log.d("tag", "onFailure: Email not sent " + e.getMessage());
                        }
                    });
                }
            });
        }


        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent (@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot != null) {
                    phone.setText(documentSnapshot.getString("phone"));
                    fullName.setText(documentSnapshot.getString("fName"));
                    email.setText(documentSnapshot.getString("email"));
                } else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }
        });


        resetPassLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {

                final EditText resetPassword = new EditText(v.getContext());

                final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password ?");
                passwordResetDialog.setMessage("Enter New Password > 6 Characters long.");
                passwordResetDialog.setView(resetPassword);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick (DialogInterface dialog, int which) {
                        // extract the email and send reset link
                        String newPassword = resetPassword.getText().toString();
                        user.updatePassword(newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess (Void aVoid) {
                                Toast.makeText(getContext(), "Password Reset Successfully.", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure (@NonNull Exception e) {
                                Toast.makeText(getContext(), "Password Reset Failed.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick (DialogInterface dialog, int which) {
                        // close
                    }
                });

                passwordResetDialog.create().show();

            }
        });

        changeProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                // open gallery
                Intent i = new Intent(v.getContext(), EditProfile.class);
                i.putExtra("fullName", fullName.getText().toString());
                i.putExtra("email", email.getText().toString());
                i.putExtra("phone", phone.getText().toString());
                startActivity(i);
            }
        });


        btnLogout.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(getContext(), Login.class);
                startActivity(intToMain);
            }
        });

        livechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse("https://wa.me/971567143100"));
                startActivity(viewIntent);
            }
        });


        // Inflate the layout for this fragment
        return view;
    }
}