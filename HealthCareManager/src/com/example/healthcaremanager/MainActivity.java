package com.example.healthcaremanager;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	private EditText edtAge, edtHeight, edtWeight;
	private TextView tvDisplayCalculateBMR, tvTotalFood, tvFoodCal, tvVegatableCal, tvDrinkCal;
	private Spinner spinnerGender, spinnerFood, spinnerVegatable, spinnerDrink;
	private Button btnBMRCalculator, btnAddToMenu, btnTotalFoodCal;
	private TimePicker timePicker1;
	private int hour;
	private int minute;
	private ListView listItem;
	private ArrayList<String> itemArray;
	private ArrayAdapter<String> adapter;
	double MBRValue;
	//private CustomAdapterList adapter;
	@Override
	public void onResume() {
	    super.onResume();
	    adapter.notifyDataSetChanged();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnBMRCalculator = (Button) findViewById(R.id.btnCalculateBMR);
		spinnerGender = (Spinner) findViewById(R.id.spinnerGender);
		spinnerFood = (Spinner) findViewById(R.id.spinnerFood);
		spinnerVegatable = (Spinner) findViewById(R.id.spinnerVegatable);
		spinnerDrink = (Spinner) findViewById(R.id.spinnerDrink);
		edtAge = (EditText) findViewById(R.id.edtAge);
		edtWeight = (EditText) findViewById(R.id.edtWeight);
		edtHeight = (EditText) findViewById(R.id.edtHeight);
		tvDisplayCalculateBMR = (TextView) findViewById(R.id.tvDisplayCalculateBMR);
		tvTotalFood = (TextView)findViewById(R.id.tvTotalFoodCal);
		tvFoodCal = (TextView)findViewById(R.id.tvFoodCal);
		tvVegatableCal = (TextView)findViewById(R.id.tvVegatableCal);
		tvDrinkCal = (TextView)findViewById(R.id.tvDrinkCal);
		timePicker1 = (TimePicker) findViewById(R.id.timePicker1);
		btnAddToMenu = (Button)findViewById(R.id.btnAddToMenu);
		btnTotalFoodCal = (Button)findViewById(R.id.btnTotalFoodCal);
		listItem = (ListView)findViewById(R.id.listItem);
		listItem.setBackgroundColor(Color.parseColor("#000000"));
		
		btnTotalFoodCal.setOnClickListener(this);
		btnAddToMenu.setOnClickListener(this);
		btnBMRCalculator.setOnClickListener(this);
		setCurrentTimeOnView();
		
		spinnerFood.setOnItemSelectedListener(new CustomFoodListener() );
		spinnerVegatable.setOnItemSelectedListener(new CustomVegatableListener());
		spinnerDrink.setOnItemSelectedListener(new CustomDrinkListener());
		
		itemArray = new ArrayList<String>();
		//adapter = new CustomAdapterList((getActivity(), R.layout.row, itemArray);
		adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, itemArray);
		listItem.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnCalculateBMR:
			try{
				MBRValue = calculatorBMR(
						String.valueOf(spinnerGender.getSelectedItem()),
						Integer.parseInt(edtAge.getText().toString()),
						Integer.parseInt(edtWeight.getText().toString()),
						Integer.parseInt(edtHeight.getText().toString()));
	
				tvDisplayCalculateBMR.setText(Double.toString(MBRValue) + " cal");
			}catch(Exception e){
				Toast.makeText(this, "Please enter the values!", Toast.LENGTH_SHORT).show();
			}
				
			break;
		case R.id.btnAddToMenu:
				itemArray.add("Date: " + Calendar.DATE + "/" + timePicker1.getCurrentHour() + ":" + timePicker1.getCurrentMinute() + 
						"  *Food: " + String.valueOf(spinnerFood.getSelectedItem()) + "  *Vegatable: " + String.valueOf(spinnerVegatable.getSelectedItem()) 
						+ "  *Drink: " + String.valueOf(spinnerDrink.getSelectedItem()));
				adapter.notifyDataSetChanged();
				//listItem.setAdapter(adapter);
	            // next thing you have to do is check if your adapter has changed 
	            
			break;
		case R.id.btnTotalFoodCal:
			int total = Integer.parseInt(tvFoodCal.getText().toString()) + 
					Integer.parseInt(tvVegatableCal.getText().toString()) + Integer.parseInt(tvDrinkCal.getText().toString());
			try{
				if(total > MBRValue){
					tvTotalFood.setText(total + "  Not good, easy to get fat");
				}else {
					tvTotalFood.setText(total + "  Good");
				}
			}catch(Exception e) {
				
			}
			break;
		default:
			break;
		}
	}

	/*
	 * Function to calculator BMR
	 */
	public double calculatorBMR(String spinnerGender, int age, float weight,
			float height) {
		if (spinnerGender.equalsIgnoreCase("Male")) {
			return 66.5 + (13.75 * weight) + (5.003 * height) - (6.755 * age);
		} else {
			return 55.1 + (9.563 * weight) + (1.850 * height) - (4.676 * age);
		}
	}

	// display current time
	public void setCurrentTimeOnView() {

		//tvDisplayTime = (TextView) findViewById(R.id.tvTime);
		timePicker1 = (TimePicker) findViewById(R.id.timePicker1);

		final Calendar c = Calendar.getInstance();
		hour = c.get(Calendar.HOUR_OF_DAY);
		minute = c.get(Calendar.MINUTE);

		// set current time into textview
		//tvDisplayTime.setText(new StringBuilder().append(pad(hour)).append(":")
		//		.append(pad(minute)));

		// set current time into timepicker
		timePicker1.setCurrentHour(hour);
		timePicker1.setCurrentMinute(minute);

	}
	
	public class CustomFoodListener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int pos,
				long id) {
			switch (pos) {
			case 0:
				tvFoodCal.setText("");
				tvFoodCal.append("195");
				break;
			case 1:
				tvFoodCal.setText("");
				tvFoodCal.append("67");
				break;
			case 2:
				tvFoodCal.setText("");
				tvFoodCal.append("190");
				break;
			case 3:
				tvFoodCal.setText("");
				tvFoodCal.append("102");
				break;

			default:
				tvFoodCal.setText("");
				tvFoodCal.append("0");
				break;
			}

		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {

		}

	}
	public class CustomDrinkListener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int pos,
				long id) {
			switch (pos) {
			case 0:
				tvDrinkCal.setText("");
				tvDrinkCal.append("130");
				break;
			case 1:
				tvDrinkCal.setText("");
				tvDrinkCal.append("100");
				break;
			case 2:
				tvDrinkCal.setText("");
				tvDrinkCal.append("260");
				break;
			case 3:
				tvDrinkCal.setText("");
				tvDrinkCal.append("210");
				break;

			default:
				tvDrinkCal.setText("");
				tvDrinkCal.append("0");
				break;
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {

		}

	}
	public class CustomVegatableListener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int pos,
				long id) {
			switch (pos) {
			case 0:
				tvVegatableCal.setText("");
				tvVegatableCal.append("195");
				break;
			case 1:
				tvVegatableCal.setText("");
				tvVegatableCal.append("67");
				break;
			case 2:
				tvVegatableCal.setText("");
				tvVegatableCal.append("190");
				break;
			case 3:
				tvVegatableCal.setText("");
				tvVegatableCal.append("102");
				break;

			default:
				tvVegatableCal.setText("");
				tvVegatableCal.append("0");
				break;
			}

		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub

		}

	}

}
