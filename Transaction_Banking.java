package Banking_Application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Transaction_Banking 
{
	public static void open_acc(Scanner sc, Connection con)
	{
		System.out.println("Enter Your First Name -- ");
		String name=sc.next();
		//System.out.println("Enter Your Middle Name (if any) -- ");
		//String name2=sc.next();
		//sc.nextLine();
		System.out.println("Enter Your Last Name -- ");
		String name3=sc.next();
		String final_name=name+" "+name3;
		/*String final_name="";
		if(name2 != null)
		{
			final_name=name+" "+name2+" "+name3;
		}
		else
		{
			final_name=name+" "+name3;
		}*/
		System.out.println("Set Your 4 Digit Pin -- ");
		int pin=sc.nextInt();
		System.out.println("Re-Enter Pin -- ");
		int pin2=sc.nextInt();
		if(pin==pin2)
		{
			System.out.println("You Have to Deposit 500 or more to Open the Account..\nEnter Amount to Deposit -- ");
			int amt=sc.nextInt();
			if(amt==500 || amt>500)
			{
				int Account_Number=0;
				double num= Math.random();
				double num2=num*10000;
				int acc2=(int) num2;
				if(acc2>1000)
				{
					Account_Number=acc2;
				}
				else
				{
					double num4=num*100000;
					int acc3=(int) num4;
					Account_Number=acc3;
				}
				
				PreparedStatement ptm10=null;
				String insert="insert into bank2 values (?,?,?,?)";
				try
				{
					con.setAutoCommit(false);
					ptm10=con.prepareStatement(insert);
					ptm10.setInt(1, Account_Number);
					ptm10.setString(2, final_name);
					ptm10.setInt(3, amt);
					ptm10.setInt(4, pin);
					ptm10.executeUpdate();
					con.commit();
					System.out.println("Your Account is Created..\n\nWelcome , "+final_name+" !!\nYour Account Number is -- "+Account_Number+"\nThank Your for Choosing Us !!");
				}
				catch(SQLException e)
				{
					try 
					{
						con.rollback();
						System.out.println("The Transaction is Cancelled ..\nThank You for Choosing Us !!");
					} 
					catch (SQLException e1) 
					{
						e1.printStackTrace();
					}
					e.printStackTrace();
				}
				finally
				{
					if(ptm10!=null)
					{
						try
						{
							ptm10.close();
						}
						catch(SQLException e)
						{
							e.printStackTrace();
						}
					}
				}
			}
			else
			{
				System.out.println("You Can't Deposit less than 500 ..\nPress --\n1.Try Again\n2.Exit");
				int x=sc.nextInt();
				if(x==1)
				{
					open_acc(sc, con);
				}
				else if(x==2)
				{
					System.out.println("Thank Your for Choosing Us !!");
				}
				else
				{
					System.out.println("Wrong Input..");
				}
			}
		}
		else
		{
			System.out.println("Pin is not Matching ..\nPress --\n1.Try Again\n2.Exit");
			int x=sc.nextInt();
			if(x==1)
			{
				open_acc(sc, con);
			}
			else if(x==2)
			{
				System.out.println("Thank Your for Choosing Us !!");
			}
			else
			{
				System.out.println("Wrong Input..");
			}
		}
		sc.close();
	}
	
	
	public static void forget_acc(Scanner sc, Connection con)
	{
		System.out.println("Enter Your First Name -- ");
		String name=sc.next();
		System.out.println("Enter Your Last Name -- ");
		String name2=sc.next();
		String final_name=name+" "+name2;
		String acc_recovary="select Acc_No from bank2 where Name=?";
		ResultSet res6=null;
		PreparedStatement ptm9=null;
		try
		{
			ptm9=con.prepareStatement(acc_recovary);
			ptm9.setString(1, final_name);
			res6=ptm9.executeQuery();
			if(res6.next())
			{
				int acc_no=res6.getInt(1);
				System.out.println("Your Account Number is -- "+acc_no);
				System.out.println("Thank You for Choosing Us !!");
			}
			else
			{
				System.out.println("No Such Account Found !!\nPress --\n1.Try Again\n2.Exit");
				int val=sc.nextInt();
				if(val==1)
				{
					forget_acc(sc, con);
				}
				else if(val==2)
				{
					System.out.println("Thank You For Choosing Us !!");
				}
				else
				{
					System.out.println("Wrong Input !!");
				}
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(res6!=null)
			{
				try
				{
					res6.close();
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}
			}
			
			if(ptm9!=null)
			{
				try
				{
					ptm9.close();
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public static void forget_pass(Scanner sc, Connection con)
	{
		System.out.println("Enter Your Account Number -- ");
		int inp=sc.nextInt();
		String pass_recovary="update bank2 set Pin=? where Acc_no=?";
		PreparedStatement ptm7=null, ptm8=null;
		ResultSet res5=null;
		String acc="select Acc_No from bank2 where Acc_no=?";
		try
		{
			con.setAutoCommit(false);
			ptm7=con.prepareStatement(acc);
			ptm7.setInt(1, inp);
			res5=ptm7.executeQuery();
			if(res5.next())
			{
				System.out.println("Enter New Password -- ");
				int new_pass=sc.nextInt();
				System.out.println("Re-Enter New Password -- ");
				int new_pass2=sc.nextInt();
				if(new_pass==new_pass2)
				{
					ptm8=con.prepareStatement(pass_recovary);
					ptm8.setInt(1, new_pass);
					ptm8.setInt(2, inp);
					ptm8.executeUpdate();
					con.commit();
					System.out.println("Your Password is Changed..");
					System.out.println("Thank You for Choosing Us !!");
				}
				else
				{
					System.out.println("Password is Not Matching ..\nPress --\n1.Try Again\n2.Exit");
					int val=sc.nextInt();
					if(val==1)
					{
						forget_pass(sc, con);
					}
					else if(val==2)
					{
						System.out.println("Thank You for Choosing Us !!");
					}
					else
					{
						System.out.println("Wrong Input !!");
					}
				}
			}
			else
			{
				System.out.println("No Such Account Found !!\nPress --\n1.Try Again\n2.Exit");
				int acc_not_found=sc.nextInt();
				if(acc_not_found==1)
				{
					forget_pass(sc, con);
				}
				else if(acc_not_found==2)
				{
					System.out.println("Thank You for Choosing Us !!");
				}
				else
				{
					System.out.println("Wrong Input !!");
				}
			}
		}
		catch(SQLException e)
		{
			try 
			{
				con.rollback();
				System.out.println("The Operation is Cancelled..\nPress --\n1.Try Again\n2.Exit");
				int rolled_back=sc.nextInt();
				if(rolled_back==1)
				{
					main(null);
				}
				else if(rolled_back==2)
				{
					System.out.println("Thank You for Choosing Us !!");
				}
				else
				{
					System.out.println("Wrong Input !!");
				}
			} 
			catch (SQLException e1) 
			{
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		finally
		{
			if(ptm8!=null)
			{
				try
				{
					ptm8.close();
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}
			}
			
			if(res5!=null)
			{
				try
				{
					res5.close();
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}
			}
			
			if(ptm7!=null)
			{
				try
				{
					ptm7.close();
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public static void login(Scanner sc, Connection con)
	{
		System.out.println("Enter your Account Number -- ");
		int id=sc.nextInt();
		System.out.println("Enter your Pin -- ");
		int pin=sc.nextInt();
		PreparedStatement ptm = null,ptm1=null;
		ResultSet res = null,res1=null;
		String user1="select * from bank2 where Acc_No=? and Pin=?";
		try
		{
			ptm=con.prepareStatement(user1);
			String check_id="select Acc_No,Pin from bank2 where Acc_No=? and Pin=?";
			ptm1=con.prepareStatement(check_id);
			ptm1.setInt(1, id);
			ptm1.setInt(2, pin);
			res1=ptm1.executeQuery();
			if(res1.next())
			{
				ptm.setInt(1, id);
				ptm.setInt(2, pin);
				res=ptm.executeQuery();
				display_name(res, sc, con, pin, id);
			}
			else
			{
				System.out.println("No Such Account Found or Wrong Account Number or Password\nPress -- \n1.Try Again\n2.Forgot Account Number?\n3.Forgot Password?\n4.Exit");
				int x=sc.nextInt();
				if(x==1)
				{
					main(null);
				}
				else if(x==2)
				{
					forget_acc(sc, con);
				}
				else if(x==3)
				{
					forget_pass(sc, con);
				}
				else if(x==4)
				{
					System.out.println("Thank You for Choosing Us !!");
				}
				else
				{
					System.out.println("Wrong Input !!");
				}
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{	
			if(ptm!=null)
			{
				try
				{
					ptm.close();
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}
			}
			
			if(res!=null)
			{
				try
				{
					res.close();
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}
			}
			
			if(ptm1!=null)
			{
				try
				{
					ptm1.close();
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}
			}
			
			if(res1!=null)
			{
				try
				{
					res1.close();
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}
			}
		}	
	}
	
	
	public static void display_name(ResultSet res,Scanner sc, Connection con, int pin, int id)
	{
		try 
		{
			if(res.next())
			{
				String name=res.getString(2);
				int bal=res.getInt(3);
				System.out.println("Hi "+name+" , Welcome to our Bank !!");
				option(sc, bal, con, pin, id);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	
	public static void option(Scanner sc,int bal, Connection con, int pin, int id)
	{
		System.out.println("Select Option -- \nPress -- \n1.Check Balance\n2.Transfer Money\n3.Exit");
		int n=sc.nextInt();
		operations(n, bal, sc, con, pin, id);
	}
	
	
	public static void operations(int n, int bal, Scanner sc, Connection con, int pin, int id)
	{
		switch (n)
		{
		case 1:
			System.out.println("Your Balance is -- "+bal);
			break;
		
		case 2:
			transfer(sc, con, pin, bal, id);
			break;
		
		case 3:
			System.out.println("Thank you for Chossing us !!");
			break;
			
		default:
			System.out.println("Wrong Input !!\nPress -- \n1.Try Again\n2.Exit");
			int n2=sc.nextInt();
			if(n2==1)
			{
				option(sc, bal, con, pin, id);
			}
			else if(n2==2)
			{
				System.out.println("Thank you for Chossing us !!");
			}
			else
			{
				System.out.println("Wrong Input !!");
			}
			break;
		}
	}
	
	
	public static void transfer(Scanner sc, Connection con, int pin, int bal, int id)
	{
		System.out.println("Enter the Account Number in Which Money to be Send -- ");
		int acc2=sc.nextInt();
		
		ResultSet res2=null,res3=null;
		PreparedStatement ptm2=null,ptm3 = null,ptm4 = null,ptm5=null;
		String send_acc="select Acc_No,Balance from bank2 where Acc_No=?";
		try
		{
			con.setAutoCommit(false);
			ptm2=con.prepareStatement(send_acc);
			ptm2.setInt(1, acc2);
			res2=ptm2.executeQuery();
			if(res2.next())
			{
				int reciver=res2.getInt(2);
				System.out.println("Enter Your Pin -- ");
				int pin2=sc.nextInt();
				if(pin2==pin)
				{
					System.out.println("Enter the Amount -- ");
					int amt=sc.nextInt();
					if(amt<=0)
					{
						System.out.println("You Can't Send 0 or Negetive Amount !!\nPress -- \n1.Try Again\n2.Exit");
						int x=sc.nextInt();
						if(x==1)
						{
							transfer(sc, con, pin, bal, id);
						}
						else if(x==2)
						{
							System.out.println("Thank You for Choosing Us !!");
						}
						else
						{
							System.out.println("Wrong Input !!");
						}
					}
					if(amt<bal && amt>0)
					{
						int sender_amt=bal-amt;
						int reciver_amt=amt+reciver;
						String update_reciver="update bank2 set Balance=? where Acc_No=?";
						ptm3=con.prepareStatement(update_reciver);
						ptm3.setInt(1, reciver_amt);
						ptm3.setInt(2, acc2);
						ptm3.executeUpdate();
						
						String update_sender="update bank2 set Balance=? where Acc_No=?";
						ptm4=con.prepareStatement(update_sender);
						ptm4.setInt(1, sender_amt);
						ptm4.setInt(2, id);
						ptm4.executeUpdate();
						con.commit();
						
						System.out.println("Money is Debited from Your Bank and Credited to Reciver Account !!\nPress -- \n1.Want to Check Your Balance\n2.Exit");
						int final_opt=sc.nextInt();
						if(final_opt==1)
						{
							String check_bal="select Balance from bank2 where Acc_No=?";		
							ptm5=con.prepareStatement(check_bal);
							ptm5.setInt(1, id);
							res3=ptm5.executeQuery();
							if(res3.next())
							{
								int final_bal=res3.getInt(1);
								System.out.println("Your Balance is -- "+final_bal);
								System.out.println("Thank You for Choosing Us !!");
							}
						}
						else if(final_opt==2)
						{
							System.out.println("Thank Your for Choosing Us !!");
						}
						else
						{
							System.out.println("Wrong Input !!");
						}
					}
					else
					{
						System.out.println("You Dont Have Sufficient Balance\nThank You for Choosing Us !!");
					}
				}
				else
				{
					System.out.println("Wrong Pin !!\nPress -- \n1.Try Again\n2.Exit");
					int x=sc.nextInt();
					if(x==1)
					{
						main(null);
					}
					else if(x==2)
					{
						System.out.println("Thank You for Choosing Us !!");
					}
					else
					{
						System.out.println("Wrong Input !!!");
					}
				}
			}
			else
			{
				System.out.println("Not a Valid Account Number !!\nPress -- \n1.Try Again\n2.Exit");
				int x=sc.nextInt();
				if(x==1)
				{
					transfer(sc, con, pin, bal, id);
				}
				else if(x==2)
				{
					System.out.println("Thank You for Choosing Us !!");
				}
				else
				{
					System.out.println("Wrong Input !!");
				}
			}
			con.commit();
		} 
		catch (SQLException e) 
		{
			try 
			{
				con.rollback();
				System.out.println("The Transaction is Cancelled..\nPress -- \n1.Try Again\n2.Exit");
				int rolled_back=sc.nextInt();
				if(rolled_back==1)
				{
					main(null);
				}
				else if(rolled_back==2)
				{
					System.out.println("Thank You for Choosing Us !!");
				}
				else
				{
					System.out.println("Wrong Input !!");
				}
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
			
			e.printStackTrace();
		}
		finally
		{
			if(res3!=null)
			{
				try
				{
					res3.close();
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}
			}
			
			if(ptm5!=null)
			{
				try
				{
					ptm5.close();
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}
			}
			
			if(ptm4!=null)
			{
				try
				{
					ptm4.close();
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}
			}
			
			if(ptm3!=null)
			{
				try
				{
					ptm3.close();
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}
			}
			
			if(res2!=null)
			{
				try
				{
					res2.close();
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}
			}
			
			if(ptm2!=null)
			{
				try
				{
					ptm2.close();
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	
	public static void main(String[] args) 
	{
		System.out.println("*** WELCOME TO PANJA BANK ***\n-----------------------------");
		Connection con=null;
		Scanner sc=new Scanner(System.in);
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Rahul?user=root&password=Rahul@2001");
			System.out.println("Press --\n1.Log in\n2.Open an Account");
			int val=sc.nextInt();
			if(val==1)
			{
				login(sc, con);
			}
			else if(val==2)
			{
				open_acc(sc, con);
			}
			else
			{
				System.out.println("Wrong Input !!");
			}
			
		}
		catch(ClassNotFoundException | SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(sc!=null)
			{
				sc.close();
			}
			
			if(con!=null)
			{
				try
				{
					con.close();
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}