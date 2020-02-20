using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Data;
using System.Data.SqlClient;
using System.Configuration;

/// <summary>
/// Summary description for ServiceDataAccess
/// </summary>
/// 

namespace AdvertiseServiceDataAccess
{
    public class ServiceDataAccess : BaseDataAccess
    {
        public ServiceDataAccess()
        {
            
        }
        public static void InstantiateBaseDataAccess()
        {

            BaseDataAccess dataAccess = new BaseDataAccess();
        }
        
        public static DataTable authenticateLogin(string _userName, string _passWord)
        {
            InstantiateBaseDataAccess();

            string sSQL = SQL.Q_USER_AUTHENTICATION;

            SqlParameter[] arrParams = new SqlParameter[2];
            SqlCommand cmd;
            arrParams[0] = _dbHelper.getParameter("@userName", DbType.String, _userName);
            arrParams[1] = _dbHelper.getParameter("@passWord", DbType.String, _passWord);

            cmd = _dbHelper.getCommand(sSQL, arrParams);
            return _dbManager.getResultset(cmd).Tables[0];
        }
        public static DataTable getUserProfileDetail(string _userid)
        {
            InstantiateBaseDataAccess();

            string sSQL = SQL.Q_GET_USER_PROFILE_DETAIL;

            SqlParameter[] arrParams = new SqlParameter[1];
            SqlCommand cmd;
            arrParams[0] = _dbHelper.getParameter("@userID", DbType.String, _userid);
            cmd = _dbHelper.getCommand(sSQL, arrParams);
            return _dbManager.getResultset(cmd).Tables[0];
        }
        public static void updateUserProfileDetail(string _userid,int columnIndex,string _columnVlaue)
        {
            InstantiateBaseDataAccess();

            string sSQL="";
            
            switch (columnIndex)
            {
                case 1:
                    sSQL = ConfigurationManager.AppSettings["Q_UPDATE_PROFILE_DETAIL_FULL_NAME"];
                    break;
                case 2:
                    sSQL = ConfigurationManager.AppSettings["Q_UPDATE_PROFILE_DETAIL_FULL_STATUS_MESSAGE"];
                    break;
                case 3:
                    sSQL = ConfigurationManager.AppSettings["Q_UPDATE_PROFILE_DETAIL_FULL_NAME_EMAIL"];
                    break;
                case 4:
                    sSQL = ConfigurationManager.AppSettings["Q_UPDATE_PROFILE_DETAIL_FULL_NAME_ADDRESS"];
                    break;
                case 5:
                    sSQL = ConfigurationManager.AppSettings["Q_UPDATE_PROFILE_DETAIL_FULL_NAME_CITY"];
                    break;
                case 6:
                    sSQL = ConfigurationManager.AppSettings["Q_UPDATE_PROFILE_DETAIL_FULL_NAME_COUNTRY"];
                    break;
                case 7:
                    sSQL = ConfigurationManager.AppSettings["Q_UPDATE_PROFILE_DETAIL_FULL_NAME_STATE"];
                    break;
                case 8:
                    sSQL = ConfigurationManager.AppSettings["Q_UPDATE_PROFILE_DETAIL_FULL_NAME_LANGUAGE"];
                    break;
                case 9:
                    sSQL = ConfigurationManager.AppSettings["Q_UPDATE_PROFILE_DETAIL_FULL_NAME_MOBILE_PHONE"];
                    break;
                case 10:
                    sSQL = ConfigurationManager.AppSettings["Q_UPDATE_PROFILE_DETAIL_FULL_NAME_HOME_PHONE"];
                    break;
                case 11:
                    sSQL = ConfigurationManager.AppSettings["Q_UPDATE_PROFILE_DETAIL_FULL_NAME_OFFICE_PHONE"];
                    break;
                case 12:
                    sSQL = ConfigurationManager.AppSettings["Q_UPDATE_PROFILE_DETAIL_FULL_NAME_HOME_PAGE"];
                    break;
                case 13:
                    sSQL = ConfigurationManager.AppSettings["Q_UPDATE_PROFILE_DETAIL_FULL_NAME_SOCIAL_FACEBOOK"];
                    break;
                case 14:
                    sSQL = ConfigurationManager.AppSettings["Q_UPDATE_PROFILE_DETAIL_FULL_NAME_SOCIAL_GOOGLE"];
                    break;
                case 15:
                    sSQL = ConfigurationManager.AppSettings["Q_UPDATE_PROFILE_DETAIL_FULL_NAME_SOCIAL_TWITTER"];
                    break;
                case 16:
                    sSQL = ConfigurationManager.AppSettings["Q_UPDATE_PROFILE_DETAIL_FULL_NAME_SOCIAL_SKYPE"];
                    break;
            }
           // sSQL = "UPDATE ADVERT_USER_PROFILE SET FULL_NAME=@columValue WHERE USER_ID=@userID";
            SqlParameter[] arrParams = new SqlParameter[2];
            SqlCommand cmd;
            arrParams[0] = _dbHelper.getParameter("@userID", DbType.String, _userid);
            arrParams[1] = _dbHelper.getParameter("@columValue", DbType.String, _columnVlaue);
            cmd = _dbHelper.getCommand(sSQL, arrParams);
            _dbManager.executeQuery(cmd);
        }
        public static DataTable isUsernameExist(string _userName)
        {
            InstantiateBaseDataAccess();

            string sSQL = SQL.Q_IS_USERNAME_EXISTS;

            SqlParameter[] arrParams = new SqlParameter[1];
            SqlCommand cmd;
            arrParams[0] = _dbHelper.getParameter("@userName", DbType.String, _userName);
            cmd = _dbHelper.getCommand(sSQL, arrParams);
            return _dbManager.getResultset(cmd).Tables[0];
        }

        public static DataTable getNewuserUserID()
        {
            InstantiateBaseDataAccess();

            string sSQL = SQL.Q_GET_NEWUSER_USERID;

            SqlParameter[] arrParams = null;
            SqlCommand cmd;
           
            cmd = _dbHelper.getCommand(sSQL, arrParams);
            return _dbManager.getResultset(cmd).Tables[0];
        }
       
         public static void registerNewUser(String _userID, String _FullName, String _emailID, String _address, String _city, String _state, String _country, String _language, String _mobileNo, String _homeNo,
             String _officeNo, String _homeLink, String _facebook, String _google, String _twitter, String _skype,String _username,String _password,String _userImage)
       {

           createUserAccount(_userID, _username, _password);

           InstantiateBaseDataAccess();
          
           string sSQL = SQL.Q_CREATE_NEW_USER_PROFILE;

           SqlParameter[] arrParams = new SqlParameter[18];
           SqlCommand cmd; 

           arrParams[0] = _dbHelper.getParameter("@userID", DbType.String, _userID);
           arrParams[1] = _dbHelper.getParameter("@fullName", DbType.String, _FullName);
           arrParams[2] = _dbHelper.getParameter("@statusMsg", DbType.String, "Enter a message here to share with all of your contacts");
           arrParams[3] = _dbHelper.getParameter("@email", DbType.String, _emailID);
           arrParams[4] = _dbHelper.getParameter("@address", DbType.String, _address);
           arrParams[5] = _dbHelper.getParameter("@city", DbType.String, _city);
           arrParams[6] = _dbHelper.getParameter("@country", DbType.String, _country);
           arrParams[7] = _dbHelper.getParameter("@state", DbType.String, _state);
           arrParams[8] = _dbHelper.getParameter("@language", DbType.String, _language);
           arrParams[9] = _dbHelper.getParameter("@mobilePhone", DbType.String, _mobileNo);
           arrParams[10] = _dbHelper.getParameter("@homePhone", DbType.String, _homeNo);
           arrParams[11] = _dbHelper.getParameter("@OfficePhone", DbType.String, _officeNo);
           arrParams[12] = _dbHelper.getParameter("@homePage", DbType.String, _homeLink);
           arrParams[13] = _dbHelper.getParameter("@socialFB", DbType.String, _facebook);
           arrParams[14] = _dbHelper.getParameter("@socialGP", DbType.String, _google);
           arrParams[15] = _dbHelper.getParameter("@socialTW", DbType.String, _twitter);
           arrParams[16] = _dbHelper.getParameter("@socialSK", DbType.String, _skype);
           arrParams[17] = _dbHelper.getParameter("@imageURL", DbType.String, _userID);

           cmd = _dbHelper.getCommand(sSQL, arrParams);
           _dbManager.executeQuery(cmd);


           saveUserImageURL(_userID, _userImage);


       }
         public static void createUserAccount(String _userID, String _userName, String _passWord)
         {
             InstantiateBaseDataAccess();

             string sSQL = SQL.Q_CREATE_NEW_USER_ACCOUNT;
             

             SqlParameter[] arrParams = new SqlParameter[3];
             SqlCommand cmd; 

             arrParams[0] = _dbHelper.getParameter("@userID", DbType.String, _userID);
             arrParams[1] = _dbHelper.getParameter("@advertisemeName", DbType.String, _userName);
             arrParams[2] = _dbHelper.getParameter("@password", DbType.String, _passWord);
             cmd = _dbHelper.getCommand(sSQL, arrParams);
             _dbManager.executeQuery(cmd);
         
         }

        public static  void saveUserImageURL(String _userID,String _url) {

             InstantiateBaseDataAccess();

             string sSQL = SQL.Q_SAVE_PROFILE_IMAGE_URL;

             SqlCommand cmd;
             SqlParameter[] arrParams = new SqlParameter[2];

             arrParams[0] = _dbHelper.getParameter("@userID", DbType.String, _userID);
             arrParams[1] = _dbHelper.getParameter("@url", DbType.String, _url);
             cmd = _dbHelper.getCommand(sSQL, arrParams);
             _dbManager.executeQuery(cmd);
             
         }
        public static DataTable getAdvertisementID(string refCode)
        {

            InstantiateBaseDataAccess();

            string sSQL = SQL.Q_GET_ADVERTISEMENT_ID;

            SqlParameter[] arrParams = new SqlParameter[1];
            SqlCommand cmd;
            arrParams[0] = _dbHelper.getParameter("@refCode", DbType.String, refCode);
            cmd = _dbHelper.getCommand(sSQL, arrParams);
            return _dbManager.getResultset(cmd).Tables[0];
        }
        public static DataTable getAdvertSearchResult(string qString)
        {

            InstantiateBaseDataAccess();

            string sSQL = SQL.Q_GET_ADVERT_SEARCH_RESULT;

            SqlParameter[] arrParams = new SqlParameter[1];
            SqlCommand cmd;
            arrParams[0] = _dbHelper.getParameter("@qString", DbType.String, qString);
            cmd = _dbHelper.getCommand(sSQL, arrParams);
            return _dbManager.getResultset(cmd).Tables[0];
        }
        public static DataTable getAdvertisementDetail(string _advertID)
        {
            InstantiateBaseDataAccess();

            string sSQL = SQL.Q_GET_ADVERT_DETAIL;

            SqlParameter[] arrParams = new SqlParameter[1];
            SqlCommand cmd;
            arrParams[0] = _dbHelper.getParameter("@advertID", DbType.String, _advertID);
            cmd = _dbHelper.getCommand(sSQL, arrParams);
            return _dbManager.getResultset(cmd).Tables[0];
        }
        public static DataTable getAdvertisementRating(string _advertID)
        {
            InstantiateBaseDataAccess();

            string sSQL = SQL.Q_GET_ADVERT_RATING;

            SqlParameter[] arrParams = new SqlParameter[1];
            SqlCommand cmd;
            arrParams[0] = _dbHelper.getParameter("@advertID", DbType.String, _advertID);
            cmd = _dbHelper.getCommand(sSQL, arrParams);
            return _dbManager.getResultset(cmd).Tables[0];
        }
        public static DataTable getSellerDetail(string _sellerID)
        {
            InstantiateBaseDataAccess();

            string sSQL = SQL.Q_GET_SELLER_DETAIL;

            SqlParameter[] arrParams = new SqlParameter[1];
            SqlCommand cmd;
            arrParams[0] = _dbHelper.getParameter("@sellerID", DbType.String, _sellerID);
            cmd = _dbHelper.getCommand(sSQL, arrParams);
            return _dbManager.getResultset(cmd).Tables[0];
        }
        public static DataTable getResourceList(string advertID)
        {

            InstantiateBaseDataAccess();

            string sSQL = SQL.Q_GET_RESOURCE_LIST;

            SqlParameter[] arrParams = new SqlParameter[1];
            SqlCommand cmd;
            arrParams[0] = _dbHelper.getParameter("@advertID", DbType.String, advertID);
            cmd = _dbHelper.getCommand(sSQL, arrParams);
            return _dbManager.getResultset(cmd).Tables[0];
        }
        public static DataTable getUserReviewList(string advertID)
        {

            InstantiateBaseDataAccess();

            string sSQL = SQL.Q_GET_USER_REVIEW_LIST;

            SqlParameter[] arrParams = new SqlParameter[1];
            SqlCommand cmd;
            arrParams[0] = _dbHelper.getParameter("@advertID", DbType.String, advertID);
            cmd = _dbHelper.getCommand(sSQL, arrParams);
            return _dbManager.getResultset(cmd).Tables[0];
        }
        public static DataTable getSellerReview(string _sellerID)
        {
            InstantiateBaseDataAccess();

            string sSQL = SQL.Q_GET_SELLER_REVIEW;

            SqlParameter[] arrParams = new SqlParameter[1];
            SqlCommand cmd;
            arrParams[0] = _dbHelper.getParameter("@sellerID", DbType.String, _sellerID);
            cmd = _dbHelper.getCommand(sSQL, arrParams);
            return _dbManager.getResultset(cmd).Tables[0];
        }
        public static DataTable getSellerFeedbackList(string sellerID)
        {

            InstantiateBaseDataAccess();

            string sSQL = SQL.Q_GET_SELLER_FEEDBACK;

            SqlParameter[] arrParams = new SqlParameter[1];
            SqlCommand cmd;
            arrParams[0] = _dbHelper.getParameter("@sellerID", DbType.String, sellerID);
            cmd = _dbHelper.getCommand(sSQL, arrParams);
            return _dbManager.getResultset(cmd).Tables[0];
        }
        public static DataTable getDailyDealList(string userID)
        {

            InstantiateBaseDataAccess();

            string sSQL = SQL.Q_GET_DAILY_DEAL_LIST;

            SqlParameter[] arrParams = new SqlParameter[1];
            SqlCommand cmd;
            arrParams[0] = _dbHelper.getParameter("@userID", DbType.String, userID);
            cmd = _dbHelper.getCommand(sSQL, arrParams);
            return _dbManager.getResultset(cmd).Tables[0];
        }
        public static DataTable getAdvertListBySeller(string sellerID)
        {

            InstantiateBaseDataAccess();

            string sSQL = SQL.Q_GET_ADVERT_LIST_BY_SELLER;

            SqlParameter[] arrParams = new SqlParameter[1];
            SqlCommand cmd;
            arrParams[0] = _dbHelper.getParameter("@sellerID", DbType.String, sellerID);
            cmd = _dbHelper.getCommand(sSQL, arrParams);
            return _dbManager.getResultset(cmd).Tables[0];
        }
        public static DataTable getDailyDealItem()
        {
            InstantiateBaseDataAccess();

            string sSQL = SQL.Q_GET_DAILY_DEAL_ITEM;

            SqlParameter[] arrParams = new SqlParameter[1];
            SqlCommand cmd;
            arrParams[0] = _dbHelper.getParameter("@sellerID", DbType.String, "");
            cmd = _dbHelper.getCommand(sSQL, arrParams);
            return _dbManager.getResultset(cmd).Tables[0];
        }

        public static DataTable getBestDealList()
        {
            InstantiateBaseDataAccess();

            string sSQL = SQL.Q_GET_BEST_DEALS_LIST;

            SqlParameter[] arrParams = new SqlParameter[1];
            SqlCommand cmd;
            arrParams[0] = _dbHelper.getParameter("@sellerID", DbType.String, "");
            cmd = _dbHelper.getCommand(sSQL, arrParams);
            return _dbManager.getResultset(cmd).Tables[0];
        }
        public static DataTable getBestDealAdvertisementDetail(string _advertID)
        {
            InstantiateBaseDataAccess();

            string sSQL = SQL.Q_GET_BEST_DEAL_ADVERT_DETAIL;

            SqlParameter[] arrParams = new SqlParameter[1];
            SqlCommand cmd;
            arrParams[0] = _dbHelper.getParameter("@advertID", DbType.String, _advertID);
            cmd = _dbHelper.getCommand(sSQL, arrParams);
            return _dbManager.getResultset(cmd).Tables[0];
        }
        public static DataTable getUserRecommendationList(String userID)
        {
            InstantiateBaseDataAccess();

            string sSQL = SQL.Q_GET_USER_RECOMMENDATION_LIST;

            SqlParameter[] arrParams = new SqlParameter[1];
            SqlCommand cmd;
            arrParams[0] = _dbHelper.getParameter("@userID", DbType.String, userID);
            cmd = _dbHelper.getCommand(sSQL, arrParams);
            return _dbManager.getResultset(cmd).Tables[0];
        }
        public static DataTable getUserConnectionList(String userID,String searchQuery)
        {
            InstantiateBaseDataAccess();
            
            //string sSQL = SQL.Q_GET_USER_CONNECTION_LIST;

            string sSQL = @"(SELECT CONNECTION_ID  AS CONNECTION_ID,PROF.CITY,PROF.STATE,PROF.COUNTRY,ACNT.ADVERTISEME_NAME,PROF.FULL_NAME,'http://192.168.69.1/AdvertiseService'+URL.URL_STRING AS URL_STRING FROM ADVERT_USER_CONNECTION CONN,ADVERT_USER_PROFILE PROF,ADVERT_USER_ACCOUNT ACNT,ADVERT_RESORUCE_URL URL
                             WHERE CONN.CONNECTION_ID=PROF.USER_ID  AND CONN.CONNECTION_ID=ACNT.USER_ID
                             AND  CONN.USER_ID=@userID AND URL.URL_ID=CONN.CONNECTION_ID AND PROF.FULL_NAME LIKE '%" + searchQuery+"%'"+
                          @")  UNION
                          (
                            SELECT CONN.USER_ID AS CONNECTION_ID,PROF.CITY,PROF.STATE,PROF.COUNTRY,ACNT.ADVERTISEME_NAME,PROF.FULL_NAME,'http://192.168.69.1/AdvertiseService'+URL.URL_STRING AS URL_STRING FROM ADVERT_USER_CONNECTION CONN,ADVERT_USER_PROFILE PROF,ADVERT_USER_ACCOUNT ACNT,ADVERT_RESORUCE_URL URL
                            WHERE CONN.USER_ID=PROF.USER_ID  AND  CONN.USER_ID=ACNT.USER_ID
                            AND CONN.CONNECTION_ID=@userID AND URL.URL_ID=CONN.USER_ID AND PROF.FULL_NAME LIKE '%" + searchQuery+"%')";

            if (searchQuery==null) {
                searchQuery = "";
            }

            SqlParameter[] arrParams = new SqlParameter[2];
            SqlCommand cmd;
            SQLStorage.searchQuery = searchQuery;
            arrParams[0] = _dbHelper.getParameter("@userID", DbType.String, userID);
            arrParams[1] = _dbHelper.getParameter("@searchQuery", DbType.String, searchQuery);
            cmd = _dbHelper.getCommand(sSQL, arrParams);
            return _dbManager.getResultset(cmd).Tables[0];
        }
        public static DataTable getConnectionRequestList(String userID)
        {
            InstantiateBaseDataAccess();

            string sSQL = SQL.Q_GET_CONNECTION_REQUEST_LIST;

            SqlParameter[] arrParams = new SqlParameter[1];
            SqlCommand cmd;
            arrParams[0] = _dbHelper.getParameter("@userID", DbType.String, userID);
            cmd = _dbHelper.getCommand(sSQL, arrParams);
            return _dbManager.getResultset(cmd).Tables[0];
        }
        public static DataTable getUserViewsList(String userID)
        {
            InstantiateBaseDataAccess();

            string sSQL = SQL.Q_GET_USER_VIEWS_LIST;

            SqlParameter[] arrParams = new SqlParameter[1];
            SqlCommand cmd;
            arrParams[0] = _dbHelper.getParameter("@userID", DbType.String, userID);
            cmd = _dbHelper.getCommand(sSQL, arrParams);
            return _dbManager.getResultset(cmd).Tables[0];
        }
        public static DataTable getUserActivityList(String userID)
        {
            InstantiateBaseDataAccess();

            string sSQL = SQL.Q_GET_USER_ACTIVITY_LIST;

            SqlParameter[] arrParams = new SqlParameter[1];
            SqlCommand cmd;
            arrParams[0] = _dbHelper.getParameter("@userID", DbType.String, userID);
            cmd = _dbHelper.getCommand(sSQL, arrParams);
            return _dbManager.getResultset(cmd).Tables[0];
        }
        public static DataTable getUserAcountCount(String userID)
        {
            InstantiateBaseDataAccess();

            string sSQL = SQL.Q_GET_USER_ACCOUNT_COUNT;

            SqlParameter[] arrParams = new SqlParameter[1];
            SqlCommand cmd;
            arrParams[0] = _dbHelper.getParameter("@userID", DbType.String, userID);
            cmd = _dbHelper.getCommand(sSQL, arrParams);
            return _dbManager.getResultset(cmd).Tables[0];
        }
        public static DataTable getMessageInboxList(String userID)
        {
            InstantiateBaseDataAccess();

            string sSQL = SQL.Q_GET_MESSAGE_INBOX_LIST;

            SqlParameter[] arrParams = new SqlParameter[1];
            SqlCommand cmd;
            arrParams[0] = _dbHelper.getParameter("@userID", DbType.String, userID);
            cmd = _dbHelper.getCommand(sSQL, arrParams);
            return _dbManager.getResultset(cmd).Tables[0];
        }
        public static DataTable getMessageSentList(String userID)
        {
            InstantiateBaseDataAccess();

            string sSQL = SQL.Q_GET_MESSAGE_SENT_LIST;

            SqlParameter[] arrParams = new SqlParameter[1];
            SqlCommand cmd;
            arrParams[0] = _dbHelper.getParameter("@userID", DbType.String, userID);
            cmd = _dbHelper.getCommand(sSQL, arrParams);
            return _dbManager.getResultset(cmd).Tables[0];
        }
        public static DataTable getMessageSavedList(String userID)
        {
            InstantiateBaseDataAccess();

            string sSQL = SQL.Q_GET_MESSAGE_SAVED_LIST;

            SqlParameter[] arrParams = new SqlParameter[1];
            SqlCommand cmd;
            arrParams[0] = _dbHelper.getParameter("@userID", DbType.String, userID);
            cmd = _dbHelper.getCommand(sSQL, arrParams);
            return _dbManager.getResultset(cmd).Tables[0];
        }
        public static DataTable getMessageDetail(String messageID,int typeFlg)
        {
            InstantiateBaseDataAccess();

            string sSQL = "";

            switch (typeFlg)
            {
                case 1:
                    sSQL = SQL.Q_GET_INBOX_MESSAGE_DETAIL;
                    break;
                case 2:
                    sSQL = SQL.Q_GET_SENT_MESSAGE_DETAIL;
                    break;
                case 3:
                    sSQL = SQL.Q_GET_SAVED_MESSAGE_DETAIL;
                    break;
            }
            SqlParameter[] arrParams = new SqlParameter[1];
            SqlCommand cmd;
            arrParams[0] = _dbHelper.getParameter("@messageID", DbType.String, messageID);
            cmd = _dbHelper.getCommand(sSQL, arrParams);
            return _dbManager.getResultset(cmd).Tables[0];
        }
        public static DataTable getAdvertListByUserPreference(string qString)
        {

            InstantiateBaseDataAccess();

            string sSQL = SQL.Q_GET_ADVERT_LIST_BY_USER_REFERENCE;

            sSQL = @"SELECT DISTINCT ADVERT.ADVERT_ID,ADVERT_TITLE,SELLER_NAME,SELL_PRICE,AVG(REVIEW_RATING) AS ADVERT_RATING,COUNT(REVIEW.ADVERT_ID) AS REVIEW_COUNT,'http://192.168.69.1/AdvertiseService'+URL_STRING AS RESOURCE_URL,ADVERT.ADVERT_ACTIVE_FLG
                                            FROM ADVERT_ADVERTISEMENT_DETAIL ADVERT,ADVERT_ITEM_PRICE PRICE,ADVERT_USER_REVIEW REVIEW,ADVERT_PREVIEW_URL URL,ADVERT_SELLER_DETAIL SELLER
                                            WHERE REVIEW.ADVERT_ID= ADVERT.ADVERT_ID AND URL_ID='ad'+REVIEW.ADVERT_ID AND SELLER.SELLER_ID=ADVERT.SELLER_ID AND ADVERT.ADVERT_ID=PRICE.ADVERT_ID
                                            AND ADVERT.ADVERT_ID IN (" + qString + ")GROUP BY ADVERT.ADVERT_ID,ADVERT_TITLE,URL_STRING,SELL_PRICE,SELLER_NAME,ADVERT.ADVERT_ACTIVE_FLG";
            SqlParameter[] arrParams = new SqlParameter[1];
            SqlCommand cmd;
            arrParams[0] = _dbHelper.getParameter("@qString", DbType.String, qString);
            cmd = _dbHelper.getCommand(sSQL, arrParams);
            return _dbManager.getResultset(cmd).Tables[0];
        }
    }
    
    }
