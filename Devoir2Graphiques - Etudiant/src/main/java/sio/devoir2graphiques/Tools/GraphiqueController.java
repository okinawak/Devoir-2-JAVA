package sio.devoir2graphiques.Tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class GraphiqueController
{
    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;

    public GraphiqueController() {
        cnx = ConnexionBDD.getCnx();
    }
    // A vous de jouer
    public HashMap<Integer, Double> GetDataGraphique1(){
        HashMap<Integer, Double> data = new HashMap();
        try {
            cnx = ConnexionBDD.getCnx();
            ps = cnx.prepareStatement("SELECT ageEmp, AVG(salaireEmp)\n" +
                    "FROM employe\n" +
                    "GROUP BY ageEmp\n" +
                    "ORDER BY ageEmp ASC");
            rs = ps.executeQuery();
            while(rs.next())
            {
                data.put(rs.getInt(1), rs.getDouble(2));
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return data;
}
    public HashMap<Integer, ArrayList> GetDataGraphique2(){
        HashMap<Integer, ArrayList> data = new HashMap();
        try {
            cnx = ConnexionBDD.getCnx();
            ps = cnx.prepareStatement("SELECT ageEmp, sexe, AVG(salaireEmp)As salaire\n" +
                    "FROM employe\n" +
                    "GROUP BY ageEmp, sexe\n" +
                    "ORDER BY ageEmp ASC");
            rs = ps.executeQuery();
            while(rs.next())
            {
                if(!data.containsKey(rs.getInt("ageEmp")))
                {
                    ArrayList<String> lesEmployes = new ArrayList<>();
                    lesEmployes.add(rs.getString("sexe"));
                    lesEmployes.add(rs.getString("salaire"));
                    data.put(rs.getInt("ageEmp"),lesEmployes);
                }
                else
                {
                    data.get(rs.getInt("ageEmp")).add(rs.getString("sexe"));
                    data.get(rs.getInt("ageEmp")).add(rs.getString("salaire"));
                }

            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return data;
    }
    public HashMap<String, Double> GetDataGraphique3(){
        HashMap<String, Double> data = new HashMap();
        try {
            cnx = ConnexionBDD.getCnx();
            ps = cnx.prepareStatement("SELECT sexe, COUNT(employe.numEmp) * 100.0 / (SELECT COUNT(employe.numEmp) FROM employe) AS pourcentage\n"+
                    "FROM employe\n"+
                    "GROUP BY sexe\n");
            rs = ps.executeQuery();
            while(rs.next())
            {
                data.put(rs.getString(1), rs.getDouble(2));
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return data;
}


}
