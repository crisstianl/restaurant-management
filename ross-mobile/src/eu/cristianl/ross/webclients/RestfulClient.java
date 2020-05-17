package eu.cristianl.ross.webclients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import eu.cristianl.ross.logging.AppLogger;

public class RestfulClient implements Runnable {
	public static final Integer CONNECTION_TIMEOUT = 5000;

	private final HttpMethod mHttpMethod;
	private final String mUrl;

	private Map<String, String> mHeaders;

	private StringBuilder mUrlParams;
	private List<NameValuePair> mFormParams;
	private JSONObject mJsonParams;

	private ICallback mCallback;

	public RestfulClient(final HttpMethod method, final String url) {
		mHttpMethod = method;
		mUrl = url;
	}

	@Override
	public void run() {
		try {
			HttpUriRequest request = null;
			switch (mHttpMethod) {
			case GET:
				request = GET();
				break;
			case POST:
				request = POST();
				break;
			case PUT:
				request = PUT();
				break;
			case DELETE:
				request = DELETE();
				break;
			default:
				throw new IllegalArgumentException();
			}
			AppLogger.info(request.getMethod() + " " + request.getURI());

			final HttpParams params = new BasicHttpParams();
			params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECTION_TIMEOUT);
			params.setParameter(CoreConnectionPNames.SO_TIMEOUT, CONNECTION_TIMEOUT);

			final HttpClient client = new DefaultHttpClient(params);
			processResponse(client.execute(request));
		} catch (Exception e) {
			AppLogger.error(e, e.getMessage());
		}
	}

	public void addHeader(final String name, final String value) {
		if (mHeaders == null) {
			mHeaders = new LinkedHashMap<String, String>();
		}
		mHeaders.put(name, value);
	}

	public void addUrlParam(final String name, final String value) throws Exception {
		if (mUrlParams == null) {
			mUrlParams = new StringBuilder('?');
		} else {
			mUrlParams.append('&');
		}

		mUrlParams.append(name).append('=').append(URLEncoder.encode(value, HTTP.UTF_8));
	}

	public void addFormParam(final String name, final String value) {
		if (mFormParams == null) {
			mFormParams = new ArrayList<NameValuePair>();
		}
		mFormParams.add(new BasicNameValuePair(name, value));
	}

	public void addJsonParam(final String name, final Object value) throws JSONException {
		if (mJsonParams == null) {
			mJsonParams = new JSONObject();
		}
		mJsonParams.put(name, value);
	}

	public final void execute(final ICallback callback) {
		mCallback = callback;
		new Thread(this).start();
	}

	private HttpUriRequest GET() {
		// add URL parameters
		final String urlParams = this.mUrlParams != null ? this.mUrlParams.toString() : "";
		final HttpGet retValue = new HttpGet(this.mUrl + urlParams);

		// add headers
		if (this.mHeaders != null) {
			for (Map.Entry<String, String> el : mHeaders.entrySet()) {
				retValue.addHeader(el.getKey(), el.getValue());
			}
		}

		return retValue;
	}

	private HttpUriRequest POST() throws IOException {
		// add URL parameters
		final String urlParams = this.mUrlParams != null ? this.mUrlParams.toString() : "";
		final HttpPost retValue = new HttpPost(this.mUrl + urlParams);

		// add headers
		if (this.mHeaders != null) {
			for (Map.Entry<String, String> el : mHeaders.entrySet()) {
				retValue.addHeader(el.getKey(), el.getValue());
			}
		}

		// add body
		if (this.mFormParams != null) {
			retValue.setEntity(new UrlEncodedFormEntity(mFormParams, HTTP.UTF_8));
		} else if (this.mJsonParams != null) {
			retValue.setEntity(new StringEntity(this.mJsonParams.toString(), HTTP.UTF_8));
		}

		return retValue;
	}

	private HttpUriRequest PUT() throws IOException {
		// add URL parameters
		final String urlParams = this.mUrlParams != null ? this.mUrlParams.toString() : "";
		final HttpPut retValue = new HttpPut(this.mUrl + urlParams);

		// add headers
		if (this.mHeaders != null) {
			for (Map.Entry<String, String> el : mHeaders.entrySet()) {
				retValue.addHeader(el.getKey(), el.getValue());
			}
		}

		// add body
		if (this.mFormParams != null) {
			retValue.setEntity(new UrlEncodedFormEntity(mFormParams, HTTP.UTF_8));
		} else if (this.mJsonParams != null) {
			retValue.setEntity(new StringEntity(this.mJsonParams.toString(), HTTP.UTF_8));
		}

		return retValue;
	}

	private HttpUriRequest DELETE() throws IOException {
		// add URL parameters
		final String urlParams = this.mUrlParams != null ? this.mUrlParams.toString() : "";
		final HttpDelete retValue = new HttpDelete(this.mUrl + urlParams);

		// add headers
		if (this.mHeaders != null) {
			for (Map.Entry<String, String> el : mHeaders.entrySet()) {
				retValue.addHeader(el.getKey(), el.getValue());
			}
		}

		return retValue;
	}

	private void processResponse(final HttpResponse response) {
		int httpCode = 0;
		String message = null;
		InputStream responseStream = null;
		BufferedReader reader = null;
		try {
			httpCode = response.getStatusLine().getStatusCode();
			if (httpCode != 200) {
				message = response.getStatusLine().getReasonPhrase();

			} else if (response.getEntity() != null) { // success
				responseStream = response.getEntity().getContent();
				reader = new BufferedReader(new InputStreamReader(responseStream, HTTP.UTF_8));
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line).append('\n');
				}
				message = sb.toString();
			}

		} catch (Exception e) {
			AppLogger.error(e, e.getMessage());
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
				if (responseStream != null) {
					responseStream.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			AppLogger.info("HTTP {} : {}", httpCode, message);
			mCallback.handleResponse(httpCode, message);
		}
	}

	public enum HttpMethod {
		GET, POST, PUT, DELETE;
	}

	public interface ICallback {
		public void handleResponse(int status, String message);
	}
}
