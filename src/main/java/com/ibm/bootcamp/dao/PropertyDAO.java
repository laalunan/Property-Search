package com.ibm.bootcamp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ibm.bootcamp.entity.Property;
import com.mysql.cj.protocol.Resultset;

public class PropertyDAO {

	private DBConnectionFactory DBProperty = new DBConnectionProperties();

	public List<Property> searchProperty(String request, String request1, String request2){
		
		List<Property> propertylist = new ArrayList<Property>();
		
		Connection conn = DBProperty.getConnection();
		PreparedStatement pstmt = null;
		Statement stmt = null;
		
		
		
		String query = "SELECT * FROM properties.property where city = ? AND typeOfProperty = ? AND propertyClassification=? AND availabilityStatus = 'AVAILABLE' ORDER BY dateTime DESC, clickCount DESC;";
		String query1 = "SELECT * FROM properties.property WHERE availabilityStatus = 'AVAILABLE' ORDER BY dateTime DESC, clickCount DESC;";
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, request);
			pstmt.setString(2, request1);
			pstmt.setString(3, request2);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(!request.isEmpty())
			{
				System.out.println("hello");
				while (rs.next()) {
					
					Property p = new Property();
					
					p.setPropertyID(rs.getInt("propertyID"));
					p.setCity(rs.getString("city"));
					p.setTypeOfProperty(rs.getString("typeOfProperty"));
					p.setPropertyClassification(rs.getString("propertyClassification"));
					p.setSellingPrice(rs.getDouble("sellingPrice"));
					p.setBedroomCount(rs.getInt("bedroomCount"));
					p.setBathroomCount(rs.getInt("bathroomCount"));
					p.setAmenities(rs.getString("amenities"));
					p.setNoOfGarage(rs.getInt("noOfGarage"));
					p.setGarageSize(rs.getDouble("garageSize"));
					p.setYearBuilt(rs.getString("yearBuilt"));
					p.setBasement(rs.getInt("basement"));
					p.setBasementDescription(rs.getString("basementDescription"));
					p.setRoofingDescription(rs.getString("roofingDescription"));
					p.setAdditionalRemarks(rs.getString("additionalRemarks"));
					p.setAvailabilityStatus(rs.getString("availabilityStatus"));
					p.setNameOfDeveloper(rs.getString("nameOfDeveloper"));
					p.setNameOfProject(rs.getString("nameOfProject"));
					p.setDateTime(rs.getString("dateTime"));
					p.setAddress(rs.getString("address"));
					p.setCountry(rs.getString("country"));
					p.setZipCode(rs.getString("zipCode"));
					p.setClickCount(rs.getInt("clickCount"));
					p.setTotalArea(rs.getDouble("totalArea"));
					
					propertylist.add(p);
				}
			}
			else if(request.isEmpty()) {
				//print all
				System.out.println("must print pls!");
				Statement s = conn.createStatement();
				ResultSet myRs = s.executeQuery(query1);
			
				while(myRs.next()) {
					
					Property p = new Property();
					System.out.println("hi");
					p.setPropertyID(myRs.getInt("propertyID"));
					p.setCity(myRs.getString("city"));
					p.setTypeOfProperty(myRs.getString("typeOfProperty"));
					p.setPropertyClassification(myRs.getString("propertyClassification"));
					p.setSellingPrice(myRs.getDouble("sellingPrice"));
					p.setBedroomCount(myRs.getInt("bedroomCount"));
					p.setBathroomCount(myRs.getInt("bathroomCount"));
					p.setAmenities(myRs.getString("amenities"));
					p.setNoOfGarage(myRs.getInt("noOfGarage"));
					p.setGarageSize(myRs.getDouble("garageSize"));
					p.setYearBuilt(myRs.getString("yearBuilt"));
					p.setBasement(myRs.getInt("basement"));
					p.setBasementDescription(myRs.getString("basementDescription"));
					p.setRoofingDescription(myRs.getString("roofingDescription"));
					p.setAdditionalRemarks(myRs.getString("additionalRemarks"));
					p.setAvailabilityStatus(myRs.getString("availabilityStatus"));
					p.setNameOfDeveloper(myRs.getString("nameOfDeveloper"));
					p.setNameOfProject(myRs.getString("nameOfProject"));
					p.setDateTime(myRs.getString("dateTime"));
					p.setAddress(myRs.getString("address"));
					p.setCountry(myRs.getString("country"));
					p.setZipCode(myRs.getString("zipCode"));
					p.setClickCount(myRs.getInt("clickCount"));
					p.setTotalArea(myRs.getDouble("totalArea"));
					
					propertylist.add(p);
				}
				
			}
			
			
		} catch (Exception e) {
		}finally {
			DBProperty.closeConnection(conn, pstmt);
		}	
		
		return propertylist;
		
	}

	public List<Property> filterProperty(String frequest, int min, int max, String frequest2, String frequest3, String frequest4, String frequest5, String frequest6, int frequest7, String frequest8, String frequest9, int size){
		List<Property> propertyList = new ArrayList<Property>();
		List<String> amenities = new ArrayList<String>();
		Connection myConn = DBProperty.getConnection();
		PreparedStatement fpstmt = null;
		String type = "", bedroom = "", bathroom = "", noOfGarage ="",garageSize="",yearBuilt="",totalArea="";
		if (frequest.equals("Any")) {type = "typeOfProperty LIKE ";} else {type = "typeOfProperty = ";}
		if (frequest2.equals("")) {bedroom = "bedroomCount LIKE ";} else {bedroom = "bedroomCount = ";}
		if (frequest3.equals("")) {bathroom = "bathroomCount LIKE ";} else {bathroom = "bathroomCount = ";}
		if (frequest4.equals("")) {noOfGarage = "noOfGarage LIKE ";} else {noOfGarage = "noOfGarage = ";}
		if (frequest5.equals("")) {garageSize = "garageSize LIKE ";} else {garageSize = "garageSize = ";}
		if (frequest6.equals("")) {yearBuilt = "yearBuilt LIKE ";} else {yearBuilt = "yearBuilt = ";}
		if (frequest8.equals("")) {totalArea = "totalArea LIKE ";} else {totalArea = "totalArea = ";}
		System.out.println(frequest9);
		String filter = "SELECT * FROM properties.property WHERE " + type + "? AND sellingPrice >= ? OR sellingPrice <= ? AND "+bedroom+"? AND "+bathroom+" ? AND "+noOfGarage+" ?  AND "+garageSize+" ? AND "+yearBuilt+" ? AND basement = ? AND "+totalArea+" ? AND amenities LIKE ? ";
		
		
		for (int i=0; i < size-1; i++) {
			filter += "AND amenities LIKE ?"; 
		}
		
		filter += " ORDER BY dateTime DESC, clickCount DESC";
		try {
			fpstmt = myConn.prepareStatement(filter);
			if (frequest.equals("Any")) {
				fpstmt.setString(1, "%");
			} else {
				fpstmt.setString(1, frequest);
			}
			fpstmt.setInt(2, min);
			fpstmt.setInt(3, max);
			if (frequest2.equals("")) {	//bedroom
				fpstmt.setString(4, "%");
			} else {
				fpstmt.setString(4, frequest2);
			}
			//fpstmt.setInt(5, frequest3);
			if (frequest3.equals("")) { //bathroom
				fpstmt.setString(5, "%");
			} else {
				fpstmt.setString(5, frequest3);
			}
			//fpstmt.setDouble(6, frequest4); no of garage
			if (frequest4.equals("")) {
				fpstmt.setString(6, "%");
			} else {
				fpstmt.setString(6, frequest4);
			}
			//fpstmt.setString(7, frequest5);garageSize
			if (frequest5.equals("")) { 
				fpstmt.setString(7, "%");
			} else {
				fpstmt.setString(7, frequest5);
			}
			//fpstmt.setInt(8, frequest6); yearBuilt
			if (frequest6.equals("")) { 
				fpstmt.setString(8, "%");
			} else {
				fpstmt.setString(8, frequest6);
			}
			fpstmt.setInt(9, frequest7);
			//fpstmt.setDouble(10, frequest8);totalArea
			if (frequest8.equals("")) { 
				fpstmt.setString(10, "%");
			} else {
				fpstmt.setString(10, frequest8);
			}
			if (frequest9.contains("Attic")) {amenities.add("Attic");}
			if (frequest9.contains("Wine cellar")) {amenities.add("Wine cellar");}
			if (frequest9.contains("Fire Place")) {amenities.add("Fire Place");}
			if (frequest9.contains("Backyard")) {amenities.add("Backyard");}
			if (frequest9.contains("Sprinklers")) {amenities.add("Sprinklers");}
			if (frequest9.contains("Laundry")) {amenities.add("Laundry");}
			if (frequest9.contains("Private space")) {amenities.add("Private space");}
			if (frequest9.contains("Gas heat")) {amenities.add("Gas heat");}
			if (frequest9.contains("Basketball court")) {amenities.add("Basketball court");}
			if (frequest9.contains("Lake view")) {amenities.add("Lake view");}
			if (frequest9.contains("Front yard")) {amenities.add("Front yard");}
			if (frequest9.contains("Washer and dryer")) {amenities.add("Washer and dryer");}
			if (frequest9.contains("Concierge")) {amenities.add("Concierge");}
			if (frequest9.contains("Storage")) {amenities.add("Storage");}
			if (frequest9.contains("Ocean view")) {amenities.add("Ocean view");}
			if (frequest9.contains("Gym")) {amenities.add("Gym");}
			if (frequest9.contains("Pet pound")) {amenities.add("Pet pound");}
			if (frequest9.contains("Pool")) {amenities.add("Pool");}
			if (frequest9.contains("Fenced yard")) {amenities.add("Fenced yard");}
			if (frequest9.contains("Deck")) {amenities.add("Deck");}
			if (frequest9.contains("Balcony")) {amenities.add("Balcony");}
			if (frequest9.contains("Doorman")) {amenities.add("Doorman");}
			if (frequest9.contains("Recreation area")) {amenities.add("Recreation area");}
			int j =0;
			if (size > 0) {
			for (int i=11; i < amenities.size()+11; i++) {
				fpstmt.setString(i, "%" + amenities.get(j) + "%");
				j++;
			}
			}
			if (size==0) {
				fpstmt.setString(11, "%");
			}
			ResultSet rs = fpstmt.executeQuery();
			
			while (rs.next()) {
				Property p = new Property();
				p.setTypeOfProperty(rs.getString("typeOfProperty"));
				p.setSellingPrice(rs.getDouble("sellingPrice"));
				p.setAmenities(rs.getString("amenities"));
				p.setBedroomCount(rs.getInt("bedroomCount"));
				p.setBathroomCount(rs.getInt("bathroomCount"));
				p.setNoOfGarage(rs.getInt("noOfGarage"));
				p.setGarageSize(rs.getInt("garageSize"));
				p.setYearBuilt(rs.getString("yearBuilt"));
				p.setBasement(rs.getInt("basement"));
				p.setTotalArea(rs.getDouble("totalArea"));

				propertyList.add(p);
			}
	
		} catch (SQLException e) {
			Logger.getLogger(PropertyDAO.class.getName()).log(Level.SEVERE, null, e);
			e.printStackTrace();
		} finally {
			DBProperty.closeConnection(myConn, fpstmt);
		}

		return propertyList; 
	}	
	
	public List<Property> compareProperty(int propertyID, int propertyID2) {
		Connection conn = DBProperty.getConnection();
		PreparedStatement pstmt = null;
		String query = "SELECT * FROM property WHERE propertyID = ? OR propertyID = ?";
		
		ResultSet rs = null;
		List<Property> two = new ArrayList<Property>();
		try {

			if (conn != null) {
				int i = 1;

				pstmt = conn.prepareStatement(query);
				pstmt.setInt(i++, propertyID);
				pstmt.setInt(i++, propertyID2);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					Property p = new Property();
					p.setPropertyID(rs.getInt("propertyID"));
					p.setTypeOfProperty(rs.getString("typeOfProperty"));
					p.setSellingPrice(rs.getDouble("sellingPrice"));
					p.setPropertyClassification(rs.getString("propertyClassification"));
					p.setTotalArea(rs.getDouble("totalArea"));
					p.setBedroomCount(rs.getInt("bedroomCount"));
					p.setBathroomCount(rs.getInt("bathroomCount"));
					p.setAmenities(rs.getString("amenities"));
					p.setNoOfGarage(rs.getInt("noOfGarage"));
					p.setGarageSize(rs.getDouble("garageSize"));
					p.setYearBuilt(rs.getString("yearBuilt"));
					p.setBasement(rs.getInt("basement"));
					p.setBasementDescription(rs.getString("basementDescription"));
					p.setRoofingDescription(rs.getString("roofingDescription"));
					p.setAdditionalRemarks(rs.getString("additionalRemarks"));
					p.setAvailabilityStatus(rs.getString("availabilityStatus"));
					p.setNameOfDeveloper(rs.getString("nameOfDeveloper"));
					p.setNameOfProject(rs.getString("nameOfProject"));
					p.setUserID(rs.getInt("userID"));
					p.setDateTime(rs.getString("dateTime"));
					p.setAddress(rs.getString("address"));
					p.setCity(rs.getString("city"));
					p.setCountry(rs.getString("country"));
					p.setZipCode(rs.getString("zipCode"));
					p.setClickCount(rs.getInt("clickCount"));
					two.add(p);
				}

				query = "UPDATE property SET clickCount = (SELECT clickCount WHERE propertyID = ?)+1 WHERE propertyID = ?";
				String query1 = "UPDATE property SET clickCount = (SELECT clickCount WHERE propertyID = ?)+1 WHERE propertyID = ?";

				int j = 1;
				int k = 1;
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(j++, propertyID);
				pstmt.setInt(j++, propertyID);
				int s = pstmt.executeUpdate();
				
				pstmt = conn.prepareStatement(query1);
				pstmt.setInt(k++, propertyID);
				pstmt.setInt(k++, propertyID);
				int t = pstmt.executeUpdate();
			}

		} catch (SQLException ex) {
			Logger.getLogger(PropertyDAO.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			DBProperty.closeConnection(conn, pstmt);
		}
		return two;
	}
}
