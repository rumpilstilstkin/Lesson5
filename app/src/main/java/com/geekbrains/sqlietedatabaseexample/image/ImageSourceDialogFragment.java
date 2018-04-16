package com.geekbrains.sqlietedatabaseexample.image;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;

import com.geekbrains.sqlietedatabaseexample.R;


public class ImageSourceDialogFragment extends AppCompatDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getContext())
                .setTitle(R.string.title_dialog_image_source)
                .setItems(R.array.image_sources, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      if(getParentFragment() != null && getParentFragment() instanceof ImageSourceDialogCallbacks) {
                          ((ImageSourceDialogCallbacks) getParentFragment()).onImageSourceSelected(getSource(which));
                      }else if(getActivity() != null && getActivity() instanceof  ImageSourceDialogCallbacks){
                          ((ImageSourceDialogCallbacks) getActivity()).onImageSourceSelected(getSource(which));
                      }
                    }
                })
            .create();
    }

    private ImageSource getSource(int which){
        switch (which){
            case 0: {
                return ImageSource.GALLERY;
            }
            case 1:{
                return ImageSource.PHOTO;
            }
            default:{
                return ImageSource.GALLERY;
            }
        }
    }
}