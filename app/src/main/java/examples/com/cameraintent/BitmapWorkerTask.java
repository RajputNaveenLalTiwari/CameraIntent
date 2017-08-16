package examples.com.cameraintent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * Created by 2114 on 09-01-2017.
 */

public class BitmapWorkerTask extends AsyncTask<File, Void, Bitmap>
{
    WeakReference<ImageView> imageViewReference;

    public BitmapWorkerTask(ImageView imageView)
    {
        imageViewReference = new WeakReference<ImageView>(imageView);
    }
    @Override
    protected Bitmap doInBackground(File... params)
    {
        return BitmapFactory.decodeFile(params[0].getAbsolutePath());
    }

    @Override
    protected void onPostExecute(Bitmap bitmap)
    {
        if( bitmap != null && imageViewReference != null)
        {
            ImageView diplay_image = imageViewReference.get();

            if( diplay_image != null )
            {
                diplay_image.setImageBitmap(bitmap);
            }
        }
    }


}
