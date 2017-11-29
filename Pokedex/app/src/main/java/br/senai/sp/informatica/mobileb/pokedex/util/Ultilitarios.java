package br.senai.sp.informatica.mobileb.pokedex.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

/**
 * Created by WEB on 29/11/2017.
 */
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class Ultilitarios {

    /*
    * Rotina para ocultar o teclado virtual no momento em que um
    * EditText tem Foco atvivo
     */

    public static void hidekeyboard (Activity activity){

        if(activity.getCurrentFocus() !=null){
            InputMethodManager inputMethodManager =
                    (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }else{

            final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);

            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        }

    }

    /*
    *Rotina para apresentar o teclado virtual no momento em que um
    * EditText tem foco ativo permitido pelo ajuste da tela
     */

    public static void showKeyBoard(Activity activity){
        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);

        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE |
                                                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
            );


    }

    /*
     * Carrega a foto do arquivo em um Bitmap
     */

    public static Bitmap setPic(int width, int height, File fotoUrl, Context context) throws IOException{
        return setFilePic(width,height,fotoUrl,context);
    }

    public static Bitmap setPic(int width, int height, Uri fotoUri,Context context) throws IOException{

        return setFilePic(width,height,fotoUri,context);
    }

    private static Bitmap setFilePic(int width, int height, Object foto, Context context) throws IOException {
        //Get the dimensions of the View
        int targetW = width;
        int targetH = height;

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        Rect rect = new Rect(-1,-1,-1,-1);

        InputStream is;

        if(foto instanceof File){
           is = context.getContentResolver().openInputStream(Uri.fromFile((File)foto));
        }else if (foto instanceof Uri){
            is = context.getContentResolver().openInputStream((Uri)foto);
        }else{
            throw new IOException("invalid URI");
        }

        BitmapFactory.decodeStream(is, rect, bmOptions);
        is.close();

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        //Determine how much to scale dow the image
        int scaleFactor = 0;
        if(targetH != 0 && targetW != 0 )
                 scaleFactor = Math.min(photoW/ targetW, photoH/ targetH);

        // Decode the image file into a Bitmap sized to fill the view
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        //bmOptions.inPurgleable

        Bitmap bitmap;
        if(foto instanceof File){
            bitmap = BitmapFactory.decodeFile(foto.toString(), bmOptions);
        }else{

            ParcelFileDescriptor parcelFileDescriptor =
                    context.getContentResolver().openFileDescriptor((Uri) foto,"r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor, rect, bmOptions);
            parcelFileDescriptor.close();
        }

        return bitmap;
    }

    /*
    * obtem a orientação da Foto
     */
    public static int getOrientation(final Context context, final  Uri selectedImageUri){
        int angle = 0;

        try{
            ExifInterface exif = new ExifInterface(selectedImageUri.getPath());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            switch (orientation){
                case ExifInterface.ORIENTATION_ROTATE_90:
                    angle = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    angle = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    angle = 270;
                    break;
                case ExifInterface.ORIENTATION_UNDEFINED:
                    String[] orientationColumn = {MediaStore.Images.Media.ORIENTATION};

                    Cursor cur = context.getContentResolver().query(selectedImageUri, orientationColumn, null, null, null);
                    angle = -1;

                    if (cur != null && cur.moveToFirst()) {
                        angle = cur.getInt(cur.getColumnIndex(orientationColumn[0]));
                        cur.close();
                    }
                    break;

            }


        }catch (IOException ex ) {

        }
        return  angle;
    }

    /*
    * Cria um arquivo temporario para armazenar a foto
     */
    public static void galleryAddPic(Activity activity, File fotoUrl){
        Uri contentUri =  Uri.fromFile(fotoUrl);

        if(fotoUrl.exists()){
            Intent mediaScanIntent =  new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            mediaScanIntent.setData(contentUri);
            activity.sendBroadcast(mediaScanIntent);
        }else {
            Toast.makeText(activity, "A Foto não foi encontrada !", Toast.LENGTH_LONG).show();
        }
    }

    /*
    * Converte um ColorDrawable em Bitmap
     */

    private static Bitmap convertToBitmap(Drawable drawable, int widthPixels, int heightPixels){

        Bitmap mutableBitmap = Bitmap.createBitmap(widthPixels, heightPixels, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas((mutableBitmap));
        drawable.setBounds(0,0,widthPixels,heightPixels);
        drawable.draw(canvas);

        return mutableBitmap;
    }

    /*
    * Cria um Bitmap com um contorno Circular
     */
    public static Bitmap circularBitmapAndText(int cor, int width, int height, String txt){

        Bitmap output = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();

        paint.setAntiAlias(true);
        paint.setColor(cor);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawARGB(0,0,0,0);
        canvas.drawCircle(width / 2, height / 2, width / 2, paint);
        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(100);
        canvas.drawText(txt, width / 2, height / 2 + 30, paint);

        return output;
    }

}
