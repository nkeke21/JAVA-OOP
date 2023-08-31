import junit.framework.TestCase;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

public class testDatabase extends TestCase {
    public void testSearch(){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/metropolises");
        dataSource.setUsername("root");
        dataSource.setPassword("*****");
        dataTableModel model = new dataTableModel(dataSource);

        List<myData> lst1 = model.search("Mumbai", "Asia", "2000000", false, true);
        model.setData(lst1);
        int getColumnCount1 = model.getColumnCount();
        int getRowCount1 = model.getRowCount();

        assertEquals(3, getColumnCount1);
        assertEquals(1, getRowCount1);

        List<myData> lst2 = model.search("Tb", "Euro", "2000000", true, false);
        model.setData(lst2);
        int getColumnCount = model.getColumnCount();
        int getRowCount = model.getRowCount();
        String columnName1 = model.getColumnName(0);
        String columnName2 = model.getColumnName(1);
        String columnName3 = model.getColumnName(2);

        assertEquals(3, getColumnCount);
        assertEquals(0, getRowCount);

        assertEquals("Metropolis", columnName1);
        assertEquals("Continent", columnName2);
        assertEquals("Population", columnName3);
    }

    public void testAdd() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/metropolises");
        dataSource.setUsername("root");
        dataSource.setPassword("******");
        dataTableModel model = new dataTableModel(dataSource);

        String city1 = "Shanghai";
        String city2 = "Delhi";
        String continent1 = "Asia";
        String continent2 = "Asia";
        int population1 = 24870895;
        int population2 = 16753235;

        myData data1 = new myData();
        myData data2 = new myData();
        data1.setMetropolis(city1);
        data2.setMetropolis(city2);
        data1.setContinent(continent1);
        data2.setContinent(continent2);
        data1.setPopulation(population1);
        data2.setPopulation(population2);

        model.addData(data1);
        model.addData(data2);

        List<myData> lst = model.search(data1.getMetropolis(), data1.getContinent(), Integer.toString(data1.getPopulation()), false, true);

        if(lst.size() > 0){
            myData inList = lst.get(0);
            assertEquals(inList.getMetropolis(), data1.getMetropolis());
            assertEquals(inList.getContinent(), data1.getContinent());
            assertEquals(inList.getPopulation(), data1.getPopulation());
        }

        Object obj = model.getValueAt(0, 0);
        Object obj1 = model.getValueAt(0, 1);
        Object obj2 = model.getValueAt(0, 2);
        assertEquals((String)obj, data1.getMetropolis());
        assertEquals((String)obj1, data1.getContinent());
        assertEquals(obj2, data1.getPopulation());

        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            String query1 = "delete from metropolises where metropolis = \"" + city1 + "\" ;";
            String query2 = "delete from metropolises where metropolis = \"" + city2 + "\" ;";
            statement.executeUpdate(query1);
            statement.executeUpdate(query2);
            dataSource.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
