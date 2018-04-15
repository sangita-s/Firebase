package generisches.lab.firebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ImageButton mFirebaseButton;
    private ImageButton mFirebaseButton2;
    private EditText mText;
    private EditText mEmail;
    private TextView mTextView1;

    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseButton = findViewById(R.id.firebase_button);
        mFirebaseButton2 = findViewById(R.id.btn_view);
        mText = findViewById(R.id.firebase_text);
        mEmail = findViewById(R.id.email_text);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        test();
        mFirebaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create a child in our root object
                //Assign value to child

                //Assign value Sangita to child
                //mDatabase.child("Name").setValue("Sangita");
                String newName = mText.getText().toString().trim();

                //Send new name to db
                //mDatabase.child("Name").setValue(newName);

                //Push value into database
                //mDatabase.push().setValue(newName);

                String email = mEmail.getText().toString().trim();

                //To store 2 in one object
                HashMap<String, String> obj = new HashMap<>();
                obj.put("Name", newName);
                obj.put("Email", email);

                mDatabase.push().setValue(obj).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(MainActivity.this, "Complete", Toast.LENGTH_SHORT).show();
                            mText.setText("");
                            mEmail.setText("");
                            //displayData();

                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Try again later", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                //Create TextView

            }
        });
        mFirebaseButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent local_intent = new Intent(MainActivity.this, ViewData.class);
                startActivity(local_intent);
            }
        });
    }


    private TextView mTextView2;
    private TextView mTextView3;
    private TextView mTextView4;
    private DatabaseReference mSampleDBReference;

    private ArrayList<String> mKeys = new ArrayList<>();
    private ArrayList<String> mValues = new ArrayList<>();

    private void test() {
        mSampleDBReference = mDatabase.child("Sample");
        mTextView1 = findViewById(R.id.textView3);
        mTextView2 = findViewById(R.id.textView4);
        mTextView3 = findViewById(R.id.textView5);
        mTextView4 = findViewById(R.id.textView6);
        mSampleDBReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("Count ", " is " + dataSnapshot.getChildrenCount());
                for(DataSnapshot d : dataSnapshot.getChildren())
                {
                    mKeys.add(d.getKey());
                    mValues.add(d.getValue(String.class));
                    Log.e("Get Data ", d.getValue(String.class));
                }
                    mTextView1.setText(mKeys.get(0) +" : "+ mValues.get(0));
                    mTextView2.setText(mKeys.get(1) +" : "+ mValues.get(1));
                    mTextView3.setText(mKeys.get(2) +" : "+ mValues.get(2));
                    mTextView4.setText(mKeys.get(3) +" : "+ mValues.get(3));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void displayData() {

        mSampleDBReference = mDatabase.child("Name");
        mTextView1 = findViewById(R.id.textView3);

        mSampleDBReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue().toString();
                String key = dataSnapshot.getKey().toString();
                mTextView1.setText("Name is " + name + " Key is "+ key);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
