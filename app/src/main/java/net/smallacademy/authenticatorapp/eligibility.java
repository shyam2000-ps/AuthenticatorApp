package net.smallacademy.authenticatorapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class eligibility extends AppCompatActivity {



    public static final String TAG = "TAG";
    EditText EditText1,EditText2,EditText3,EditText4;
    FirebaseAuth fAuth;
    RadioButton Focp,Dhl;
    Button Next;
    Button Cancel;
    String hairlength, haircolor, hairsplit, greyhair;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eligibility);
        fAuth = FirebaseAuth.getInstance();
        EditText1 = findViewById(R.id.editText1);
        EditText2 = findViewById(R.id.editText2);
        EditText3 = findViewById(R.id.editText3);
        EditText4 = findViewById(R.id.editText4);
        Focp  = findViewById(R.id.focp);
        Dhl = findViewById(R.id.dhl);
        Next = findViewById(R.id.next);
        Cancel =  findViewById(R.id.cancel);

        // Create a new user with a first, middle, and last name
 


        // Add a new document with a generated ID


        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(EditText1.getText().toString().isEmpty() && EditText2.getText().toString().isEmpty() && EditText3.getText().toString().isEmpty() && EditText4.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Text Fields are empty", Toast.LENGTH_SHORT).show();
                } else {
                    hairlength = EditText1.getText().toString().trim();
                    haircolor = EditText2.getText().toString().trim();
                    hairsplit = EditText3.getText().toString().trim();
                    greyhair = EditText4.getText().toString().trim();

                    Map<String, Object> user = new HashMap<>();
                    user.put("hairlength", hairlength);
                    user.put("haircolor", haircolor);
                    user.put("hairsplit", hairsplit);
                    user.put("greyhair", greyhair);
                    user.put("focp", Focp.isChecked());
                    user.put("dhl", Dhl.isChecked());

                    db.collection("hairdonation_form").document(fAuth.getCurrentUser().getUid())
                            .set(user)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess (Void aVoid) {
                                    Toast.makeText(getApplicationContext(),"Form submitted Successfully! FOCP will contact you soon.",Toast.LENGTH_LONG).show();
                                    finish();

                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure (@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(),"Error Submitting Form",Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                setContentView(R.layout.activity_eligibility);
                Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

    }


    public void onRadioButtonClicked (View view){
        // Is the button now checked?
        boolean checked = ( (RadioButton) view ).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.dhl:
                if (checked)
                    Focp.setChecked(false);
                    break;
            case R.id.focp:
                if (checked)
                    Dhl.setChecked(false);
                    break;
        }
    }
}

