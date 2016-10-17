package project.dev16.android.nearbuyoffers;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ClientPage extends AppCompatActivity implements View.OnClickListener {

    private StorageReference mStorage;
    TextView textViewShop;
    private Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_page);
        Firebase.setAndroidContext(this);
        mStorage= FirebaseStorage.getInstance().getReference();

        Firebase ref = new Firebase(Config.FIREBASE_URL);
        textViewShop=(TextView)findViewById(R.id.textViewShop);
        b1=(Button)findViewById(R.id.b1);
        b1.setOnClickListener(this);

        // Get a reference to our posts
        // Attach an listener to read the data at our posts reference

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    //Getting the data from snapshot
                    Shop shop = postSnapshot.getValue(Shop.class);

                    //Adding it to a string
                    String string = "Name: "+ shop.getName()+"\nItem: "+ shop.getItem()+"\nQuantity: "+ shop.getQuantity()+"\nDiscount: "+ shop.getDiscount();
                    Toast.makeText(getApplicationContext(), string, Toast.LENGTH_LONG).show();
                    //Displaying it on textview
                  textViewShop.setText(snapshot.getValue().toString());

                }



            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });


    }


    @Override
    public void onClick(View v) {
        String coordinates = String.format("geo:0,0?q=Supermarkets");
        Intent intentMap = new Intent(Intent.ACTION_VIEW, Uri.parse(coordinates));
        startActivity(intentMap);
    }
}
