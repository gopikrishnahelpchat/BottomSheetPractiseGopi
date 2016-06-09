package com.gopi.bottomsheet.bottomsheetpractisegopi;

/**
 * Created by Ohm on 17/06/15.
 */
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class ChooserArrayAdapter extends ArrayAdapter<String> {
    PackageManager mPm;
    List<String> mPackages;

    public ChooserArrayAdapter(Context context, List<String> packagesList) {
        super(context, 0, packagesList);
        mPm = context.getPackageManager();
        mPackages = packagesList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String pkg = mPackages.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bottomsheetitem, parent, false);
        }
        View view = convertView;

        try {
            CharSequence appName;
            Drawable appIcon;
            if (pkg.equalsIgnoreCase("ShareAll")) {
                appName = "Other Apps";
//                appIcon = getContext().getResources().getDrawable(R.drawable.ic_launcher, getContext().getTheme());
                appIcon = ContextCompat.getDrawable(getContext(), (R.drawable.ic_launcher));
            }else {
                ApplicationInfo ai = mPm.getApplicationInfo(pkg, 0);
                appName = mPm.getApplicationLabel(ai);
                appIcon = mPm.getApplicationIcon(pkg);
            }

            TextView textView = (TextView) view.findViewById(R.id.bottom_sheet_item_text);
            textView.setText(appName);
            ((ImageView) view.findViewById(R.id.bottom_sheet_item_image)).setImageDrawable(appIcon);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        return view;
    }

}