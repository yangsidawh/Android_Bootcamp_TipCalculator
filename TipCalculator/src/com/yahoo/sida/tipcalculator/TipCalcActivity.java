package com.yahoo.sida.tipcalculator;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TipCalcActivity extends Activity {
	private double totalAmount;
	private int percentage;
	private int numOfPeople;
	private double tips;
	private	double tipsPerPerson;
	
	private EditText etTotal;
	private EditText etPercent;
	private EditText etNumOfPpl;
	private TextView tvTipsTotal;
	private TextView tvTipsPerPerson;
	private Button bt10;
	private Button bt15;
	private Button bt20;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_calc);      
        
        //set different views
        etTotal = (EditText) findViewById(R.id.etTotal);
        etPercent = (EditText) findViewById(R.id.etCustomPerc);
        etNumOfPpl= (EditText) findViewById(R.id.etNumPpl);
        tvTipsTotal = (TextView) findViewById(R.id.tvTipsTotal);
        tvTipsPerPerson = (TextView) findViewById(R.id.tvTipsPerPp);
        
        
        bt10 = (Button) findViewById(R.id.btTip10);
        bt15 = (Button) findViewById(R.id.btTip15);
        bt20 = (Button) findViewById(R.id.btTip20);
        
        //set value of each button
        bt10.setTag(10);
        bt15.setTag(15);
        bt20.setTag(20);
        
        //Initialize default value
        totalAmount = 0;
        numOfPeople = 1;
        readSettings();

        //add text change listener
        etTotal.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            	if (!s.toString().isEmpty()) {
	            	totalAmount = Double.parseDouble(s.toString());
	            	calculate();
            	}
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });
        
        etPercent.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            	if (!s.toString().isEmpty()) {
            		percentage = Integer.parseInt(s.toString());
            		calculate();
            	}
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });
        
        etPercent.setOnFocusChangeListener(new OnFocusChangeListener() {          
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus && etPercent.getText().toString().isEmpty()) {
                	etPercent.setText(String.valueOf(percentage));
                }
            }
        });
        
        etNumOfPpl.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            	if (!s.toString().isEmpty()) {
            		numOfPeople = Integer.parseInt(s.toString());
            		calculate();
            	}
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });
        
        etNumOfPpl.setOnFocusChangeListener(new OnFocusChangeListener() {          
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus && etNumOfPpl.getText().toString().isEmpty()) {
                	etNumOfPpl.setText(String.valueOf(numOfPeople));
                }
            }
        });
        
        
    }
    
//    private void setParam() {
//    	 //TODO error handling for numbers
//        totalAmount = Double.parseDouble(etTotal.getText().toString());
//        percentage = Integer.parseInt(etPercent.getText().toString());
//        numOfPeople = Integer.parseInt(etNumOfPpl.getText().toString());        
//    }
    
    public void onBtnClick(View v) {
    	percentage = (Integer) v.getTag();
    	etPercent.setText(String.valueOf(percentage));
    }
    
    public void calculate() {
    	try {
    		tips = totalAmount * percentage / 100;
    		tipsPerPerson = tips / numOfPeople;    		
    		DecimalFormat df = new DecimalFormat("#.##");
    		
    		tvTipsTotal.setText('$' +  df.format(tips));
    		tvTipsPerPerson.setText('$' + df.format(tipsPerPerson));
    		
    		saveSettings();
    	} catch (Exception e) {
    		
    		e.printStackTrace();
    	}
    }
    
    private void readSettings() {
    	File fileDir = getFilesDir();
    	File setting = new File(fileDir, "setting.cfg");
    	try {
    		percentage = Integer.parseInt(FileUtils.readFileToString(setting));
    		etPercent.setText(String.valueOf(percentage));
    	} catch (IOException e) {
    		percentage = 15;
    		e.printStackTrace();
    	}
    }
    
    private void saveSettings() {
    	File fileDir = getFilesDir();
    	File setting = new File(fileDir, "setting.cfg");
    	try {
    		FileUtils.writeStringToFile(setting, String.valueOf(percentage), false);
    	} catch (IOException e) {
    		e.printStackTrace();
    	} 
    }
    
}
