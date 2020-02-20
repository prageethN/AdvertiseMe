using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Data;
using System.Data.SqlClient;

namespace AdvertiseServiceDataAccess
{

     
    public class DBManager 
    {
        SqlConnection con = BaseDataAccess.Connection;

        public DataSet getResultset(SqlCommand cmd)
        {
            SqlDataAdapter adp_Common;
            DataSet dataSet;
            dataSet = new DataSet();
            adp_Common = new SqlDataAdapter(cmd);
            adp_Common.Fill(dataSet);

            return dataSet;
        }

        public void executeQuery(SqlCommand cmd){
           try{
               con = BaseDataAccess.getConnection();
               cmd.Connection = con;
               cmd.ExecuteNonQuery();
               con.Close();           
           }catch(Exception ex){
               int x = 0;
           }

        
        } 
    }
}
