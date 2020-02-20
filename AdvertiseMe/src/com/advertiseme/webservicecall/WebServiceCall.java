package com.advertiseme.webservicecall;


import java.util.ArrayList;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import android.util.Log;

public class WebServiceCall {
	private final String NAMESPACE = "http://tempuri.org/";
	// private final String URL = "http://10.0.2.2:50761/Service1.asmx?WSDL";
	private final String URL = "http://192.168.69.1/AdvertiseService/AdvertiseService.asmx?WSDL";
	private final String DM_URL="http://192.168.69.1/AdvertiseMeDMService/MiningService.asmx?WSDL";
	
	public String authenticateLogin(String userName, String passWord) {
		String result = "";

		final String SOAP_ACTION = "http://tempuri.org/authenticateLogin";
		final String METHOD_NAME = "authenticateLogin";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_userName");
		propInfo1.setValue(userName);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);

		PropertyInfo propInfo2 = new PropertyInfo();
		propInfo2.setName("_passWord");
		propInfo2.setValue(passWord);
		propInfo2.setType(String.class);
		request.addProperty(propInfo2);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true; // put this only if the web service is .NET one
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			Log.i("myApp", response.toString());
			if (!response.toString().equalsIgnoreCase("200")) {
				result = response.toString();
			} else {
				result = "200";

			}
		} catch (Exception e) {
			
			result="300";
			
		}
		return result;
	}
	
	
	public String[] getUserProfileDetail(String userID) {
		String result[] = null;

		final String SOAP_ACTION = "http://tempuri.org/getUserProfileDetail";
		final String METHOD_NAME = "getUserProfileDetail";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_userid");
		propInfo1.setValue(userID);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);			
			SoapObject response = (SoapObject) envelope.getResponse();
			result = getResultSetFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	public Boolean isUsernameExist(String userName) {

		boolean result = false;

		final String SOAP_ACTION = "http://tempuri.org/isUsernameExist";
		final String METHOD_NAME = "isUsernameExist";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_userName");
		propInfo1.setValue(userName);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true; // put this only if the web service is .NET one
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			Log.i("myApp", response.toString());
			if (response.toString().equalsIgnoreCase("true")) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	public Boolean registerNewUser(ArrayList paraList) {

		boolean result = false;

		final String SOAP_ACTION = "http://tempuri.org/registerNewUser";
		final String METHOD_NAME = "registerNewUser";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		
		String[] paraNameList = {"_FullName", "_emailID", "_address", "_city","_state", "_country","_language","_mobileNo","_homeNo", "_officeNo", "_homeLink"
				,"_facebook","_google","_twitter","_skype","_username","_password"
				,"_userImage"};
		for (int count = 0; count <= paraNameList.length - 1; count++) {
			PropertyInfo propInfo1 = new PropertyInfo();
			propInfo1.setName(paraNameList[count]);
			propInfo1.setValue(paraList.get(count).toString());
			propInfo1.setType(String.class);
			request.addProperty(propInfo1);
		}

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true; // put this only if the web service is .NET one
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			Log.i("myApp", response.toString());
			if (response.toString().equalsIgnoreCase("true")) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}


	public String getAdvertisementID(String refCode) {

		String result = null;

		final String SOAP_ACTION = "http://tempuri.org/getAdvertisementID";
		final String METHOD_NAME = "getAdvertisementID";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_refCode");
		propInfo1.setValue(refCode);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true; // put this only if the web service is .NET one
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			Log.i("myApp", response.toString());
			result=response.toString();
		} catch (Exception e) {
			e.printStackTrace();
			result="error";
		}
		return result;
	}
	public ArrayList<ArrayList> getAdvertSearchResult(String qString) {
		ArrayList resultList = null;

		final String SOAP_ACTION = "http://tempuri.org/getAdvertSearchResult";
		final String METHOD_NAME = "getAdvertSearchResult";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_qString");
		propInfo1.setValue(qString);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true; // put this only if the web service is .NET one
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			SoapObject response = (SoapObject) envelope.getResponse();
			resultList = getResultSetsFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}
	public String[] getAdvertisementDetail(String advertID) {
		String result[] = null;

		final String SOAP_ACTION = "http://tempuri.org/getAdvertisementDetail";
		final String METHOD_NAME = "getAdvertisementDetail";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_advertID");
		propInfo1.setValue(advertID);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);			
			SoapObject response = (SoapObject) envelope.getResponse();
			result = getResultSetFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	public String[] getAdvertisementRating(String advertID) {
		String result[] = null;

		final String SOAP_ACTION = "http://tempuri.org/getAdvertisementRating";
		final String METHOD_NAME = "getAdvertisementRating";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_advertID");
		propInfo1.setValue(advertID);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);			
			SoapObject response = (SoapObject) envelope.getResponse();
			result = getResultSetFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	
	public String[] getSellerDetail(String sellerID) {
		String result[] = null;

		final String SOAP_ACTION = "http://tempuri.org/getSellerDetail";
		final String METHOD_NAME = "getSellerDetail";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_sellerID");
		propInfo1.setValue(sellerID);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);			
			SoapObject response = (SoapObject) envelope.getResponse();
			result = getResultSetFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	public ArrayList<ArrayList> getResourceList(String advertID) {
		ArrayList resultList = null;

		final String SOAP_ACTION = "http://tempuri.org/getResourceList";
		final String METHOD_NAME = "getResourceList";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_advertID");
		propInfo1.setValue(advertID);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true; // put this only if the web service is .NET one
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			SoapObject response = (SoapObject) envelope.getResponse();
			resultList = getResultSetsFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}
	public ArrayList<ArrayList> getUserReviewList(String advertID) {
		ArrayList resultList = null;

		final String SOAP_ACTION = "http://tempuri.org/getUserReviewList";
		final String METHOD_NAME = "getUserReviewList";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_advertID");
		propInfo1.setValue(advertID);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true; // put this only if the web service is .NET one
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			SoapObject response = (SoapObject) envelope.getResponse();
			resultList = getResultSetsFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}
	
	public String[] getSellerReview(String sellerID) {
		String result[] = null;

		final String SOAP_ACTION = "http://tempuri.org/getSellerReview";
		final String METHOD_NAME = "getSellerReview";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_sellerID");
		propInfo1.setValue(sellerID);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);			
			SoapObject response = (SoapObject) envelope.getResponse();
			result = getResultSetFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	public ArrayList<ArrayList> getSellerFeedbackList(String sellerID) {
		ArrayList resultList = null;

		final String SOAP_ACTION = "http://tempuri.org/getSellerFeedbackList";
		final String METHOD_NAME = "getSellerFeedbackList";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_sellerID");
		propInfo1.setValue(sellerID);		
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true; // put this only if the web service is .NET one
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			SoapObject response = (SoapObject) envelope.getResponse();
			resultList = getResultSetsFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}

	public ArrayList<ArrayList> getDailyDealList(String userID) {
		ArrayList resultList = null;

		final String SOAP_ACTION = "http://tempuri.org/getDailyDealList";
		final String METHOD_NAME = "getDailyDealList";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_userID");
		propInfo1.setValue(userID);		
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true; // put this only if the web service is .NET one
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			SoapObject response = (SoapObject) envelope.getResponse();
			resultList = getResultSetsFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}
	
	
	public ArrayList<ArrayList> getAdvertListBySeller(String sellerID) {
		ArrayList resultList = null;

		final String SOAP_ACTION = "http://tempuri.org/getAdvertListBySeller";
		final String METHOD_NAME = "getAdvertListBySeller";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_sellerID");
		propInfo1.setValue(sellerID);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true; // put this only if the web service is .NET one
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			SoapObject response = (SoapObject) envelope.getResponse();
			resultList = getResultSetsFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}
	
	public String[] getDailyDealItem() {
		String result[] = null;

		final String SOAP_ACTION = "http://tempuri.org/getDailyDealItem";
		final String METHOD_NAME = "getDailyDealItem";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);			
			SoapObject response = (SoapObject) envelope.getResponse();
			result = getResultSetFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	public ArrayList<ArrayList> getBestDealList(String sellerID) {
		ArrayList resultList = null;

		final String SOAP_ACTION = "http://tempuri.org/getBestDealList";
		final String METHOD_NAME = "getBestDealList";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_sellerID");
		propInfo1.setValue(sellerID);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true; // put this only if the web service is .NET one
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			SoapObject response = (SoapObject) envelope.getResponse();
			resultList = getResultSetsFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}
	public String[] getBestDealAdvertisementDetail(String advertID) {
		String result[] = null;

		final String SOAP_ACTION = "http://tempuri.org/getBestDealAdvertisementDetail";
		final String METHOD_NAME = "getBestDealAdvertisementDetail";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_advertID");
		propInfo1.setValue(advertID);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);			
			SoapObject response = (SoapObject) envelope.getResponse();
			result = getResultSetFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	public ArrayList<ArrayList> getUserRecommendationList(String _queryString) {
		ArrayList resultList = null;

		final String SOAP_ACTION = "http://tempuri.org/getUserRecommendationList";
		final String METHOD_NAME = "getUserRecommendationList";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_queryString");
		propInfo1.setValue(_queryString);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);		
				
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true; // put this only if the web service is .NET one
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			SoapObject response = (SoapObject) envelope.getResponse();
			resultList = getResultSetsFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}
	public ArrayList<ArrayList> getUserConnectionList(String userID,String searchQuery) {
		ArrayList resultList = null;

		final String SOAP_ACTION = "http://tempuri.org/getUserConnectionList";
		final String METHOD_NAME = "getUserConnectionList";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_userID");
		propInfo1.setValue(userID);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);
		
		PropertyInfo propInfo2 = new PropertyInfo();
		propInfo2.setName("_searchQuery");
		propInfo2.setValue(searchQuery);
		propInfo2.setType(String.class);
		request.addProperty(propInfo2);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true; // put this only if the web service is .NET one
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			SoapObject response = (SoapObject) envelope.getResponse();
			resultList = getResultSetsFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}
	public ArrayList<ArrayList> getNetwokConnectionList(String userID,String searchQuery) {
		ArrayList resultList = null;

		final String SOAP_ACTION = "http://tempuri.org/getNetwokConnectionList";
		final String METHOD_NAME = "getNetwokConnectionList";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_userID");
		propInfo1.setValue(userID);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);
		
		PropertyInfo propInfo2 = new PropertyInfo();
		propInfo2.setName("_searchQuery");
		propInfo2.setValue(searchQuery);
		propInfo2.setType(String.class);
		request.addProperty(propInfo2);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true; // put this only if the web service is .NET one
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			SoapObject response = (SoapObject) envelope.getResponse();
			resultList = getResultSetsFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}
	public ArrayList<ArrayList> getConnectionRequestList(String userID) {
		ArrayList resultList = null;

		final String SOAP_ACTION = "http://tempuri.org/getConnectionRequestList";
		final String METHOD_NAME = "getConnectionRequestList";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_userID");
		propInfo1.setValue(userID);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);
		
			
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true; // put this only if the web service is .NET one
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			SoapObject response = (SoapObject) envelope.getResponse();
			resultList = getResultSetsFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}
	public ArrayList<ArrayList> getUserViewsList(String userID) {
		ArrayList resultList = null;

		final String SOAP_ACTION = "http://tempuri.org/getUserViewsList";
		final String METHOD_NAME = "getUserViewsList";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_userID");
		propInfo1.setValue(userID);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);
		
			
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true; // put this only if the web service is .NET one
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			SoapObject response = (SoapObject) envelope.getResponse();
			resultList = getResultSetsFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}
	public ArrayList<ArrayList> getUserActivityList(String userID) {
		ArrayList resultList = null;

		final String SOAP_ACTION = "http://tempuri.org/getUserActivityList";
		final String METHOD_NAME = "getUserActivityList";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_userID");
		propInfo1.setValue(userID);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);
		
			
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true; // put this only if the web service is .NET one
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			SoapObject response = (SoapObject) envelope.getResponse();
			resultList = getResultSetsFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}
	public String[] getUserAcountCount(String userID) {
		String result[] = null;

		final String SOAP_ACTION = "http://tempuri.org/getUserAcountCount";
		final String METHOD_NAME = "getUserAcountCount";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_userID");
		propInfo1.setValue(userID);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);			
			SoapObject response = (SoapObject) envelope.getResponse();
			result = getResultSetFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	public ArrayList<ArrayList> getMessageInboxList(String userID) {
		ArrayList resultList = null;

		final String SOAP_ACTION = "http://tempuri.org/getMessageInboxList";
		final String METHOD_NAME = "getMessageInboxList";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_userID");
		propInfo1.setValue(userID);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);
		
			
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true; // put this only if the web service is .NET one
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			SoapObject response = (SoapObject) envelope.getResponse();
			resultList = getResultSetsFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}
	public ArrayList<ArrayList> getMessageSentList(String userID) {
		ArrayList resultList = null;

		final String SOAP_ACTION = "http://tempuri.org/getMessageSentList";
		final String METHOD_NAME = "getMessageSentList";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_userID");
		propInfo1.setValue(userID);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);
		
			
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true; // put this only if the web service is .NET one
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			SoapObject response = (SoapObject) envelope.getResponse();
			resultList = getResultSetsFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}
	
	public ArrayList<ArrayList> getMessageSavedList(String userID) {
		ArrayList resultList = null;

		final String SOAP_ACTION = "http://tempuri.org/getMessageSavedList";
		final String METHOD_NAME = "getMessageSavedList";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_userID");
		propInfo1.setValue(userID);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);
		
			
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true; // put this only if the web service is .NET one
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			SoapObject response = (SoapObject) envelope.getResponse();
			resultList = getResultSetsFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}
	public String[] getMessageDetail(String messageID,String typeFlg) {
		String result[] = null;

		final String SOAP_ACTION = "http://tempuri.org/getMessageDetail";
		final String METHOD_NAME = "getMessageDetail";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_messageID");
		propInfo1.setValue(messageID);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);
		
		PropertyInfo propInfo2= new PropertyInfo();
		propInfo2.setName("_typeFlg");
		propInfo2.setValue(typeFlg);
		propInfo2.setType(String.class);
		request.addProperty(propInfo2);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);			
			SoapObject response = (SoapObject) envelope.getResponse();
			result = getResultSetFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	public ArrayList<ArrayList> getAdvertListByUserPreference(String qString) {
		ArrayList resultList = null;

		final String SOAP_ACTION = "http://tempuri.org/getAdvertListByUserPreference";
		final String METHOD_NAME = "getAdvertListByUserPreference";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_qString");
		propInfo1.setValue(qString);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);
		
			
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true; // put this only if the web service is .NET one
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			SoapObject response = (SoapObject) envelope.getResponse();
			resultList = getResultSetsFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}
	public ArrayList<ArrayList> getSellerActiveList(String sellerID,String activeFlg) {
		ArrayList resultList = null;

		final String SOAP_ACTION = "http://tempuri.org/getSellerActiveList";
		final String METHOD_NAME = "getSellerActiveList";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_sellerID");
		propInfo1.setValue(sellerID);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);
		
		PropertyInfo propInfo2 = new PropertyInfo();
		propInfo2.setName("_activeFlg");
		propInfo2.setValue(activeFlg);
		propInfo2.setType(String.class);
		request.addProperty(propInfo2);
			
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true; // put this only if the web service is .NET one
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			SoapObject response = (SoapObject) envelope.getResponse();
			resultList = getResultSetsFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}
	public ArrayList<ArrayList> getBuyerOfferList(String userID) {
		ArrayList resultList = null;

		final String SOAP_ACTION = "http://tempuri.org/getBuyerOfferList";
		final String METHOD_NAME = "getBuyerOfferList";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_userID");
		propInfo1.setValue(userID);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);
		
			
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true; // put this only if the web service is .NET one
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			SoapObject response = (SoapObject) envelope.getResponse();
			resultList = getResultSetsFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}
	public ArrayList<ArrayList> getScheduledAdvertList(String sellerID) {
		ArrayList resultList = null;

		final String SOAP_ACTION = "http://tempuri.org/getScheduledAdvertList";
		final String METHOD_NAME = "getScheduledAdvertList";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_sellerID");
		propInfo1.setValue(sellerID);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);
		
			
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true; // put this only if the web service is .NET one
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			SoapObject response = (SoapObject) envelope.getResponse();
			resultList = getResultSetsFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}
	public ArrayList<ArrayList> getSellerOfferList(String sellerID) {
		ArrayList resultList = null;

		final String SOAP_ACTION = "http://tempuri.org/getSellerOfferList";
		final String METHOD_NAME = "getSellerOfferList";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_sellerID");
		propInfo1.setValue(sellerID);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);
		
			
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true; // put this only if the web service is .NET one
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			SoapObject response = (SoapObject) envelope.getResponse();
			resultList = getResultSetsFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}
	public String[] getMyAdvertiseMeCount(String userID) {
		String result[] = null;

		final String SOAP_ACTION = "http://tempuri.org/getMyAdvertiseMeCount";
		final String METHOD_NAME = "getMyAdvertiseMeCount";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_userID");
		propInfo1.setValue(userID);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);
		
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);			
			SoapObject response = (SoapObject) envelope.getResponse();
			result = getResultSetFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	public ArrayList<ArrayList> getAdvertCategoryList() {
		ArrayList resultList = null;

		final String SOAP_ACTION = "http://tempuri.org/getAdvertCategoryList";
		final String METHOD_NAME = "getAdvertCategoryList";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

					
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true; // put this only if the web service is .NET one
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			SoapObject response = (SoapObject) envelope.getResponse();
			resultList = getResultSetsFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}
	public ArrayList<ArrayList> getAdvertListbyCategory(String qString,String typeFlg) {
		ArrayList resultList = null;

		final String SOAP_ACTION = "http://tempuri.org/getAdvertListbyCategory";
		final String METHOD_NAME = "getAdvertListbyCategory";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_qString");
		propInfo1.setValue(qString);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);
		
		PropertyInfo propInfo2 = new PropertyInfo();
		propInfo2.setName("_typeFlg");
		propInfo2.setValue(typeFlg);
		propInfo2.setType(String.class);
		request.addProperty(propInfo2);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true; // put this only if the web service is .NET one
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			SoapObject response = (SoapObject) envelope.getResponse();
			resultList = getResultSetsFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}
	public ArrayList<ArrayList> getDealListbyCategory(String qString,String typeFlg) {
		ArrayList resultList = null;

		final String SOAP_ACTION = "http://tempuri.org/getDealListbyCategory";
		final String METHOD_NAME = "getDealListbyCategory";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_qString");
		propInfo1.setValue(qString);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);
		
		PropertyInfo propInfo2 = new PropertyInfo();
		propInfo2.setName("_typeFlg");
		propInfo2.setValue(typeFlg);
		propInfo2.setType(String.class);
		request.addProperty(propInfo2);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true; // put this only if the web service is .NET one
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			SoapObject response = (SoapObject) envelope.getResponse();
			resultList = getResultSetsFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}
	public String[] getBuyerOfferByBuyerList(String userID,String advertID) {
		String result[] = null;

		final String SOAP_ACTION = "http://tempuri.org/getBuyerOfferByBuyerList";
		final String METHOD_NAME = "getBuyerOfferByBuyerList";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_userID");
		propInfo1.setValue(userID);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);
		
		PropertyInfo propInfo2= new PropertyInfo();
		propInfo2.setName("_advertID");
		propInfo2.setValue(advertID);
		propInfo2.setType(String.class);
		request.addProperty(propInfo2);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);			
			SoapObject response = (SoapObject) envelope.getResponse();
			result = getResultSetFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	public String[] getBuyerOfferBySellerList(String offerID) {
		String result[] = null;

		final String SOAP_ACTION = "http://tempuri.org/getBuyerOfferBySellerList";
		final String METHOD_NAME = "getBuyerOfferBySellerList";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_offerID");
		propInfo1.setValue(offerID);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);
		
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);			
			SoapObject response = (SoapObject) envelope.getResponse();
			result = getResultSetFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	/*   Recommendation List     */
	public  String[] getItemRecommendationList(String _userID,String _advertID){
		
		String result[] = null;

		final String SOAP_ACTION = "http://tempuri.org/getItemRecommendationList";
		final String METHOD_NAME = "getItemRecommendationList";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_userID");
		propInfo1.setValue(_userID);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);
		
		PropertyInfo propInfo2 = new PropertyInfo();
		propInfo2.setName("_advertID");
		propInfo2.setValue(_advertID);
		propInfo2.setType(String.class);
		request.addProperty(propInfo2);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(DM_URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);			
			SoapObject response = (SoapObject) envelope.getResponse();
			result = getResultSetFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
		
	}
	public  String[] getUserRecommendationList(String _userID,String _advertID){
		
		String result[] = null;

		final String SOAP_ACTION = "http://tempuri.org/getUserRecommendationList";
		final String METHOD_NAME = "getUserRecommendationList";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_userID");
		propInfo1.setValue(_userID);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);
		
		PropertyInfo propInfo2 = new PropertyInfo();
		propInfo2.setName("_advertID");
		propInfo2.setValue(_advertID);
		propInfo2.setType(String.class);
		request.addProperty(propInfo2);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(DM_URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);			
			SoapObject response = (SoapObject) envelope.getResponse();
			result = getResultSetFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
		
	}
	public  String[] getRelatedItemSet(String _advertID){
	
	String result[] = null;

	final String SOAP_ACTION = "http://tempuri.org/getRelatedItemSet";
	final String METHOD_NAME = "getRelatedItemSet";

	SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

	
	PropertyInfo propInfo1 = new PropertyInfo();
	propInfo1.setName("_advertID");
	propInfo1.setValue(_advertID);
	propInfo1.setType(String.class);
	request.addProperty(propInfo1);
	
	SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
			SoapEnvelope.VER11);
	envelope.dotNet = true;
	envelope.setOutputSoapObject(request);
	HttpTransportSE androidHttpTransport = new HttpTransportSE(DM_URL);

	try {
		androidHttpTransport.call(SOAP_ACTION, envelope);			
		SoapObject response = (SoapObject) envelope.getResponse();
		result = getResultSetFromResponse(response);
		Log.i("myApp", response.toString());
	} catch (Exception e) {
		e.printStackTrace();
	}

	return result;
	
}
	
	public ArrayList<ArrayList> getRecommendationList(String _queryString) {
		ArrayList resultList = null;

		final String SOAP_ACTION = "http://tempuri.org/getRecommendationList";
		final String METHOD_NAME = "getRecommendationList";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_queryString");
		propInfo1.setValue(_queryString);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true; // put this only if the web service is .NET one
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			SoapObject response = (SoapObject) envelope.getResponse();
			resultList = getResultSetsFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}
	public ArrayList<ArrayList> getGeoLocationInfo(String _userID)
     {
		ArrayList resultList = null;

		final String SOAP_ACTION = "http://tempuri.org/getGeoLocationInfo";
		final String METHOD_NAME = "getGeoLocationInfo";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.setName("_userID");
		propInfo1.setValue(_userID);
		propInfo1.setType(String.class);
		request.addProperty(propInfo1);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true; // put this only if the web service is .NET one
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			SoapObject response = (SoapObject) envelope.getResponse();
			resultList = getResultSetsFromResponse(response);
			Log.i("myApp", response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}
	
	
	
	
	public Boolean saveAdvertReview(ArrayList paraList) {

		boolean result = false;

		final String SOAP_ACTION = "http://tempuri.org/saveAdvertReview";
		final String METHOD_NAME = "saveAdvertReview";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		
		String[] paraNameList = {"_advertID", "_userID", "_reviewContent", "_rateValue","_reviewHeading"};
		for (int count = 0; count <= paraNameList.length - 1; count++) {
			PropertyInfo propInfo1 = new PropertyInfo();
			propInfo1.setName(paraNameList[count]);
			propInfo1.setValue(paraList.get(count).toString());
			propInfo1.setType(String.class);
			request.addProperty(propInfo1);
		}

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true; // put this only if the web service is .NET one
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			Log.i("myApp", response.toString());
			if (response.toString().equalsIgnoreCase("true")) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public Boolean saveAdvertView(ArrayList paraList) {

		boolean result = false;

		final String SOAP_ACTION = "http://tempuri.org/saveAdvertView";
		final String METHOD_NAME = "saveAdvertView";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		
		String[] paraNameList = {"_advertID", "_userID"};
		for (int count = 0; count <= paraNameList.length - 1; count++) {
			PropertyInfo propInfo1 = new PropertyInfo();
			propInfo1.setName(paraNameList[count]);
			propInfo1.setValue(paraList.get(count).toString());
			propInfo1.setType(String.class);
			request.addProperty(propInfo1);
		}

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true; // put this only if the web service is .NET one
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			Log.i("myApp", response.toString());
			if (response.toString().equalsIgnoreCase("true")) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public Boolean saveSellerReview(ArrayList paraList) {

		boolean result = false;

		final String SOAP_ACTION = "http://tempuri.org/saveSellerReview";
		final String METHOD_NAME = "saveSellerReview";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		
		String[] paraNameList = {"_sellerID","_userID", "_reviewFlg", "_reviewComment"};
		for (int count = 0; count <= paraNameList.length - 1; count++) {
			PropertyInfo propInfo1 = new PropertyInfo();
			propInfo1.setName(paraNameList[count]);
			propInfo1.setValue(paraList.get(count).toString());
			propInfo1.setType(String.class);
			request.addProperty(propInfo1);
		}

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true; // put this only if the web service is .NET one
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			Log.i("myApp", response.toString());
			if (response.toString().equalsIgnoreCase("true")) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public Boolean saveBuyerOffer(ArrayList paraList) {

		boolean result = false;

		final String SOAP_ACTION = "http://tempuri.org/saveBuyerOffer";
		final String METHOD_NAME = "saveBuyerOffer";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		
		String[] paraNameList = {"_userID", "_advertID", "_buyerOffer", "_sellerOffer", "_buyerTerms", "_sellerTerms"};
		for (int count = 0; count <= paraNameList.length - 1; count++) {
			PropertyInfo propInfo1 = new PropertyInfo();
			propInfo1.setName(paraNameList[count]);
			propInfo1.setValue(paraList.get(count).toString());
			propInfo1.setType(String.class);
			request.addProperty(propInfo1);
		}

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true; // put this only if the web service is .NET one
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			Log.i("myApp", response.toString());
			if (response.toString().equalsIgnoreCase("true")) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public Boolean updateBuyerOffer(ArrayList paraList) {

		boolean result = false;

		final String SOAP_ACTION = "http://tempuri.org/updateBuyerOffer";
		final String METHOD_NAME = "updateBuyerOffer";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		
		String[] paraNameList = {"_userID", "_advertID", "_buyerOffer", "_sellerOffer", "_buyerTerms", "_sellerTerms"};
		for (int count = 0; count <= paraNameList.length - 1; count++) {
			PropertyInfo propInfo1 = new PropertyInfo();
			propInfo1.setName(paraNameList[count]);
			propInfo1.setValue(paraList.get(count).toString());
			propInfo1.setType(String.class);
			request.addProperty(propInfo1);
		}

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true; // put this only if the web service is .NET one
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			Log.i("myApp", response.toString());
			if (response.toString().equalsIgnoreCase("true")) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public Boolean updateBuyerOfferBySeller(ArrayList paraList) {

		boolean result = false;

		final String SOAP_ACTION = "http://tempuri.org/updateBuyerOfferBySeller";
		final String METHOD_NAME = "updateBuyerOfferBySeller";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		
		String[] paraNameList = {"_offerID", "_buyerOffer", "_sellerOffer", "_buyerTerms", "_sellerTerms","_sellerFlg"};
		for (int count = 0; count <= paraNameList.length - 1; count++) {
			PropertyInfo propInfo1 = new PropertyInfo();
			propInfo1.setName(paraNameList[count]);
			propInfo1.setValue(paraList.get(count).toString());
			propInfo1.setType(String.class);
			request.addProperty(propInfo1);
		}

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true; // put this only if the web service is .NET one
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			Log.i("myApp", response.toString());
			if (response.toString().equalsIgnoreCase("true")) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public Boolean saveContactRequest(ArrayList paraList) {

		boolean result = false;

		final String SOAP_ACTION = "http://tempuri.org/saveContactRequest";
		final String METHOD_NAME = "saveContactRequest";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		
		String[] paraNameList = {"_userID", "_connID"};
		for (int count = 0; count <= paraNameList.length - 1; count++) {
			PropertyInfo propInfo1 = new PropertyInfo();
			propInfo1.setName(paraNameList[count]);
			propInfo1.setValue(paraList.get(count).toString());
			propInfo1.setType(String.class);
			request.addProperty(propInfo1);
		}

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true; // put this only if the web service is .NET one
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			Log.i("myApp", response.toString());
			if (response.toString().equalsIgnoreCase("true")) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public Boolean saveContact(ArrayList paraList) {

		boolean result = false;

		final String SOAP_ACTION = "http://tempuri.org/saveContact";
		final String METHOD_NAME = "saveContact";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		
		String[] paraNameList = {"_userID", "_connID"};
		for (int count = 0; count <= paraNameList.length - 1; count++) {
			PropertyInfo propInfo1 = new PropertyInfo();
			propInfo1.setName(paraNameList[count]);
			propInfo1.setValue(paraList.get(count).toString());
			propInfo1.setType(String.class);
			request.addProperty(propInfo1);
		}

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true; // put this only if the web service is .NET one
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			Log.i("myApp", response.toString());
			if (response.toString().equalsIgnoreCase("true")) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public Boolean removeFromContact(ArrayList paraList) {

		boolean result = false;

		final String SOAP_ACTION = "http://tempuri.org/removeFromContact";
		final String METHOD_NAME = "removeFromContact";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		
		String[] paraNameList = {"_userID", "_connID"};
		for (int count = 0; count <= paraNameList.length - 1; count++) {
			PropertyInfo propInfo1 = new PropertyInfo();
			propInfo1.setName(paraNameList[count]);
			propInfo1.setValue(paraList.get(count).toString());
			propInfo1.setType(String.class);
			request.addProperty(propInfo1);
		}

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true; // put this only if the web service is .NET one
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			Log.i("myApp", response.toString());
			if (response.toString().equalsIgnoreCase("true")) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	//----------------- Support Methods ------------------------------

	public String[] getResultSetFromResponse(SoapObject soap) {

		String[] itemArr = new String[soap.getPropertyCount()];
		for (int i = 0; i < itemArr.length; i++) {

			String item = soap.getProperty(i).toString();
			itemArr[i] = item;
		}
		return itemArr;
	}
	public ArrayList<ArrayList> getResultSetsFromResponse(SoapObject soap) {

		
		SoapObject rowStr= (SoapObject) soap.getProperty(0);
		
		ArrayList<ArrayList> resultList = null;
		ArrayList<String> rowList=null;
		int totalCount = soap.getPropertyCount();
		
		if (totalCount > 0 ) {
			resultList = new ArrayList<ArrayList>();
		    for (int detailCount = 0; detailCount < totalCount; detailCount++) {
		        SoapObject rowSoap = (SoapObject) soap.getProperty(detailCount);
		        rowList = new ArrayList<String>();
		        for(int columnCount=0;columnCount<rowSoap.getPropertyCount();columnCount++){
		        	
		        	rowList.add(rowSoap.getProperty(columnCount).toString());	
		        }
		       
		        resultList.add(rowList);
		    }
		}
		return resultList;
	}

}
