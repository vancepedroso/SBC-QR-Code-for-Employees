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
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.app.Activity;
import android.content.SharedPreferences;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.budiyev.android.codescanner.*;
import com.google.zxing.*;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class TotalviewActivity extends  AppCompatActivity  { 
	
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private HashMap<String, Object> maps = new HashMap<>();
	private double n1 = 0;
	private HashMap<String, Object> mats = new HashMap<>();
	private HashMap<String, Object> out = new HashMap<>();
	private HashMap<String, Object> in = new HashMap<>();
	private double searchnum = 0;
	private HashMap<String, Object> map = new HashMap<>();
	private HashMap<String, Object> cacheMap = new HashMap<>();
	private boolean searching = false;
	
	private ArrayList<HashMap<String, Object>> maplist = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> t = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> tt = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> searchmap = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> itemsList = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> itemslistmap = new ArrayList<>();
	private ArrayList<String> catname = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> search_listmap = new ArrayList<>();
	private ArrayList<String> itemnames = new ArrayList<>();
	
	private LinearLayout linear1;
	private LinearLayout linear2;
	private TextView textview1;
	private ListView listview1;
	
	private SharedPreferences database;
	private Calendar c = Calendar.getInstance();
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.totalview);
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
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		textview1 = (TextView) findViewById(R.id.textview1);
		listview1 = (ListView) findViewById(R.id.listview1);
		database = getSharedPreferences("database", Activity.MODE_PRIVATE);
	}
	
	private void initializeLogic() {
		_hideBackButton();
		if (!database.getString("all", "").equals("")) {
			maplist = new Gson().fromJson(database.getString("all", ""), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
			n1 = 0;
			searchnum = 0;
			for(int _repeat174 = 0; _repeat174 < (int)(maplist.size()); _repeat174++) {
				if (maplist.get((int)searchnum).get("id").toString().contains(maplist.get((int)n1).get("id").toString())) {
					maps = new HashMap<>();
					maps.put("name", maplist.get((int)n1).get("id").toString());
					maps.put("in", maplist.get((int)n1).get("date").toString());
					maps.put("out", maplist.get((int)searchnum).get("date").toString());
					maps.put("total", String.valueOf((long)(n1 - searchnum)));
					searchmap.add(maps);
				}
				searchnum++;
				n1++;
			}
			listview1.setAdapter(new Listview1Adapter(searchmap));
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
	
	public void _hideBackButton () {
		 getSupportActionBar().setDisplayShowTitleEnabled(true); getSupportActionBar().setDisplayHomeAsUpEnabled(false);
	}
	
	
	public void _deletes (final double _position) {
		
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
				_view = _inflater.inflate(R.layout.totalreports, null);
			}
			
			final LinearLayout linear2 = (LinearLayout) _view.findViewById(R.id.linear2);
			final LinearLayout linear3 = (LinearLayout) _view.findViewById(R.id.linear3);
			final LinearLayout linear4 = (LinearLayout) _view.findViewById(R.id.linear4);
			final LinearLayout linear5 = (LinearLayout) _view.findViewById(R.id.linear5);
			final LinearLayout linear6 = (LinearLayout) _view.findViewById(R.id.linear6);
			final TextView name = (TextView) _view.findViewById(R.id.name);
			final TextView In = (TextView) _view.findViewById(R.id.In);
			final TextView out = (TextView) _view.findViewById(R.id.out);
			final TextView total = (TextView) _view.findViewById(R.id.total);
			
			name.setText(searchmap.get((int)_position).get("name").toString());
			In.setText(searchmap.get((int)_position).get("in").toString());
			out.setText(searchmap.get((int)_position).get("out").toString());
			total.setText(searchmap.get((int)_position).get("total").toString());
			
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