package net.smallacademy.authenticatorapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import static net.smallacademy.authenticatorapp.Register.TAG;

public class home extends Fragment {

    Button hair, cash;
    FirebaseAuth fAuth;
    Boolean formcheck;

    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.activity_home, container, false);
        fAuth= FirebaseAuth.getInstance();
        hair = view.findViewById(R.id.hair);
        cash = view.findViewById(R.id.cash);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference eligibilityDoc = db.collection("hairdonation_form").document(fAuth.getCurrentUser().getUid());
        eligibilityDoc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete (@NonNull @NotNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        formcheck = true;
                    } else {
                        formcheck = false;
                    }
                } else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });
        hair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {

                if(formcheck)
                {
                    Toast.makeText(getContext(),"Sorry form already submitted",Toast.LENGTH_LONG).show();
                }else {
                    Intent myIntent = new Intent(getContext(), eligibility.class);
                    startActivityForResult(myIntent, 0);
                }
            }
        });


        cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse("https://focp.ae/donate/?program=focp"));
                startActivity(viewIntent);
            }
        });
        return view;
    }
}


