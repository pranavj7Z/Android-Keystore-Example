package com.pranavjayaraj.matic.network.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;

import com.pranavjayaraj.matic.network.R;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class TokenListAdapter extends BaseAdapter {
    private ArrayList<String> token;
    private ArrayList<String> coin;
    private ArrayList<String> value;
    private ArrayList<String> listData;
    private LayoutInflater layoutInflater;
    Context context;

    public TokenListAdapter(Context context, ArrayList<String> listData , ArrayList<String> token , ArrayList<String> coin, ArrayList<String> value) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
        this.token = token;
        this.coin = coin;
        this.value = value;
        hasStableIds();
        this.context = context;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
     public  int getItemViewType(int position)
    {
     return position;
    }

    @Override
    public  int getViewTypeCount()
    {
        return getCount();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.row, null);
            holder = new ViewHolder();
            holder.ll = (LinearLayout) convertView.findViewById(R.id.matic);
            holder.icon = (ImageView) convertView.findViewById(R.id.icon);
            holder.value = (TextView) convertView.findViewById(R.id.value);
            holder.coin = (TextView) convertView.findViewById(R.id.coin);
            holder.token = (TextView) convertView.findViewById(R.id.token);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.token.setText(token.get(position));
        holder.value.setText(value.get(position));
        holder.coin.setText(coin.get(position));
        if (holder.icon != null) {
            new BitmapWorkerTask(holder.icon).execute(listData.get(position));
        }
        return convertView;
    }

    class ViewHolder {
        ImageView icon;
        TextView value;
        TextView token;
        TextView coin;
        LinearLayout ll;

    }
    // ----------------------------------------------------
// Load bitmap in AsyncTask

    class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;
        private String imageUrl;

        public BitmapWorkerTask(ImageView imageView) {
            // Use a WeakReference to ensure the ImageView can be garbage
            // collected
            imageViewReference = new WeakReference<ImageView>(imageView);
        }

        // Decode image in background.
        @Override
        protected Bitmap doInBackground(String... params)
        {
            imageUrl = params[0];
            return LoadImage(imageUrl);
        }

        // Once complete, see if ImageView is still around and set bitmap.
        @Override
        protected void onPostExecute(Bitmap bitmap)
        {
            if (imageViewReference != null && bitmap != null)
            {
                final ImageView imageView = imageViewReference.get();

                if (imageView != null)
                {
                    imageView.setImageBitmap(bitmap);

                }
            }
        }

        private Bitmap LoadImage(String URL) {
            Bitmap bitmap = null;
            @NonNull
            InputStream in;
            try
            {
                in = OpenHttpConnection(URL);
                bitmap = BitmapFactory.decodeStream(in);
                if(in!=null)
                {
                    in.close();
                }
                else
                {
                }
            } catch (IOException e1)
            {
                System.out.println(e1.getCause());

            }
            return bitmap;
        }

        private InputStream OpenHttpConnection(String strURL)
                throws IOException {
            InputStream inputStream = null;
            URL url = new URL(strURL);
            URLConnection conn = url.openConnection();

            try {
                HttpURLConnection httpConn = (HttpURLConnection) conn;
                httpConn.setRequestMethod("GET");
                httpConn.connect();

                if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    inputStream = httpConn.getInputStream();
                }
            } catch (Exception ex) {
            }
            return inputStream;
        }
    }

}