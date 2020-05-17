package eu.cristianl.ross.android.activities;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import eu.cristianl.ross.R;
import eu.cristianl.ross.android.RossApplication;
import eu.cristianl.ross.android.listeners.TextChangeListener;
import eu.cristianl.ross.android.services.dataDownload.MessageSeed;
import eu.cristianl.ross.android.services.dataDownload.TableDataDownloadService;
import eu.cristianl.ross.android.utils.Resources;
import eu.cristianl.ross.android.widgets.Console;
import eu.cristianl.ross.utils.ConnectionUtils;
import eu.cristianl.ross.utils.Utils;
import eu.cristianl.ross.webclients.LoginClient;

public class LoginActivity extends GenericActivity {

	private DownloadServiceReceiver mDownloadServiceRec;

	private String mEmployeeName;
	private String mEmployeePass;

	@Override
	protected void initializeData(Bundle savedInstance) {
		// If previous logged, skip to main activity
		if (RossApplication.getInstance().isUserLogged()) {
			goToMainActivity();
			return;
		}

		mDownloadServiceRec = new DownloadServiceReceiver();
	}

	@Override
	protected void initializeViews() {
		getActionBar().hide();
		setContentView(R.layout.activity_login);
		setupRestaurantName();
		setupEmployeeName();
		setupEmployeePass();
		setupLoginButton();
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (mDownloadServiceRec != null && mDownloadServiceRec.isRegistered) {
			super.unregisterReceiver(mDownloadServiceRec);
			mDownloadServiceRec.isRegistered = false;
		}
	}

	private void setupRestaurantName() {
		TextView textView = (TextView) findViewById(R.id.activity_login_restaurant_name);
		textView.setText(Resources.getString(R.string.restaurant_name));
	}

	private void setupEmployeeName() {
		EditText editText = (EditText) findViewById(R.id.activity_login_employee_name);
		mEmployeeName = RossApplication.getInstance().getUserName();
		if (!Utils.isEmptyOrBlank(mEmployeeName)) {
			editText.setText(mEmployeeName);
		}
		editText.addTextChangedListener(new EditTextChangeListener(editText.getId()));
	}

	private void setupEmployeePass() {
		EditText editText = (EditText) findViewById(R.id.activity_login_employee_pass);
		editText.addTextChangedListener(new EditTextChangeListener(editText.getId()));
	}

	private void setupLoginButton() {
		Button button = (Button) findViewById(R.id.activity_login_employee_confirm);
		button.setOnClickListener(new OnButtonClickListener());
	}

	private void performLogin() {
		if (Utils.isEmptyOrBlank(mEmployeeName) || Utils.isEmptyOrBlank(mEmployeePass)) {
			Console.get(Resources.getString(R.string.activity_login_invalid_fields)).error();
			return;
		}

		if (ConnectionUtils.getNetworkConnection() == ConnectionUtils.NO_CONNECTION) {
			Console.get(Resources.getString(R.string.dictionary_no_internet_connection)).error();
			return;
		}

		LoginClient client = new LoginClient();
		client.login(mEmployeeName, mEmployeePass);
		LoginClient.Response response = client.getResponse();

		if (response != null && response.isSuccess()) {
			RossApplication.getInstance().setUser(response.getEmployeeId(), response.getEmployeeUsername(),
					response.getEmployeeName());
			RossApplication.getInstance().setUserSession(response.getSessionId(), response.getSessionTime());

			startDownloadData();
		} else {
			Console.get(Resources.getString(R.string.activity_login_authentification_failed)).error();
		}
	}

	private void goToMainActivity() {
		mNavigator.startActivity(MainActivity.class);
	}

	private void startDownloadData() {
		super.registerReceiver(mDownloadServiceRec, new IntentFilter(TableDataDownloadService.ACTION));
		mDownloadServiceRec.isRegistered = true;

		super.startService(new Intent(this, TableDataDownloadService.class));
	}

	private void downloadFinished() {
		super.unregisterReceiver(mDownloadServiceRec);
		mDownloadServiceRec.isRegistered = false;

		goToMainActivity();
	}

	public class DownloadServiceReceiver extends BroadcastReceiver {
		private boolean isRegistered = false;

		private ProgressDialog mProgressDialog = buildProgressDialog();

		@Override
		public void onReceive(Context context, Intent intent) {
			MessageSeed message = intent.getParcelableExtra(TableDataDownloadService.MESSAGE_KEY);

			mProgressDialog.setMessage(message.getText());
			mProgressDialog.setProgress(message.getProgress());
			if (!mProgressDialog.isShowing()) {
				mProgressDialog.show();
			}

			if (message.getType() == MessageSeed.TYPE_END) {
				mProgressDialog.dismiss();
				downloadFinished();
			}
		}

		private ProgressDialog buildProgressDialog() {
			ProgressDialog retValue = new ProgressDialog(LoginActivity.this);
			retValue.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			retValue.setProgress(0);
			retValue.setMax(100);

			return retValue;
		}
	}

	private class EditTextChangeListener extends TextChangeListener {
		private int mCallerId;

		private EditTextChangeListener(int callerId) {
			mCallerId = callerId;
		}

		@Override
		public void afterTextChanged(Editable s) {
			switch (mCallerId) {
			case R.id.activity_login_employee_name:
				mEmployeeName = s.toString();
				break;
			case R.id.activity_login_employee_pass:
				mEmployeePass = s.toString();
				break;
			}
		}
	}

	private class OnButtonClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.activity_login_employee_confirm:
				performLogin();
				break;
			}
		}
	}
}
