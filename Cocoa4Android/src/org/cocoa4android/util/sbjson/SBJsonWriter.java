/*
 * Copyright (C) 2012 Wu Tong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cocoa4android.util.sbjson;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.cocoa4android.ns.NSObject;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

public class SBJsonWriter extends NSObject {
	public static String stringWithObject(Object value){
		String contentString ="";
		JSONStringer js = new JSONStringer();
		serialize(js, value);
		contentString = js.toString();
		return contentString;
	}
	/**
	 * 序列化为JSON
	 * @param js json对象
	 * @param o	待需序列化的对象
	 */
	private static void serialize(JSONStringer js, Object o) {
		if (isNull(o)) {
			try {
				js.value(null);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return;
		}

		Class<?> clazz = o.getClass();
		if (isObject(clazz)) { // 对象
			serializeObject(js, o);
		} else if (isArray(clazz)) { // 数组
			serializeArray(js, o);
		} else if (isCollection(clazz)) { // 集合
			Collection<?> collection = (Collection<?>) o;
			serializeCollect(js, collection);
		}else if (isMap(clazz)) { // 集合
			HashMap<?,?> collection = (HashMap<?,?>) o;
			serializeMap(js, collection);
		} else { // 单个值
			try {
				js.value(o);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 序列化数组 
	 * @param js	json对象
	 * @param array	数组
	 */
	private static void serializeArray(JSONStringer js, Object array) {
		try {
			js.array();
			for (int i = 0; i < Array.getLength(array); ++i) {
				Object o = Array.get(array, i);
				serialize(js, o);
			}
			js.endArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 序列化集合
	 * @param js	json对象
	 * @param collection	集合
	 */
	private static void serializeCollect(JSONStringer js, Collection<?> collection) {
		try {
			js.array();
			for (Object o : collection) {
				serialize(js, o);
			}
			js.endArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 序列化Map
	 * @param js	json对象
	 * @param map	map对象
	 */
	private static void serializeMap(JSONStringer js, Map<?,?> map) {
		try {
			js.object();
			@SuppressWarnings("unchecked")
			Map<String, Object> valueMap = (Map<String, Object>) map;
			Iterator<Map.Entry<String, Object>> it = valueMap.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>)it.next();
				js.key(entry.getKey());
				serialize(js,entry.getValue());
			}
			js.endObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 序列化对象
	 * @param js	json对象
	 * @param obj	待序列化对象
	 */
	private static void serializeObject(JSONStringer js, Object obj) {
		try {
			js.object();
			Class<? extends Object> objClazz = obj.getClass();
			Method[] methods = objClazz.getDeclaredMethods();   
	        Field[] fields = objClazz.getDeclaredFields();     
	        for (Field field : fields) {   
	            try {   
	                String fieldType = field.getType().getSimpleName();   
	                String fieldGetName = parseMethodName(field.getName(),"get");   
	                if (!haveMethod(methods, fieldGetName)) {   
	                    continue;   
	                }   
	                Method fieldGetMet = objClazz.getMethod(fieldGetName, new Class[] {});   
	                Object fieldVal = fieldGetMet.invoke(obj, new Object[] {});   
	                String result = null;   
	                if ("Date".equals(fieldType)) {   
	                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);   
	                    result = sdf.format((Date)fieldVal);  

	                } else {   
	                    if (null != fieldVal) {   
	                        result = String.valueOf(fieldVal);   
	                    }   
	                }   
	                js.key(field.getName());
					serialize(js, result);  
	            } catch (Exception e) {   
	                continue;   
	            }   
	        }  
			js.endObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 判断是否存在某属性的 get方法
	 * @param methods	引用方法的数组
	 * @param fieldMethod	方法名称
	 * @return true或者false
	 */
	public static boolean haveMethod(Method[] methods, String fieldMethod) {
		for (Method met : methods) {
			if (fieldMethod.equals(met.getName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 拼接某属性的 get或者set方法
	 * @param fieldName	字段名称
	 * @param methodType	方法类型
	 * @return 方法名称
	 */
	public static String parseMethodName(String fieldName,String methodType) {
		if (null == fieldName || "".equals(fieldName)) {
			return null;
		}
		return methodType + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}
	/**
	 * 判断对象是否为空
	 * @param obj	实例
	 * @return 
	 */
	private static boolean isNull(Object obj) {
		if (obj instanceof JSONObject) {
			return JSONObject.NULL.equals(obj);
		}
		return obj == null;
	}

	/**
	 * 判断是否是值类型 
	 * @param clazz	
	 * @return
	 */
	private static boolean isSingle(Class<?> clazz) {
		return isBoolean(clazz) || isNumber(clazz) || isString(clazz);
	}

	/**
	 * 是否布尔值
	 * @param clazz	
	 * @return
	 */
	public static boolean isBoolean(Class<?> clazz) {
		return (clazz != null)
				&& ((Boolean.TYPE.isAssignableFrom(clazz)) || (Boolean.class
						.isAssignableFrom(clazz)));
	}

	/**
	 * 是否数值 
	 * @param clazz	
	 * @return
	 */
	public static boolean isNumber(Class<?> clazz) {
		return (clazz != null)
				&& ((Byte.TYPE.isAssignableFrom(clazz)) || (Short.TYPE.isAssignableFrom(clazz))
						|| (Integer.TYPE.isAssignableFrom(clazz))
						|| (Long.TYPE.isAssignableFrom(clazz))
						|| (Float.TYPE.isAssignableFrom(clazz))
						|| (Double.TYPE.isAssignableFrom(clazz)) || (Number.class
						.isAssignableFrom(clazz)));
	}

	/**
	 * 判断是否是字符串 
	 * @param clazz	
	 * @return
	 */
	public static boolean isString(Class<?> clazz) {
		return (clazz != null)
				&& ((String.class.isAssignableFrom(clazz))
						|| (Character.TYPE.isAssignableFrom(clazz)) || (Character.class
						.isAssignableFrom(clazz)));
	}

	/**
	 * 判断是否是对象
	 * @param clazz	
	 * @return
	 */
	private static boolean isObject(Class<?> clazz) {
		return clazz != null && !isSingle(clazz) && !isArray(clazz) && !isCollection(clazz) && !isMap(clazz);
	}

	/**
	 * 判断是否是数组 
	 * @param clazz
	 * @return
	 */
	public static boolean isArray(Class<?> clazz) {
		return clazz != null && clazz.isArray();
	}

	/**
	 * 判断是否是集合
	 * @param clazz
	 * @return
	 */
	public static boolean isCollection(Class<?> clazz) {
		return clazz != null && Collection.class.isAssignableFrom(clazz);
	}
		
	/**
	 * 判断是否是Map
	 * @param clazz
	 * @return
	 */
	public static boolean isMap(Class<?> clazz) {
		return clazz != null && Map.class.isAssignableFrom(clazz);
	}
	
	/**
	 * 判断是否是列表 
	 * @param clazz
	 * @return
	 */
	public static boolean isList(Class<?> clazz) {
		return clazz != null && List.class.isAssignableFrom(clazz);
	}
}
