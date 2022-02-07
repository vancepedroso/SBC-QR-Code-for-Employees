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
import android.view.View;
import android.widget.AdapterView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.budiyev.android.codescanner.*;
import com.google.zxing.*;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class SignupActivity extends  AppCompatActivity  { 
	
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private double t = 0;
	private HashMap<String, Object> ma = new HashMap<>();
	
	private ArrayList<HashMap<String, Object>> mat = new ArrayList<>();
	private ArrayList<String> list = new ArrayList<>();
	
	private LinearLayout linear1;
	private TextView textview1;
	private LinearLayout linear2;
	private LinearLayout linear3;
	private LinearLayout linear4;
	private LinearLayout linear5;
	private EditText edittext1;
	private EditText edittext2;
	private Button button1;
	private ListView listview1;
	
	private SharedPreferences file;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.signup);
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
		textview1 = (TextView) findViewById(R.id.textview1);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		edittext1 = (EditText) findViewById(R.id.edittext1);
		edittext2 = (EditText) findViewById(R.id.edittext2);
		button1 = (Button) findViewById(R.id.button1);
		listview1 = (ListView) findViewById(R.id.listview1);
		file = getSharedPreferences("file", Activity.MODE_PRIVATE);
		
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if ((0 < edittext1.getText().toString().length()) && (0 < edittext2.getText().toString().length())) {
					ma = new HashMap<>();
					ma.put("username", edittext1.getText().toString());
					ma.put("password", edittext2.getText().toString());
					file.edit().putString(edittext1.getText().toString(), edittext2.getText().toString()).commit();
					file.edit().putString("irien", new Gson().toJson(ma)).commit();
					file.edit().putString("positionx", getIntent().getStringExtra("position")).commit();
					SketchwareUtil.showMessage(getApplicationContext(), "Sign up complete");
					finish();
				}
				else {
					SketchwareUtil.showMessage(getApplicationContext(), "User and Pass required");
				}
			}
		});
		
		listview1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				PopupMenu popup = new PopupMenu(SignupActivity.this, listview1);
				Menu menu = popup.getMenu();
				menu.add("Delete");
				menu.add("Edit");
				popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
					@Override
					public boolean onMenuItemClick(MenuItem item){
						switch (item.getTitle().toString()){
							case "Delete":
							mat.remove((int)(_position));
							file.edit().putString("alls", new Gson().toJson(mat)).commit();
							listview1.setAdapter(new Listview1Adapter(mat));
							((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
							break;
							case "Edit":
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
		if (!file.getString("alls", "").equals("")) {
			mat = new Gson().fromJson(file.getString("alls", ""), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
			listview1.setAdapter(new Listview1Adapter(mat));
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
		if (!file.getString("irien", "").equals("")) {
			ma = new Gson().fromJson(file.getString("irien", ""), new TypeToken<HashMap<String, Object>>(){}.getType());
			file.edit().putString("irien", "").commit();
			if (file.getString("positionx", "").equals("") || file.getString("positionx", "").equals("none")) {
				mat.add(ma);
			}
			else {
				mat.add((int)Double.parseDouble(file.getString("positionx", "")), ma);
				mat.remove((int)(Double.parseDouble(file.getString("positionx", "")) + 1));
				file.edit().putString("positionx", "").commit();
			}
			file.edit().putString("alls", new Gson().toJson(mat)).commit();
			listview1.setAdapter(new Listview1Adapter(mat));
			((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
		}
	}
	public void _hideBackButton () {
		 getSupportActionBar().setDisplayShowTitleEnabled(true); getSupportActionBar().setDisplayHomeAsUpEnabled(false);
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
				_view = _inflater.inflate(R.layout.admintotal, null);
			}
			
			final LinearLayout linear2 = (LinearLayout) _view.findViewById(R.id.linear2);
			final LinearLayout linear3 = (LinearLayout) _view.findViewById(R.id.linear3);
			final LinearLayout linear4 = (LinearLayout) _view.findViewById(R.id.linear4);
			final TextView textview1 = (TextView) _view.findViewById(R.id.textview1);
			final TextView textview2 = (TextView) _view.findViewById(R.id.textview2);
			
			textview1.setText(mat.get((int)_position).get("username").toString());
			textview2.setText(mat.get((int)_position).get("password").toString());
			
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