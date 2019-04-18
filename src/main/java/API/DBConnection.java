package API;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DBConnection {
    public Connection openConnection() {
        Connection conexion = null;
        try {
            //use jdbc driver to get conneciont from the mariadb with user admin and pass 1234
            conexion = DriverManager.getConnection("jdbc:mysql://localhost", "admin", "1234");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conexion;
    }

    public void closeConnection(Connection conexion) throws SQLException {
        conexion.close();
    }
    //function for get all gaste from database
    public String getGaste(Connection conexion) throws SQLException {
        //use your database name here
        conexion.createStatement().executeQuery("use gastos");
        //select from table "gastos"
        ResultSet rs = conexion.createStatement().executeQuery("select * from gastos");
        //since this line we only put format in the response data
        //you can format here or in your app
        String response = "Establecimiento\tGasto\tFecha\n";
        while (rs.next()) {
            if (rs.getInt(2) == 1) {
                response += rs.getString(4) + "\t" + (float) rs.getInt(3) + "€\t" + rs.getString(5) + "\n";
            } else {
                float temp = (float) rs.getInt(3);
                response += rs.getString(4) + ":" + -temp + "\n";
            }
        }
        response = response.substring(0, response.length() - 1);

        return response;
    }
    //function for get the last id in the table
    public Integer getId(Connection conexion) throws SQLException {
        //use your database name here
        conexion.createStatement().executeQuery("use gastos");
        //select from table "gastos"
        ResultSet rs = conexion.createStatement().executeQuery("select * from gastos");
        int i = 0;
        while (rs.next()) {
            i = rs.getInt(1);
        }
        return i + 1;
    }
    //function to add a gaste to the database
    public void addGaste(Connection conexion, String shop, String ingress, String value) throws SQLException {
        ResultSet rs = null;
        Float f = Float.parseFloat(value);
        //use your database name here
        conexion.createStatement().executeQuery("use gastos");
        Date date = new Date();
        if (ingress.compareTo("1") == 0) {
            //add a new gaste to the "gastos" table
            String query = "INSERT INTO gastos VALUES (" + getId(conexion) + ",1," + f + ",'" + shop + "','" + new SimpleDateFormat("yyyy-MM-dd").format(date) + "')";
            rs = conexion.createStatement().executeQuery(query);
        } else {
            //add a new ingress to the "gastos" table
            String query = "INSERT INTO gastos VALUES (2," + f + ",'" + shop + "','" + new SimpleDateFormat("yyyy-MM-dd").format(date) + "')";
            rs = conexion.createStatement().executeQuery(query);
        }
    }
}
