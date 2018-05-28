package com.ultraviolet.delieve;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class EvaluateDeliver1 extends AppCompatActivity implements View.OnClickListener {

    private static final int PICK_FROM_CAMERA=0;
    private static final int CROP_FROM_IMAGE=2;

    private Uri mImageCaptureUri;
    private ImageView iv_UserPhoto;
    private int id_view;
    private String absolutePath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_deliever1);

        //iv_UserPhoto=(ImageView)this.findViewById(R.id.user_image);
        Button btn_agreeJoin=(Button)this.findViewById(R.id.btn_UpladPicture);
        StrictMode.VmPolicy.Builder builder=new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        btn_agreeJoin.setOnClickListener(this);
        Button finishE1=(Button)findViewById(R.id.btn_signupfinish);
        finishE1.setOnClickListener(this);

    }
    public void doTakePhotoAction(){
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        String url="tmp_"+String.valueOf(System.currentTimeMillis())+".jpg";
        mImageCaptureUri=Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));
        int permissionCheck= ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if(permissionCheck== PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},0);
        }
        else{
            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
            startActivityForResult(intent, PICK_FROM_CAMERA);
        }

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
                    iv_UserPhoto=(ImageView)findViewById(R.id.deliver_image);
                    iv_UserPhoto.setImageBitmap(photo);

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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==0){
            if(grantResults[0]==0){
                Toast.makeText(this, "카메라 권한이 승인됨", Toast.LENGTH_SHORT);

            }
            else{
                Toast.makeText(this,"카메라 권한이 거절 되었습니다.", Toast.LENGTH_SHORT);
            }
        }
    }

    @Override
    public void onClick(View v) {
        id_view= v.getId();
        if(v.getId()==R.id.btn_signupfinish){
            Intent mainIntent=new Intent(EvaluateDeliver1.this, EvaluateDeliver2.class);
            startActivity(mainIntent);
            finish();

        }
        else if(v.getId()==R.id.btn_UpladPicture){
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
