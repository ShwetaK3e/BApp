package com.bzyness.bzyness.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bzyness.bzyness.BaseActivity;
import com.bzyness.bzyness.R;
import com.bzyness.bzyness.adapters.BusinessTypeHomeAdapter;
import com.bzyness.bzyness.adapters.ChatUsersAdapter;
import com.bzyness.bzyness.adapters.FeaturedBusinessHomeAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    RecyclerView typeList;
    RecyclerView featuredList;
    BusinessTypeHomeAdapter businessTypeHomeAdapter;
    FeaturedBusinessHomeAdapter featuredBusinessHomeAdapter;

    List<String> typeNames=new ArrayList<>();
    List<String> typeImagesURI=new ArrayList<>();

    Button buildBusiness,newsFeed;

    List<Drawable> featuredProfileImgs=new ArrayList<>();
    List<Drawable> featuredLogoImgs=new ArrayList<>();
    List<String> featuredBusinessNames=new ArrayList<>();

    TextView  editor_pick_text_header;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        typeList=(RecyclerView)findViewById(R.id.type_list);
        populateTypes();

        buildBusiness=(Button)findViewById(R.id.build_business);
        buildBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,NewBusinessDetailsActivity.class));
            }
        });

        newsFeed=(Button)findViewById(R.id.news_feed);
        newsFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ChatUsersActivity.class));
            }
        });

        featuredList=(RecyclerView)findViewById(R.id.featured_shops);
        populateFeatureList();

        /*editor_pick_text_header=(TextView)findViewById(R.id.editor_pick_text_header);
        Typeface tf=Typeface.createFromAsset(getAssets(),"fonts/barbie.ttf");
        editor_pick_text_header.setTypeface(tf);*/

    }

    void populateTypes(){
        BaseActivity.listTypeNames(typeNames,typeImagesURI,this);
        businessTypeHomeAdapter =new BusinessTypeHomeAdapter(this,typeNames,typeImagesURI, new BusinessTypeHomeAdapter.OnMyItemClickListener() {
            @Override
            public void onClick(String name, Drawable img) {

            }
        });
        typeList.setAdapter(businessTypeHomeAdapter);
    }

    void populateFeatureList(){
      BaseActivity.listFeaturedBusiness(featuredProfileImgs,featuredLogoImgs,featuredBusinessNames);
      featuredBusinessHomeAdapter=new FeaturedBusinessHomeAdapter(this, featuredProfileImgs,featuredLogoImgs,featuredBusinessNames, new FeaturedBusinessHomeAdapter.OnMyItemClickListener() {
          @Override
          public void onClick(String name, Drawable img) {

          }
      });
      featuredList.setAdapter(featuredBusinessHomeAdapter);
    }
}
