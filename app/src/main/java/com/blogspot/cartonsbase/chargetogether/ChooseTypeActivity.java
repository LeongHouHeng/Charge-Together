package com.blogspot.cartonsbase.chargetogether;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ChooseTypeActivity extends AppCompatActivity{

    Button btn_borrower;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.choose_type );

        Toolbar toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("You want to...");

        btn_borrower = (Button )findViewById( R.id.btn_borrower );

        btn_borrower.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Intent intent = new Intent(  );
                intent.setClass( ChooseTypeActivity.this, Home.class );
                startActivity( intent );

            }
        } );

    }
}
