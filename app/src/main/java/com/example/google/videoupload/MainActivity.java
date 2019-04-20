package com.example.google.videoupload;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    private Button button;
    EditText coursename,coursedesc,coursepublisher,publisherphone;
    Bitmap bitma;
    int SELECT_PICTURE = 1;
    int bcheck;
    int CAMERA_REQUEST=1;
    ImageView uploadCam,itempic,uploadFile;
    Button uploadItem;
    String check;
    String usersname,phone,field;
    EditText itemName,itemDescription;
    RequestQueue requestQueue;
    String insertUrl = "http://192.168.1.33/ayaan/uploadItems.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        uploadItem = findViewById(R.id.uploadItem);
        Intent intents =getIntent();
        usersname = intents.getStringExtra("name");
        field = intents.getStringExtra("field");
        phone = intents.getStringExtra("phone");
        uploadCam = (ImageView) findViewById(R.id.uploadCam);
        itempic = (ImageView) findViewById(R.id.itempic);
        itemName = findViewById(R.id.itemnname);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        itemDescription = findViewById(R.id.itemdesc);
        uploadFile = (ImageView) findViewById(R.id.uploadFile);
        uploadCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewGroup.LayoutParams params=itempic.getLayoutParams();
                params.height=100;
                bcheck=1;
                itempic.setLayoutParams(params);
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
        uploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewGroup.LayoutParams params=itempic.getLayoutParams();
                params.height=100;
                bcheck = 2;
                itempic.setLayoutParams(params);
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
            }
        });

        uploadItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Check<><><><><>",check);
                StringRequest requests = new StringRequest(com.android.volley.Request.Method.POST, insertUrl, new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                    }
                }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> parameters = new HashMap<String,String>();
                        parameters.put("seller",usersname);
                        parameters.put("field",field);
                        parameters.put("phone",phone);
                        parameters.put("itemname",itemName.getText().toString());
                        parameters.put("link",check);
                        parameters.put("description",itemDescription.getText().toString());
                        parameters.put("photo",getStringImage(bitma));

                        return parameters;
                    }
                };
                requestQueue.add(requests);
                Intent intent = new Intent(MainActivity.this,AppHome.class);
                intent.putExtra("name",usersname);
                intent.putExtra("phone",phone);
                intent.putExtra("field",field);
                startActivity(intent);

            }
        });
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
                return;
            }
        }

        enable_button();
    }

    private void enable_button() {

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialFilePicker()
                        .withActivity(MainActivity.this)
                        .withRequestCode(10)
                        .start();

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 100 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)){
            enable_button();
        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
            }
        }
    }

    ProgressDialog progress;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK && bcheck==1) {
            bitma = (Bitmap) data.getExtras().get("data");
            itempic.setImageBitmap(bitma);
            Log.d("IMAGE>>>>>>>>",getStringImage(bitma));

        }

        if (resultCode == RESULT_OK && requestCode == SELECT_PICTURE && bcheck==2) {


            Uri selectedImageURI = data.getData();

            try {
                bitma = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageURI);
                Log.d("IMAGE>>>>>>>>",getStringImage(bitma));

            } catch (IOException e) {
                e.printStackTrace();
            }
            Picasso.with(MainActivity.this).load(selectedImageURI).noPlaceholder().fit().into(itempic);
        }
        if(requestCode == 10 && resultCode == RESULT_OK){

            progress = new ProgressDialog(MainActivity.this);
            progress.setTitle("Uploading");
            progress.setMessage("Please wait...");
            progress.show();

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {

                    File f  = new File(data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH));
                    String content_type  = getMimeType(f.getPath());

                    String file_path = f.getAbsolutePath();
                    OkHttpClient client = new OkHttpClient();
                    RequestBody file_body = RequestBody.create(MediaType.parse(content_type),f);

                    check = file_path.substring(file_path.lastIndexOf("/")+1);

                    RequestBody request_body = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("type",content_type)
                            .addFormDataPart("uploaded_file",file_path.substring(file_path.lastIndexOf("/")+1), file_body)
                            .build();

                    Request request = new Request.Builder()
                            .url("http://192.168.1.33/VideoUpload/upload.php")
                            .post(request_body)
                            .build();

                    try {
                        Response response = client.newCall(request).execute();

                        if(!response.isSuccessful()){
                            throw new IOException("Error : "+response);
                        }

                        progress.dismiss();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            });

            t.start();




        }
    }

    private String getMimeType(String path) {

        String extension = MimeTypeMap.getFileExtensionFromUrl(path);

        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }


    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 40, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
}