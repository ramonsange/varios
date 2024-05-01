package com.mycompany.eddusmanager.dao;


import com.mycompany.eddusmanager.entities.Fixpoint;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import net.ucanaccess.jdbc.UcanaccessSQLException;

public class FixpointDao {
    private Connector connector;

    public FixpointDao() {
        this.connector= new Connector();
    }

    public Fixpoint create(Fixpoint f)throws Exception //Long id, String designator, String position, String remarks) 
    {
        Fixpoint p=null;
        ResultSet rs = null;
        /*try
        {*/
            String query = "INSERT INTO fixes ( designator, position, remarks) VALUES (?, ?, ?)";
            PreparedStatement statement = connector.getConnection().prepareStatement(query);//, Statement.RETURN_GENERATED_KEYS);
            
            statement.setString(1, f.getDesignator());
            statement.setString(2, f.getPosition());
            statement.setString(3, f.getRemarks());

            statement.executeUpdate();
            rs = statement.getGeneratedKeys();
            if (rs.next()) {
                long generatedId = rs.getLong(1);
                p = new Fixpoint(generatedId, f.getDesignator(), f.getPosition(), f.getRemarks());
            } 
            
            
        //}
        /*catch(Exception ex)
        {
            ex.printStackTrace();
        }*/
        /*finally
        {*/
            if(rs!=null){rs.close();}
            if(this.connector.getConnection()!=null){this.connector.closeConnection();}
        //}
        return p;
    }

    public Fixpoint read(long id) throws SQLException {
        Fixpoint p=null;
        ResultSet rs = null;
        PreparedStatement statement=null;
        try
        {
        
            String query = "SELECT * FROM fixes WHERE id = ?";
            statement = connector.getConnection().prepareStatement(query);
            statement.setLong(1, id);
            rs = statement.executeQuery();
            while(rs.next()) 
            {
                long fixPointId = rs.getLong("id");
                String designator = rs.getString("designator");
                String position = rs.getString("position");
                String remarks = rs.getString("remarks");
                p=  new Fixpoint(fixPointId, designator, position, remarks);
            } 
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            if(rs!=null){rs.close();}
            if(statement!=null){statement.close();}
            if(this.connector.getConnection()!=null){this.connector.closeConnection();}
            
        }
        return p;
    }

    public void update(Fixpoint fixPoint)throws SQLException{
        PreparedStatement statement =null;
        try
        {
            String query = "UPDATE fixes SET id = ?, designator = ?, position= ? , remarks= ? WHERE id = ?";
            statement = connector.getConnection().prepareStatement(query);
            statement.setLong(1, fixPoint.getId());
            statement.setString(2, fixPoint.getDesignator());
            statement.setString(3, fixPoint.getPosition());
            statement.setString(4, fixPoint.getRemarks());
            statement.executeUpdate();
            statement.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            if(statement!=null){statement.close();}
            if(this.connector.getConnection()!=null){this.connector.closeConnection();}
        }
        
    }

    public void delete(long id) throws SQLException {
        PreparedStatement statement=null;
        try
        {
            String query = "DELETE FROM fixes WHERE id = ?";
        
            statement = connector.getConnection().prepareStatement(query);
            statement.setLong(1, id);
            statement.executeUpdate();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            if(statement!=null){statement.close();}
            if(this.connector.getConnection()!=null){this.connector.closeConnection();}
        }
    }

    public List<Fixpoint> list() throws SQLException {
        
        ResultSet rs = null;
        PreparedStatement statement=null;
        List<Fixpoint> fixPoints = new ArrayList<>();
        try
        {
            String query = "SELECT * FROM fixes order by designator";
            statement = connector.getConnection().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                long id = 0;// resultSet.getLong("id");
                String designator = resultSet.getString("designator");
                String position = resultSet.getString("position");
                String remarks = resultSet.getString("remarks");
                fixPoints.add(new Fixpoint(id, designator, position, remarks));
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            if(rs!=null){rs.close();}
            if(statement!=null){statement.close();}
            if(this.connector.getConnection()!=null){this.connector.closeConnection();}
        }
        return fixPoints;
    }
}
