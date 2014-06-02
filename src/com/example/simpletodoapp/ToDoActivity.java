package com.example.simpletodoapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class ToDoActivity extends Activity {

	private ArrayList<String> items;
	private ArrayAdapter<String> itemsAdapter;
	private ListView lvToDo;
	private EditText edText;
	private final int REQUEST_CODE = 20;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_to_do);
		initialListView();
		this.edText = (EditText) this.findViewById(R.id.etNewItem);
		setListViewListener();
	}

	private void initialListView() {
		this.lvToDo = (ListView) super.findViewById(R.id.lvToDo);
		loadItemsToArrayList();
		this.itemsAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, this.items);
		this.lvToDo.setAdapter(this.itemsAdapter);
	}

	public void addNewItem(View v) {
		this.itemsAdapter.add(this.edText.getText().toString());
		this.edText.setText("");
		writeItemsToFile();
	}

	private void setListViewListener() {
		this.lvToDo.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				items.remove(position);
				itemsAdapter.notifyDataSetChanged();
				writeItemsToFile();
				return true;
			}

		});

		this.lvToDo.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(ToDoActivity.this, EditItemActivity.class);
				i.putExtra("prevStr", items.get(position));
				i.putExtra("prevPos", position);
				startActivityForResult(i, REQUEST_CODE);
			}

		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  // REQUEST_CODE is defined above
	  if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
	     // Extract name value from result extras
	     String name = data.getExtras().getString("newName");
	     int pos = data.getExtras().getInt("pos");
	     this.items.set(pos, name);
	     this.itemsAdapter.notifyDataSetChanged();
	     writeItemsToFile();
	  }
	} 

	private void loadItemsToArrayList() {
		File dirFile = this.getFilesDir();
		File itemFile = new File(dirFile, "items.txt");
		try {
			this.items = new ArrayList<String>(FileUtils.readLines(itemFile));
		} catch (IOException e) {
			this.items = new ArrayList<String>();
		}
	}

	private void writeItemsToFile() {
		File dirFile = this.getFilesDir();
		File itemFile = new File(dirFile, "items.txt");
		try {
			FileUtils.writeLines(itemFile, this.items);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
