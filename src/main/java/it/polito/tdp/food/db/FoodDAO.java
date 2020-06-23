package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Arco;
import it.polito.tdp.food.model.Condiment;
import it.polito.tdp.food.model.Condimento;

public class FoodDAO {

	public List<Food> listAllFood(){
		String sql = "SELECT * FROM food" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Food> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Food(res.getInt("food_id"),
							res.getInt("food_code"),
							res.getString("display_name"), 
							res.getInt("portion_default"), 
							res.getDouble("portion_amount"),
							res.getString("portion_display_name"),
							res.getDouble("calories")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Condiment> listAllCondiment(){
		String sql = "SELECT * FROM condiment" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Condiment> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Condiment(res.getInt("condiment_id"),
							res.getInt("food_code"),
							res.getString("display_name"), 
							res.getString("condiment_portion_size"), 
							res.getDouble("condiment_calories")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}

	}

	public List<Condimento> getVertici(Double calorie, Map<Integer, Condimento> idMap) {
		String sql = "select food_code, condiment_calories " + 
				"from condiment " + 
				"where condiment_calories<? ";
		List<Condimento> vertici = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			
			st.setDouble(1, calorie);
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
					
					Condimento c = new Condimento(res.getInt("food_code"),
							res.getDouble("condiment_calories"));
					vertici.add(c);
					idMap.put(c.getCodice(), c);
				
			}
			
			conn.close();
			return vertici ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Arco> archi(Map<Integer, Condimento> idMap){
		String sql = "select f1.`condiment_food_code` as c1, f2.`condiment_food_code` as c2, count(distinct f1.`food_code`) as peso " + 
				"from food_condiment f1, food_condiment f2 " + 
				"where f1.`condiment_food_code`< f2.`condiment_food_code` " + 
				"and f1.`food_code` = f2.`food_code` " + 
				"group by f1.`condiment_food_code`, f2.`condiment_food_code`";
		
		List<Arco> archi = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
					
					Condimento c1 = idMap.get(res.getInt("c1"));
					Condimento c2 = idMap.get(res.getInt("c2"));
					
					
					if(c1!=null && c2!=null) {
						archi.add(new Arco(c1, c2, res.getInt("peso")));
					}
				
			}
			
			conn.close();
			
			return archi ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	
}

