public class myData {
    private String Metropolis;
    private String Continent;
    private int Population;

    public myData(){}
    public void setMetropolis(String metropolis){
        Metropolis = metropolis;
    }
    public void setContinent(String continent){
        Continent = continent;
    }
    public void setPopulation(int population){
        Population = population;
    }
    public String getMetropolis(){
        return Metropolis;
    }
    public String getContinent(){
        return Continent;
    }
    public int getPopulation(){
        return Population;
    }
}
