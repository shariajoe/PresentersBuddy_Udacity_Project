package com.example.sharia.presentersbuddy;

public class Section {

	// private variables
	int _id;
	String _title;
	String _time;

	// Empty constructor
	public Section() {

	}

	// constructor
	public Section(int id, String title, String time) {
		this._id = id;
		this._title = title;
		this._time = time;
	}

	// constructor
	public Section(String title, String time) {
		this._title = title;
		this._time = time;
	}

	// getting ID
	public int getID() {
		return this._id;
	}

	// setting id
	public void setID(int id) {
		this._id = id;
	}

	// getting title
	public String getTitle() {
		return this._title;
	}

	// setting title
	public void setTitle(String title) {
		this._title = title;
	}

	// getting time
	public String getTime() {
		return this._time;
	}

	// setting time
	public void setTime(String time) {
		this._time = time;
	}

}
