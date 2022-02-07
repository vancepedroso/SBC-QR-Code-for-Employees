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
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Button;
import android.content.Intent;
import android.net.Uri;
import android.app.AlertDialog;
import android.content.DialogInterface;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.ClipData;
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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.*;
import jxl.*;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.Cell;
import jxl.Sheet;
import jxl.write.*;
import jxl.write.WriteException;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.biff.RowsExceededException;

public class DashboardActivity extends  AppCompatActivity  { 
	
	public final int REQ_CD_F = 101;
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private double size = 0;
	private String item = "";
	private String buttonLabel = "";
	private HashMap<String, Object> listview = new HashMap<>();
	private String itemKey = "";
	private HashMap<String, Object> maps = new HashMap<>();
	private String text1 = "";
	private String SavedData = "";
	private double k = 0;
	private String filename = "";
	private String path = "";
	private String fileName = "";
	
	private ArrayList<HashMap<String, Object>> listmap = new ArrayList<>();
	private ArrayList<String> lisview = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> map = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> maplist = new ArrayList<>();
	
	private LinearLayout linear2;
	private LinearLayout linear3;
	private LinearLayout linear4;
	private ListView listview1;
	private LinearLayout linear6;
	private RadioGroup radiogroup4;
	private LinearLayout linear8;
	private ImageView imageview1;
	private RadioButton radiobutton1;
	private RadioButton radiobutton2;
	private Button button1;
	
	private Intent i = new Intent();
	private AlertDialog.Builder D;
	private Calendar c = Calendar.getInstance();
	private SharedPreferences database;
	private Intent f = new Intent(Intent.ACTION_GET_CONTENT);
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.dashboard);
		initialize(_savedInstanceState);
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
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
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		listview1 = (ListView) findViewById(R.id.listview1);
		linear6 = (LinearLayout) findViewById(R.id.linear6);
		radiogroup4 = (RadioGroup) findViewById(R.id.radiogroup4);
		linear8 = (LinearLayout) findViewById(R.id.linear8);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		radiobutton1 = (RadioButton) findViewById(R.id.radiobutton1);
		radiobutton2 = (RadioButton) findViewById(R.id.radiobutton2);
		button1 = (Button) findViewById(R.id.button1);
		D = new AlertDialog.Builder(this);
		database = getSharedPreferences("database", Activity.MODE_PRIVATE);
		f.setType("text/*");
		f.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		
		listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				SketchwareUtil.showMessage(getApplicationContext(), maplist.get((int)_position).get("id").toString().concat(";").concat(maplist.get((int)_position).get("date").toString().concat(";").concat(maplist.get((int)_position).get("arrival").toString())));
			}
		});
		
		listview1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				PopupMenu popup = new PopupMenu(DashboardActivity.this, listview1);
				Menu menu = popup.getMenu();
				menu.add("Delete");
				popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
					@Override
					public boolean onMenuItemClick(MenuItem item){
						switch (item.getTitle().toString()){
							case "Delete":
							_deletes(_position);
							break;}
						return true;
					}
				});
				popup.show();
				return true;
			}
		});
		
		imageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_PopUpMenu(imageview1);
			}
		});
		
		radiobutton1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		radiobutton2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (radiobutton1.isChecked()) {
					i.setClass(getApplicationContext(), ScanActivity.class);
					startActivity(i);
				}
				else {
					if (radiobutton2.isChecked()) {
						i.setClass(getApplicationContext(), Scan2Activity.class);
						startActivity(i);
					}
					else {
						SketchwareUtil.showMessage(getApplicationContext(), "Please select option first");
					}
				}
			}
		});
	}
	
	private void initializeLogic() {
		_hideBackButton();
		if (!database.getString("all", "").equals("")) {
			maplist = new Gson().fromJson(database.getString("all", ""), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
			listview1.setAdapter(new Listview1Adapter(maplist));
			((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		
		menu.add("Add new Faculty").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
		
		menu.add("Add new admin").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
		
		menu.add("Exit").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
		
		return true;
	}
	
	@Override 
	public boolean onOptionsItemSelected(final MenuItem item) {
		switch (item.getTitle().toString()) {
			
			case "Add new Faculty":
			_aboutClicked();
			return true;
			
			case "Add new admin":
			_privacyClicked();
			return true;
			
			case "Exit":
			_exitClicked();
			return true;
			
			default:
			return super.onOptionsItemSelected(item);
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
		if (!database.getString("new", "").equals("")) {
			maps = new Gson().fromJson(database.getString("new", ""), new TypeToken<HashMap<String, Object>>(){}.getType());
			database.edit().putString("new", "").commit();
			if (database.getString("position", "").equals("") || database.getString("position", "").equals("none")) {
				maplist.add(maps);
			}
			else {
				maplist.add((int)Double.parseDouble(database.getString("position", "")), maps);
				maplist.remove((int)(Double.parseDouble(database.getString("position", "")) + 1));
				database.edit().putString("position", "").commit();
			}
			database.edit().putString("all", new Gson().toJson(maplist)).commit();
			listview1.setAdapter(new Listview1Adapter(maplist));
			((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
		}
	}
	
	public void _aboutClicked () {
		i.setClass(getApplicationContext(), AddfacultyActivity.class);
		startActivity(i);
	}
	
	
	public void _privacyClicked () {
		i.setClass(getApplicationContext(), SignupActivity.class);
		startActivity(i);
	}
	
	
	public void _settingsClicked () {
		D.setTitle("Auto Detect");
		D.setPositiveButton("Activate", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface _dialog, int _which) {
				
			}
		});
		D.setNegativeButton("Deactivate", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface _dialog, int _which) {
				
			}
		});
		D.create().show();
	}
	
	
	public void _exitClicked () {
		
	}
	
	
	public void _hideBackButton () {
		 getSupportActionBar().setDisplayShowTitleEnabled(true); getSupportActionBar().setDisplayHomeAsUpEnabled(false);
	}
	
	
	public void _PopUpMenu (final View _view) {
		//change the activity if mainactivity is not yours
		
		PopupMenu popup = new PopupMenu(DashboardActivity.this, _view);
		Menu menu = popup.getMenu();
		//add menu or change the name
		// you will change the name in the different case or add other cases in the blocks below
		
		menu.add("Export");
		menu.add("View Record");
		menu.add("Delete List");
		popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
			@Override
			public boolean onMenuItemClick(MenuItem item){
				switch (item.getTitle().toString()){
					case "Export":
					_DialogWithEditText();
					break;
					case "View Record":
					
					i.setClass(getApplicationContext(), TotalviewActivity.class);
					startActivity(i);
					break; case "Delete List":
					maplist.clear();
					database.edit().putString("all", new Gson().toJson(maplist)).commit();
					listview1.setAdapter(new Listview1Adapter(listmap));
					((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
					break;}
				return true;
			}
		});
		popup.show();
	}
	
	
	public void _clearSP (final SharedPreferences _sharedPreference) {
		_sharedPreference.edit().clear().commit();
	}
	
	
	public void _deletes (final double _position) {
		maplist.remove((int)(_position));
		database.edit().putString("all", new Gson().toJson(maplist)).commit();
		listview1.setAdapter(new Listview1Adapter(maplist));
		((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
	}
	
	
	public void _Toast (final double _hi, final double _wt, final String _co, final String _bc, final String _msg) {
		LinearLayout v = new LinearLayout(this);
		TextView tvu = new TextView(this);
		tvu.setTextColor(Color.parseColor("#"+_bc));
		tvu.setGravity(Gravity.CENTER);
		tvu.setLayoutParams(new ViewGroup.LayoutParams((int)_wt, (int)_hi));
		android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
		gd.setColor(Color.parseColor("#"+_bc));
		android.graphics.drawable.GradientDrawable ed = new android.graphics.drawable.GradientDrawable();
		ed.setColor(Color.parseColor("#"+_co));
		ed.setCornerRadius(4);
		gd.setCornerRadius(6);
		v.setBackground(gd);
		tvu.setBackground(ed);
		v.setPadding(2,2,2,2);
		tvu.setText(_msg);
		v.addView(tvu);
		Toast t = Toast.makeText(getApplicationContext(),"",Toast.LENGTH_LONG);
		t.setView(v);
		t.show();
	}
	
	
	public void _DialogWithEditText () {
		maplist = new Gson().fromJson(database.getString("all", ""), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
		///Step 1 create a custom view name is custom_dialog
		///Step 2 add LinearLayout name it linear1bg, add your EditText name it edittext1, and ImageView name it img1
		///Step 3 name your linear1 layout linear1bg
		///Step copy below ASD blocks into a moreblock
		///****IMPORTANT STEP**** Don't forget to change CustomDialogActivity.this to your activity name.
		AlertDialog.Builder alert = new AlertDialog.Builder(DashboardActivity.this);
		
		View dialog1 = getLayoutInflater().inflate(R.layout.excel,null);
		
		alert.setView(dialog1);
		
		final AlertDialog dialog = alert.create ();
		///you can change false to true this depends on if you allow the user to click the back button or outside the dialog to dismiss
		
		
		dialog.setCanceledOnTouchOutside(true);
		dialog.setCancelable(true);
		dialog.getWindow().setBackgroundDrawableResource(android.R.color.white);
		
		dialog.show();
		
		final EditText entertext = (EditText) dialog1.findViewById(R.id.edittext1);
		
		final Button save = dialog1.findViewById(R.id.button1);
		
		final LinearLayout linear = (LinearLayout) dialog1.findViewById (R.id.linear1);
		entertext.setFocusableInTouchMode(true);
		entertext.addTextChangedListener(new TextWatcher() {
						@Override
						public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
								final String _charSeq = _param1.toString();
								///code
				
						}
						
						@Override
						public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
								
						}
						
						@Override
						public void afterTextChanged(Editable _param1) {
								
						}
				});
		save.setOnClickListener(new View.OnClickListener(){
			
			@Override
			public void onClick( View dialogmain){
				dialog.dismiss();
				SavedData = entertext.getText().toString();
				fileName = SavedData.concat(".xls");
				FileUtil.makeDir(FileUtil.getExternalStorageDir().concat("/SBCQR"));
				//Save to File Path
				File sdCard = Environment.getExternalStorageDirectory();
				File directory = new File(sdCard.getAbsolutePath() + "/SBCQR");
				
				//file path
				    File file = new File(directory, fileName);
				
				
				WorkbookSettings wbSettings = new WorkbookSettings();
				wbSettings.setLocale(new Locale("en", "EN"));
				WritableWorkbook workbook;
				//Tidak Boleh Mengubah Block Try Catch nya...
				
				try{
					   //Buat Nama Sheet
					
					workbook = Workbook.createWorkbook(file, wbSettings);
					        //Excel sheet name. 0 represents first sheet
					        WritableSheet sheet = workbook.createSheet("SBC QR ATTENDANCE SHEET", 0);
					try{
						  
						//Buat Label di sini dengan row 0
						
						sheet.addCell(new Label(0, 0, "Name"));
						sheet.addCell(new Label(1, 0, "Date"));
						sheet.addCell(new Label(2, 0, "Arrival"));
						//Repeat Data
						for(int i= 0; i < (int)(maplist.size()); i++) {
							
							//Masukan Value
							
							sheet.addCell(new Label(0, i + 1, maplist.get((int)i).get("id").toString()));
							sheet.addCell(new Label(1, i + 1, maplist.get((int)i).get("date").toString()));
							sheet.addCell(new Label(2, i + 1, maplist.get((int)i).get("arrival").toString()));
						}
						
						//Total di baris terakhir
						
					} catch (RowsExceededException e){
						  SketchwareUtil.showMessage(getApplicationContext(), "Error1");
					} catch (WriteException e){
						  SketchwareUtil.showMessage(getApplicationContext(), "Error1");
					}
					try{
						   workbook.write();
						workbook.close();
					} catch (WriteException e){
						   SketchwareUtil.showMessage(getApplicationContext(), "Error2");
					}
				} catch (Exception e){
					   SketchwareUtil.showMessage(getApplicationContext(), "Error3");
				}
				SketchwareUtil.showMessage(getApplicationContext(), "Save successfully!");
				
			}
			 
		});
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
				_view = _inflater.inflate(R.layout.listview, null);
			}
			
			final LinearLayout linear3 = (LinearLayout) _view.findViewById(R.id.linear3);
			final LinearLayout linear4 = (LinearLayout) _view.findViewById(R.id.linear4);
			final LinearLayout linear5 = (LinearLayout) _view.findViewById(R.id.linear5);
			final LinearLayout linear6 = (LinearLayout) _view.findViewById(R.id.linear6);
			final TextView id = (TextView) _view.findViewById(R.id.id);
			final TextView date = (TextView) _view.findViewById(R.id.date);
			final TextView arrival = (TextView) _view.findViewById(R.id.arrival);
			
			id.setText(maplist.get((int)_position).get("id").toString());
			date.setText(maplist.get((int)_position).get("date").toString());
			arrival.setText(maplist.get((int)_position).get("arrival").toString());
			
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