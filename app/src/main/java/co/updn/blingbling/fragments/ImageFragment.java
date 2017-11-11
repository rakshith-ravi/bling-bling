package co.updn.blingbling.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class ImageFragment extends Fragment {

    String imageURL;

    public void setImage(String imageURL) {
        this.imageURL = imageURL;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ImageView imageView = new ImageView(getContext());
        Glide.with(getContext())
                .load(imageURL)//"http://photos.state.gov/libraries/media/788/images/500x500-sample.jpg")
                .asBitmap()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .asIs()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);
        return imageView;
    }
}
