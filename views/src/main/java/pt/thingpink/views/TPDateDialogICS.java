package pt.thingpink.views;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

import java.util.Calendar;

public class TPDateDialogICS extends DialogFragment {
	private static Context context;
	private static String title;
	private static Calendar calendar;
	private static OnDateSetListener dateSetListener;

	public static TPDateDialogICS newInstance(Context context, String title, Calendar date, OnDateSetListener DateSetListener) {
		TPDateDialogICS dialog = new TPDateDialogICS();
		TPDateDialogICS.context = context;
		TPDateDialogICS.title = title;
		TPDateDialogICS.calendar = Calendar.getInstance();
		TPDateDialogICS.calendar.setTimeInMillis(date.getTimeInMillis());
		TPDateDialogICS.dateSetListener = DateSetListener;
		return dialog;
	}

	public Dialog onCreateDialog(Bundle savedInstanceState) {

		DatePickerDialog datePickerDialog = null;

		datePickerDialog = new DatePickerDialog(TPDateDialogICS.context, TPDateDialogICS.dateSetListener, TPDateDialogICS.calendar.get(Calendar.YEAR), TPDateDialogICS.calendar.get(Calendar.MONTH), TPDateDialogICS.calendar.get(Calendar.DAY_OF_MONTH));

		datePickerDialog.setTitle(TPDateDialogICS.title);

		return datePickerDialog;

	}
}