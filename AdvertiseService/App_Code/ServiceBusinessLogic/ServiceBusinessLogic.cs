using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using AdvertiseServiceDataAccess;
using System.Data;
using System.Globalization;
/// <summary>
/// Summary description for ServiceBusinessLogic
/// </summary>
/// 
namespace AdvertiseServiceBusinessLogic
{
    public class ServiceBusinessLogic : ServiceDataAccess
    {
        public ServiceBusinessLogic()
        {
            //
            // TODO: Add constructor logic here
            //
        }
        public static String authenticateLogin(string _userName, string _passWord)
        {


            DataTable dt = ServiceDataAccess.authenticateLogin(_userName, _passWord);
            if (dt.Rows.Count > 0)
            {
                return dt.Rows[0][0].ToString();
            }
            else
            {
                return "100";
            }

        }
        public static string[] getUserProfileDetail(string _userid)
        {

            DataTable dt = ServiceDataAccess.getUserProfileDetail(_userid);

            int count = 0, maxLength = dt.Columns.Count;
            string[] arrProfile = new string[maxLength];


            while (count < arrProfile.Length)
            {

                arrProfile[count] = dt.Rows[0][count].ToString();              
                count++;
            }

            return arrProfile;

        }


        public static Boolean updateUserProfileDetail(string _userid, int _columnIndex, string _columnVlaue)
        {
            try { 
            ServiceDataAccess.updateUserProfileDetail(_userid, _columnIndex ,_columnVlaue);
            return true;
            }
            catch (Exception ex) {
             return false;
            }
        
        }

        public static Boolean isUsernameExist(string _userName)
        {
            DataTable dt = ServiceDataAccess.isUsernameExist(_userName);
            if (dt.Rows.Count > 0)
            {
                return true;
            }
            else
            {

                return false;
            }
        }

        public static string  getNewuserUserID()
        {
           
            DataTable dt = ServiceDataAccess.getNewuserUserID();
            if (dt.Rows.Count > 0)
            {
                int val = Int16.Parse(dt.Rows[0][0].ToString());
                val++;
                return val.ToString().PadLeft(4, '0');
            }
            else
            {

                return "0001";
            }
        
        }
        public static Boolean registerNewUser( String _userID,String _FullName, String _emailID, String _address, String _city, String _state, String _country, String _language, String _mobileNo, String _homeNo,
             String _officeNo, String _homeLink, String _facebook, String _google, String _twitter, String _skype, String _username, String _password, String _userImage)
        {

           
            ServiceDataAccess.registerNewUser( _userID,  _FullName,  _emailID,  _address,  _city,  _state,  _country,  _language,  _mobileNo,  _homeNo,
              _officeNo,  _homeLink,  _facebook,  _google,  _twitter,  _skype,  _username,  _password,  _userImage);
            return true;
        }

        public static String getAdvertisementID(string refCode)
        {


            DataTable dt = ServiceDataAccess.getAdvertisementID(refCode);
            if (dt.Rows.Count > 0)
            {
                return dt.Rows[0][0].ToString();
            }
            else
            {

                return "error";
            }

        }
        public static List<List<String>> getAdvertSearchResult(string qString)
        {

            DataTable dt = ServiceDataAccess.getAdvertSearchResult(qString);
          
            int  
            columnLength = dt.Columns.Count,
            rowLength=dt.Rows.Count;
            
            List<List<String>> resultList=new List<List<string>>();
            List<String> rowList;

            if (dt.Rows.Count > 0)
            {
                for (int i = 0; i <= rowLength - 1; i++)
                {
                    rowList=new List<String>();
                    for (int j = 0; j <= columnLength - 1;j++ ) {
                                               
                        rowList.Add(dt.Rows[i][j].ToString());
                    }
                   
                    resultList.Add(rowList);
                 }
            }

            return resultList;
        }
        public static string[] getAdvertisementDetail(string _advertID)
        {

            DataTable dt = ServiceDataAccess.getAdvertisementDetail(_advertID);

            int count = 0, maxLength = dt.Columns.Count;
            string[] arrProfile = new string[maxLength];


            while (count < arrProfile.Length)
            {

                arrProfile[count] = dt.Rows[0][count].ToString();               
                count++;
            }

            return arrProfile;

        }
        public static string[] getAdvertisementRating(string _advertID)
        {

            DataTable dt = ServiceDataAccess.getAdvertisementRating(_advertID);

            int count = 0, maxLength = dt.Columns.Count;
            string[] arrProfile = new string[maxLength];


            while (count < arrProfile.Length)
            {

                arrProfile[count] = dt.Rows[0][count].ToString();
                count++;
            }

            return arrProfile;

        }
        public static string[] getSellerDetail(string _sellerID)
        {

            DataTable dt = ServiceDataAccess.getSellerDetail(_sellerID);

            int count = 0, maxLength = dt.Columns.Count;
            string[] arrProfile = new string[maxLength];

            DateTime dateTime = new DateTime();
            
            while (count < arrProfile.Length)
            {

                arrProfile[count] = dt.Rows[0][count].ToString();
                if(count==5){
                    dateTime = Convert.ToDateTime(dt.Rows[0][count].ToString());
                    arrProfile[count] = dateTime.Year.ToString();
                }
                count++;
            }

            return arrProfile;

        }
        public static List<List<String>> getResourceList(string advertID)
        {

            DataTable dt = ServiceDataAccess.getResourceList(advertID);

            int
            columnLength = dt.Columns.Count,
            rowLength = dt.Rows.Count;

            List<List<String>> resultList = new List<List<string>>();
            List<String> rowList;

            if (dt.Rows.Count > 0)
            {
                for (int i = 0; i <= rowLength - 1; i++)
                {
                    rowList = new List<String>();
                    for (int j = 0; j <= columnLength - 1; j++)
                    {

                        rowList.Add(dt.Rows[i][j].ToString());
                    }

                    resultList.Add(rowList);
                }
            }

            return resultList;
        }
        public static List<List<String>> getUserReviewList(string advertID)
        {

            DataTable dt = ServiceDataAccess.getUserReviewList(advertID);

            int
            columnLength = dt.Columns.Count,
            rowLength = dt.Rows.Count;

            List<List<String>> resultList = new List<List<string>>();
            List<String> rowList;
            string format = "MMM d, yyyy";
            DateTime dateTime=new DateTime();
           

            if (dt.Rows.Count > 0)
            {
                for (int i = 0; i <= rowLength - 1; i++)
                {
                    rowList = new List<String>();
                    for (int j = 0; j <= columnLength - 1; j++)
                    {
                        if (j == 3)
                        {
                            String date = dt.Rows[i][j].ToString();
                            //dateTime = DateTime.ParseExact(dt.Rows[i][j].ToString(), "MM/dd/yy HH:mm:ss tt", null);
                            dateTime = Convert.ToDateTime(date);
                            rowList.Add(dateTime.ToString(format));
                        }
                        else {
                            rowList.Add(dt.Rows[i][j].ToString());
                        }
                        
                       
                    }

                    resultList.Add(rowList);
                }
            }

            return resultList;
        }
        public static string[] getSellerReview(string _sellerID)
        {

            DataTable dt = ServiceDataAccess.getSellerReview(_sellerID);

            int count = 0, maxLength = dt.Columns.Count;
            string[] arrProfile = new string[maxLength];

            DateTime dateTime = new DateTime();

            while (count < arrProfile.Length)
            {

                arrProfile[count] = dt.Rows[0][count].ToString();              
                count++;
            }

            return arrProfile;

        }
        public static List<List<String>> getSellerFeedbackList(string sellerID)
        {

            DataTable dt = ServiceDataAccess.getSellerFeedbackList(sellerID);

            int
            columnLength = dt.Columns.Count,
            rowLength = dt.Rows.Count;

            List<List<String>> resultList = new List<List<string>>();
            List<String> rowList;
            string format = "MMM d, yyyy";
            DateTime dateTime = new DateTime();


            if (dt.Rows.Count > 0)
            {
                for (int i = 0; i <= rowLength - 1; i++)
                {
                    rowList = new List<String>();
                    for (int j = 0; j <= columnLength - 1; j++)
                    {
                        if (j == 3)
                        {
                            String date = dt.Rows[i][j].ToString();
                            //dateTime = DateTime.ParseExact(dt.Rows[i][j].ToString(), "MM/dd/yy HH:mm:ss tt", null);
                            dateTime = Convert.ToDateTime(date);
                            rowList.Add(dateTime.ToString(format));
                        }
                        else
                        {
                            rowList.Add(dt.Rows[i][j].ToString());
                        }


                    }

                    resultList.Add(rowList);
                }
            }

            return resultList;
        }
        public static List<List<String>> getDailyDealList(string userID)
        {

            DataTable dt = ServiceDataAccess.getDailyDealList(userID);

            int
            columnLength = dt.Columns.Count,
            rowLength = dt.Rows.Count;

            List<List<String>> resultList = new List<List<string>>();
            List<String> rowList;

            if (dt.Rows.Count > 0)
            {
                for (int i = 0; i <= rowLength - 1; i++)
                {
                    rowList = new List<String>();
                    for (int j = 0; j <= columnLength - 1; j++)
                    {

                        rowList.Add(dt.Rows[i][j].ToString());
                    }

                    resultList.Add(rowList);
                }
            }

            return resultList;
        }
        public static List<List<String>> getAdvertListBySeller(string sellerID)
        {

            DataTable dt = ServiceDataAccess.getAdvertListBySeller(sellerID);

            int
            columnLength = dt.Columns.Count,
            rowLength = dt.Rows.Count;

            List<List<String>> resultList = new List<List<string>>();
            List<String> rowList;

            if (dt.Rows.Count > 0)
            {
                for (int i = 0; i <= rowLength - 1; i++)
                {
                    rowList = new List<String>();
                    for (int j = 0; j <= columnLength - 1; j++)
                    {

                        rowList.Add(dt.Rows[i][j].ToString());
                    }

                    resultList.Add(rowList);
                }
            }

            return resultList;
        }
        public static string[] getDailyDealItem( )
        {

            DataTable dt = ServiceDataAccess.getDailyDealItem();

            int count = 0, maxLength = dt.Columns.Count;
            string[] arrProfile = new string[maxLength];

            DateTime dateTime = new DateTime();

            while (count < arrProfile.Length)
            {

                arrProfile[count] = dt.Rows[0][count].ToString();
                count++;
            }

            return arrProfile;

        }
        public static List<List<String>> getBestDealList()
        {

            DataTable dt = ServiceDataAccess.getBestDealList();

            int
            columnLength = dt.Columns.Count,
            rowLength = dt.Rows.Count;

            List<List<String>> resultList = new List<List<string>>();
            List<String> rowList;

            if (dt.Rows.Count > 0)
            {
                for (int i = 0; i <= rowLength - 1; i++)
                {
                    rowList = new List<String>();
                    for (int j = 0; j <= columnLength - 1; j++)
                    {

                        rowList.Add(dt.Rows[i][j].ToString());
                    }

                    resultList.Add(rowList);
                }
            }

            return resultList;
        }
        public static string[] getBestDealAdvertisementDetail(string _advertID)
        {

            DataTable dt = ServiceDataAccess.getBestDealAdvertisementDetail(_advertID);

            int count = 0, maxLength = dt.Columns.Count;
            string[] arrProfile = new string[maxLength];


            while (count < arrProfile.Length)
            {

                arrProfile[count] = dt.Rows[0][count].ToString();
                count++;
            }

            return arrProfile;

        }
        public static List<List<String>> getUserRecommendationList(String userID)
        {

            DataTable dt = ServiceDataAccess.getUserRecommendationList(userID);

            int
            columnLength = dt.Columns.Count,
            rowLength = dt.Rows.Count;

            List<List<String>> resultList = new List<List<string>>();
            List<String> rowList;

            if (dt.Rows.Count > 0)
            {
                for (int i = 0; i <= rowLength - 1; i++)
                {
                    rowList = new List<String>();
                    for (int j = 0; j <= columnLength - 1; j++)
                    {

                        rowList.Add(dt.Rows[i][j].ToString());
                    }

                    resultList.Add(rowList);
                }
            }

            return resultList;
        }
        public static List<List<String>> getUserConnectionList(String userID,String searchQuery)
        {

            DataTable dt = ServiceDataAccess.getUserConnectionList(userID, searchQuery);

            int
            columnLength = dt.Columns.Count,
            rowLength = dt.Rows.Count;

            List<List<String>> resultList = new List<List<string>>();
            List<String> rowList;

            if (dt.Rows.Count > 0)
            {
                for (int i = 0; i <= rowLength - 1; i++)
                {
                    rowList = new List<String>();
                    for (int j = 0; j <= columnLength - 1; j++)
                    {

                        rowList.Add(dt.Rows[i][j].ToString());
                    }

                    resultList.Add(rowList);
                }
            }

            return resultList;
        }
        public static List<List<String>> getConnectionRequestList(String userID)
        {

            DataTable dt = ServiceDataAccess.getConnectionRequestList(userID);

            int
            columnLength = dt.Columns.Count,
            rowLength = dt.Rows.Count;

            List<List<String>> resultList = new List<List<string>>();
            List<String> rowList;

            if (dt.Rows.Count > 0)
            {
                for (int i = 0; i <= rowLength - 1; i++)
                {
                    rowList = new List<String>();
                    for (int j = 0; j <= columnLength - 1; j++)
                    {

                        rowList.Add(dt.Rows[i][j].ToString());
                    }

                    resultList.Add(rowList);
                }
            }

            return resultList;
        }
        public static List<List<String>> getUserViewsList(string userID)
        {

            DataTable dt = ServiceDataAccess.getUserViewsList(userID);

            int
            columnLength = dt.Columns.Count,
            rowLength = dt.Rows.Count;

            List<List<String>> resultList = new List<List<string>>();
            List<String> rowList;
            string format = "MMM d, yyyy";
            DateTime dateTime = new DateTime();


            if (dt.Rows.Count > 0)
            {
                for (int i = 0; i <= rowLength - 1; i++)
                {
                    rowList = new List<String>();
                    for (int j = 0; j <= columnLength - 1; j++)
                    {
                        if (j == 3)
                        {
                            String date = dt.Rows[i][j].ToString();
                            //dateTime = DateTime.ParseExact(dt.Rows[i][j].ToString(), "MM/dd/yy HH:mm:ss tt", null);
                            dateTime = Convert.ToDateTime(date);
                            rowList.Add(dateTime.ToString(format));
                        }
                        else
                        {
                            rowList.Add(dt.Rows[i][j].ToString());
                        }


                    }

                    resultList.Add(rowList);
                }
            }

            return resultList;
        }
        public static List<List<String>> getUserActivityList(string userID)
        {

            DataTable dt = ServiceDataAccess.getUserActivityList(userID);

            int
            columnLength = dt.Columns.Count,
            rowLength = dt.Rows.Count;

            List<List<String>> resultList = new List<List<string>>();
            List<String> rowList;
            string format = "MMM d, yyyy";
            DateTime dateTime = new DateTime();


            if (dt.Rows.Count > 0)
            {
                for (int i = 0; i <= rowLength - 1; i++)
                {
                    rowList = new List<String>();
                    for (int j = 0; j <= columnLength - 1; j++)
                    {
                        if (j == 8)
                        {
                            String date = dt.Rows[i][j].ToString();
                            //dateTime = DateTime.ParseExact(dt.Rows[i][j].ToString(), "MM/dd/yy HH:mm:ss tt", null);
                            dateTime = Convert.ToDateTime(date);
                            rowList.Add(dateTime.ToString(format));
                        }else if(j==9){

                            String[] arrName = dt.Rows[i][j].ToString().Split(' ');
                            rowList.Add(arrName[0]);
                        }
                        else
                        {
                            rowList.Add(dt.Rows[i][j].ToString());
                        }


                    }

                    resultList.Add(rowList);
                }
            }

            return resultList;
        }
        public static string[] getUserAcountCount(string _userID)
        {

            DataTable dt = ServiceDataAccess.getUserAcountCount(_userID);

            int count = 0, maxLength = dt.Columns.Count;
            string[] arrProfile = new string[maxLength];


            while (count < arrProfile.Length)
            {

                arrProfile[count] = dt.Rows[0][count].ToString();
                count++;
            }

            return arrProfile;

        }
        public static List<List<String>> getMessageInboxList(string userID)
        {

            DataTable dt = ServiceDataAccess.getMessageInboxList(userID);

            int
            columnLength = dt.Columns.Count,
            rowLength = dt.Rows.Count;

            List<List<String>> resultList = new List<List<string>>();
            List<String> rowList;
            string format = "MMM d, yyyy H:mm";
            DateTime dateTime = new DateTime();


            if (dt.Rows.Count > 0)
            {
                for (int i = 0; i <= rowLength - 1; i++)
                {
                    rowList = new List<String>();
                    for (int j = 0; j <= columnLength - 1; j++)
                    {
                        if (j == 3)
                        {
                            String date = dt.Rows[i][j].ToString();                            
                           
                            dateTime = Convert.ToDateTime(date);
                            String[] arrDate = dateTime.ToString("f").Split(' ');

                            rowList.Add(dateTime.ToString(format) +" "+ arrDate[arrDate.Length-1]);
                        }
                        else
                        {
                            rowList.Add(dt.Rows[i][j].ToString());
                        }


                    }

                    resultList.Add(rowList);
                }
            }

            return resultList;
        }
        public static List<List<String>> getMessageSentList(string userID)
        {

            DataTable dt = ServiceDataAccess.getMessageSentList(userID);

            int
            columnLength = dt.Columns.Count,
            rowLength = dt.Rows.Count;

            List<List<String>> resultList = new List<List<string>>();
            List<String> rowList;
            string format = "MMM d, yyyy H:mm";
            DateTime dateTime = new DateTime();


            if (dt.Rows.Count > 0)
            {
                for (int i = 0; i <= rowLength - 1; i++)
                {
                    rowList = new List<String>();
                    for (int j = 0; j <= columnLength - 1; j++)
                    {
                        if (j == 3)
                        {
                            String date = dt.Rows[i][j].ToString();
                            //dateTime = DateTime.ParseExact(dt.Rows[i][j].ToString(), "MM/dd/yy HH:mm:ss tt", null);
                            dateTime = Convert.ToDateTime(date);
                            String[] arrDate = dateTime.ToString("f").Split(' ');

                            rowList.Add(dateTime.ToString(format) + " " + arrDate[arrDate.Length - 1]);
                        }
                        else
                        {
                            rowList.Add(dt.Rows[i][j].ToString());
                        }


                    }

                    resultList.Add(rowList);
                }
            }

            return resultList;
        }
        public static List<List<String>> getMessageSavedList(string userID)
        {

            DataTable dt = ServiceDataAccess.getMessageSavedList(userID);

            int
            columnLength = dt.Columns.Count,
            rowLength = dt.Rows.Count;

            List<List<String>> resultList = new List<List<string>>();
            List<String> rowList;
            string format = "MMM d, yyyy H:mm";
            DateTime dateTime = new DateTime();


            if (dt.Rows.Count > 0)
            {
                for (int i = 0; i <= rowLength - 1; i++)
                {
                    rowList = new List<String>();
                    for (int j = 0; j <= columnLength - 1; j++)
                    {
                        if (j == 3)
                        {
                            String date = dt.Rows[i][j].ToString();
                            //dateTime = DateTime.ParseExact(dt.Rows[i][j].ToString(), "MM/dd/yy HH:mm:ss tt", null);
                            dateTime = Convert.ToDateTime(date);
                            String[] arrDate = dateTime.ToString("f").Split(' ');

                            rowList.Add(dateTime.ToString(format) + " " + arrDate[arrDate.Length - 1]);
                        }
                        else
                        {
                            rowList.Add(dt.Rows[i][j].ToString());
                        }


                    }

                    resultList.Add(rowList);
                }
            }

            return resultList;
        }

        public static string[] getMessageDetail(string _messageID, string _typeFlg)
        {

            DataTable dt = ServiceDataAccess.getMessageDetail(_messageID,Int32.Parse(_typeFlg));

            int count = 0, maxLength = dt.Columns.Count;
            string[] arrProfile = new string[maxLength];

            string format = "MMM d, yyyy H:mm";
            DateTime dateTime = new DateTime();

            while (count < arrProfile.Length)
            {
                if (count == 3)
                {
                    String date = dt.Rows[0][count].ToString();

                    dateTime = Convert.ToDateTime(date);
                    String[] arrDate = dateTime.ToString("f").Split(' ');

                    arrProfile[count] = dateTime.ToString(format) + " " + arrDate[arrDate.Length - 1];
                }
                else
                {
                    arrProfile[count] = dt.Rows[0][count].ToString();
                }
               
                count++;
            }

            return arrProfile;

        }
        public static List<List<String>> getAdvertListByUserPreference(string qString)
        {

            DataTable dt = ServiceDataAccess.getAdvertListByUserPreference(qString);

            int
            columnLength = dt.Columns.Count,
            rowLength = dt.Rows.Count;

            List<List<String>> resultList = new List<List<string>>();
            List<String> rowList;

            if (dt.Rows.Count > 0)
            {
                for (int i = 0; i <= rowLength - 1; i++)
                {
                    rowList = new List<String>();
                    for (int j = 0; j <= columnLength - 1; j++)
                    {

                        rowList.Add(dt.Rows[i][j].ToString());
                    }

                    resultList.Add(rowList);
                }
            }

            return resultList;
        }
    }
}
    