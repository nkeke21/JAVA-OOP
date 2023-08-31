import jdk.jfr.consumer.MetadataEvent;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.swing.table.AbstractTableModel;
import java.net.ConnectException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class dataTableModel extends AbstractTableModel {
    /* Columns' Names*/
    String[] header = {"Metropolis", "Continent", "Population"};
    private final BasicDataSource dataSource;

    /* Current data that is displayed on screen*/
    List<myData> data;

    public dataTableModel(BasicDataSource dataSource){
        data = new ArrayList<>();
        this.dataSource = dataSource;
    }
    /* Tell JTable to update table */
    public void setData(List<myData> data) {
        this.data = data;
        this.fireTableDataChanged();
    }
    @Override
    public String getColumnName(int column) {
        return header[column];
    }
    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return header.length;
    }

    @Override
    public Object getValueAt(int i, int j) {
        myData data1 = data.get(i);
        return switch (j) {
            case 0 -> data1.getMetropolis();
            case 1 -> data1.getContinent();
            case 2 -> data1.getPopulation();
            default -> throw new RuntimeException("Illegal column: " + j);
        };
    }
    /* Adding element in the database and bringing it to the sqreen */
    public List<myData> addData(myData newData){
        data.clear();
        try {
            /* Insert element in the database */
            Connection connection = dataSource.getConnection();
            String insert = "INSERT INTO metropolises VALUES (?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(insert);
            statement.setString(1, newData.getMetropolis());
            statement.setString(2, newData.getContinent());
            statement.setInt(3, newData.getPopulation());
            statement.executeUpdate();

            /* Bringing the added element to the sqreen */
            String select =  "select * from metropolises where metropolis = ? and  continent = ? and population = ?";
            PreparedStatement statement1 = connection.prepareStatement(select);
            statement1.setString(1, newData.getMetropolis());
            statement1.setString(2, newData.getContinent());
            statement1.setInt(3, newData.getPopulation());
            ResultSet resultSet1 = statement1.executeQuery();
            data = createData(resultSet1);
            fireTableDataChanged();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return data;
    }
    /* Searching element */
    public List<myData> search(String metropolis, String continent, String population, boolean smaller, boolean exactMatch){
        data.clear();
        try {
            Connection connection = dataSource.getConnection();
            String result = "select * from metropolises";
            String popOperation = ""; // Operation: < or >= for the population comapre
            String matOperation = ""; // Operation: like or = for the metropolis and continent comparison
            String metr_S = "";
            String cont_S = "";

            /* Detect the compare operation for population*/
            if(smaller) popOperation = " < ";
            else popOperation = " >= ";
            /* Detect the compare operation for metropolis and region */
            if(exactMatch) {
                matOperation = " = ";
                metr_S = "\"" + metropolis + "\"";
                cont_S = "\"" + continent + "\"";
            } else{
                matOperation = "like ";
                metr_S = "\"%" + metropolis + "%\"";
                cont_S = "\"%" + continent + "%\"";
            }
            /* Checking which textField is empty and which is not */
            boolean metr = !metropolis.isEmpty();
            boolean cont = !continent.isEmpty();
            boolean popu = !population.isEmpty();
            /* Creating the sql query */
            if(metr || cont || popu){
                result += " where ";

                if(metr) result += "metropolis " +  matOperation + metr_S;
                if(metr && cont) result += " and ";
                if(cont) result += "continent " + matOperation + cont_S;
                if((metr || cont) && popu) result += " and ";
                if(popu) result += "population" + popOperation + population;
            }
            result += ";";
            System.out.println(result);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(result);
            data = createData(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return data;
    }

    /* Return the list of myData for specific ResultSet */
    private List<myData> createData(ResultSet resultSet) throws SQLException {
        List<myData> lst = new ArrayList<>();
        while (resultSet.next()){
            myData info = new myData();
            info.setMetropolis(resultSet.getString("metropolis"));
            info.setContinent(resultSet.getString("continent"));
            info.setPopulation(resultSet.getInt("population"));
            lst.add(info);
        }
        return lst;
    }
}
