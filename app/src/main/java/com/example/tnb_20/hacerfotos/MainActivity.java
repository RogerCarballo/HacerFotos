package com.example.tnb_20.hacerfotos;

import android.content.Intent;
import android.graphics.Bitmap;
import java.io.IOException;
import android.os.Bundle;
import java.io.FileOutputStream;
import java.io.File;
import android.widget.Button;
import java.io.OutputStream;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.view.View;
import android.provider.MediaStore;
import android.net.Uri;





public class MainActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    Intent hacerFoto;
    File rutaArchivo = new File("data"+File.separator+"data"+File.separator+"com.example.tnb_20.hacerfotos"+File.separator+"files");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button botonFoto = findViewById(R.id.button);

        if(!rutaArchivo.exists()){
            rutaArchivo.mkdir();
        }
        botonFoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                hacerFoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (hacerFoto.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(hacerFoto, REQUEST_IMAGE_CAPTURE);
                }
            }
        });


        ImageView img = findViewById(R.id.imageView);
        if(rutaArchivo.listFiles().length>0){
            File image = new File(rutaArchivo, rutaArchivo.listFiles()[rutaArchivo.listFiles().length-1].getName());
            if (image.exists()){
                Uri uri = Uri.fromFile(image);
                img.setImageURI(uri);
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageView iv = findViewById(R.id.imageView);
            iv.setImageBitmap(imageBitmap);



            OutputStream outStream;
            try {
                for (int k = 0; k<=rutaArchivo.listFiles().length; k++){
                    File archivo = new File(rutaArchivo, "photo" + k + ".png");
                    if (!archivo.exists()){
                        outStream = new FileOutputStream(archivo);
                        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
                        k=rutaArchivo.listFiles().length;
                    }
                }
            } catch(IOException e) {
                System.out.println("error! No se pudo guardar la imagen");
            }

        }

    }
}