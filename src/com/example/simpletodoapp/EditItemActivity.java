package com.example.simpletodoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends Activity {

	private EditText edText;
	private int pos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);

		this.edText = (EditText) this.findViewById(R.id.edEditItem);
		String prevStr = getIntent().getStringExtra("prevStr");
		this.pos = getIntent().getExtras().getInt("prevPos");
		this.edText.setText(prevStr);
		this.edText.setCursorVisible(true);
		this.edText.setSelection(this.edText.getText().length());
	}

	public void saveItem(View v) {
		Intent data = new Intent();
		// Pass relevant data back as a result
		data.putExtra("newName", this.edText.getText().toString());
		data.putExtra("pos", this.pos);
		// Activity finished ok, return the data
		setResult(RESULT_OK, data); 
		finish();
	}

}
