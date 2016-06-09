package com.gopi.bottomsheet.bottomsheetpractisegopi;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BottomSheetPracDialogActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet_dialog_prac);
        Button button2 = (Button) findViewById( R.id.button_2 );
        button2.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch( v.getId() ) {
            case R.id.button_2: {
                BottomSheetDialogHelpchat.shareFunctionalityDialog(BottomSheetPracDialogActivity.this);
                break;
            }
        }
    }
}
