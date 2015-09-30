package pt.thingpink.utils;

public interface PromptDialogCallback {

	public void positiveButtonClick(String inputText);

	public void negativeButtonClick();

	public void dismiss();
}
