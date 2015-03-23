package com.example.sharia.presentersbuddy;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Timer2 extends Activity implements LoaderManager.LoaderCallbacks<SharedPreferences> {
	TextView  set;

	EditText title, hrs, min, sec;
	String hrss, mins, secs, duration;
	int sek, dak, saa;
	public static int jumla = 0;
	Button submit;

	ArrayList<HashMap<String, String>> sectionList;

	private static final String TAG_TITLE = "title";
	private static final String TAG_TIME = "time";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timer);


		title = (EditText) findViewById(R.id.title);
		hrs = (EditText) findViewById(R.id.hrss);
		min = (EditText) findViewById(R.id.mins);
		sec = (EditText) findViewById(R.id.secs);
		set = (TextView) findViewById(R.id.set);

		submit = (Button) findViewById(R.id.submit);

        getLoaderManager().initLoader(0, null, this);

		sectionList = new ArrayList<HashMap<String, String>>();

		submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				secs = sec.getText().toString();
				hrss = hrs.getText().toString();
				mins = min.getText().toString();

				if (!title.getText().toString().equalsIgnoreCase("")
						&& !secs.equalsIgnoreCase("")
						&& !mins.equalsIgnoreCase("")
						&& !hrss.equalsIgnoreCase("")) {

					sek = Integer.parseInt(secs);
					saa = Integer.parseInt(hrss);
					dak = Integer.parseInt(mins);
					if (sek <= 60 && dak <= 60) {
						if (hrss.length() != 1 && mins.length() != 1
								&& secs.length() != 1) {

							duration = hrss + ":" + mins + ":" + secs;
							jumla += ((saa * 3600 * 1000) + (dak * 60 * 1000) + (sek * 1000));

                            getLoaderManager().initLoader(0, null, Timer2.this);

							addSection(new Section(title.getText()
									.toString(), duration));

							// creating new HashMap
							HashMap<String, String> map = new HashMap<String, String>();
							// adding each child node to HashMap key => value
							map.put(TAG_TITLE, title.getText().toString());
							map.put(TAG_TIME, duration);
							// adding HashList to ArrayList
							sectionList.add(map);
							ListView lv = (ListView) findViewById(R.id.list);

							SimpleAdapter adapters = new SimpleAdapter(
									Timer2.this, sectionList,
									R.layout.list_item, new String[] {
											TAG_TITLE, TAG_TIME }, new int[] {
											R.id.title, R.id.duration }) {
								@Override
								public View getView(int pos, View convertView,
										ViewGroup parent) {
									View v = convertView;
									if (v == null) {
										LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
										v = vi.inflate(R.layout.list_item, null);
									}
									TextView tv = (TextView) v
											.findViewById(R.id.title);
									tv.setText(sectionList.get(pos)
											.get("title"));

									TextView tvs = (TextView) v
											.findViewById(R.id.duration);
									tvs.setText(sectionList.get(pos)
											.get("time"));

									return v;
								}
							};
							// updating listview
							lv.setAdapter(adapters);
							((MainActivity) getParent()).updatemain();
						} else {
							String message = "if input is one digit, add a leading zero";

							Toast toast = Toast.makeText(
									getApplicationContext(), message,
									Toast.LENGTH_LONG);
							View txt = toast.getView();
							TextView tv = (TextView) txt
									.findViewById(android.R.id.message);
							tv.setGravity(Gravity.CENTER);
							toast.show();
						}

					} else {
						String message = "Invalid input on time";

						Toast toast = Toast.makeText(getApplicationContext(),
								message, Toast.LENGTH_LONG);
						View txt = toast.getView();
						TextView tv = (TextView) txt
								.findViewById(android.R.id.message);
						tv.setGravity(Gravity.CENTER);
						toast.show();
					}

				} else {
					String message = "Please make sure you have filled both fields as specified";

					Toast toast = Toast.makeText(getApplicationContext(),
							message, Toast.LENGTH_LONG);
					View txt = toast.getView();
					TextView tv = (TextView) txt
							.findViewById(android.R.id.message);
					tv.setGravity(Gravity.CENTER);
					toast.show();

				}
			}
		});
		loadSavedPreferences();
	}

	private void loadSavedPreferences() {

		// Reading all contacts
		Log.d("Reading: ", "Reading all sections..");
		List<Section> sections = getAllSections();

		for (Section cn : sections) {
			String title = cn.getTitle();
			String time = cn.getTime();
			// creating new HashMap
			HashMap<String, String> map = new HashMap<String, String>();
			// adding each child node to HashMap key => value
			map.put(TAG_TITLE, title);
			map.put(TAG_TIME, time);
			// adding HashList to ArrayList
			sectionList.add(map);
			ListView lv = (ListView) findViewById(R.id.list);

			SimpleAdapter adapters = new SimpleAdapter(Timer2.this,
					sectionList, R.layout.list_item, new String[] { TAG_TITLE,
							TAG_TIME }, new int[] { R.id.title, R.id.duration }) ;

			// updating listview
			lv.setAdapter(adapters);
			((MainActivity) getParent()).updatemain();
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
    public void addSection(Section section) {
        // Add a new section record
        ContentValues values = new ContentValues();

        values.put(BuddyProvider.TITLE,section.getTitle());

        values.put(BuddyProvider.TIME,section.getTime());

        Uri uri = getContentResolver().insert(
                BuddyProvider.CONTENT_URI, values);
    }


    @Override
    public Loader<SharedPreferences> onCreateLoader(int id, Bundle args) {
        return (new SharedPreferencesLoader(this));
    }


    @Override
    public void onLoadFinished(Loader<SharedPreferences> loader, SharedPreferences data) {
        SharedPreferences.Editor editor = data
                .edit();
        editor.putInt("storedJumula", +jumla);
        SharedPreferencesLoader.persist(editor);
    }

    @Override
    public void onLoaderReset(Loader<SharedPreferences> loader) {

    }
}
