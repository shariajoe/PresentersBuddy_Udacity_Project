package com.example.sharia.presentersbuddy;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class Slides extends FragmentActivity {
	MyPageAdapter pageAdapter;

	int counter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_view);
       
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
		counter = pref.getInt("counter", 0);
		
        List<Fragment> fragments = getFragments();
        
        pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);
        
        ViewPager pager = (ViewPager)findViewById(R.id.viewpager);
        pager.setAdapter(pageAdapter);
        
    }
    
    private List<Fragment> getFragments(){
    	List<Fragment> fList = new ArrayList<Fragment>();
    	
    	for(int i=0;i<=counter;i++){
    	fList.add(MyFragment.newInstance(String.valueOf(i)));
    	
    	}
    	
    	return fList;
    }

    private class MyPageAdapter extends FragmentPagerAdapter {
    	private List<Fragment> fragments;

        public MyPageAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }
        @Override
        public Fragment getItem(int position) {
            return this.fragments.get(position);
        }
     
        @Override
        public int getCount() {
            return this.fragments.size();
        }
    }


}
