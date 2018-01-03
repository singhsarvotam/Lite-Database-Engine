import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;
import org.apache.commons.io.FileUtils;



public class InformationSchema {
	static Integer line_schemata = 1;
	static Integer line_columns = 11;
	static Integer line_tables = 1;
	static Integer line_count = 0;
	static RandomAccessFile schemataTableFile;
	static RandomAccessFile tablesTableFile;
	static RandomAccessFile columnsTableFile;
	static String tableDataFile = "minidb.dat";
	static String tableIndexFile = "minidb.schema.ndx";
	
	public static void main(String[] args) {
		try {
			/* FIXME: Put all binary data files in a separate subdirectory (subdirectory tree?) */
			/* FIXME: Should there not be separate Class static variables for the file names? 
			 *        and just hard code them here?
			 */
			/* TODO: Should there be separate methods to checkfor and subsequently create each file 
			 *       granularly, instead of a big bang all or nothing? 
			 */
			schemataTableFile = new RandomAccessFile("information_schema.schemata.tbl", "rw");
			tablesTableFile = new RandomAccessFile("information_schema.table.tbl", "rw");
			columnsTableFile = new RandomAccessFile("information_schema.columns.tbl", "rw");
			//RandomAccessFile count_schema = new RandomAccessFile("information_schema.count_schema.tbl", "rw");
			/*
			 *  Create the SCHEMATA table file.
			 *  Initially it has only one entry:
			 *      information_schema
			 */
			// ROW 1: information_schema.schemata.tbl
			schemataTableFile.writeByte("information_schema".length());
			schemataTableFile.writeBytes("information_schema");
			BufferedWriter bw = new BufferedWriter(new FileWriter("schema_count.txt"));
			bw.write(line_schemata.toString());
			bw.close();
			BufferedWriter bw1 = new BufferedWriter(new FileWriter("schema_names.txt"));
			PrintWriter pw = new PrintWriter(bw1);
			pw.println("information_schema");
			pw.close();
			bw1.close();
			
			// ROW 1: information_schema.tables.tbl
			BufferedWriter bw_table = new BufferedWriter(new FileWriter("table_count.txt"));
			PrintWriter pw_table = new PrintWriter(bw_table);
			pw_table.println("information_schema\t3");
			bw_table.close();
			pw_table.close();
			
			BufferedWriter bw1_table = new BufferedWriter(new FileWriter("table_names.txt"));
			PrintWriter pw1_table = new PrintWriter(bw1_table);
			
			tablesTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
			tablesTableFile.writeBytes("information_schema");
			tablesTableFile.writeByte("SCHEMATA".length()); // TABLE_NAME
			tablesTableFile.writeBytes("SCHEMATA");
			tablesTableFile.writeLong(1); // TABLE_ROWS
			pw1_table.println("information_schema\tSCHEMATA\t1");
			

			// ROW 2: information_schema.tables.tbl
			tablesTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
			tablesTableFile.writeBytes("information_schema");
			tablesTableFile.writeByte("TABLES  ".length()); // TABLE_NAME
			tablesTableFile.writeBytes("TABLES  ");
			tablesTableFile.writeLong(3); // TABLE_ROWS
			pw1_table.println("information_schema\tTABLES\t3");
			

			// ROW 3: information_schema.tables.tbl
			tablesTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
			tablesTableFile.writeBytes("information_schema");
			tablesTableFile.writeByte("COLUMNS ".length()); // TABLE_NAME
			tablesTableFile.writeBytes("COLUMNS ");
			tablesTableFile.writeLong(7); // TABLE_ROWS
			pw1_table.println("information_schema\tCOLUMNS\t7");
			pw1_table.close();
			bw1_table.close();
			/*
			 *  Create the COLUMNS table file.
			 *  Initially it has 11 rows:
			 */
			// ROW 1: information_schema.columns.tbl
			columnsTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
			columnsTableFile.writeBytes("information_schema");
			columnsTableFile.writeByte("SCHEMATA".length()); // TABLE_NAME
			columnsTableFile.writeBytes("SCHEMATA");
			columnsTableFile.writeByte("SCHEMA_NAME".length()); // COLUMN_NAME
			columnsTableFile.writeBytes("SCHEMA_NAME");
			columnsTableFile.writeInt(1); // ORDINAL_POSITION
			columnsTableFile.writeByte("varchar(64)".length()); // COLUMN_TYPE
			columnsTableFile.writeBytes("varchar(64)");
			columnsTableFile.writeByte("NO".length()); // IS_NULLABLE
			columnsTableFile.writeBytes("NO");
			columnsTableFile.writeByte("".length()); // COLUMN_KEY
			columnsTableFile.writeBytes("");

			// ROW 2: information_schema.columns.tbl
			columnsTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
			columnsTableFile.writeBytes("information_schema");
			columnsTableFile.writeByte("TABLES  ".length()); // TABLE_NAME
			columnsTableFile.writeBytes("TABLES  ");
			columnsTableFile.writeByte("TABLE_SCHEMA".length()); // COLUMN_NAME
			columnsTableFile.writeBytes("TABLE_SCHEMA");
			columnsTableFile.writeInt(1); // ORDINAL_POSITION
			columnsTableFile.writeByte("varchar(64)".length()); // COLUMN_TYPE
			columnsTableFile.writeBytes("varchar(64)");
			columnsTableFile.writeByte("NO".length()); // IS_NULLABLE
			columnsTableFile.writeBytes("NO");
			columnsTableFile.writeByte("".length()); // COLUMN_KEY
			columnsTableFile.writeBytes("");

			// ROW 3: information_schema.columns.tbl
			columnsTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
			columnsTableFile.writeBytes("information_schema");
			columnsTableFile.writeByte("TABLES  ".length()); // TABLE_NAME
			columnsTableFile.writeBytes("TABLES  ");
			columnsTableFile.writeByte("TABLE_NAME".length()); // COLUMN_NAME
			columnsTableFile.writeBytes("TABLE_NAME");
			columnsTableFile.writeInt(2); // ORDINAL_POSITION
			columnsTableFile.writeByte("varchar(64)".length()); // COLUMN_TYPE
			columnsTableFile.writeBytes("varchar(64)");
			columnsTableFile.writeByte("NO".length()); // IS_NULLABLE
			columnsTableFile.writeBytes("NO");
			columnsTableFile.writeByte("".length()); // COLUMN_KEY
			columnsTableFile.writeBytes("");

			// ROW 4: information_schema.columns.tbl
			columnsTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
			columnsTableFile.writeBytes("information_schema");
			columnsTableFile.writeByte("TABLES  ".length()); // TABLE_NAME
			columnsTableFile.writeBytes("TABLES  ");
			columnsTableFile.writeByte("TABLE_ROWS".length()); // COLUMN_NAME
			columnsTableFile.writeBytes("TABLE_ROWS");
			columnsTableFile.writeInt(3); // ORDINAL_POSITION
			columnsTableFile.writeByte("long int".length()); // COLUMN_TYPE
			columnsTableFile.writeBytes("long int");
			columnsTableFile.writeByte("NO".length()); // IS_NULLABLE
			columnsTableFile.writeBytes("NO");
			columnsTableFile.writeByte("".length()); // COLUMN_KEY
			columnsTableFile.writeBytes("");

			// ROW 5: information_schema.columns.tbl
			columnsTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
			columnsTableFile.writeBytes("information_schema");
			columnsTableFile.writeByte("COLUMNS ".length()); // TABLE_NAME
			columnsTableFile.writeBytes("COLUMNS ");
			columnsTableFile.writeByte("TABLE_SCHEMA".length()); // COLUMN_NAME
			columnsTableFile.writeBytes("TABLE_SCHEMA");
			columnsTableFile.writeInt(1); // ORDINAL_POSITION
			columnsTableFile.writeByte("varchar(64)".length()); // COLUMN_TYPE
			columnsTableFile.writeBytes("varchar(64)");
			columnsTableFile.writeByte("NO".length()); // IS_NULLABLE
			columnsTableFile.writeBytes("NO");
			columnsTableFile.writeByte("".length()); // COLUMN_KEY
			columnsTableFile.writeBytes("");

			// ROW 6: information_schema.columns.tbl
			columnsTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
			columnsTableFile.writeBytes("information_schema");
			columnsTableFile.writeByte("COLUMNS ".length()); // TABLE_NAME
			columnsTableFile.writeBytes("COLUMNS ");
			columnsTableFile.writeByte("TABLE_NAME".length()); // COLUMN_NAME
			columnsTableFile.writeBytes("TABLE_NAME");
			columnsTableFile.writeInt(2); // ORDINAL_POSITION
			columnsTableFile.writeByte("varchar(64)".length()); // COLUMN_TYPE
			columnsTableFile.writeBytes("varchar(64)");
			columnsTableFile.writeByte("NO".length()); // IS_NULLABLE
			columnsTableFile.writeBytes("NO");
			columnsTableFile.writeByte("".length()); // COLUMN_KEY
			columnsTableFile.writeBytes("");

			// ROW 7: information_schema.columns.tbl
			columnsTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
			columnsTableFile.writeBytes("information_schema");
			columnsTableFile.writeByte("COLUMNS ".length()); // TABLE_NAME
			columnsTableFile.writeBytes("COLUMNS ");
			columnsTableFile.writeByte("COLUMN_NAME".length()); // COLUMN_NAME
			columnsTableFile.writeBytes("COLUMN_NAME");
			columnsTableFile.writeInt(3); // ORDINAL_POSITION
			columnsTableFile.writeByte("varchar(64)".length()); // COLUMN_TYPE
			columnsTableFile.writeBytes("varchar(64)");
			columnsTableFile.writeByte("NO".length()); // IS_NULLABLE
			columnsTableFile.writeBytes("NO");
			columnsTableFile.writeByte("".length()); // COLUMN_KEY
			columnsTableFile.writeBytes("");

			// ROW 8: information_schema.columns.tbl
			columnsTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
			columnsTableFile.writeBytes("information_schema");
			columnsTableFile.writeByte("COLUMNS ".length()); // TABLE_NAME
			columnsTableFile.writeBytes("COLUMNS ");
			columnsTableFile.writeByte("ORDINAL_POS".length()); // COLUMN_NAME
			columnsTableFile.writeBytes("ORDINAL_POS");
			columnsTableFile.writeInt(4); // ORDINAL_POSITION
			columnsTableFile.writeByte("integer ".length()); // COLUMN_TYPE
			columnsTableFile.writeBytes("integer ");
			//columnsTableFile.writeUTF(" ");
			columnsTableFile.writeByte("NO".length()); // IS_NULLABLE
			columnsTableFile.writeBytes("NO");
			columnsTableFile.writeByte("".length()); // COLUMN_KEY
			columnsTableFile.writeBytes("");

			// ROW 9: information_schema.columns.tbl
			columnsTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
			columnsTableFile.writeBytes("information_schema");
			columnsTableFile.writeByte("COLUMNS ".length()); // TABLE_NAME
			columnsTableFile.writeBytes("COLUMNS ");
			columnsTableFile.writeByte("COLUMN_TYPE".length()); // COLUMN_NAME
			columnsTableFile.writeBytes("COLUMN_TYPE");
			columnsTableFile.writeInt(5); // ORDINAL_POSITION
			columnsTableFile.writeByte("varchar(64)".length()); // COLUMN_TYPE
			columnsTableFile.writeBytes("varchar(64)");
			columnsTableFile.writeByte("NO".length()); // IS_NULLABLE
			columnsTableFile.writeBytes("NO");
			columnsTableFile.writeByte("".length()); // COLUMN_KEY
			columnsTableFile.writeBytes("");

			// ROW 10: information_schema.columns.tbl
			columnsTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
			columnsTableFile.writeBytes("information_schema");
			columnsTableFile.writeByte("COLUMNS ".length()); // TABLE_NAME
			columnsTableFile.writeBytes("COLUMNS ");
			columnsTableFile.writeByte("IS_NULLABLE".length()); // COLUMN_NAME
			columnsTableFile.writeBytes("IS_NULLABLE");
			columnsTableFile.writeInt(6); // ORDINAL_POSITION
			columnsTableFile.writeByte("varchar(3)".length()); // COLUMN_TYPE
			columnsTableFile.writeBytes("varchar(3)");
			columnsTableFile.writeByte("NO".length()); // IS_NULLABLE
			columnsTableFile.writeBytes("NO");
			columnsTableFile.writeByte("".length()); // COLUMN_KEY
			columnsTableFile.writeBytes("");

			// ROW 11: information_schema.columns.tbl
			columnsTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
			columnsTableFile.writeBytes("information_schema");
			columnsTableFile.writeByte("COLUMNS ".length()); // TABLE_NAME
			columnsTableFile.writeBytes("COLUMNS ");
			columnsTableFile.writeByte("COLUMN_KEY".length()); // COLUMN_NAME
			columnsTableFile.writeBytes("COLUMN_KEY");
			columnsTableFile.writeInt(7); // ORDINAL_POSITION
			columnsTableFile.writeByte("varchar(3)".length()); // COLUMN_TYPE
			columnsTableFile.writeBytes("varchar(3)");
			columnsTableFile.writeByte("NO".length()); // IS_NULLABLE
			columnsTableFile.writeBytes("NO");
			columnsTableFile.writeByte("".length()); // COLUMN_KEY
			columnsTableFile.writeBytes("");

			/*
			 *  Navigate throught the binary data file, displaying each widget record
			 *  in the order that it physically appears in the file. Convert binary data
			 *  to appropriate data types for each field.
			 */
			schemataTableFile.close();
		}
		catch(Exception e) {
			System.out.println(e);
		}	
	}


public static void writeDataToSchemata(String str) throws FileNotFoundException,IOException
{
		int count=0;
		
	    RandomAccessFile schemataTableFile1 = new RandomAccessFile("information_schema.schemata.tbl", "rw");
	    		while(schemataTableFile1.read() != -1)
	    			count=count + 1;
	    		schemataTableFile1.seek(count);
	    		schemataTableFile1.writeByte(str.length());
	    		schemataTableFile1.writeBytes(str);
	    		line_schemata = line_schemata + 1;
	    		
	    		BufferedWriter bw = new BufferedWriter(new FileWriter("schema_count.txt"));
	    		bw.write(line_schemata.toString());
	    		bw.close();
	    		schemataTableFile1.close();
	    		
}


@SuppressWarnings("resource")
public static void writeDataToTables(String str, String[] table) throws FileNotFoundException,IOException
{
		int count=0;
		System.out.println("create 1: " + table[2]);
	    RandomAccessFile tablesTableFile1 = new RandomAccessFile("information_schema.table.tbl", "rw");
	    		while(tablesTableFile1.read() != -1)
	    			count=count + 1;
	    		tablesTableFile1.seek(count);
	    		tablesTableFile1.writeByte(str.length());
	    		tablesTableFile1.writeBytes(str);
	    		tablesTableFile1.writeByte(table[2].length()); // TABLE_NAME
				tablesTableFile1.writeBytes(table[2]);
				tablesTableFile1.writeLong(3); // TABLE_ROWS
	    		tablesTableFile1.close();
}

public static void writeDataToColumns(String active, String table, String col_name, String col_type, Integer pos, String isNull, String key) throws FileNotFoundException,IOException
{
		int count=0;
		
	    RandomAccessFile columnsTableFile1 = new RandomAccessFile("information_schema.columns.tbl", "rw");
	    		while(columnsTableFile1.read() != -1)
	    			count=count + 1;
	    		columnsTableFile1.seek(count);
	    		columnsTableFile1.writeByte(active.length()); // TABLE_SCHEMA
				columnsTableFile1.writeBytes(active);
				columnsTableFile1.writeByte(table.length()); // TABLE_NAME
				columnsTableFile1.writeBytes(table);
				columnsTableFile1.writeByte(col_name.length()); // COLUMN_NAME
				columnsTableFile1.writeBytes(col_name);
				columnsTableFile1.writeInt(pos); // ORDINAL_POSITION
				columnsTableFile1.writeByte(col_type.length()); // COLUMN_TYPE
				columnsTableFile1.writeBytes(col_type);
				columnsTableFile1.writeByte(isNull.length()); // IS_NULLABLE
				columnsTableFile1.writeBytes(isNull);
				columnsTableFile1.writeByte(key.length()); // COLUMN_KEY
				columnsTableFile1.writeBytes(key);
				columnsTableFile1.close();
}


public static void readDataFromColumns() throws FileNotFoundException,IOException
{
	RandomAccessFile infoschema = new RandomAccessFile("information_schema.columns.tbl", "rw");
	
	for(int record = 0;record < 11; record++) {
		byte varcharLength1 = infoschema.readByte();
		for(int i = 0; i < varcharLength1; i++)
			System.out.print((char)infoschema.readByte());
		System.out.print("\t");
		byte varcharLength2 = infoschema.readByte();
		for(int i = 0; i < varcharLength2; i++)
			System.out.print((char)infoschema.readByte());
		System.out.print("\t\t");
		
		byte varcharLength3 = infoschema.readByte();
		for(int i = 0; i < varcharLength3; i++)
			System.out.print((char)infoschema.readByte());
		System.out.print("\t");
		System.out.print(infoschema.readInt());
		System.out.print("\t");
		byte varcharLength4 = infoschema.readByte();
		for(int i = 0; i < varcharLength4; i++)
			System.out.print((char)infoschema.readByte());
		System.out.print("\t");
		byte varcharLength5 = infoschema.readByte();
		for(int i = 0; i < varcharLength5; i++)
			System.out.print((char)infoschema.readByte());
		System.out.print("\t");
		byte varcharLength6 = infoschema.readByte();
		for(int i = 0; i < varcharLength6; i++)
			System.out.print((char)infoschema.readByte());
		System.out.print("\n");
	}
	infoschema.close();
  }

public static void readDataFromTables(String active) throws FileNotFoundException,IOException
{
	
	RandomAccessFile infoschema = new RandomAccessFile("information_schema.table.tbl", "rw");
	//int line_tables = 0;
	Scanner sc1= new Scanner(new File("table_names.txt"));
	System.out.println("Tables");
	System.out.println("**************");
	while(sc1.hasNextLine()) {
	String[] str = sc1.nextLine().split("\t");
	if(str[0].equalsIgnoreCase(active))
	System.out.println(str[1]);
 }
	sc1.close();
	infoschema.close();
}

public static void readDataFromSchemata() throws FileNotFoundException,IOException
{
	RandomAccessFile infoschema = new RandomAccessFile("information_schema.schemata.tbl", "rw");

	Scanner sc1= new Scanner(new File("schema_count.txt"));
	Integer p1 = Integer.parseInt(sc1.next());
	System.out.println(p1);
	System.out.println("Database");
	System.out.println("*********************");
	for(int record = 0;record < p1; record++) {
		byte varcharLength1 = infoschema.readByte();
		for(int i = 0; i < varcharLength1; i++)
			System.out.print((char)infoschema.readByte());
		System.out.print("\n");
	}
	sc1.close();
	infoschema.close();
  }

	public static void dropFromTables(String active, String table) throws IOException
	{
		RandomAccessFile tablesTableFile2 = new RandomAccessFile("temp.tbl", "rw");
		File oldName = new File("temp.txt");
		File newName = new File("table_names.txt");
		Scanner sc = new Scanner(newName);
		String schema_table[] = null;
	    
		BufferedWriter bw = new BufferedWriter(new FileWriter("temp.txt"));
		PrintWriter pw = new PrintWriter(bw);
		while(sc.hasNextLine())
		{
			schema_table = sc.nextLine().split("\t");
			if(schema_table[0].equalsIgnoreCase(active) && schema_table[1].equalsIgnoreCase(table))
			{
				System.out.println("Inside if: " + schema_table[0] + ":" + schema_table[1] + ":" + schema_table[2]);
				continue;
			}
			else
			{
				System.out.println("Inside else: " + schema_table[0] + ":" + schema_table[1] + ":" + schema_table[2]);
				pw.println(schema_table[0] + "\t" + schema_table[1]+ "\t" + schema_table[2]);
	    		tablesTableFile2.writeByte(schema_table[0].length());
	    		tablesTableFile2.writeBytes(schema_table[0]);
	    		tablesTableFile2.writeByte(schema_table[1].length()); // TABLE_NAME
				tablesTableFile2.writeBytes(schema_table[1]);
				tablesTableFile2.writeLong(Integer.parseInt(schema_table[2])); // TABLE_ROWS
			}
		}
		pw.close();
		bw.close();
		sc.close();
		FileUtils.copyFile(oldName, newName);
		tablesTableFile2.close();
		File oldFile = new File("temp.tbl");
		File newFile = new File("information_schema.table.tbl");
		FileUtils.copyFile(oldFile, newFile);
	}
}