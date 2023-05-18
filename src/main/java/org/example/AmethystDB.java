package org.example;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Random;
import java.util.Set;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AmethystDB {

	LinkedHashMap<String, ArrayList<Object>> database = new LinkedHashMap<>();
	private final String storedObject;
	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";

	public AmethystDB(String storedObject) throws ClassNotFoundException {

		this.storedObject = storedObject;

		Class<?> clazz = Class.forName( storedObject );
		Field[] fields = clazz.getDeclaredFields();

		for ( Field field : fields) {
			this.database.put(field.getName(), new ArrayList<Object>());
		}
		this.database.put("objectHash", new ArrayList<>());
	}

	public static String generateRandomString(int length) {
		Random random = new Random();
		StringBuilder sb = new StringBuilder(length);

		for (int i = 0; i < length; i++) {
			int randomIndex = random.nextInt(CHARACTERS.length());
			char randomChar = CHARACTERS.charAt(randomIndex);
			sb.append(randomChar);
		}

		return sb.toString();
	}

	public void addRecord(Object object) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		Class<?> clazz = Class.forName( storedObject );
		Method[] methods = clazz.getDeclaredMethods();

		for(Method method: methods){
			String methodName = method.getName().toLowerCase().replaceAll( "get", "");
			if(this.database.containsKey(methodName)){
				Method selectedMethod = object.getClass().getMethod( "get" + methodName.substring(0, 1).toUpperCase() + methodName.substring(1));
				this.database.get( methodName ).add( selectedMethod.invoke( object ) );
			}
		}
		this.database.get( "objectHash" ).add( generateRandomString( 8 ) );
	}

	public void removeRecord(String row, Object value){
		ArrayList<Object> recordList = new ArrayList<>();
		int indexOfValue = this.database.get( row ).indexOf( value );

		System.out.println(indexOfValue);
		for(String dbRow: this.database.keySet()){
			recordList.add(this.database.get( dbRow ).remove( indexOfValue ));
		}
	}

	public void updateRecord(String row, Object value, Object newValue){
		ArrayList<Object> recordList = new ArrayList<>();
		int indexOfValue = this.database.get( row ).indexOf( value );
		this.database.get( row ).set( indexOfValue,  newValue);

	}

	public ArrayList<Object> getRecord( String row, String value){

		ArrayList<Object> recordList = new ArrayList<>();
		int indexOfValue = this.database.get( row ).indexOf( value );

		for(String dbRow: this.database.keySet()){
			recordList.add(this.database.get( dbRow ).get( indexOfValue ));
		}

		return recordList;
	}

	public void showBase(){
		System.out.println(this.database);
	}

	public void saveBase(String filepath){
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(new File(filepath), this.database);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadBase(String filepath){
		File file = new File(filepath);

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			TypeReference<LinkedHashMap<String, ArrayList<Object>>> typeReference =
					new TypeReference<LinkedHashMap<String, ArrayList<Object>>>() {};

			LinkedHashMap<String, ArrayList<Object>> data =
					objectMapper.readValue(file, typeReference);


			this.database.clear();
			// Access and use the data
			for (String key : data.keySet()) {
				this.database.put( key, data.get(key) );

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}



