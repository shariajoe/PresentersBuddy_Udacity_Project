package com.example.sharia.presentersbuddy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class MyFragment extends Fragment {

	private static int SPLASH_TIME_OUT3 = 500;
	public static final String PG_NO = "0";

	EditText notes, title;
	TextView inv;
	Button save, delete;
	String ref, ref2;
	int pg;

	public static MyFragment newInstance(String i) {
		MyFragment f = new MyFragment();
		Bundle bdl = new Bundle(1);

		bdl.putString(PG_NO, i);
		f.setArguments(bdl);

		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		String pgno = getArguments().getString(PG_NO);

		pg = Integer.parseInt(pgno);
		ref = "storedmsg" + pg;
		ref2 = "storedttl" + pg;

		SharedPreferences pref = getActivity()
				.getSharedPreferences("MyPref", 0);
		String msg = pref.getString(ref, "");
		String ttl = pref.getString(ref2, "");

		View v = inflater.inflate(R.layout.myfragment_layout, container, false);

		notes = (EditText) v.findViewById(R.id.textView);
		title = (EditText) v.findViewById(R.id.txtView);
		save = (Button) v.findViewById(R.id.save);
		delete = (Button) v.findViewById(R.id.delete);
		inv = (TextView) v.findViewById(R.id.inv);
		inv.setText("" + pg);

		save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String not = notes.getText().toString();
				String titl = title.getText().toString();
				if (!not.equalsIgnoreCase("") && !titl.equalsIgnoreCase("")) {

					SharedPreferences pref;
					Toast toast = Toast.makeText(getActivity()
							.getApplicationContext(), "note saved",
							Toast.LENGTH_SHORT);
					View txt = toast.getView();
					TextView tv = (TextView) txt
							.findViewById(android.R.id.message);
					tv.setGravity(Gravity.CENTER);
					toast.show();

					pref = getActivity().getSharedPreferences("MyPref", 0);
					SharedPreferences.Editor edt = pref.edit();
					edt.putString("storedmsg" + inv.getText().toString(), not);
					edt.putString("storedttl" + inv.getText().toString(), titl);
					edt.apply();
				} else {
					Toast toast = Toast
							.makeText(
									getActivity().getApplicationContext(),
									"can not save an empty field \n check title and notes",
									Toast.LENGTH_SHORT);
					View txt = toast.getView();
					TextView tv = (TextView) txt
							.findViewById(android.R.id.message);
					tv.setGravity(Gravity.CENTER);
					toast.show();
				}

			}
		});
		delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast toast = Toast.makeText(getActivity()
						.getApplicationContext(), "note deleted",
						Toast.LENGTH_SHORT);
				View txt = toast.getView();
				TextView tv = (TextView) txt.findViewById(android.R.id.message);
				tv.setGravity(Gravity.CENTER);
				toast.show();
				SharedPreferences pref = getActivity().getSharedPreferences(
						"MyPref", 0);
				int counter = pref.getInt("counter", 0);
				int pgn = Integer.parseInt(inv.getText().toString());
				for (int i = pgn; i < counter; i++) {

					String msg = pref.getString("storedmsg" + (i + 1), "");
					String ttl = pref.getString("storedttl" + (i + 1), "");
					SharedPreferences.Editor edt = pref.edit();
					edt.putString("storedmsg" + (i), msg);
					edt.putString("storedttl" + (i), ttl);
					edt.apply();
				}
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						SharedPreferences pref = getActivity()
								.getSharedPreferences("MyPref", 0);
						int counter = pref.getInt("counter", 0);
						SharedPreferences.Editor edt = pref.edit();
						edt.remove("storedmsg" + counter);
						edt.remove("storedttl" + counter);
						if (counter > 0) {
							edt.putInt("counter", counter - 1);
						}
						edt.apply();

						Intent intent = new Intent(getActivity(),
								MainActivity.class);
						startActivity(intent);
					}
				}, SPLASH_TIME_OUT3);
			}
		});
		notes.setHintTextColor(Color.WHITE);
		title.setHintTextColor(Color.BLACK);

		notes.setText(msg);
		title.setText(ttl);

		return v;
	}

}
