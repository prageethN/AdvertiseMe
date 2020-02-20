using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using AdvertiseServiceBusinessLogic;

[WebService(Namespace = "http://tempuri.org/")]
[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
// To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
// [System.Web.Script.Services.ScriptService]

public class AdvertiseService : System.Web.Services.WebService
{
    public AdvertiseService()
    {

        //Uncomment the following line if using designed components 
        //InitializeComponent(); 
    }
        
    [WebMethod]
    public String[] getUserProfileDetail(String _userid)
    {
        return ServiceBusinessLogic.getUserProfileDetail(_userid);
    }
    [WebMethod]
    public String authenticateLogin(string _userName, string _passWord)

    {
        return ServiceBusinessLogic.authenticateLogin(_userName, _passWord);
    }
     [WebMethod]
    public Boolean updateUserProfileDetail(string _userid, int _columnIndex, string _columnVlaue)
    {
        return ServiceBusinessLogic.updateUserProfileDetail(_userid, _columnIndex, _columnVlaue);
    }
     [WebMethod]
     public Boolean isUsernameExist(string _userName)
    {
        return ServiceBusinessLogic.isUsernameExist(_userName);
    }
    [WebMethod]
     public String getNewuserUserID()
    {
        return ServiceBusinessLogic.getNewuserUserID();
    }

     [WebMethod]
    public Boolean registerNewUser(String _FullName, String _emailID, String _address, String _city, String _state, String _country, String _language, String _mobileNo, String _homeNo,
            String _officeNo, String _homeLink, String _facebook, String _google, String _twitter, String _skype, String _username, String _password, String _userImage)
    {
        String _userID = ServiceBusinessLogic.getNewuserUserID();
        String userImagePath = storeProfileImage(_userImage, _userID);
        
        ServiceBusinessLogic.registerNewUser(_userID, _FullName, _emailID, _address, _city, _state, _country, _language, _mobileNo, _homeNo,
          _officeNo, _homeLink, _facebook, _google, _twitter, _skype, _username, _password, userImagePath);
        return true;
    }

     [WebMethod]
     public String getAdvertisementID(string _refCode)
     {
         return ServiceBusinessLogic.getAdvertisementID(_refCode);
     }

     [WebMethod]
     public List<List<String>> getAdvertSearchResult(string _qString)
     {
         return ServiceBusinessLogic.getAdvertSearchResult(_qString);
     }

     [WebMethod]
     public String[] getAdvertisementDetail(String _advertID)
     {
         return ServiceBusinessLogic.getAdvertisementDetail(_advertID);
     }
     [WebMethod]
     public String[] getAdvertisementRating(String _advertID)
     {
         return ServiceBusinessLogic.getAdvertisementRating(_advertID);
     }
     [WebMethod]
     public String[] getSellerDetail(String _sellerID)
     {
         return ServiceBusinessLogic.getSellerDetail(_sellerID);
     }
     [WebMethod]
     public List<List<String>> getResourceList(string _advertID)
     {
         return ServiceBusinessLogic.getResourceList(_advertID);
     }

     [WebMethod]
     public List<List<String>> getUserReviewList(string _advertID)
     {
         return ServiceBusinessLogic.getUserReviewList(_advertID);
     }
    [WebMethod]
     public String[] getSellerReview(String _sellerID)
     {
         return ServiceBusinessLogic.getSellerReview(_sellerID);
     }
    [WebMethod]
    public List<List<String>> getSellerFeedbackList(string _sellerID)
    {
        return ServiceBusinessLogic.getSellerFeedbackList(_sellerID);
    }
    [WebMethod]
    public List<List<String>> getDailyDealList(string _userID)
    {
        return ServiceBusinessLogic.getDailyDealList(_userID);
    }
    [WebMethod]
    public List<List<String>> getAdvertListBySeller(string _sellerID)
    {
        return ServiceBusinessLogic.getAdvertListBySeller(_sellerID);
    }
    [WebMethod]
    public String[] getDailyDealItem()
    {
        return ServiceBusinessLogic.getDailyDealItem();
    }
    [WebMethod]
    public List<List<String>> getBestDealList()
    {
        return ServiceBusinessLogic.getBestDealList();
    }
    [WebMethod]
    public String[] getBestDealAdvertisementDetail(String _advertID)
    {
        return ServiceBusinessLogic.getBestDealAdvertisementDetail(_advertID);
    }
    [WebMethod]
    public List<List<String>> getUserRecommendationList(String _userID)
    {
        return ServiceBusinessLogic.getUserRecommendationList(_userID);
    }
    [WebMethod]
    public List<List<String>> getUserConnectionList(String _userID,String _searchQuery)
    {
        return ServiceBusinessLogic.getUserConnectionList(_userID, _searchQuery);
    }
    [WebMethod]
    public List<List<String>> getConnectionRequestList(String _userID)
    {
        return ServiceBusinessLogic.getConnectionRequestList(_userID);
    }
    [WebMethod]
    public List<List<String>> getUserViewsList(String _userID)
    {
        return ServiceBusinessLogic.getUserViewsList(_userID);
    }
    [WebMethod]
    public List<List<String>> getUserActivityList(String _userID)
    {
        return ServiceBusinessLogic.getUserActivityList(_userID);
    }
    [WebMethod]
    public String[] getUserAcountCount(String _userID)
    {
        return ServiceBusinessLogic.getUserAcountCount(_userID);
    }
    [WebMethod]
    public List<List<String>> getMessageInboxList(String _userID)
    {
        return ServiceBusinessLogic.getMessageInboxList(_userID);
    }
    [WebMethod]
    public List<List<String>> getMessageSentList(String _userID)
    {
        return ServiceBusinessLogic.getMessageSentList(_userID);
    }
    [WebMethod]
    public List<List<String>> getMessageSavedList(String _userID)
    {
        return ServiceBusinessLogic.getMessageSavedList(_userID);
    }
    [WebMethod]
    public String[] getMessageDetail(String _messageID, String _typeFlg)
    {
        return ServiceBusinessLogic.getMessageDetail(_messageID, _typeFlg);
    }
    [WebMethod]
    public List<List<String>> getAdvertListByUserPreference(string _qString)
    {
        return ServiceBusinessLogic.getAdvertListByUserPreference(_qString);
    }







    // ------------------------------ Supoort Functions ----------------------------------------

    String storeProfileImage(String _userImage, String _userID)
    {
        string profileImgURL = "";
        if (_userImage != "")
        {

            string sSavePath = "App_Resource/ProfilePicture/";
            System.Text.UTF8Encoding encoder = new System.Text.UTF8Encoding();
            System.Text.Decoder utf8Decode = encoder.GetDecoder();
            byte[] ret = Convert.FromBase64String(_userImage);

            // Save the stream to disk
            System.IO.FileStream newFile = new System.IO.FileStream(Server.MapPath(sSavePath + _userID + ".jpg"), System.IO.FileMode.Create);
            newFile.Write(ret, 0, ret.Length);
            newFile.Close();
            profileImgURL = "/App_Resource/ProfilePicture/" + _userID + ".jpg";
        }
        return profileImgURL;

    }

}