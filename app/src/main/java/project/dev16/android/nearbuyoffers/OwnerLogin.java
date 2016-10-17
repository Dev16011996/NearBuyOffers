package project.dev16.android.nearbuyoffers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class OwnerLogin extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextName;
    private EditText editTextquan;
    private EditText editTextdiscount;
    private EditText editTextItem;
    private TextView textViewShop;
    private Button buttonSave;
    private Button buttonUpload;
    private StorageReference mStorage;
    private static final int GALLERY_INTENT =2;
    public ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_login);

        Firebase.setAndroidContext(this);
        mStorage= FirebaseStorage.getInstance().getReference();
        buttonUpload=(Button)findViewById(R.id.button_upload);
        buttonUpload.setOnClickListener(this);

        buttonSave = (Button) findViewById(R.id.buttonSave);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextItem = (EditText) findViewById(R.id.editTextItem);
        editTextquan = (EditText) findViewById(R.id.editTextQuantity);
        editTextdiscount = (EditText) findViewById(R.id.editTextDiscount);

        textViewShop = (TextView) findViewById(R.id.textViewShop);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating firebase object
                Firebase ref = new Firebase(Config.FIREBASE_URL);

                //Getting values to store
                String name = editTextName.getText().toString().trim();
                String item = editTextItem.getText().toString().trim();
                String quantity = editTextquan.getText().toString().trim();
                String discount = editTextdiscount.getText().toString().trim();

                //Creating Shop object
                Shop shop = new Shop();

                //Adding values
                shop.setName(name);
                shop.setItem(item);
                shop.setQuantity(quantity);
                shop.setDiscount(discount);

                //Storing values to firebase
                ref.child(name).setValue(shop);


                //Value event listener for realtime data update
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            //Getting the data from snapshot
                            Shop shop = postSnapshot.getValue(Shop.class);

                            //Adding it to a string
                            String string = "Name: "+ shop.getName()+"\nItem: "+ shop.getItem()+"\nQuantity: "+ shop.getQuantity()+"\nDiscount: "+ shop.getDiscount();

                            //Displaying it on textview
                            textViewShop.setText(string);
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        System.out.println("The read failed: " + firebaseError.getMessage());
                    }
                });

            }
        });
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,GALLERY_INTENT);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==GALLERY_INTENT && resultCode==RESULT_OK){
            Uri uri=data.getData();

            progress.setMessage("Uploading...");
            progress.show();

            StorageReference filepath=mStorage.child("photos").child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(OwnerLogin.this,"Uploaded",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}