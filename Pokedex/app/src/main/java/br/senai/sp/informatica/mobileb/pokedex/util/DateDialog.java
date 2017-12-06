package br.senai.sp.informatica.mobileb.pokedex.util;

import android.app.DatePickerDialog;
import android.app.Dialog;

import android.os.Bundle;

import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.EditText;
import java.text.DateFormat;
import java.util.Calendar;

/**
 * Created by WEB on 24/11/2017.
 */

public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    private Calendar calender;
    private int editTextResource;
    private static DateFormat fmt = DateFormat.getDateInstance(DateFormat.LONG);


    public static DateDialog makeDialog(Calendar calendar, int editTextResoruce){
        DateDialog dialog = new DateDialog();
        dialog.calender = calendar;
        dialog.editTextResource = editTextResoruce ;
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        if (calender == null){
            long cal =  savedInstanceState.getLong("cal");
            editTextResource = savedInstanceState.getInt("edit");
            calender = Calendar.getInstance();
            calender.setTimeInMillis(cal);


        }
        int dia = calender.get(Calendar.DAY_OF_MONTH);
        int mes = calender.get(Calendar.MONTH);
        int ano = calender.get(Calendar.YEAR);

        DatePickerDialog dialog= new DatePickerDialog(getActivity(), this, ano, mes, dia);
        return dialog;
    }

    @Override
    public void onDateSet(DatePicker view, int ano, int mes, int dia) {

        calender.set(ano,mes,dia);
        EditText editText = (EditText)getActivity().findViewById(editTextResource);
        editText.setText(fmt.format(calender.getTime()));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("edit", editTextResource);
        outState.putLong("cal", calender.getTimeInMillis());
        super.onSaveInstanceState(outState);
    }
}
