package examples.com.cameraintent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by 2114 on 09-01-2017.
 */

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder>
{
//    private File imagesFile;
    private File[] imageFiles;

    /*public RecyclerviewAdapter(File folderFile)
    {
        imagesFile = folderFile;
    }*/

    public RecyclerviewAdapter(File[] folderFiles)
    {
        imageFiles = folderFiles;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
//        File image = imagesFile.listFiles()[position];

        File image = imageFiles[position];

//        Bitmap imageBitmap = BitmapFactory.decodeFile(image.getAbsolutePath());
//
//        holder.getImageView().setImageBitmap(imageBitmap);

        BitmapWorkerTask workerTask = new BitmapWorkerTask(holder.getImageView());

        workerTask.execute(image);

    }

    @Override
    public int getItemCount()
    {
//        return imagesFile.listFiles().length;

        return imageFiles.length;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView imageView;

        public ViewHolder( View view )
        {
            super(view);

            imageView = (ImageView) view.findViewById(R.id.image);
        }

        public ImageView getImageView()
        {
            return imageView;
        }
    }
}
