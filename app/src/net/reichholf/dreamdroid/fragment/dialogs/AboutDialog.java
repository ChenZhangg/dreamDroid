/* © 2010 Stephan Reichholf <stephan at reichholf dot net>
 * 
 * Licensed under the Create-Commons Attribution-Noncommercial-Share Alike 3.0 Unported
 * http://creativecommons.org/licenses/by-nc-sa/3.0/
 */

package net.reichholf.dreamdroid.fragment.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.util.Linkify;
import android.widget.TextView;

import net.reichholf.dreamdroid.BuildConfig;
import net.reichholf.dreamdroid.DreamDroid;
import net.reichholf.dreamdroid.R;
import net.reichholf.dreamdroid.activities.abs.BaseActivity;
import net.reichholf.dreamdroid.activities.abs.MultiPaneHandler;
import net.reichholf.dreamdroid.helpers.ExtendedHashMap;
import net.reichholf.dreamdroid.helpers.Statics;

/**
 * @author sre
 */
public class AboutDialog extends ActionDialog {
	public static AboutDialog newInstance() {
		return new AboutDialog();
	}


	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		String text = String.format("%s\n\n%s\n\n%s", DreamDroid.VERSION_STRING, getString(R.string.license_gplv3),
				getString(R.string.source_code_link));

		AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		builder.setTitle(R.string.about)
				.setMessage(text)
				.setCancelable(true)
				.setPositiveButton(R.string.privacy, (dialog, which) -> finishDialog(Statics.ACTION_SHOW_PRIVACY_STATEMENT, null));
		if (BuildConfig.FLAVOR.equals("google")) {
			builder.setNeutralButton(R.string.donate, (dialog, which) -> {
				ExtendedHashMap skus = ((BaseActivity) getActivity()).getIabItems();
				DonationDialog d = DonationDialog.newInstance(skus);
				((MultiPaneHandler) getActivity()).showDialogFragment(d, "donate_dialog");
			});
		}
		AlertDialog dialog = builder.create();
		dialog.setOnShowListener(dialog1 -> {
			TextView message = getDialog().findViewById(android.R.id.message);
			Linkify.addLinks(message, Linkify.EMAIL_ADDRESSES | Linkify.WEB_URLS);
		});
		return dialog;
	}
}
