package store;

import com.mysql.cj.xdevapi.Collection;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class myDatabse{
    private final String database = "c_cs108_psyoung";
    private Statement statement;
    public ArrayList<Product> products;
    public myDatabse() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/c_cs108_psyoung");
        dataSource.setUsername("root");
        dataSource.setPassword("babajana111");
        Connection connection= null;
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            statement.executeQuery("USE c_cs108_psyoung;");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public ArrayList<Product> getProducts() {
        try {
            ArrayList<Product> result = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery("select* from products");
            while (resultSet.next()) {
                result.add(new Product(resultSet.getString("productid"), resultSet.getString("name"), resultSet.getString("imagefile"), resultSet.getDouble("price")));
            }
            return result;
        }catch (Exception ignored){}

        return null;
    }
}
