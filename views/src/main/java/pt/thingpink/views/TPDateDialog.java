package pt.thingpink.views;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

public class TPDateDialog extends DialogFragment {
	private static Context context;
	private static String title;
	private static Calendar calendar;
	private static OnDateSetListener dateSetListener;

	public static TPDateDialog newInstance(Context context, String title, Calendar date, DatePickerDialog.OnDateSetListener DateSetListener) {
		TPDateDialog dialog = new TPDateDialog();
		TPDateDialog.context = context;
		TPDateDialog.title = title;
		TPDateDialog.calendar = Calendar.getInstance();
		TPDateDialog.calendar.setTimeInMillis(date.getTimeInMillis());
		TPDateDialog.dateSetListener = DateSetListener;
		return dialog;
	}

	public Dialog onCreateDialog(Bundle savedInstanceState) {

		DatePickerDialog datePickerDialog = null;

		datePickerDialog = new DatePickerDialog(TPDateDialog.context, TPDateDialog.dateSetListener, TPDateDialog.calendar.get(Calendar.YEAR), TPDateDialog.calendar.get(Calendar.MONTH), TPDateDialog.calendar.get(Calendar.DAY_OF_MONTH));

		datePickerDialog.setTitle(TPDateDialog.title);

		return datePickerDialog;

	}
}