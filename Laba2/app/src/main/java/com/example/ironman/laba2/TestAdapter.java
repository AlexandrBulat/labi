package com.example.ironman.laba2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Ironman on 27.10.2016.
 */
public class TestAdapter extends BaseAdapter {

    List<Contacts> listItem;
    public List<Contacts> parkingList;


    ArrayList<Contacts> arraylist;

    Context mContext;

    public TestAdapter(Context mContext, List<Contacts> listItem) {
        this.mContext = mContext;
        this.listItem = listItem;
        this.parkingList = listItem;
        arraylist = new ArrayList<Contacts>();
        arraylist.addAll(parkingList);

    }

    public int getCount() {
        return listItem.size();
    }

    public Object getItem(int arg0) {
        return null;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View arg1, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.list_item, viewGroup, false);


        Contacts contacts = listItem.get(position);
        TextView tv_event = (TextView) row.findViewById(R.id.tv_event);
        final TextView tv_date = (TextView) row.findViewById(R.id.tv_date);
        Button delete = (Button)row.findViewById(R.id.delete);
        ImageView imageView = (ImageView)row.findViewById(R.id.imageView2);
        imageView.setImageBitmap(StringToBitMap(contacts.getImage()));

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listItem.remove(position);
                notifyDataSetChanged();
            }
        });
        tv_date.setText(contacts.getName());

        return row;
    }
    public void filter(String charText) {

        charText = charText.toLowerCase(Locale.getDefault());

        parkingList.clear();
        if (charText.length() == 0) {
            parkingList.addAll(arraylist);

        } else {
            for (Contacts postDetail : arraylist) {
                if (charText.length() != 0 && postDetail.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    parkingList.add(postDetail);
                } else if (charText.length() != 0 && postDetail.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    parkingList.add(postDetail);
                }
            }
        }
        notifyDataSetChanged();
    }
    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }
}

