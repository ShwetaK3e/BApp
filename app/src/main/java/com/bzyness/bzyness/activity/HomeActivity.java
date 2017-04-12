package com.bzyness.bzyness.activity;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.bzyness.bzyness.BaseActivity;
import com.bzyness.bzyness.R;
import com.bzyness.bzyness.adapters.BusinessTypeHomeAdapter;
import com.bzyness.bzyness.adapters.FeaturedBusinessHomeAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    RecyclerView typeList;
    RecyclerView featuredList;
    BusinessTypeHomeAdapter businessTypeHomeAdapter;
    FeaturedBusinessHomeAdapter featuredBusinessHomeAdapter;
    List<String> typeNames=new ArrayList<>();
    List<Drawable> typeImages=new ArrayList<>();
    List<Drawable> featuredImages=new ArrayList<>();

    TextView  editor_pick_text_header;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        typeList=(RecyclerView)findViewById(R.id.type_list);
        populateTypes();

        featuredList=(RecyclerView)findViewById(R.id.feature_list);
        populateFeatureList();

        editor_pick_text_header=(TextView)findViewById(R.id.editor_pick_text_header);
        Typeface tf=Typeface.createFromAsset(getAssets(),"fonts/barbie.ttf");
        editor_pick_text_header.setTypeface(tf);

    }

    void populateTypes(){
        BaseActivity.listTypeNames(typeNames,typeImages,this);
        businessTypeHomeAdapter =new BusinessTypeHomeAdapter(this,typeNames,typeImages, new BusinessTypeHomeAdapter.OnMyItemClickListener() {
            @Override
            public void onClick(String name, Drawable img) {

            }
        });
        typeList.setAdapter(businessTypeHomeAdapter);
    }

    void populateFeatureList(){
      BaseActivity.listFeaturedBusiness(featuredImages);
      featuredBusinessHomeAdapter=new FeaturedBusinessHomeAdapter(this, featuredImages, new FeaturedBusinessHomeAdapter.OnMyItemClickListener() {
          @Override
          public void onClick(String name, Drawable img) {

          }
      });
      featuredList.setAdapter(featuredBusinessHomeAdapter);
    }


}
