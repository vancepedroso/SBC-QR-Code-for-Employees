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
import android.widget.EditText;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Build;
import androidx.core.content.FileProvider;
import java.io.File;
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
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;


public class AddstudentActivity extends  AppCompatActivity  { 
	
	public final int REQ_CD_CAMERA = 101;
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private  CodeScanner mCodeScanner;
	private HashMap<String, Object> maps = new HashMap<>();
	private HashMap<String, Object> map = new HashMap<>();
	private String hasilScan = "";
	
	private ArrayList<HashMap<String, Object>> listmap = new ArrayList<>();
	
	private LinearLayout linear1;
	private LinearLayout linear3;
	private EditText edittext1;
	private EditText edittext2;
	private EditText edittext3;
	private Button button2;
	private Button button1;
	private ListView listview3;
	
	private Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	private File _file_camera;
	private SharedPreferences database;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.addstudent);
		initialize(_savedInstanceState);
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
		}
		else {
			initializeLogic();
		}
	}
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
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
		edittext1 = (EditText) findViewById(R.id.edittext1);
		edittext2 = (EditText) findViewById(R.id.edittext2);
		edittext3 = (EditText) findViewById(R.id.edittext3);
		button2 = (Button) findViewById(R.id.button2);
		button1 = (Button) findViewById(R.id.button1);
		listview3 = (ListView) findViewById(R.id.listview3);
		_file_camera = FileUtil.createNewPictureFile(getApplicationContext());
		Uri _uri_camera = null;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			_uri_camera= FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", _file_camera);
		}
		else {
			_uri_camera = Uri.fromFile(_file_camera);
		}
		camera.putExtra(MediaStore.EXTRA_OUTPUT, _uri_camera);
		camera.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		database = getSharedPreferences("database", Activity.MODE_PRIVATE);
		
		edittext1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		edittext2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		edittext3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		button2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_scan();
			}
		});
		
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if ((edittext1.getText().toString().length() > 0) && ((edittext2.getText().toString().length() > 0) && (edittext3.getText().toString().length() > 0))) {
					map = new HashMap<>();
					map.put("name", edittext1.getText().toString());
					map.put("id", edittext2.getText().toString());
					map.put("classification", edittext3.getText().toString());
					SketchwareUtil.showMessage(getApplicationContext(), "Success!");
					database.edit().putString("information", new Gson().toJson(map)).commit();
					database.edit().putString("positions", getIntent().getStringExtra("positions")).commit();
				}
				else {
					SketchwareUtil.showMessage(getApplicationContext(), "Error");
				}
			}
		});
		
		listview3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				
			}
		});
		
		listview3.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				
				return true;
			}
		});
	}
	
	private void initializeLogic() {
		_hideBackButton();
		if (!database.getString("alls", "").equals("")) {
			listmap = new Gson().fromJson(database.getString("alls", ""), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
			listview3.setAdapter(new Listview3Adapter(listmap));
			((BaseAdapter)listview3.getAdapter()).notifyDataSetChanged();
		}
	}/*
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
if (!database.getString("information", "").equals("")) {
map = new Gson().fromJson(database.getString("information", ""), new TypeToken<HashMap<String, Object>>(){}.getType());
database.edit().putString("information", "").commit();
if (database.getString("positions", "").equals("") || database.getString("positions", "").equals("none")) {
listmap.add(map);
}
else {
listmap.add((int)Double.parseDouble(database.getString("positions", "")), map);
listmap.remove((int)(Double.parseDouble(database.getString("positions", "")) + 1));
database.edit().putString("positions", "").commit();
}
database.edit().putString("alls", new Gson().toJson(listmap)).commit();
listview3.setAdapter(new Listview3Adapter(listmap));
((BaseAdapter)listview3.getAdapter()).notifyDataSetChanged();
}
}
public void _dumi () {
MUST add this at the FIRST MoreBlock, not second or third or last, must be at FIRST
*/
	@Override 
	protected void onActivityResult(int requestCode, int resultCode, Intent data) { 
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 0) { 
			if (resultCode == RESULT_OK) { hasilScan = data.getStringExtra("SCAN_RESULT"); } 
			if(resultCode == RESULT_CANCELED){ hasilScan = "Di batalkan !!!"; } } 
		edittext2.setText(hasilScan);
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
	
	
	public class Listview3Adapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public Listview3Adapter(ArrayList<HashMap<String, Object>> _arr) {
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
				_view = _inflater.inflate(R.layout.custom, null);
			}
			
			final LinearLayout linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
			final LinearLayout linear2 = (LinearLayout) _view.findViewById(R.id.linear2);
			final LinearLayout linear3 = (LinearLayout) _view.findViewById(R.id.linear3);
			final LinearLayout linear4 = (LinearLayout) _view.findViewById(R.id.linear4);
			final TextView textview1 = (TextView) _view.findViewById(R.id.textview1);
			final TextView textview2 = (TextView) _view.findViewById(R.id.textview2);
			final TextView textview3 = (TextView) _view.findViewById(R.id.textview3);
			
			textview1.setText(listmap.get((int)_position).get("name").toString());
			textview2.setText(listmap.get((int)_position).get("id").toString());
			textview3.setText(listmap.get((int)_position).get("classification").toString());
			
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