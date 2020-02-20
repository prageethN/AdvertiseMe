using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Data.SqlClient;
using System.Data;
using System.Configuration;

namespace AdvertiseServiceDataAccess
{
    public class BaseDataAccess
           
    {
        public  static SqlConnection Connection;
        public static DBManager _dbManager;
        public static DBHelper _dbHelper ;
        public static SQLStorage SQL;
      
        public BaseDataAccess() {
           
            setConnection();
            if (_dbManager == null) {
                _dbManager = new DBManager();
            }
            if (_dbHelper==null) {

                _dbHelper = new DBHelper();
            }
           if (SQL==null){
               SQL = new SQLStorage();
           }
           
            
        }

        private static void setConnection()
        { // Function which creates the Connection object

            if (Connection == null)
            {
                Connection = new SqlConnection();
                string s = ConfigurationManager.AppSettings.Get("test");
                //Connection.ConnectionString = ConfigurationManager.AppSettings.Get("connectionString");
                Connection.ConnectionString = "Persist Security Info=False;User ID=prageeth;Password=prageeth;Initial Catalog=ADVERTISEME_DB;Server=Prageeth-PC";

            }

        }

        public static SqlConnection getConnection()
        { //  Function to return the conncetion to the DB

            setConnection();
            if (Connection.State != ConnectionState.Open)
            {
                Connection.Open();
            }
            return Connection;
        }

        
    }
}
