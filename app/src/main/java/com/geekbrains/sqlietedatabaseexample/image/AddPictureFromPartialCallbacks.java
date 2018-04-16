package com.geekbrains.sqlietedatabaseexample.image;

///////////////////////////////////////////////////////////////////////////
// Add Picture From Partial Callbacks
///////////////////////////////////////////////////////////////////////////


import android.content.Intent;
import android.net.Uri;


public interface AddPictureFromPartialCallbacks {

    boolean areAddPictureFromPermissionsGranted(String[] permissions);

    void requestAddPictureFromPermissions(String[] permissions, int requestCode);

    void showAddPictureFromRationale(String[] permissions, int requestCode);

    void startAddPictureFromActivityForResult(Intent intent, int requestCode);

    void onAddPictureFromSelected(String imageFileName, Uri imageUri);
}
