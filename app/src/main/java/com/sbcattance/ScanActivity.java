package com.sbcattance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;
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


public class ScanActivity extends  AppCompatActivity  { 
	
	
	private  CodeScanner mCodeScanner;
	private String SavedData = "";
	private HashMap<String, Object> maps = new HashMap<>();
	private HashMap<String, Object> mapppp = new HashMap<>();
	private String str_value = "";
	private String update = "";
	private String latest_version = "";
	private String mylog = "";
	private String url = "";
	private String ilink = "";
	private HashMap<String, Object> location = new HashMap<>();
	private HashMap<String, Object> liss = new HashMap<>();
	private boolean searching = false;
	private double searchnum = 0;
	private HashMap<String, Object> map = new HashMap<>();
	private double n1 = 0;
	private HashMap<String, Object> cacheMap = new HashMap<>();
	private double position = 0;
	private double n2 = 0;
	
	private ArrayList<HashMap<String, Object>> listmap = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> lis = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> itemslistmap = new ArrayList<>();
	private ArrayList<String> catname = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> search_listmap = new ArrayList<>();
	private ArrayList<String> itemnames = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> searchmap = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> itemsList = new ArrayList<>();
	
	private LinearLayout linear1;
	private CodeScannerView scannerview;
	private TextView textview1;
	
	private SharedPreferences database;
	private Calendar calendar = Calendar.getInstance();
	private AlertDialog.Builder d;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.scan);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		scannerview = (CodeScannerView) findViewById(R.id.scannerview);
		textview1 = (TextView) findViewById(R.id.textview1);
		database = getSharedPreferences("database", Activity.MODE_PRIVATE);
		d = new AlertDialog.Builder(this);
	}
	
	private void initializeLogic() {
		n1 = 0;
		listmap = new Gson().fromJson(database.getString("data", ""), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
		CodeScannerView scannerView = findViewById(R.id.scannerview);
		 mCodeScanner = new CodeScanner(this, scannerView);
		mCodeScanner.setDecodeCallback(new DecodeCallback() {
			    @Override public void onDecoded(@NonNull final Result result) { runOnUiThread(new Runnable() {
					         @Override
					          public void run() { 
						          
						       for(int _repeat199 = 0; _repeat199 < (int)(listmap.size()); _repeat199++) {
							if (result.getText().contains(listmap.get((int)n1).get("id").toString())) {
								maps = new HashMap<>();
								maps.put("id", listmap.get((int)n1).get("name").toString());
								maps.put("date", new SimpleDateFormat("MMM dd yyyy hh:mm").format(calendar.getTime()));
								maps.put("arrival", "In");
								SketchwareUtil.showMessage(getApplicationContext(), "Thank You ! Your In Time is ".concat(new SimpleDateFormat("MMM dd yyyy hh:mm").format(calendar.getTime())));
								database.edit().putString("new", new Gson().toJson(maps)).commit();
								database.edit().putString("position", getIntent().getStringExtra("position")).commit();
								finish();
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