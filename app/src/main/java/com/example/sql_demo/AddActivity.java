package com.example.sql_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.sql_demo.dal.SQLiteHelper;
import com.example.sql_demo.model.Item;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity implements View.OnClickListener{
    public Spinner sp;
    private EditText txtTitle,txtPrice, txtDate;
    private Button btUpdate, btCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();
        btUpdate.setOnClickListener(this);
        btCancel.setOnClickListener(this);
        txtDate.setOnClickListener(this);
    }

    private void initView() {
        sp = findViewById(R.id.spCategory);
        txtTitle = findViewById(R.id.txtTitle);
        txtPrice = findViewById(R.id.txtPrice);
        txtDate = findViewById(R.id.txtDate);
        btUpdate = findViewById(R.id.btUpdate);
        btCancel = findViewById(R.id.btCancel);
        sp.setAdapter(new ArrayAdapter<String>(this,R.layout.item_spinner,
                getResources().getStringArray(R.array.category)));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txtDate:
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                        String date ="";
                        if(m>8){
                            date = d+"/" +(m+1)+"/"+y;
                        }else{
                            date = d+"/0" +(m+1)+"/"+y;
                        }
                        txtDate.setText(date);
                    }
                },year,month,day);
                dialog.show();
                break;
            case R.id.btCancel:
                finish();
                break;
            case R.id.btUpdate:
                String t = txtTitle.getText()+"";
                String p = txtPrice.getText()+"";
                String category = sp.getSelectedItem().toString();
                String d = txtDate.getText()+"";
                if(!t.isEmpty() && p.matches("\\d+")){
                    Item i =new Item(t,category,p,d);
                    SQLiteHelper db = new SQLiteHelper(this);
                    db.addItem(i);
                    finish();
                }
                break;
        }
    }
}