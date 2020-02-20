using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Data.SqlClient;
using System.Data;
namespace AdvertiseServiceDataAccess
{
   public  class DBHelper 
    {

        SqlConnection con = BaseDataAccess.Connection;
        public SqlParameter getParameter(string paraName,DbType paraType, object paraValue)
        {


            SqlParameter param = new SqlParameter();
            param.ParameterName = paraName;
            param.DbType = paraType;
            param.Value = paraValue;
            return param;

        }

        public SqlCommand getCommand(string sql, SqlParameter[] values)
        {
            int i;
            SqlCommand command = new SqlCommand();
            command.Connection = con;
            command.CommandText = sql;
            if (!(values==null)) {
                for (i = 0; i < values.Length; i++)
                {
                    command.Parameters.Add(values[i]);
                }
            }
           
            return command;
        }
    }
      
}
