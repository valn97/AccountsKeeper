package cn.ppqing.accountskeeper;
import  java.util.*;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import cn.ppqing.accountskeeper.db.DataOperator;

public class IncomeFragment extends Fragment {

    private IncomeViewModel mViewModel;
    private int currency=0;
    private CheckBox HKD;
    private CheckBox CNY;
    private ImageView cur;
    private Button Back;
    private Button Submit;
    private String [] types_outcome;
    private String [] methods;
    private String [] types_income;
    private TextView date;
    private String get_type;
    private String get_method;
    private int get_costs;
    private EditText cos_num;
    private EditText remarks_Text;
    private  String re;

    public static IncomeFragment newInstance() {
        return new IncomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_income, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(IncomeViewModel.class);
        HKD = (CheckBox)getActivity().findViewById(R.id.check_hkd2);
        CNY = (CheckBox)getActivity().findViewById(R.id.check_cny2);
        cur = (ImageView)getActivity().findViewById(R.id.cur2);
        Back = (Button)getActivity().findViewById(R.id.Cancel2);
        Submit = (Button)getActivity().findViewById(R.id.submit2);
        date = (TextView) getActivity().findViewById(R.id.Date2);
        cos_num=(EditText)getActivity().findViewById(R.id.cost_num2);
        remarks_Text=(EditText)getActivity().findViewById(R.id.Remarks_text2);
        date.setText(Data.getDate());
        Spinner spinner_type_income = (Spinner)getActivity().findViewById(R.id.type2);
        Spinner spinner_method_income = (Spinner)getActivity().findViewById(R.id.method2);
        types_income = getResources().getStringArray(R.array.Bill_type_income);
        methods = getResources().getStringArray(R.array.method_type);
        ArrayAdapter<String> adapter1= new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item , types_income);
        ArrayAdapter<String> adapter3= new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item , methods);
        spinner_type_income.setAdapter(adapter1);
        spinner_method_income.setAdapter(adapter3);
        // TODO: Use the ViewModel
        HKD.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(HKD.isChecked()){
                    currency=2;
                    cur.setImageDrawable(getResources().getDrawable((R.drawable.hk)));
                    CNY.setChecked(false);
                }
            }
        });
        CNY.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(CNY.isChecked()){
                    currency=1;
                    cur.setImageDrawable(getResources().getDrawable((R.drawable.rmb)));
                    HKD.setChecked(false);
                }
            }
        });
        Submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (!HKD.isChecked()&&!CNY.isChecked()){
                    Log.e("ckecked","nononono");
                    final AlertDialog.Builder alterDiaglog = new AlertDialog.Builder(getActivity());
                    //alterDiaglog.setIcon(R.drawable.icon);图标
                    //alterDiaglog.setTitle(R.string.u);//文字
                    alterDiaglog.setMessage(R.string.unchecked);//提示消息

                    //积极的选择
                    alterDiaglog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alterDiaglog.show();


                }
                else{

                    int cos = Integer.parseInt(cos_num.getText().toString());
                    re= remarks_Text.getText().toString();
                    String d = Data.getDate();

                    Data input = new Data(cos,get_type,get_method,d,re);
                    Log.e("checked",input.date);
                    DataOperator.addToDB(getContext(),input);
                    Toast.makeText(getContext(), "added", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }
            }
        });
        Back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.e("OK","finish");
                getActivity().finish();}

        });
        spinner_type_income.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0,
                                               View arg1, int arg2, long arg3) {
                        int index = arg0.getSelectedItemPosition();
                        get_type = types_income[index];
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });
        spinner_method_income.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0,
                                               View arg1, int arg2, long arg3) {
                        int index = arg0.getSelectedItemPosition();
                        get_method = methods[index];
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });

        // TODO: Use the ViewModel
    }

}
