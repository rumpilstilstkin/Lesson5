package com.geekbrains.sqlietedatabaseexample.image;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.ImageView;

import com.geekbrains.sqlietedatabaseexample.R;


public class ImageActivity extends AppCompatActivity implements ImageSourceDialogCallbacks {

    private ImageView image;
    private AppCompatButton select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        image = findViewById(R.id.ai_iv_image);
        select = findViewById(R.id.ai_acb_select);

        select.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                (new ImageSourceDialogFragment()).show(getSupportFragmentManager(), "image_source_dialog_fragment_tag");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onImageSourceSelected(ImageSource source) {
        switch (source){
            case GALLERY:{

            }
            case PHOTO:{

            }
        }
    }
}
