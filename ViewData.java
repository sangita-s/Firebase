package generisches.lab.firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ViewData extends AppCompatActivity {

    private ListView l;
    private DatabaseReference mDatabaseReference;

    private ArrayList<String> mUserName = new ArrayList<>();
    private ArrayList<String> mKeys = new ArrayList<>();
    private ArrayList<String> mBoth = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("User_02");
        l = findViewById(R.id.lst_data);

        final ArrayAdapter<String> local_arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mBoth);

        l.setAdapter(local_arrayAdapter);

        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getValue(String.class);
                mUserName.add(value);

                String key = dataSnapshot.getKey();
                mKeys.add(key);

                for (int i = 0; i<mKeys.size(); i++)
                {
                    mBoth.add(mKeys.get(i));
                    mBoth.add(mUserName.get(i));
                }

                local_arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                String value = dataSnapshot.getValue(String.class);
                String key = dataSnapshot.getKey();
                int index = mKeys.indexOf(key);
                mUserName.set(index,value);
                local_arrayAdapter.notifyDataSetChanged();;
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
