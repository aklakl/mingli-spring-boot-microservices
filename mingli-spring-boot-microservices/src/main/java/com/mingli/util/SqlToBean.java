package com.mingli.util;

/*
 * http://www.cnblogs.com/firstdream/p/5474834.html
 * 给出数据库JAR包，数据库链接路径，数据库表空间名，数据库名，数据库密码，表名, 可以提取出来创建表属性的javaBean文件，并且提供标准的get,set方法。, 此程序将所有字段和数据提取出来定义为String类型，如有别的需要可以提取表中字段的类型和别的表信息，自动生成
 *  
 * \t as a space
 * \r as a tab  equals to \n
 * ResultSetMetaData  
 * */
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlToBean {
	private Connection conn = null; //
	private Statement stmt = null; //
	private ResultSetMetaData meta = null; //
	private ResultSet rs = null; //
	private OutputStreamWriter osw = null;
	private BufferedWriter bw = null;
	private FileOutputStream fos = null;
	private static StringBuffer coding = new StringBuffer(); //
	private String driver = null; //
	private String url = null; //
	private String table = null; //
	private String password = null; //
	private String tableName = null; //

	public SqlToBean(String driver, String url, String table, String password, String tableName) {
		this.driver = driver;
		this.url = url;
		this.table = table;
		this.password = password;
		this.tableName = tableName;
	}

	private String getCoding(StringBuffer code) {
		return code.toString();
	}

	private StringBuffer createGenerate(String property) {
		String prop = property.toLowerCase();
		coding.append("\r \t private String " + prop + ";");
		return coding;
	}

	private StringBuffer createMethod(String[] str) {
		for (int i = 0; i < str.length; i++) {
			// str[i].charAt(0) - 32) convert to lowcase or upcase
			str[i] = str[i].toLowerCase();
			coding.append("\r \t public void set" + (char) (str[i].charAt(0) - 32) + str[i].substring(1) + "(String "
					+ str[i] + "){");
			coding.append("\r \t\t this." + str[i] + "=" + str[i] + ";");
			coding.append("\r \t }");
			coding.append("\r \t public String get" + (char) (str[i].charAt(0) - 32) + str[i].substring(1) + "(){");
			coding.append("\r \t\t return this." + str[i] + ";");
			coding.append("\r \t }\n");
		}
		return coding;
	}

	/*
	 * disconnect the DB connection
	 */
	private void destroy() {
		try {
			if (conn != null) {
				conn.close();
				conn = null;
			}
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}
			if (rs != null) {
				rs.close();
				rs = null;
			}

			if (bw != null) {
				bw.close();
				bw = null;
			}
			if (fos != null) {
				fos.close();
				fos = null;
			}
			if (osw != null) {
				osw.close();
				osw = null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * when the DB has exception close the db connection
	 */
	private void connect() {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, table, password);
			stmt = conn.createStatement();

			rs = stmt.executeQuery("select  * from " + tableName); //
			meta = rs.getMetaData(); //
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			try {
				if (conn != null) {
					conn.close();
					conn = null;
				}
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
				if (rs != null) {
					rs.close();
					rs = null;
				}
			} catch (SQLException e1) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				if (conn != null) {
					conn.close();
					conn = null;
				}
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
				if (rs != null) {
					rs.close();
					rs = null;
				}
			} catch (SQLException e1) {
				e.printStackTrace();
			}
		}

	}

	private String[] getColumenName() {
		/*
		 * get all tables name  as a array return them 
		 */
		int count;
		String[] str = null;
		try {
			count = meta.getColumnCount();
			String[] strColumenName = new String[count];
			for (int i = 1; i <= count; i++) {
				strColumenName[i - 1] = meta.getColumnName(i);
			}
			str = strColumenName;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 
	 * @param message
	 */
	private void writeData(String message, String className) {
		String file = "C:\\" + className + ".java";
		try {
			fos = new FileOutputStream(file);
			osw = new OutputStreamWriter(fos);
			bw = new BufferedWriter(osw);
			bw.write(message);
			bw.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public StringBuffer createClassName(String className) {
		coding.append("public class " + className + "{\n");
		return coding;
	}

/*	
	public static void main(String[] args) {
		String className = "Hellow";
		// SqlToBean sqlToBean = new
		// SqlToBean("oracle.jdbc.driver.OracleDriver","jdbc:oracle:thin:@192.168.3.11:1521:orcl","mamibon","mamibon","my_standard_data2");
		SqlToBean sqlToBean = new SqlToBean("org.gjt.mm.mysql.Driver", "jdbc:mysql://117.79.84.144:3306/wordpress",
				"wangjun", "wangjun123", "wp_users");
		// connect to DB
		sqlToBean.connect();
		sqlToBean.createClassName(className);
		// get table's column
		String[] str;
		str = sqlToBean.getColumenName();
		for (int i = 0; i < str.length; i++) {
			sqlToBean.createGenerate(str[i]);
		}
		coding.append("\n");
		sqlToBean.createMethod(str);
		coding.append("\n}");
		// write in file
		sqlToBean.writeData(sqlToBean.getCoding(coding), className);
		sqlToBean.destroy();

		System.out.println(" ");
	}
*/

}
