
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import org.apache.commons.io.FileUtils;



public class DatabaseLite {

	static String looks = "H@wksql> ";
	static String schema_used = "information_schema";
	static Integer line_count = 0;
	static Integer line_tables = 1;
	static HashMap <String, String> col_datatype = new HashMap<String, String>();
	static HashMap <String, String> col_data = new HashMap<String, String>();
	static ArrayList<String> ordinal_pos = new ArrayList<String>();
	private static final String database_file = "data.db"; //data file
    private static final String id_file = "id.ndx";       //index file on id
    private static final String lname_file = "lname.ndx"; //index file on last name
    private static final String state_file = "state.ndx"; //index file on state
    private static final String email_file = "email.ndx"; //index file on email
    private static long fileSize = 0;
    private static String line = null;
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	@SuppressWarnings("resource")
	public static void main(String [] args) throws FileNotFoundException, IOException
	{
		welcomeScreen();
		Scanner sc;
		String [] userCommand = null;
		do
		{
			System.out.print("\n" + looks);
			sc= new Scanner(System.in).useDelimiter(";");
			userCommand = sc.next().split(" ");
			if(userCommand[0].equalsIgnoreCase("Select"))
				selectTable(userCommand);
			else if(userCommand[0].equalsIgnoreCase("Use"))
			{
				if(userCommand.length == 2)
					useSchemas(userCommand[1]);
				else
					System.out.println("The command is not right. TRY AGAIN!!!");
			}
				
			else if(userCommand[0].equalsIgnoreCase("Show"))
			{
				if(userCommand[1].equalsIgnoreCase("Schemas"))
					showSchemas();
				else if(userCommand[1].equalsIgnoreCase("Tables"))
					showTables();
				else
					System.out.println("I didn't understand the command. TRY AGAIN!!!");
			}
			else if(userCommand[0].equalsIgnoreCase("Insert"))
				insertIntoTable(userCommand);
			else if(userCommand[0].equalsIgnoreCase("Drop"))
				dropTable(userCommand);
			else if(userCommand[0].equalsIgnoreCase("Create"))
			{
				if(userCommand[1].equalsIgnoreCase("Schema"))
					createSchema(userCommand[2]);
				else if(userCommand[1].equalsIgnoreCase("Table"))
				{
					
					if(createTable(userCommand))
					System.out.println("Your Table is Ready");
					
				}
				else
					System.out.println("I didn't understand the command. TRY AGAIN!!");
			}
		}while(!userCommand[0].equalsIgnoreCase("exit"));
		System.out.println("You have exited");
		sc.close();
	}
	
	public static void welcomeScreen()
	{
		printLine("@",80);
		System.out.println();
		System.out.println("\t\t\tThis is DATABASE LITE Version");
		System.out.println("\t\t   Sarvotam Pal Singh, SXS155032, CS6360");
		printLine("@",80);
		System.out.println();
		System.out.println("USE THE FOLLOWING COMMANDS:");
		System.out.println("1. create schema schema_name;");
		System.out.println("2. use schema_name;");
		System.out.println("3. create table table_name (col_name1 col_datatype1, col_name2 col_datatype2);");
		System.out.println("4. Show tables;");
		System.out.println("5. insert into table_name values (val1, val2, ...);");
		System.out.println("6. select * from table_name;");
	
	}
	
	public static void showSchemas() throws FileNotFoundException, IOException
	{
		System.out.println("Schema in use is: " + schema_used);
		InformationSchema.readDataFromSchemata();
	}
	
	@SuppressWarnings("resource")
	public static void useSchemas(String schema) throws FileNotFoundException,IOException
	{
		Scanner sc = new Scanner (new File ("schema_names.txt"));
		while(sc.hasNext())
		{
			if(schema.equalsIgnoreCase(sc.next()))
			{
				System.out.println("Using schema " + schema);
				schema_used = schema;
				return;
			}
		}
		System.out.println(schema + " doesn't exist");
		sc.close();
		
	}
	
	@SuppressWarnings("resource")
	public static void createSchema(String schema) throws FileNotFoundException, IOException
	{
		Scanner sc = new Scanner (new File ("schema_names.txt"));
		while(sc.hasNext())
		{
			if(schema.equalsIgnoreCase(sc.next()))
			{
				System.out.println("Schema with the same name already exists");
				return;
			}
		}
		System.out.println("Creating schema " + schema);
		sc.close();
		//System.out.println(schema[2]);
		BufferedWriter bw1 = new BufferedWriter(new FileWriter("schema_names.txt",true));
		PrintWriter pw = new PrintWriter(bw1);
		pw.println(schema);
		pw.close();
		bw1.close();
		InformationSchema.writeDataToSchemata(schema);
	}
	
	public static int checkDataTypes(String str)
	{
		int flag = 0 ;
			if(str.equalsIgnoreCase("byte"))
			{
				System.out.println("check: byte");
				flag=1;
			}
			else if(str.equalsIgnoreCase("int"))
			{
				System.out.println("check: int");
				flag=1;
			}
			else if(str.equalsIgnoreCase("long"))
			{
				System.out.println("check: long");
				flag=1;
			}
			else if(str.equalsIgnoreCase("short"))
			{
				System.out.println("check: short");
				flag=1;
			}
			else if(str.equalsIgnoreCase("char"))
			{
				System.out.println("check: char");
				flag=1;
			}
			else if(str.contains("varchar"))
			{
				System.out.println("check: varchar");
				flag=1;
			}
			else if(str.equalsIgnoreCase("float"))
			{
				System.out.println("check: float");
				flag=1;
			}
			else if(str.equalsIgnoreCase("double"))
			{
				System.out.println("check: double");
				flag=1;
			}
			else if(str.equalsIgnoreCase("date"))
			{
				System.out.println("check: date");
				flag=1;
			}
			else if(str.equalsIgnoreCase("datetime"))
			{
				System.out.println("check: datetime");
				flag=1;
			}
			else
			{
				System.out.println("Invalid Datatype");
				return flag;
			}
			return flag;
	}
	
	@SuppressWarnings({ "resource" })
	public static boolean createTable(String[] comm) throws FileNotFoundException, IOException
	{ 
		int flag= 0;
		System.out.println("create table");
		Scanner sc = new Scanner (new File ("table_names.txt"));
		String[] schema_table = null;
		while(sc.hasNextLine())
		{
			schema_table = sc.nextLine().split("\t");
			System.out.println(schema_table[0]+"."+schema_table[1]);
			if(schema_used.equalsIgnoreCase(schema_table[0]) && comm[2].equalsIgnoreCase(schema_table[1]))
			{
				System.out.println("Table with the same name already exists");
				return false;
			}
		}
		
		
		System.out.println("Creating table " + comm[2] + " under schema " + schema_used);
		
		for(int i=3; i<comm.length; i++)
		{
			if(comm[i].startsWith("(") && !comm[i+1].contains(",") && !comm[i+1].contains("))"))
			{
					if(comm[i+2].equalsIgnoreCase("primary"))
					{
						System.out.println(comm[i].substring(1) + " is a Primary Key");
						System.out.println("Cretate table: Column names: " + comm[i].substring(1));
						col_datatype.put(comm[i].substring(1), comm[i+1]);
						int check = checkDataTypes(comm[i+1]);
						if(check ==0)
							return false;
						ordinal_pos.add(comm[i].substring(1));
						System.out.println(ordinal_pos.indexOf(comm[i].substring(1)));
						System.out.println(comm[i].substring(1) + ":" + col_datatype.get(comm[i].substring(1)));
						InformationSchema.writeDataToColumns(schema_used, comm[2], comm[i].substring(1), comm[i+1], ordinal_pos.indexOf(comm[i].substring(1))+1, "NO", comm[i].substring(1));
						i+=3;
					}
					else if(comm[i+2].equalsIgnoreCase("not"))
					{
						System.out.println(comm[i] + " is a not NULL");
						System.out.println("Create table: Column names: " + comm[i].substring(1));
						col_datatype.put(comm[i].substring(1), comm[i+1]);
						int check = checkDataTypes(comm[i+1]);
						if(check ==0)
							return false;
						ordinal_pos.add(comm[i].substring(1));
						System.out.println(ordinal_pos.indexOf(comm[i].substring(1)));
						System.out.println(comm[i].substring(1) + ":" + col_datatype.get(comm[i].substring(1)));
						InformationSchema.writeDataToColumns(schema_used, comm[2], comm[i].substring(1), comm[i+1], ordinal_pos.indexOf(comm[i].substring(1))+1, "NO", "NULL");
						i+=3;
					}
			}
			else if(comm[i].startsWith("(") && (comm[i+1].contains(",") || comm[i+1].contains("))")))
			{
					System.out.println("Cretate table: Column names: " + comm[i].substring(1));
					col_datatype.put(comm[i].substring(1), comm[i+1].substring(0, comm[i+1].length()-1));
					int check = checkDataTypes(comm[i+1].substring(0, comm[i+1].length()-1));
					if(check ==0)
						return false;
					ordinal_pos.add(comm[i].substring(1));
					System.out.println(ordinal_pos.indexOf(comm[i].substring(1)));
					System.out.println(comm[i].substring(1) + ":" + col_datatype.get(comm[i].substring(1)));
					InformationSchema.writeDataToColumns(schema_used, comm[2], comm[i].substring(1), col_datatype.get(comm[i].substring(1)), ordinal_pos.indexOf(comm[i].substring(1))+1, "YES", "NULL");
					i++;
			}
			else if(comm[i+1].contains("(") && !comm[i+1].contains(",") && !comm[i+1].contains("))"))
			{
				if(comm[i+2].equalsIgnoreCase("primary"))
				{
					System.out.println(comm[i] + " is a Primary Key");
					System.out.println("Cretate table: Column names: " + comm[i]);
					
					int check = checkDataTypes(comm[i+1]);
					if(check ==0)
						return false;
					col_datatype.put(comm[i], comm[i+1]);
					ordinal_pos.add(comm[i]);
					System.out.println(ordinal_pos.indexOf(comm[i]));
					System.out.println(comm[i] + ":" + col_datatype.get(comm[i]));
					InformationSchema.writeDataToColumns(schema_used, comm[2], comm[i], comm[i+1], ordinal_pos.indexOf(comm[i])+1, "NO", comm[i]);
					i+=3;
				}
				else if(comm[i+2].equalsIgnoreCase("not"))
				{
					System.out.println(comm[i] + " is a not NULL");
					System.out.println("Create table: Column names: " + comm[i]);
					
					int check = checkDataTypes(comm[i+1]);
					if(check ==0)
						return false;
					col_datatype.put(comm[i], comm[i+1]);
					ordinal_pos.add(comm[i]);
					System.out.println(ordinal_pos.indexOf(comm[i]));
					System.out.println(comm[i] + ":" + col_datatype.get(comm[i]));
					InformationSchema.writeDataToColumns(schema_used, comm[2], comm[i], comm[i+1], ordinal_pos.indexOf(comm[i])+1, "NO", "NULL");
					i+=3;
				}
			}
			else
			{
				System.out.println("Create table: Column names: " + comm[i]);
				
				int check = checkDataTypes(comm[i+1].substring(0, comm[i+1].length()-1));
				if(check ==0)
					return false;
				col_datatype.put(comm[i], comm[i+1].substring(0, comm[i+1].length()-1));
				ordinal_pos.add(comm[i]);
				System.out.println(ordinal_pos.indexOf(comm[i]));
				System.out.println(comm[i] + ":" + col_datatype.get(comm[i]));
				InformationSchema.writeDataToColumns(schema_used, comm[2], comm[i], col_datatype.get(comm[i]), ordinal_pos.indexOf(comm[i])+1, "YES", "");
				i++;
			}
		}
		
		BufferedWriter bw1 = new BufferedWriter(new FileWriter("table_names.txt",true));
		PrintWriter pw1 = new PrintWriter(bw1);
		pw1.println(schema_used + "\t" + comm[2] + "\t5");
		pw1.close();
		bw1.close();
		sc.close();
		InformationSchema.writeDataToTables(schema_used, comm);
		Scanner sc1 = new Scanner(new File("table_count.txt"));
		
		String input[] = null;
		int num = 0;
		while(sc1.hasNextLine())
		{
			input = sc1.nextLine().split("\t");
			if(input[0].equalsIgnoreCase(schema_used))
			{
				System.out.println("Before->" + schema_used + ":" + input[1]);
				line_count = Integer.parseInt(input[1]);
				num = line_count.intValue()+1;
				Integer line = new Integer(num);
				input[1] = line.toString();
				System.out.println("After->" + schema_used + ":" + input[1]);
				BufferedWriter bw = new BufferedWriter(new FileWriter("temp_count.txt",true));
				PrintWriter pw = new PrintWriter(bw);
				pw.println(input[0] + "\t" + input[1]);
				pw.close();
				bw.close();
				flag=1;
			}
			else
			{
				BufferedWriter bw = new BufferedWriter(new FileWriter("temp_count.txt",true));
				PrintWriter pw = new PrintWriter(bw);
				pw.println(input[0] + "\t" + input[1]);
				pw.close();
				bw.close();
			}
		}
		if(flag==1)
		{}
		else {
		//hmap.put(schema_used, line_tables.toString());
		BufferedWriter bw = new BufferedWriter(new FileWriter("temp_count.txt",true));
		PrintWriter pw = new PrintWriter(bw);
		pw.println(schema_used + "\t" + line_tables.toString());
		pw.close();
		bw.close();
		sc1.close();}
		File f1 = new File("temp_count.txt");
		File f2 = new File("table_count.txt");
		FileUtils.copyFile(f1, f2);
		f1.delete();
		return true;
	}
	
	public static void showTables() throws FileNotFoundException, IOException
	{
		System.out.println("show table");
		System.out.println("Showing tables for " + schema_used + ":");
		InformationSchema.readDataFromTables(schema_used);
	}
	
	public static void insertIntoTable(String str[]) throws IOException
	{
		if(str[1].equalsIgnoreCase("into") && str[3].equalsIgnoreCase("values"))
		{
			RandomAccessFile raf = new RandomAccessFile(schema_used+"."+ str[2]+".dat","rw");
			RandomAccessFile tableIdIndex = new RandomAccessFile(schema_used+"."+ str[2]+".ndx", "rw");
			Scanner sc = new Scanner (new File ("table_names.txt"));
			String[] schema_table = null;
			int count = 0;
			while(sc.hasNextLine())
			{
			//System.out.println(sc.nextLine());
				schema_table = sc.nextLine().split("\t");
				System.out.println(schema_table[0]+"."+schema_table[1]);
				if(schema_used.equalsIgnoreCase(schema_table[0]) && str[2].equalsIgnoreCase(schema_table[1]))
				{
					tableIdIndex.writeInt(Integer.parseInt(str[4].substring(1,str[4].length()-1).toString()));
					tableIdIndex.writeLong(raf.getFilePointer());
					while(raf.read() != -1)
		    			count=count + 1;
		    		raf.seek(count);
					raf.writeBytes(str[4].substring(1,str[4].length()-1));
					String s = str[5].substring(str[5].indexOf("'")+1);
					System.out.println("Here in insert: " + s.substring(0, s.indexOf("'")));
					raf.writeByte(s.substring(0, s.indexOf("'")).length());
					raf.writeBytes(s.substring(0, s.indexOf("'")));
					raf.writeShort(Integer.parseInt(str[6].substring(0,str[6].length()-1).toString()));
				}
			}
			raf.close();
		}
		else
		{
			System.out.println("I didn't understand the command..!!Try again");
		}
	}
	
	public static void selectTable(String[] select) throws IOException
	{
		System.out.println("select table");
		RandomAccessFile raf = new RandomAccessFile(schema_used+"."+ select[3]+".dat","rw");
		RandomAccessFile raf_index = new RandomAccessFile(schema_used+"."+ select[3]+".ndx","rw");
		if(select[1].equalsIgnoreCase("*") && select[2].equalsIgnoreCase("from") && select.length==4)
		{
			for(int record = 0;record < 2; record++) {
				System.out.println("Iside if");
				System.out.println(raf.readInt());
				System.out.print("\t");
				byte varcharLength1 = raf.readByte();
				for(int i = 0; i < varcharLength1; i++)
					System.out.print((char)raf.readByte());
				System.out.print("\t");
				System.out.print(raf.readShort());
				System.out.print("\n");
			}
		}
		else if(select[1].equalsIgnoreCase("*") && select[2].equalsIgnoreCase("from") && select[4].equalsIgnoreCase("where"))
		{
			Integer id =2;
			System.out.println("Inside else if");
			int indexFileLocation = 0;
			long indexOfRecord = 0;
			boolean recordFound = false;
			while(!recordFound) {
				System.out.println("Iside while");
				raf_index.seek(indexFileLocation);
				if(raf_index.readInt() == 2) {
					raf_index.seek(indexFileLocation);
					indexOfRecord = raf_index.readLong();
					recordFound = true;
				}
				indexFileLocation += 12;
			}
			raf.seek(indexOfRecord);
			System.out.println(raf.readInt());
			System.out.print("\t");
			byte varcharLength1 = raf.readByte();
			for(int i = 0; i < varcharLength1; i++)
				System.out.print((char)raf.readByte());
			System.out.print("\t");
			System.out.print(raf.readShort());
			System.out.print("\n");
		}
		else
		{
			System.out.println("I didn't understand the command.Try again!!");
		}
		raf.close();
	}
	
	
	public static void dropTable(String[] drop) throws IOException
	{
		System.out.println("drop table");
		if(drop[1].equalsIgnoreCase("table"))
		{
			Scanner sc = new Scanner(new File("table_names.txt"));
			String[] str = null;
			while(sc.hasNextLine())
			{
				str = sc.nextLine().split("\t");
				if(str[0].equalsIgnoreCase(schema_used) && str[1].equalsIgnoreCase(drop[2]))
					InformationSchema.dropFromTables(schema_used, drop[2]);
				else
					System.out.println("No table with such a name exist under a given schema");
			}
			
		}
		else
			System.out.println("Didn't understand the second keyword");
	}
	
	public static void printLine(String str, int num)
	{
		for(int i=1;i<=num; i++)
		System.out.print(str);
	}
}
