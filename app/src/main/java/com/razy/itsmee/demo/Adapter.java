package com.razy.itsmee.demo;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.razy.itsmee.demo.Models.picmee;

import java.util.ArrayList;

public class Adapter extends BaseAdapter {
    public Activity context;
    LayoutInflater inflater;
    private ArrayList<picmee> al;

    public Adapter(Activity cxt, ArrayList<picmee> al) {
        this.context = cxt;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.al = al;
    }

    @Override
    public int getCount() {
        return al == null ? 0 : al.size();
    }

    @Override
    public picmee getItem(int position) {
        return al.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_list, null);
            holder = new ViewHolder();
            holder.tvUname = convertView.findViewById(R.id.tvUnameId);
            holder.tvName = convertView.findViewById(R.id.tvNameId);

            convertView.setTag(holder);

        } else
            holder = (ViewHolder) convertView.getTag();
        holder.tvUname.setText(getItem(position).getUser().getUserName() != null ? getItem(position).getUser().getUserName() : "Sushant Dongal");
        holder.tvName.setText(getItem(position).getUser().getFirstName() != null ? (getItem(position).getUser().getFirstName() + " " + getItem(position).getUser().getLastName()) : "Sushant Dongal");

        return convertView;
    }

    private class ViewHolder {
        TextView tvUname, tvName;
    }
}



