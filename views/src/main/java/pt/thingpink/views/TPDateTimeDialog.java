package pt.thingpink.views;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by joseaguiar on 20/04/15.
 */
public class TPDateTimeDialog extends DialogFragment {

    public interface OnDateTimeSetListener {

        void onDateSet(int year, int monthOfYear, int dayOfMonth, int hour, int minutes);
    }

    private static Context context;
    private static String title;
    private static Calendar calendar;
    private static OnDateTimeSetListener dateTimeSetListener;

    public static TPDateTimeDialog newInstance(Context context, String title, Calendar date, OnDateTimeSetListener dateTimeSetListener) {
        TPDateTimeDialog dialog = new TPDateTimeDialog();
        TPDateTimeDialog.context = context;
        TPDateTimeDialog.title = title;
        TPDateTimeDialog.calendar = Calendar.getInstance();
        TPDateTimeDialog.calendar.setTimeInMillis(date.getTimeInMillis());
        TPDateTimeDialog.dateTimeSetListener = dateTimeSetListener;
        return dialog;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Dialog dialog = new Dialog(TPDateTimeDialog.context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.date_time_dialog);
        dialog.setCanceledOnTouchOutside(true);

        final DatePicker datePicker = (DatePicker) dialog.findViewById(R.id.event_dialog_date_picker);
        datePicker.updateDate(TPDateTimeDialog.calendar.get(Calendar.YEAR), TPDateTimeDialog.calendar.get(Calendar.MONTH), TPDateTimeDialog.calendar.get(Calendar.DAY_OF_MONTH));

        final TimePicker timePicker = (TimePicker) dialog.findViewById(R.id.event_dialog_time_picker);
        timePicker.setCurrentHour(TPDateTimeDialog.calendar.get(Calendar.HOUR_OF_DAY));
        timePicker.setCurrentMinute(TPDateTimeDialog.calendar.get(Calendar.MINUTE));
        timePicker.setIs24HourView(true);

        TPFontableTextView confirmTaskUpdate = (TPFontableTextView) dialog.findViewById(R.id.event_dialog_confirm);

        confirmTaskUpdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TPDateTimeDialog.dateTimeSetListener.onDateSet(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute());
                dialog.dismiss();
            }
        });

        dialog.setTitle(TPDateTimeDialog.title);

        return dialog;
    }
}
