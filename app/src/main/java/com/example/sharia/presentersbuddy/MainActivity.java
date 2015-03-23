package com.example.sharia.presentersbuddy;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends TabActivity {
	public int heights = 0;

	private static int SPLASH_TIME_OUT = 1;
	private static int SPLASH_TIME_OUT2 = 6000;
	private static int SPLASH_TIME_OUT3 = 0;

	private TabHost mTabHost;
	Button  btnStart, btnStop, btnadd,btnfull;
	View v1, v2;
	int sek = 0;

	ScrollView scroll;
	TextView txt1, txt2, logo, textViewTime;

	String newtimer;
	int sec, counter;

	String[] titl;
	String[] tim2;
	String[] tim;

	CounterClass timer;

	// Get the app's shared preferences
	SharedPreferences app_preferences2,pref;

	private void setupTabHost() {
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setupTabHost();
		logo = (TextView) findViewById(R.id.logo2);
		textViewTime = (TextView) findViewById(R.id.textViewTime);

		txt1 = new TextView(this);
		txt2 = new TextView(this);
        txt1.setTypeface(Typeface.DEFAULT_BOLD);
        txt2.setTypeface(Typeface.DEFAULT_BOLD);

		btnStart = (Button) findViewById(R.id.start);
		btnStop = (Button) findViewById(R.id.stop);
		btnadd = (Button) findViewById(R.id.add);
		btnfull = (Button) findViewById(R.id.fullscreen);

		v1 = btnStart.getRootView();
		v2 = btnStop.getRootView();

		// Get the app's shared preferences
		final SharedPreferences app_preferences = getApplicationContext().getSharedPreferences("MyPref", 0);

		pref = getApplicationContext().getSharedPreferences("MyPref", 0);
		app_preferences2=getApplicationContext().getSharedPreferences("MyPref", 0);
		// Get the value for the run counter
		counter = pref.getInt("counter", 0);
		

		btnStart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!textViewTime.getText().toString()
						.equalsIgnoreCase("00:00:00")) {

					btnStart.setVisibility(v1.GONE);
					btnStop.setVisibility(v2.VISIBLE);
					int jumla = app_preferences.getInt("storedJumula", 0);
					timer = new CounterClass(jumla, 1000);
					timer.start();
				}
			}
		});
		btnfull.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String message = "switching to full screen \n press the back button to exit full screen";

				Toast toast = Toast.makeText(getApplicationContext(),
						message, Toast.LENGTH_LONG);
				View txt = toast.getView();
				TextView tv = (TextView) txt
						.findViewById(android.R.id.message);
				tv.setGravity(Gravity.CENTER);
				toast.show();
				Intent in = new Intent(MainActivity.this, Slides.class);
				startActivity(in);
			}
		});
			
		btnStop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				timer.cancel();
				newtimer = textViewTime.getText().toString();
				sec = (Integer.parseInt(newtimer.substring(0, 2)) * 60000 * 60)
						+ (Integer.parseInt(newtimer.substring(3, 5)) * 60000)
						+ (Integer.parseInt(newtimer.substring(6)) * 1000);
				timer = new CounterClass(sec, 1000);
				btnStop.setVisibility(v2.GONE);
				btnStart.setVisibility(v1.VISIBLE);
			}
		});
		btnadd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String message ="note added \n slide to view new note";

				Toast toast = Toast.makeText(getApplicationContext(),
						message, Toast.LENGTH_SHORT);
				View txt = toast.getView();
				TextView tv = (TextView) txt
						.findViewById(android.R.id.message);
				tv.setGravity(Gravity.CENTER);
				toast.show();
				// Increment the counter
				Editor editor = pref.edit();
				editor.putInt("counter", ++counter);
				editor.apply(); // Very important
				Intent i = new Intent(MainActivity.this, MainActivity.class);
				startActivity(i);
			}
		});

		int jumla = app_preferences.getInt("storedJumula", 0);
		int millis = jumla;
		String hms = String.format(
				"%02d:%02d:%02d",
				TimeUnit.MILLISECONDS.toHours(millis),
				TimeUnit.MILLISECONDS.toMinutes(millis)
						- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
								.toHours(millis)),
				TimeUnit.MILLISECONDS.toSeconds(millis)
						- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
								.toMinutes(millis)));
		textViewTime.setText(hms);

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {

				setupTab(
						txt1,
						"MY NOTES         MY NOTES         MY NOTES         MY NOTES",
						"Slides.class");
				setupTab(
						txt2,
						"SET TIMER         SET TIMER         SET TIMER         SET TIMER",
						"Timer2.class");

			}
		}, SPLASH_TIME_OUT);
		loadSavedPreferences();
	}

	private void setupTab(final View view, final String tag,
			final String className) {
		View tabview = createTabView(mTabHost.getContext(), tag);
		TabHost tabHost = getTabHost();
		TabSpec spec;
		Intent intent;
		intent = new Intent().setClass(this, Slides.class);
		if (className.equals("Slides.class")) {
			intent = new Intent().setClass(this, Slides.class);
		}
		if (className.equals("Timer2.class")) {
			intent = new Intent().setClass(this, Timer2.class);
		}

		TabSpec setContent = mTabHost.newTabSpec(tag).setIndicator(tabview)
				.setContent(intent);
		{

		}

		mTabHost.addTab(setContent);

	}

	private static View createTabView(final Context context, final String text) {
		View view = LayoutInflater.from(context)
				.inflate(R.layout.tabs_bg, null);
		TextView tv = (TextView) view.findViewById(R.id.tabsText);
		tv.setText(text);

		return view;
	}

	private void loadSavedPreferences() {


		List<Section> sections = getAllSections();
		titl = new String[sections.size()];
		tim = new String[sections.size()];
		for (Section cn : sections) {
			titl[sections.indexOf(cn)] = cn.getTitle();
			tim[sections.indexOf(cn)] = cn.getTime();
		}

	}
    public List<Section>getAllSections() {
        // Show all the sections sorted by id
        String URL = "content://com.example.sharia.presentersbuddy.BuddyProv/sections";
        Uri sections = Uri.parse(URL);
        Cursor c = getContentResolver().query(sections, null, null, null, "title");
        List<Section> sectionList = new ArrayList<Section>();
        if (c.moveToFirst()) {

            do{
                Section section = new Section();
                section.setID(Integer.parseInt(c.getString(0)));
                section.setTitle(c.getString(1));
                section.setTime(c.getString(2));
                // Adding section to list
                sectionList.add(section);

            } while (c.moveToNext());
        }
        // return section list
        return sectionList;

    }
    public void deleteAllSections () {
        // delete all the records and the table of the database provider
        String URL = "content://com.example.sharia.presentersbuddy.BuddyProv/sections";
        Uri sections = Uri.parse(URL);
        int count = getContentResolver().delete(
                sections, null, null);

    }

	public void updatemain() {
		// Get the app's shared preferences
		final SharedPreferences app_preferences =getApplicationContext().getSharedPreferences("MyPref", 0);
		int jumla = app_preferences.getInt("storedJumula", 0);
		int millis = jumla;
		String hms = String.format(
				"%02d:%02d:%02d",
				TimeUnit.MILLISECONDS.toHours(millis),
				TimeUnit.MILLISECONDS.toMinutes(millis)
						- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
								.toHours(millis)),
				TimeUnit.MILLISECONDS.toSeconds(millis)
						- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
								.toMinutes(millis)));
		textViewTime.setText(hms);
	}

	public class CounterClass extends CountDownTimer {
		public CounterClass(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
			textViewTime.setText("Times up!");
			btnStop.setVisibility(v2.GONE);
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {

					int jumla = app_preferences2.getInt("storedJumula", 0);
					int millis = jumla;
					String hms = String.format(
							"%02d:%02d:%02d",
							TimeUnit.MILLISECONDS.toHours(millis),
							TimeUnit.MILLISECONDS.toMinutes(millis)
									- TimeUnit.HOURS
											.toMinutes(TimeUnit.MILLISECONDS
													.toHours(millis)),
							TimeUnit.MILLISECONDS.toSeconds(millis)
									- TimeUnit.MINUTES
											.toSeconds(TimeUnit.MILLISECONDS
													.toMinutes(millis)));
					textViewTime.setText(hms);
					btnStart.setVisibility(v1.VISIBLE);

				}
			}, SPLASH_TIME_OUT2);
		}

		@SuppressLint("NewApi")
		@TargetApi(Build.VERSION_CODES.GINGERBREAD)
		@Override
		public void onTick(long millisUntilFinished) {
			long millis = millisUntilFinished;
			String hms = String.format(
					"%02d:%02d:%02d",
					TimeUnit.MILLISECONDS.toHours(millis),
					TimeUnit.MILLISECONDS.toMinutes(millis)
							- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
									.toHours(millis)),
					TimeUnit.MILLISECONDS.toSeconds(millis)
							- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
									.toMinutes(millis)));
			System.out.println(hms);

			//count = db.getSectionCount();

			List<Section> sections = getAllSections();
			tim2 = new String[sections.size()];
			int jumla = 0;
			jumla = app_preferences2.getInt("storedJumula", 0);
			for (Section cn : sections) {

				sek = (Integer.parseInt(cn.getTime().substring(0, 2)) * 60000 * 60)
						+ (Integer.parseInt(cn.getTime().substring(3, 5)) * 60000)
						+ (Integer.parseInt(cn.getTime().substring(6)) * 1000);

				jumla -= sek;
				int milli = jumla;
				String hm = String.format(
						"%02d:%02d:%02d",
						TimeUnit.MILLISECONDS.toHours(milli),
						TimeUnit.MILLISECONDS.toMinutes(milli)
								- TimeUnit.HOURS
										.toMinutes(TimeUnit.MILLISECONDS
												.toHours(milli)),
						TimeUnit.MILLISECONDS.toSeconds(milli)
								- TimeUnit.MINUTES
										.toSeconds(TimeUnit.MILLISECONDS
												.toMinutes(milli)));

				tim2[sections.indexOf(cn)] = hm;

			}
			for (Section cn : sections) {
				if (hms.equalsIgnoreCase(tim2[sections.indexOf(cn)])) {
					// Get instance of Vibrator from current Context
					Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

					v.vibrate(1000);
					String message = cn.getTitle() + " should be complete!";

					Toast toast = Toast.makeText(getApplicationContext(),
							message, Toast.LENGTH_SHORT);
					View txt = toast.getView();
					TextView tv = (TextView) txt
							.findViewById(android.R.id.message);
					tv.setGravity(Gravity.CENTER);
					toast.show();
				} else if (hms.equalsIgnoreCase("00:00:01")) {
					Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

					v.vibrate(1000);
				}
			}

			textViewTime.setText(hms);
		}
	}
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_options_menu, menu);
        return true;
    }



	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.notes:
			
			 final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); 
			
			counter = pref.getInt("counter", 0);
			for (int i = 0; i <= counter; i++) {
				
				Editor edt = pref.edit();
				edt.remove("storedmsg" + i);
				edt.remove("storedttl" + i);
				edt.apply();
			
			}
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					Editor editor = pref.edit();
					editor.putInt("counter", 0);
					editor.apply(); // Very important

					Intent i = new Intent(MainActivity.this, MainActivity.class);
					startActivity(i);
				}
			}, SPLASH_TIME_OUT3);
			break;
		case R.id.timers:

			Timer2.jumla = 0;
			// Get the app's shared preferences
			final SharedPreferences app_preferences2 = getApplicationContext().getSharedPreferences("MyPref", 0);

			Editor editors = app_preferences2.edit();
			editors.putInt("storedJumula", 0);
			editors.apply();
			textViewTime.setText("00:00:00");
            deleteAllSections();
			Intent n = new Intent(MainActivity.this, MainActivity.class);
			startActivity(n);
			break;
		case R.id.help:
			Intent in = new Intent(MainActivity.this, HelpActivity.class);
			
			startActivity(in);
			break;


        default:
        }
        return super.onOptionsItemSelected(item);
	}

}