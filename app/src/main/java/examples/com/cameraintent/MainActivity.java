package examples.com.cameraintent;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
{
    protected static final int START_CAMERA_APP = 1;
    private String image_file_location;
    private Button take_photo;
    private RecyclerView recyclerView;

    private String GALLERY_LOCATION = "IMAGE GALLERY";
    private File GALLERY_FOLDER ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createImageGallery();

        take_photo = (Button) findViewById(R.id.take_photo);

        take_photo.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view)
            {
                callCameraApplication();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,1);

        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter recyclerviewAdapter = new RecyclerviewAdapter(sortFilesToLatest(GALLERY_FOLDER));

        recyclerView.setAdapter(recyclerviewAdapter);
    }

    public void callCameraApplication()
    {
        Intent call_camera_intent = new Intent();

        call_camera_intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

        File photoFile = null;

        try
        {
            photoFile = createImageFile();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        call_camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));

        startActivityForResult(call_camera_intent, START_CAMERA_APP);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, android.content.Intent data)
    {
        if( requestCode == START_CAMERA_APP && resultCode == RESULT_OK )
        {
//			Bundle extras = data.getExtras();
//
//			Bitmap photo_captured_bitmap = (Bitmap) extras.get("data");
//
//			captured_photo.setImageBitmap(photo_captured_bitmap);

//			Bitmap photo_captured_bitmap = BitmapFactory.decodeFile(image_file_location);
//
//			captured_photo.setImageBitmap(photo_captured_bitmap);

//          rotateImage(setReducedImageSize());

            RecyclerView.Adapter newRecyclerAdapter = new RecyclerviewAdapter(sortFilesToLatest(GALLERY_FOLDER));

            recyclerView.swapAdapter(newRecyclerAdapter,false);
        }
    };

    public void createImageGallery()
    {
        File storage_directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        GALLERY_FOLDER = new File( storage_directory,GALLERY_LOCATION );

        if( !GALLERY_FOLDER.exists() )
        {
            GALLERY_FOLDER.mkdir();
        }
    }

    File createImageFile() throws IOException
    {
        String time_stamp = new SimpleDateFormat("yyyyMMDD_HHmmss", Locale.US).format(new Date());

        String image_file_name = "IMAGE_" + time_stamp + "_";


        File image = File.createTempFile(image_file_name, ".jpg", GALLERY_FOLDER);

        image_file_location = image.getAbsolutePath();

        return image;
    }

/*
    public Bitmap setReducedImageSize()
    {
        int target_width = captured_photo.getWidth();
        int target_height = captured_photo.getHeight();

        BitmapFactory.Options bitmap_factory_options = new BitmapFactory.Options();
        bitmap_factory_options.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(image_file_location, bitmap_factory_options);

        int source_width = bitmap_factory_options.outWidth;
        int source_height = bitmap_factory_options.outHeight;

        int scale_factor = Math.min(( source_width / target_width ), ( source_height / target_height ));

        bitmap_factory_options.inSampleSize = scale_factor;
        bitmap_factory_options.inJustDecodeBounds = false;

//		Bitmap reducedSizeBitmap = BitmapFactory.decodeFile(image_file_location, bitmap_factory_options);
//
//		captured_photo.setImageBitmap(reducedSizeBitmap);

		return BitmapFactory.decodeFile(image_file_location, bitmap_factory_options);
    }

    public void rotateImage(Bitmap bitmap)
    {
        ExifInterface exifInterface = null;

        try
        {
            exifInterface = new ExifInterface(image_file_location);
        }
        catch(Exception e)
        {

        }

        if( exifInterface != null )
        {
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

            Matrix matrix = new Matrix();

            switch (orientation)
            {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    matrix.setRotate(90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    matrix.setRotate(180);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    matrix.setRotate(270);
                    break;
                default:
                    break;
            }

            Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            captured_photo.setImageBitmap(rotatedBitmap);
        }

    }
*/

    private File[] sortFilesToLatest(File fileImagesDirectory)
    {
        File[] files = fileImagesDirectory.listFiles();

        Arrays.sort(files, new Comparator<File>()
        {
            @Override
            public int compare(File objectLHS, File objectRHS)
            {
                return Long.valueOf( (objectRHS.lastModified())).compareTo(objectLHS.lastModified());
            }
        });

        return files;
    }
}
