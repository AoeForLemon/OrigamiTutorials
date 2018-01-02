package io.github.ivero.origamitutorials.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import io.github.ivero.origamitutorials.R;
import io.github.ivero.origamitutorials.model.OrigamiBean;

/**
 * Created by iVero on 2017/12/25.
 */

public class OrigamiArrayAdapter extends BaseAdapter {

    private List<OrigamiBean> origamiBeanList;
    private Context context;

    public OrigamiArrayAdapter(Context context, List<OrigamiBean> origamiBeanList) {
        this.context = context;
        this.origamiBeanList = origamiBeanList;
    }

    @Override
    public int getCount() {
        return origamiBeanList.size();
    }

    @Nullable
    @Override
    public OrigamiBean getItem(int position) {
        return origamiBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            View view = LayoutInflater.from(context).inflate(R.layout.list_video_view, parent, false);
            viewHolder = ViewHolder.create((RelativeLayout) view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        OrigamiBean origamiBean = getItem(position);

        viewHolder.textView1.setText(origamiBean.getName());
        viewHolder.textView2.setText(origamiBean.getDescription());
        Picasso.with(context).load(origamiBean.getPic()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(viewHolder.imageView);

        return viewHolder.rootView;
    }

    private static class ViewHolder {
        private final RelativeLayout rootView;
        private final ImageView imageView;
        private final TextView textView1;
        private final TextView textView2;

        private ViewHolder(RelativeLayout rootView, ImageView imageView, TextView textView1, TextView textView2) {
            this.rootView = rootView;
            this.imageView = imageView;
            this.textView1 = textView1;
            this.textView2 = textView2;
        }

        private static ViewHolder create(RelativeLayout rootView) {
            ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView);
            TextView textView1 = (TextView) rootView.findViewById(R.id.textView1);
            TextView textView2 = (TextView) rootView.findViewById(R.id.textView2);
            return new ViewHolder(rootView, imageView, textView1, textView2);
        }
    }

}
