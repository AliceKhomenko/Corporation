package corp;

import java.util.ArrayList;

public class Worker {
public int uid;
public ArrayList spec = new ArrayList();
public ArrayList works = new ArrayList();
public String[] TaskName = {"writing code", "drawing model","testing the program","saling the service","making the report"};
public String[] SpecName = {"Developer","Designer","Tester","Booker","Manager","Director"};
public int[] SpecPay = {200,50,100,10000,12000,20000};
public boolean WorkerStatus;
public boolean fixPay;
public int TaskTime;
public int WeekTime;
public int[] CurrentMonthWork = new int[4]; 
public int Pay;
}