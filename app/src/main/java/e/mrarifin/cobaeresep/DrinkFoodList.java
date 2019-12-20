package e.mrarifin.cobaeresep;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import e.mrarifin.cobaeresep.DrinkFood;
import e.mrarifin.cobaeresep.DrinkFoodListAdapter;
import e.mrarifin.cobaeresep.DrinkMainActivity;
import e.mrarifin.cobaeresep.DrinkSQLiteHelper;
import e.mrarifin.cobaeresep.DrinkUpdateAdapter;

/**
 * Created by Quoc Nguyen on 13-Dec-16.
 */

public class DrinkFoodList extends AppCompatActivity {

    GridView gridView,gridView2;
    ArrayList<DrinkFood> list;

    DrinkFoodListAdapter adapter = null;
    DrinkUpdateAdapter adapter2 = null;
    final int REQUEST_CODE_GALLERY = 999;
    Intent intent;
    Uri fileUri;
    Button btn_choose_image;
    ImageView getImageViewFood;
    Bitmap bitmap, decoded;
    public final int REQUEST_CAMERA = 0;
    public final int SELECT_FILE = 1;

    int bitmap_size = 40; // image quality 1 - 100;
    int max_resolution_image = 800;
    public static DrinkSQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_list_activity);

        gridView = (GridView) findViewById(R.id.gridView);

        list = new ArrayList<>();

        adapter = new DrinkFoodListAdapter(this, R.layout.food_items, list);


        gridView.setAdapter(adapter);

        // get all data from sqlite
        Cursor cursor = DrinkMainActivity.sqLiteHelper.getData("SELECT * FROM DRINK");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String price = cursor.getString(2);
            String cara = cursor.getString(3);
            byte[] image = cursor.getBlob(4);

            list.add(new DrinkFood(name, price, cara, image, id));
        }
        adapter.notifyDataSetChanged();


        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                final CharSequence[] items = {"Tampilkan","Update", "Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(DrinkFoodList.this);

                dialog.setTitle("Pilih Aksi");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            Cursor c = DrinkMainActivity.sqLiteHelper.getData("SELECT id FROM DRINK");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            showDialogTampil(DrinkFoodList.this, arrID.get(position));



                        } if (item == 1) {
                            // update
                            Cursor c = DrinkMainActivity.sqLiteHelper.getData("SELECT id FROM DRINK");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            // show dialog update at here
                            showDialogUpdate(DrinkFoodList.this, arrID.get(position));

                        } if (item == 2) {
                            Cursor c = DrinkMainActivity.sqLiteHelper.getData("SELECT id FROM DRINK");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            showDialogDelete(arrID.get(position));

                        }
                    }
                });
                dialog.show();
                return true;
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,Menu.class);
        finish();
        startActivity(intent);}


    ImageView imageViewFood;
    private void showDialogUpdate(Activity activity, final int position){

        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.food_items2);
        dialog.setTitle("Update/Ubah Resep");
        int id = position;
        imageViewFood = (ImageView) dialog.findViewById(R.id.imageViewFood);
        final EditText edtName = (EditText) dialog.findViewById(R.id.edtName);
        final EditText edtPrice = (EditText) dialog.findViewById(R.id.edtPrice);
        final EditText edtcara = (EditText) dialog.findViewById(R.id.edtcara);
        Button btnUpdate = (Button) dialog.findViewById(R.id.btnUpdate);
        Cursor cursor = DrinkMainActivity.sqLiteHelper.getData("SELECT * FROM DRINK WHERE id='" +id+ "'");
        edtcara.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                if (view.getId() == R.id.edtcara) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction()&MotionEvent.ACTION_MASK){
                        case MotionEvent.ACTION_UP:
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });
        edtPrice.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                if (view.getId() == R.id.edtcara) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction()&MotionEvent.ACTION_MASK){
                        case MotionEvent.ACTION_UP:
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });
        while (cursor.moveToNext()) {

            String name = cursor.getString(1);
            String price = cursor.getString(2);
            String cara = cursor.getString(3);
            byte[] image = cursor.getBlob(4);
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);

            edtName.setText(name);
            edtPrice.setText(price);
            edtcara.setText(cara);
            imageViewFood.setImageBitmap(bitmap);

        }




        // set width for dialog
        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 1);
        // set height for dialog
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 1);
        dialog.getWindow().setLayout(width, height);
        dialog.show();


        imageViewFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (String.valueOf(edtName.getText()).equals(null) || String.valueOf(edtName.getText()).equals("") ||
                        String.valueOf(edtPrice.getText()).equals(null) || String.valueOf(edtPrice.getText()).equals("")||
                        String.valueOf(edtcara.getText()).equals(null) || String.valueOf(edtcara.getText()).equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "Please input Semua Data ...", Toast.LENGTH_SHORT).show();}
                else{
                try {

                    DrinkMainActivity.sqLiteHelper.updateData(
                            edtName.getText().toString().trim(),
                            edtPrice.getText().toString().trim(),
                            edtcara.getText().toString().trim(),
                            DrinkMainActivity.imageViewToByte(imageViewFood),
                            position
                    );
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Update successfully!!!",Toast.LENGTH_SHORT).show();
                }
                catch (Exception error) {
                    Log.e("Update error", error.getMessage());
                }}
                updateFoodList();
            }
        });
    }

    private void showDialogTampil(Activity activity, final int position){

        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.tampilkan);
        dialog.setTitle("Resep Masakan");
        int id = position;
        imageViewFood = (ImageView) dialog.findViewById(R.id.imageViewFood);
        final TextView edtName = (TextView) dialog.findViewById(R.id.edtName);
        final TextView edtPrice = (TextView) dialog.findViewById(R.id.edtPrice);
        final TextView edtcara = (TextView) dialog.findViewById(R.id.edtcara);
        Cursor cursor = DrinkMainActivity.sqLiteHelper.getData("SELECT * FROM DRINK WHERE id='" +id+ "'");

        while (cursor.moveToNext()) {

            String name = cursor.getString(1);
            String price = cursor.getString(2);
            String cara = cursor.getString(3);
            byte[] image = cursor.getBlob(4);
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);

            edtName.setText(name);
            edtPrice.setText(price);
            edtcara.setText(cara);
            imageViewFood.setImageBitmap(bitmap);

        }




        // set width for dialog
        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 1);
        // set height for dialog
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 1);
        dialog.getWindow().setLayout(width, height);
        dialog.show();



    }


    private void showDialogDelete(final int idFood){
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(DrinkFoodList.this);

        dialogDelete.setTitle("Warning!!");
        dialogDelete.setMessage("Are you sure you want to this delete?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    DrinkMainActivity.sqLiteHelper.deleteData(idFood);
                    Toast.makeText(getApplicationContext(), "Delete successfully!!!",Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    Log.e("error", e.getMessage());
                }
                updateFoodList();
            }
        });

        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDelete.show();
    }

    private void updateFoodList(){
        // get all data from sqlite
        Cursor cursor =DrinkMainActivity.sqLiteHelper.getData("SELECT * FROM DRINK");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String price = cursor.getString(2);
            String cara = cursor.getString(3);
            byte[] image = cursor.getBlob(4);

            list.add(new DrinkFood(name, price, cara, image, id));
        }
        adapter.notifyDataSetChanged();
    }

    private void selectImage() {
        imageViewFood.setImageResource(0);
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(DrinkFoodList.this);
        builder.setTitle("Add Photo!");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri();
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Library")) {
                    intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("onActivityResult", "requestCode " + requestCode + ", resultCode " + resultCode);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                try {
                    Log.e("CAMERA", fileUri.getPath());

                    bitmap = BitmapFactory.decodeFile(fileUri.getPath());
                    setToImageView(getResizedBitmap(bitmap, max_resolution_image));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == SELECT_FILE && data != null && data.getData() != null) {
                try {
                    // mengambil gambar dari Gallery
                    bitmap = MediaStore.Images.Media.getBitmap(DrinkFoodList.this.getContentResolver(), data.getData());
                    setToImageView(getResizedBitmap(bitmap, max_resolution_image));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Untuk menampilkan bitmap pada ImageView
    private void setToImageView(Bitmap bmp) {
        //compress image
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes);
        decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));

        //menampilkan gambar yang dipilih dari camera/gallery ke ImageView
        imageViewFood.setImageBitmap(decoded);
    }

    // Untuk resize bitmap
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public Uri getOutputMediaFileUri() {
        return Uri.fromFile(getOutputMediaFile());
    }

    private static File getOutputMediaFile() {

        // External sdcard location
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "DeKa");

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.e("Monitoring", "Oops! Failed create Monitoring directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_DeKa_" + timeStamp + ".jpg");

        return mediaFile;
    }
    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

}