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
    private EditText editText;
    private static DateFormat fmt = DateFormat.getDateInstance(DateFormat.LONG);

    public static DateDialog makeDialog(Calendar calendar, EditText editText){
        DateDialog dialog = new DateDialog();
        dialog.calender = calendar;
        dialog.editText = editText;

        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int dia = calender.get(Calendar.DAY_OF_MONTH);
        int mes = calender.get(Calendar.MONTH);
        int ano = calender.get(Calendar.YEAR);

        return new DatePickerDialog(getActivity(), this, ano, mes, dia);
    }

    @Override
    public void onDateSet(DatePicker view, int ano, int mes, int dia) {
        calender.set(ano,mes,dia);
        editText.setText(fmt.format(calender.getTime()));
    }
}
