package com.ultraviolet.delieve.view.enroll;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ultraviolet.delieve.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class EvaluateDeliver2 extends AppCompatActivity implements View.OnClickListener {

    private static final int PICK_FROM_CAMERA=0;
    private static final int CROP_FROM_IMAGE=2;

    private Uri mImageCaptureUri;
    private ImageView iv_UserPhoto;
    private int id_view;
    private String absolutePath;

    Intent mainIntent;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_deliever2);

        Bitmap selfie= getIntent().getParcelableExtra("selfie");
        mainIntent=new Intent(getApplicationContext(), EvaluateDeliver3.class);
        mainIntent.putExtra("selfie", selfie);


        Button btn_agreeJoin= this.findViewById(R.id.btn_UpladId);
        StrictMode.VmPolicy.Builder builder=new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        btn_agreeJoin.setOnClickListener(this);
        Button finishE1= findViewById(R.id.btn_evaluatefinish);
        finishE1.setOnClickListener(this);

    }
    public void doTakePhotoAction(){
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        String url="tmp_"+String.valueOf(System.currentTimeMillis())+".jpg";
        mImageCaptureUri=Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));

        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        startActivityForResult(intent, PICK_FROM_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!=RESULT_OK)
            return;
        switch (requestCode){
            case PICK_FROM_CAMERA:
            {
                Intent intent=new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri, "image/*");

                intent.putExtra("outputX", 200);
                intent.putExtra("outputY", 200);
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CROP_FROM_IMAGE);

                break;
            }
            case CROP_FROM_IMAGE:
            {
                if(resultCode!=RESULT_OK){
                    return;
                }
                final Bundle extras=data.getExtras();

                String filePath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/SmartWheel/"+System.currentTimeMillis()+".jpg";

                if (extras != null) {
                    Bitmap photo=extras.getParcelable("data");

                    iv_UserPhoto.setImageBitmap(photo);
                    mainIntent.putExtra("id", photo);


                    storeCropImage(photo, filePath);

                    absolutePath=filePath;
                    break;
                }

                File f=new File(mImageCaptureUri.getPath());
                if(f.exists()){
                    f.delete();
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        id_view= v.getId();
        if(v.getId()==R.id.btn_evaluatefinish){

            startActivity(mainIntent);
            finish();

        }
        else if(v.getId()==R.id.btn_UpladId){
            DialogInterface.OnClickListener cameraListener=new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    doTakePhotoAction();
                }
            };

            DialogInterface.OnClickListener cancelListener=new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    dialog.dismiss();
                }
            };

            new AlertDialog.Builder(this)
                    .setTitle("업로드할 신분증 선택")
                    .setPositiveButton("사진촬영", cameraListener)
                    .setNegativeButton("취소", cancelListener)
                    .show();

        }
    }

    private void storeCropImage(Bitmap bitmap, String filePath){
        String dirPath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/SmartWheel";
        File directory_SmartWheel=new File(dirPath);

        if(!directory_SmartWheel.exists())
            directory_SmartWheel.mkdir();

        File copyFile=new File(filePath);
        BufferedOutputStream out=null;

        try{
            copyFile.createNewFile();
            out=new BufferedOutputStream(new FileOutputStream(copyFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG,100, out);


            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(copyFile)));

            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
