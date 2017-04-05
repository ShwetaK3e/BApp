package com.bzyness.bzyness.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bzyness.bzyness.R;

import java.io.IOException;

public class UploadImageActivity extends AppCompatActivity {

    int PICK_AN_IMAGE=1;
    Bitmap bitmap;
    Button chooseImg,uploadImg;
    ImageView profileImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        chooseImg=(Button)findViewById(R.id.buttonChoose);
        chooseImg.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

        uploadImg=(Button)findViewById(R.id.buttonUpload);
        uploadImg.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                uploadProfileImg();
            }
        });


        profileImg=(ImageView)findViewById(R.id.profilePic);

    }

    private void uploadProfileImg() {

        Intent i=new Intent(this,NewBusinessDetailsActivity.class);
        startActivity(i);
    }

    void imageChooser(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select an Image"),PICK_AN_IMAGE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==PICK_AN_IMAGE && resultCode==RESULT_OK&& data!=null &&data.getData()!=null){
          Uri imagePath=data.getData();
            try {
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),imagePath);
                profileImg.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
