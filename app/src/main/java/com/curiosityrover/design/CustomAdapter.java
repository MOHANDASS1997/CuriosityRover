package com.curiosityrover.design;

import com.curiosityrover.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.curiosityrover.model.CustomImageCard;
import com.curiosityrover.network.CustomVolleyRequest;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<CustomImageCard> imageList;
    private ImageLoader imgLoader;

    public CustomAdapter(Context context, ArrayList<CustomImageCard> imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    @Override
    public int getCount() {
        return  imageList.size();
    }

    @Override
    public Object getItem(int position) {
        return imageList.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_card, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();

        CustomImageCard listItem = imageList.get(position);
        imgLoader = CustomVolleyRequest.getInstance(context)
                .getImageLoader();
        imgLoader.get(listItem.getImage_url(), ImageLoader.getImageListener(viewHolder.image,
                com.google.android.material.R.drawable.ic_clock_black_24dp, android.R.drawable
                        .ic_dialog_alert));
        viewHolder.image.setImageUrl(listItem.getImage_url(), imgLoader);
        viewHolder.image_id.setText(listItem.getImage_id());

        return convertView;
    }

    private static  class ViewHolder{
        private final NetworkImageView image;
        private final TextView image_id;

        public ViewHolder(View view) {
            image = view.findViewById(R.id.rover_image);
            image_id = view.findViewById(R.id.rover_image_id);

        }
    }

}
