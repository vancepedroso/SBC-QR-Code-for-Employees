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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.Button;
import android.content.Intent;
import android.net.Uri;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.View;
import com.budiyev.android.codescanner.*;
import com.google.zxing.*;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class MainActivity extends  AppCompatActivity  { 
	
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	
	private LinearLayout linear15;
	private LinearLayout linear16;
	private LinearLayout linear17;
	private LinearLayout linear21;
	private TextView textview4;
	private ImageView imageview2;
	private LinearLayout linear22;
	private LinearLayout linear23;
	private LinearLayout linear24;
	private TextView textview5;
	private EditText user_edittext5;
	private EditText pass_edittext6;
	private Button button1;
	
	private Intent i = new Intent();
	private SharedPreferences file;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
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
		linear15 = (LinearLayout) findViewById(R.id.linear15);
		linear16 = (LinearLayout) findViewById(R.id.linear16);
		linear17 = (LinearLayout) findViewById(R.id.linear17);
		linear21 = (LinearLayout) findViewById(R.id.linear21);
		textview4 = (TextView) findViewById(R.id.textview4);
		imageview2 = (ImageView) findViewById(R.id.imageview2);
		linear22 = (LinearLayout) findViewById(R.id.linear22);
		linear23 = (LinearLayout) findViewById(R.id.linear23);
		linear24 = (LinearLayout) findViewById(R.id.linear24);
		textview5 = (TextView) findViewById(R.id.textview5);
		user_edittext5 = (EditText) findViewById(R.id.user_edittext5);
		pass_edittext6 = (EditText) findViewById(R.id.pass_edittext6);
		button1 = (Button) findViewById(R.id.button1);
		file = getSharedPreferences("file", Activity.MODE_PRIVATE);
		
		user_edittext5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		pass_edittext6.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_hidePassword(pass_edittext6);
			}
		});
		
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if ((0 < user_edittext5.getText().toString().length()) && (0 < pass_edittext6.getText().toString().length())) {
					if (file.getString(user_edittext5.getText().toString(), "").equals(pass_edittext6.getText().toString())) {
						i.setClass(getApplicationContext(), DashboardActivity.class);
						startActivity(i);
					}
					else {
						if (user_edittext5.getText().toString().equals("admin") && pass_edittext6.getText().toString().equals("admin")) {
							i.setClass(getApplicationContext(), DashboardActivity.class);
							startActivity(i);
						}
						else {
							if (user_edittext5.getText().toString().equals("Guard") && pass_edittext6.getText().toString().equals("Guard")) {
								i.setClass(getApplicationContext(), DashboardActivity.class);
								startActivity(i);
							}
							else {
								SketchwareUtil.showMessage(getApplicationContext(), "Login failed");
							}
						}
					}
				}
				else {
					SketchwareUtil.showMessage(getApplicationContext(), "Username and pass required");
				}
			}
		});
	}
	
	private void initializeLogic() {
		_hideBackButton();
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	public void _singlLine (final TextView _editText) {
		_editText.setSingleLine(true);
	}
	
	
	public void _hideBackButton () {
		 getSupportActionBar().setDisplayShowTitleEnabled(true); getSupportActionBar().setDisplayHomeAsUpEnabled(false);
	}
	
	
	public void _hidePassword (final TextView _txt) {
		_txt.setTransformationMethod(android.text.method.PasswordTransformationMethod.getInstance());
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