package com.sbcattance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.AppBarLayout;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.webkit.*;
import android.animation.*;
import android.view.animation.*;
import java.util.*;
import java.util.regex.*;
import java.text.*;
import org.json.*;
import java.util.HashMap;
import java.util.ArrayList;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.text.Editable;
import android.text.TextWatcher;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.budiyev.android.codescanner.*;
import com.google.zxing.*;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class AddfacultyActivity extends  AppCompatActivity  { 
	
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private String hasilScan = "";
	private HashMap<String, Object> location = new HashMap<>();
	
	private ArrayList<String> map = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
	
	private LinearLayout linear1;
	private LinearLayout linear3;
	private LinearLayout linear4;
	private LinearLayout linear5;
	private TextView textview1;
	private EditText edittext1;
	private EditText edittext2;
	private EditText edittext3;
	private Button scan;
	private Button save;
	private ListView listview1;
	
	private SharedPreferences database;
	private Intent i = new Intent();
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.addfaculty);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		_app_bar = (AppBarLayout) findViewById(R.id._app_bar);
		_coordinator = (CoordinatorLayout) findViewById(R.id._coordinator);
		_toolbar = (Toolbar) findViewById(R.id._toolbar);
		setSupportActionBar(_toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) {
				onBackPressed();
			}
		});
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		textview1 = (TextView) findViewById(R.id.textview1);
		edittext1 = (EditText) findViewById(R.id.edittext1);
		edittext2 = (EditText) findViewById(R.id.edittext2);
		edittext3 = (EditText) findViewById(R.id.edittext3);
		scan = (Button) findViewById(R.id.scan);
		save = (Button) findViewById(R.id.save);
		listview1 = (ListView) findViewById(R.id.listview1);
		database = getSharedPreferences("database", Activity.MODE_PRIVATE);
		
		edittext2.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		scan.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), ScanidActivity.class);
				startActivity(i);
			}
		});
		
		save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if ((edittext1.getText().toString().length() > 0) && ((edittext2.getText().toString().length() > 0) && (edittext3.getText().toString().length() > 0))) {
					location = new HashMap<>();
					location.put("name", edittext1.getText().toString());
					location.put("id", edittext2.getText().toString());
					location.put("classification", edittext3.getText().toString());
					SketchwareUtil.showMessage(getApplicationContext(), "Success!");
					database.edit().putString("info", new Gson().toJson(location)).commit();
					database.edit().putString("location", getIntent().getStringExtra("location")).commit();
					edittext1.setText("");
					edittext2.setText("");
					edittext3.setText("");
					finish();
				}
				else {
					SketchwareUtil.showMessage(getApplicationContext(), "Error");
				}
			}
		});
		
		listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				SketchwareUtil.showMessage(getApplicationContext(), list.get((int)_position).get("name").toString().concat(list.get((int)_position).get("id").toString().concat(list.get((int)_position).get("classification").toString())));
			}
		});
		
		listview1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				PopupMenu popup = new PopupMenu(AddfacultyActivity.this, listview1);
				Menu menu = popup.getMenu();
				menu.add("Delete");
				menu.add("Edit");
				popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
					@Override
					public boolean onMenuItemClick(MenuItem item){
						switch (item.getTitle().toString()){
							case "Delete":
							_deletes(_position);
							break;
							case "Edit":
							_edit(_position);
							break;}
						return true;
					}
				});
				popup.show();
				return true;
			}
		});
	}
	
	private void initializeLogic() {
		_hideBackButton();
		if (!database.getString("data", "").equals("")) {
			list = new Gson().fromJson(database.getString("data", ""), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
			edittext2.setText(getIntent().getStringExtra("results"));
			listview1.setAdapter(new Listview1Adapter(list));
			((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
		}
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	@Override
	public void onStart() {
		super.onStart();
		if (!database.getString("info", "").equals("")) {
			location = new Gson().fromJson(database.getString("info", ""), new TypeToken<HashMap<String, Object>>(){}.getType());
			database.edit().putString("info", "").commit();
			if (database.getString("location", "").equals("") || database.getString("location", "").equals("none")) {
				list.add(location);
			}
			else {
				list.add((int)Double.parseDouble(database.getString("location", "")), location);
				list.remove((int)(Double.parseDouble(database.getString("location", "")) + 1));
				database.edit().putString("location", "").commit();
			}
			database.edit().putString("data", new Gson().toJson(list)).commit();
			listview1.setAdapter(new Listview1Adapter(list));
			((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
		}
	}
	public void _scan () {
		// add this On Button Click
		try { 
			Intent i = new Intent("com.google.zxing.client.android.SCAN"); i.putExtra("SCAN_MODE", "SCAN_QR_MODE");
			i.putExtra("PROMPT_MESSAGE","");
			startActivityForResult(i, 0); 
		} 
		catch (Exception e) { 
			Uri goplay = Uri.parse("market://details?id=com.google.zxing.client.android"); 
			Intent gplay = new Intent(Intent.ACTION_VIEW,goplay); 
			startActivity(gplay);
		}
	}
	
	
	public void _hideBackButton () {
		 getSupportActionBar().setDisplayShowTitleEnabled(true); getSupportActionBar().setDisplayHomeAsUpEnabled(false);
	}
	
	
	public void _deletes (final double _position) {
		list.remove((int)(_position));
		database.edit().putString("data", new Gson().toJson(list)).commit();
		listview1.setAdapter(new Listview1Adapter(list));
		((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
	}
	
	
	public void _edit (final double _position) {
		edittext1.setText(list.get((int)_position).get("name").toString());
		edittext2.setText(list.get((int)_position).get("id").toString());
		edittext3.setText(list.get((int)_position).get("classification").toString());
		database.edit().putString("data", new Gson().toJson(list)).commit();
		listview1.setAdapter(new Listview1Adapter(list));
		((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
	}
	
	
	public class Listview1Adapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public Listview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public int getCount() {
			return _data.size();
		}
		
		@Override
		public HashMap<String, Object> getItem(int _index) {
			return _data.get(_index);
		}
		
		@Override
		public long getItemId(int _index) {
			return _index;
		}
		@Override
		public View getView(final int _position, View _v, ViewGroup _container) {
			LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _view = _v;
			if (_view == null) {
				_view = _inflater.inflate(R.layout.add, null);
			}
			
			final LinearLayout linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
			final LinearLayout linear2 = (LinearLayout) _view.findViewById(R.id.linear2);
			final LinearLayout linear3 = (LinearLayout) _view.findViewById(R.id.linear3);
			final LinearLayout linear4 = (LinearLayout) _view.findViewById(R.id.linear4);
			final TextView textview1 = (TextView) _view.findViewById(R.id.textview1);
			final TextView textview2 = (TextView) _view.findViewById(R.id.textview2);
			final TextView textview3 = (TextView) _view.findViewById(R.id.textview3);
			
			textview1.setText(list.get((int)_position).get("name").toString());
			textview2.setText(list.get((int)_position).get("id").toString());
			textview3.setText(list.get((int)_position).get("classification").toString());
			
			return _view;
		}
	}
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input){
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels(){
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels(){
		return getResources().getDisplayMetrics().heightPixels;
	}
	
}