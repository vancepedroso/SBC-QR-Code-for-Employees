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
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Build;
import androidx.core.content.FileProvider;
import java.io.File;
import android.app.Activity;
import android.content.SharedPreferences;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import android.app.AlertDialog;
import android.content.DialogInterface;
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


public class Scan2Activity extends  AppCompatActivity  { 
	
	public final int REQ_CD_C = 101;
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private HashMap<String, Object> maps = new HashMap<>();
	private  CodeScanner mCodeScanner;
	private double n1 = 0;
	private double m1 = 0;
	private String minutes = "";
	private String hour = "";
	private double a = 0;
	private double result = 0;
	private double i = 0;
	private double result2 = 0;
	private String SaveData = "";
	private double diff = 0;
	private String indate = "";
	private String outdate = "";
	private String dayDifference = "";
	
	private ArrayList<HashMap<String, Object>> listmap = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> maplist = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> datemap = new ArrayList<>();
	
	private CodeScannerView scannerview;
	
	private Intent c = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	private File _file_c;
	private SharedPreferences database;
	private Calendar cal = Calendar.getInstance();
	private AlertDialog.Builder d;
	private Calendar calendar = Calendar.getInstance();
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.scan2);
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
		scannerview = (CodeScannerView) findViewById(R.id.scannerview);
		_file_c = FileUtil.createNewPictureFile(getApplicationContext());
		Uri _uri_c = null;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			_uri_c= FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", _file_c);
		}
		else {
			_uri_c = Uri.fromFile(_file_c);
		}
		c.putExtra(MediaStore.EXTRA_OUTPUT, _uri_c);
		c.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		database = getSharedPreferences("database", Activity.MODE_PRIVATE);
		d = new AlertDialog.Builder(this);
	}
	
	private void initializeLogic() {
		_hideBackButton();
		maplist = new Gson().fromJson(database.getString("data", ""), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
		listmap = new Gson().fromJson(database.getString("all", ""), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
		n1 = 0;
		m1 = 0;
		CodeScannerView scannerView = findViewById(R.id.scannerview);
		 mCodeScanner = new CodeScanner(this, scannerView);
		mCodeScanner.setDecodeCallback(new DecodeCallback() {
			    @Override public void onDecoded(@NonNull final Result result) { runOnUiThread(new Runnable() {
					         @Override
					          public void run() { 
						          
						       for(int _repeat103 = 0; _repeat103 < (int)(maplist.size()); _repeat103++) {
							if (result.getText().contains(maplist.get((int)n1).get("id").toString())) {
								for(int _repeat148 = 0; _repeat148 < (int)(listmap.size()); _repeat148++) {
									if (listmap.get((int)m1).get("id").toString().contains(maplist.get((int)n1).get("name").toString())) {
										maps = new HashMap<>();
										maps.put("id", maplist.get((int)n1).get("name").toString());
										maps.put("date", new SimpleDateFormat("MMM dd yyyy hh:mm").format(cal.getTime()));
										maps.put("arrival", "Out");
										SketchwareUtil.showMessage(getApplicationContext(), "Thank You ! Your In Time is ".concat(new SimpleDateFormat("MMM dd yyyy hh:mm").format(cal.getTime())));
										database.edit().putString("new", new Gson().toJson(maps)).commit();
										database.edit().putString("position", getIntent().getStringExtra("position")).commit();
										finish();
									}
									m1++;
								}
							}
							n1++;
						}
						          
						           } }
				           
				           ); }
			           
			            }
		           
		           );
		scannerView.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View view) { mCodeScanner.startPreview(); } }); 
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
	
	
	public void _compare (final double _total) {
		database.edit().putString("key", String.valueOf((long)(_total))).commit();
	}
	
	
	public void _computeTotal (final double _num1) {
		database.edit().putString("total", String.valueOf((long)(_num1))).commit();
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