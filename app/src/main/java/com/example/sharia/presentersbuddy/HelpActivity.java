package com.example.sharia.presentersbuddy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class HelpActivity extends Activity {
	TextView note, head, time,menu;
	Button back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);


		head = (TextView) findViewById(R.id.head);
		note = (TextView) findViewById(R.id.note);
		time = (TextView) findViewById(R.id.time);
		menu = (TextView) findViewById(R.id.menu);
		back = (Button) findViewById(R.id.back);

		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent in = new Intent(HelpActivity.this, MainActivity.class);

				startActivity(in);
			}
		});
		note.setText("*******************************\n\n" +
		         "1. Press to view notes in full screen\n"
				+ "to revert back press back button on your phone\n\n"
				+ "2. Press to play timer or pause timer\n\n"
				+ "3. Press to add a new page to your notes\n"
				+ "Slide the notes view from start page to view added page\n\n"
				+ "4. Save page (VERY IMPORTANT)\n\n" + "5. Delete page\n\n"
				+ "6. View notes tab\n\n" + "7. View timer tab\n\n"
				+ "8. Click to enter title\n\n" + "9.Click to enter notes\n\n");
		time.setText("*******************************\n\n" +
				"9. Enter the name of a section of your presentation\n\n" +
				"10. Enter the number of hours you would wish to take on your presentation(format:00)\n" +
				"If one digit ensure there is a leading zero\n\n" +
				"11. Enter the number of minutes you would wish to take on your presentation(format:00)\n" +
				"If one digit ensure there is a leading zero\n\n" +
				"12. Enter the number of seconds you would wish to take on your presentation(format:00)\n" +
				"If one digit ensure there is a leading zero\n\n" +
				"13. Your stored section with time allocated displays as on this list\n\n" );
		menu.setText(
				"*******************************\n\n" +
				"On clicking the menu you will find additional options which may\n" +
				"assist in further modification of your presentation i.e\n\n" +
				"1. Delete notes\n" +
				"2. Clear timer: removes all items on section list\n" +
				"3. Help: displays this page");
	}
}
