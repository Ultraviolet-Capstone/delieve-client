package com.ultraviolet.delieve.ui;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.ultraviolet.delieve.R;

public class GenerateQrcode extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qr);

        ImageView qrImageView=(ImageView)findViewById(R.id.qrcode);
        qrImageView.setImageBitmap(generateQRCode("data"));

    }
    public static Bitmap generateQRCode(String contents){
        Bitmap bitmap=null;

        try {
            QRCodeWriter qrCodeWriter=new QRCodeWriter();
            bitmap=toBitmap(qrCodeWriter.encode(contents, BarcodeFormat.QR_CODE, 1000, 1000));
        } catch (WriterException e) {
            e.printStackTrace();
        }

        return bitmap;

    }

    private static Bitmap toBitmap(BitMatrix matrix){
        int height=matrix.getHeight();
        int width=matrix.getWidth();
        Bitmap bmp=Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        for(int x=0; x<width;x++){
            for(int y=0; y<height; y++){
                bmp.setPixel(x, y, matrix.get(x, y) ? Color.BLACK : Color.WHITE);
            }
        }

        return bmp;
    }

}
