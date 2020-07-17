package com.example.viewpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class MainActivity extends AppCompatActivity {

    SmoothBottomBar bottomBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //loadLocale();
        //setLocale("vi");
        //setAppLocale("vi");
        setContentView(R.layout.activity_main);
        Checkinternet.startTimer(this);
        ImageView imageView = findViewById(R.id.imageView);
        int imagechon = CustomImageActivity.imagechon;
        imageView.setImageDrawable(getDrawable(imagechon));
        bottomBar = findViewById(R.id.bottomBar);

        bottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {

                if (i==2){
                    showChangelanguge();
                }
               // Toast.makeText(MainActivity.this, ""+i, Toast.LENGTH_SHORT).show();

                return false;
            }
        });

        ViewPager2 locationViewPage = findViewById(R.id.locationsViewPager2);
        List<TravelLocation> travelLocations = new ArrayList<>();
        TravelLocation travelLocationEiffelTo = new TravelLocation();
        travelLocationEiffelTo.imageUrl = "https://images.vov.vn/uploaded/69lwz2nmezg/2019_08_05/2_ZHBP.jpg";
        travelLocationEiffelTo.title = "Quyt";
        travelLocationEiffelTo.location = "Binh Dinh";
        travelLocationEiffelTo.starRating = 4.8f;
        travelLocations.add(travelLocationEiffelTo);

        TravelLocation travelLocationEiffelToDua = new TravelLocation();
        travelLocationEiffelToDua.imageUrl = "https://images.vov.vn/uploaded/69lwz2nmezg/2019_08_05/3_BQKH.jpg";
        travelLocationEiffelToDua.title = "Dua";
        travelLocationEiffelToDua.location = "Quang Ngai";
        travelLocationEiffelToDua.starRating = 4.5f;
        travelLocations.add(travelLocationEiffelToDua);

        TravelLocation travelLocationEiffelToChuoi = new TravelLocation();
        travelLocationEiffelToChuoi.imageUrl = "https://images.vov.vn/uploaded/69lwz2nmezg/2019_08_05/4_JFCY.png";
        travelLocationEiffelToChuoi.title = "Chuoi";
        travelLocationEiffelToChuoi.location = "Dong Nai";
        travelLocationEiffelToChuoi.starRating = 4.3f;
        travelLocations.add(travelLocationEiffelToChuoi);

        TravelLocation travelLocationEiffelToDuahau = new TravelLocation();
        travelLocationEiffelToDuahau.imageUrl = "https://images.vov.vn/uploaded/69lwz2nmezg/2019_08_05/6_WMSC.jpg";
        travelLocationEiffelToDuahau.title = "Dua Hau";
        travelLocationEiffelToDuahau.location = "Tp.Ho Chi Minh";
        travelLocationEiffelToDuahau.starRating = 4.2f;
        travelLocations.add(travelLocationEiffelToDuahau);

        TravelLocation travelLocationEiffelToLuu = new TravelLocation();
        travelLocationEiffelToLuu.imageUrl = "https://images.vov.vn/uploaded/69lwz2nmezg/2019_08_05/7_VZPZ.jpg";
        travelLocationEiffelToLuu.title = "Luu";
        travelLocationEiffelToLuu.location = "Ha Noi";
        travelLocationEiffelToLuu.starRating = 4.8f;
        travelLocations.add(travelLocationEiffelToLuu);

        locationViewPage.setAdapter(new TravelLocationAdapter(travelLocations));

        locationViewPage.setClipToPadding(false);
        locationViewPage.setClipChildren(false);
        locationViewPage.setOffscreenPageLimit(3);
        locationViewPage.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(30));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.95f + r * 0.05f);
            }
        });
        locationViewPage.setPageTransformer(compositePageTransformer);



    }

    private void setAppLocale(String localeCode){
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR1){
            config.setLocale(new Locale(localeCode.toLowerCase()));
        } else {
            config.locale = new Locale(localeCode.toLowerCase());
        }
        resources.updateConfiguration(config, dm);

        SharedPreferences.Editor editor = getSharedPreferences("settings",MODE_PRIVATE).edit();
        editor.putString("my_lang",localeCode);
        editor.apply();
    }

    private void showChangelanguge() {
        final String[] listItems = {"ประเทศไทย","Việt Nam","English"};
        AlertDialog.Builder mbuilder = new AlertDialog.Builder(MainActivity.this);
        mbuilder.setTitle(R.string.choose_languge);
        mbuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (i==0){
                    setAppLocale("th");
                    recreate();
                }else if(i==1){
                    setAppLocale("vi");
                    recreate();
                }else if(i==2){
                    setAppLocale("en");
                    recreate();
                }
                dialog.dismiss();
            }
        });

        AlertDialog mdialog =mbuilder.create();
        mdialog.show();
    }

    private  void  setLocale(String lang){
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);


        Configuration configuration = new Configuration();
        configuration.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(configuration,getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences.Editor editor = getSharedPreferences("settings",MODE_PRIVATE).edit();
        editor.putString("my_lang",lang);
        editor.apply();
    }
    public void loadLocale(){
        SharedPreferences preferences = getSharedPreferences("settings",Activity.MODE_PRIVATE);
        String language = preferences.getString("my_lang","");
        setAppLocale(language);
    }
}