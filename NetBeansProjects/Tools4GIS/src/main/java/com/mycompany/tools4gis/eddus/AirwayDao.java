package com.mycompany.tools4gis.eddus;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AirwayDao {
    private Connector connector;

    public AirwayDao(String url) {
        this.connector= new Connector(url);
    }

    public Fixpoint create(Airway a)throws Exception //Long id, String designator, String position, String remarks) 
    {
        Fixpoint p=null;
        ResultSet rs = null;
        /*try
        {*/
            /*String query = "INSERT INTO fixes ( designator, position, remarks) VALUES (?, ?, ?)";
            PreparedStatement statement = connector.getConnection().prepareStatement(query);//, Statement.RETURN_GENERATED_KEYS);
            
            statement.setString(1, a.getDesignator());
            statement.setString(2, a.getPosition());
            statement.setString(3, a.getRemarks());

            statement.executeUpdate();
            rs = statement.getGeneratedKeys();
            if (rs.next()) {
                long generatedId = rs.getLong(1);
                p = new Fixpoint(generatedId, a.getDesignator(), a.getPosition(), a.getRemarks());
            } */
            
            
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

    public Fixpoint getById(long id) throws SQLException {
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
    
    public Fixpoint getByDesignator(String des) throws SQLException {
        Fixpoint p=null;
        ResultSet rs = null;
        PreparedStatement statement=null;
        try
        {
        
            String query = "SELECT * FROM fixes WHERE designator = ?";
            statement = connector.getConnection().prepareStatement(query);
            statement.setString(1, des);
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

    public List<Airway> getAll() throws SQLException {
        
        ResultSet rs = null;
        PreparedStatement statement=null;
        List<Airway> airways = new ArrayList<>();
        //List<Fixpoint> fixPoints = new ArrayList<>();
        //Airway(long id, String name, List<Fixpoint> fixpoints)
        try
        {
            String query = "SELECT * FROM airwayPoints order by designator";
            statement = connector.getConnection().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                long id = 0;// resultSet.getLong("id");
                String designator = resultSet.getString("designator");
                String position = resultSet.getString("position");
                String remarks = resultSet.getString("remarks");
                //airways.add(new Airway(id, designator, position, remarks));
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
        return airways;
    }
}
