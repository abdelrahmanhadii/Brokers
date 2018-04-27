package com.example.quantum.brokers;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.InputStream;
import java.util.Random;

public class newItem extends AppCompatActivity implements LocationListener{

    String imgHolder;

    EditText name;
    EditText mobile;
    EditText address;
    EditText price;
    Button btn;

    String latitude;
    String longtude;

    StorageReference storage;

    ProgressDialog progress;

    DatabaseReference brokerDb;

    private ImageView viewById;

    Location location;

    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);
        brokerDb = FirebaseDatabase.getInstance().getReference("broker");

        name = (EditText) findViewById(R.id.name);
        mobile = (EditText) findViewById(R.id.mobile);
        address = (EditText) findViewById(R.id.address);
        price = (EditText) findViewById(R.id.price);
        viewById = (ImageView) findViewById(R.id.imagePreview);
        viewById.buildDrawingCache();
        btn = (Button) findViewById(R.id.btnInsert);

        locationManager = (LocationManager) newItem.this.getSystemService(Context.LOCATION_SERVICE);
        if(ActivityCompat.checkSelfPermission(newItem.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ){
            ActivityCompat.requestPermissions(newItem.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},120);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,newItem.this);
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            newItem.this.onLocationChanged(location);
        }else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,newItem.this);
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            newItem.this.onLocationChanged(location);
        }

        storage = FirebaseStorage.getInstance().getReference();
        progress = new ProgressDialog(this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRow();
            }
        });
    }

    private void addRow()
    {
        String nameHolder = name.getText().toString().trim();
        String mobileHolder = mobile.getText().toString().trim();
        String addressHolder = address.getText().toString().trim();
        String priceHolder = price.getText().toString().trim();
        String latitudeHolder = latitude.trim();
        String longtudeHolder = longtude.trim();

        if(!TextUtils.isEmpty(nameHolder) && !TextUtils.isEmpty(mobileHolder) && !TextUtils.isEmpty(addressHolder) && !TextUtils.isEmpty(priceHolder) && !TextUtils.isEmpty(imgHolder) && !TextUtils.isEmpty(latitude) && !TextUtils.isEmpty(longtude))
        {
            realStateClass rsc = new realStateClass(nameHolder, mobileHolder, addressHolder, priceHolder, latitudeHolder, longtudeHolder, imgHolder);
            brokerDb.child(mobileHolder).setValue(rsc);
            Toast.makeText(newItem.this, "INSERTED", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(newItem.this, home.class));
        }
        else
        {
            Toast.makeText(newItem.this, "Fill All The Boxes!", Toast.LENGTH_SHORT).show();

        }
    }

    public void selectImage(View v){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        File pictureDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String path = pictureDir.getPath();
        Uri data = Uri.parse(path);
        photoPickerIntent.setDataAndType(data, "image/*");
        startActivityForResult(photoPickerIntent, 20);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK)
        {
            if(requestCode==20)
            {
                progress.setMessage("uploading");
                progress.show();
                Uri dataUri = data.getData();
                InputStream inputStream;
                try
                {
                    inputStream = getContentResolver().openInputStream(dataUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    viewById.setImageBitmap(bitmap);

                    Random rand = new Random();

                    int  n = rand.nextInt(50000) + 1;

                    StorageReference path=storage.child("photos").child(name.getText().toString()+n);
                    path.putFile(dataUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progress.dismiss();
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            Toast.makeText(getApplicationContext(),"Sucessfully uploaded",Toast.LENGTH_LONG).show();
                            imgHolder = downloadUrl.toString();
                        }
                    });
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onLocationChanged(Location location)
    {
        if(location == null)
        {
            if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},120);
                Location lastKnownLocationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                latitude = lastKnownLocationGPS.getLatitude()+"";
                longtude = lastKnownLocationGPS.getLongitude()+"";
            }else{
                Location lastKnownLocationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                latitude = lastKnownLocationGPS.getLatitude()+"";
                longtude = lastKnownLocationGPS.getLongitude()+"";
            }
        }
        else
        {
            latitude = location.getLatitude()+"";
            longtude = location.getLongitude()+"";
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
