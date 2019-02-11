package com.johnyhawkdesigns.a55_childhealthapp_1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



public class SelectPhotoDialog extends DialogFragment {

    private static final String TAG = SelectPhotoDialog.class.getSimpleName();
    private static final int PICKFILE_REQUEST_CODE = 1234;

    //Create interface to listen for image selection or taking new photo with camera
    public interface OnPhotoSelectedListener{
        void getImagePath(Uri imagePath);
    }
    OnPhotoSelectedListener mOnPhotoSelectedListener; //We need to create interface object, and then instantiate in onAttach()

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_selectphoto, container, false);

        //For selecting photo from gallery
        TextView selectPhoto = (TextView) view.findViewById(R.id.dialogChoosePhoto);
        selectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: accessing phones memory. PICKFILE_REQUEST_CODE = " + PICKFILE_REQUEST_CODE);
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);//Allow the user to select a particular kind of data and return it. This is different than ACTION_PICK in that here we just say what kind of data is desired, not a URI of existing data from which the user can pick.
                intent.setType("image/*");
                startActivityForResult(intent, PICKFILE_REQUEST_CODE);
            }
        });

        return view;

    }

    //Receive the result from a previous call to startActivityForResult(Intent, int).
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Results when selecting a new image from memory
        if (requestCode == PICKFILE_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Uri selectedImageUri = data.getData();
            Log.d(TAG, "onActivityResult: image uri: " + selectedImageUri);

            //send the uri to PostFragment & dismiss dialog
            mOnPhotoSelectedListener.getImagePath(selectedImageUri);
            getDialog().dismiss();
        }

    }


    //We instantiate our listener in onAttach
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            //getTargetFragment() Return the target fragment set by setTargetFragment(). setTargetFragment() is done in PostFragment line 71
            mOnPhotoSelectedListener = (OnPhotoSelectedListener) getTargetFragment(); //We previously used getActivity() here.
        }catch (ClassCastException e){
            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage() );
        }
    }
}
