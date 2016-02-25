package corp;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import corp.Worker;

public class Model {

	public static void main(String[] args) 
	{
		ArrayList WorkerList = new ArrayList();
		ArrayList AllSpecs = new ArrayList();
		ArrayList AllTasks = new ArrayList();
        int Freelance  = 0;  
        int FreelancePay = 150;
		int[] freelanceWeek = new int[4];
		
		Random rand = new Random();
        int Numbers = rand.nextInt(91)+10;// Numbers of workers from 10 till 100
        
        
        
        for (int i=0;i<Numbers;i++)
        	{
        	Worker CurrentWorker = new Worker();
        	CurrentWorker.uid = i;
        	CurrentWorker.WorkerStatus = true;
        	CurrentWorker.TaskTime = 0;
        	CurrentWorker.WeekTime = 0;
        	CurrentWorker.fixPay = false;
        	CurrentWorker.Pay=0;
        	//specialization and pay of every worker
        	for (int j=0;j<6;j++)                  
        		{
        		boolean[] arraySpec = new boolean[6];
        		arraySpec[j] = rand.nextBoolean();
        		if (arraySpec[j]==true)
        		{	
        			CurrentWorker.spec.add(CurrentWorker.SpecName[j]);
        		if (j<5)
        			CurrentWorker.works.add(CurrentWorker.TaskName[j]);
        		if (j<3)
        			CurrentWorker.Pay=CurrentWorker.Pay+CurrentWorker.SpecPay[j];
        		else
        			{
        			if (j==3) 
            			CurrentWorker.Pay = CurrentWorker.SpecPay[3];
        			if (j==4) 
            			CurrentWorker.Pay = CurrentWorker.SpecPay[4];
        			if (j==5) 
        			CurrentWorker.Pay = CurrentWorker.SpecPay[5];
        			CurrentWorker.fixPay = true;
        			}
        			AllSpecs.add(CurrentWorker.SpecName[j]);
        		}
        		}
        	if (CurrentWorker.spec.size()==0)       //if worker hasn't any specialization
        	   {
        		int someIndex=rand.nextInt(5);
        		CurrentWorker.spec.add(CurrentWorker.SpecName[someIndex]);
        		if (someIndex<5)
        			CurrentWorker.works.add(CurrentWorker.TaskName[someIndex]);
        		if (someIndex<3)
        			CurrentWorker.Pay=CurrentWorker.SpecPay[someIndex];
        		else
        			{if (someIndex==3) 
            			CurrentWorker.Pay = CurrentWorker.SpecPay[3];
        			if (someIndex==4) 
            			CurrentWorker.Pay = CurrentWorker.SpecPay[4];
        			if (someIndex==5) 
        			CurrentWorker.Pay = CurrentWorker.SpecPay[5];
        			CurrentWorker.fixPay = true;
        			}
        		AllSpecs.add(CurrentWorker.SpecName[someIndex]);
        		}
        	WorkerList.add(CurrentWorker);
        	}
        int count_direct=Collections.frequency(AllSpecs, "Director"); //a count of directors,bookers and managers in firm 
        int count_buh=Collections.frequency(AllSpecs, "Booker");
        int count_manage=Collections.frequency(AllSpecs, "Manager");
        if (count_direct==0)                      //if the company hasn't any director
        	{
        	int someIndex=rand.nextInt(Numbers);
        	Worker CurrentWorker = new Worker();
        	CurrentWorker = (Worker) WorkerList.get(someIndex);
        	CurrentWorker.spec.add("Director");
        	WorkerList.set(someIndex, CurrentWorker);
        	AllSpecs.add("Director");	
        	}
        if (count_buh==0)							 //if the company hasn't any booker
    		{
        	int someIndex=rand.nextInt(Numbers);
        	Worker CurrentWorker = new Worker();
        	CurrentWorker = (Worker) WorkerList.get(someIndex);
        	CurrentWorker.spec.add("Booker");
        	WorkerList.set(someIndex, CurrentWorker);
        	AllSpecs.add("Booker");
    		}
        if (count_manage==0)						//if the company hasn't any manager
    		{
        	int someIndex=rand.nextInt(Numbers);
        	Worker CurrentWorker = new Worker();
        	CurrentWorker = (Worker) WorkerList.get(someIndex);
        	CurrentWorker.spec.add("Manager");
        	WorkerList.set(someIndex, CurrentWorker);
        	AllSpecs.add("Manager");
    		}
        
    for (int week=0;week<4;week++)       //tasks are for 4 weeks 
    {
    	for (int hours=0;hours<40;hours++)  //director makes tasks for 40 hours a week
    	{
    	Worker CurrentWorker = new Worker();
    	Task HourTasks = new Task();
    	HourTasks.NumbersTask = rand.nextInt(Numbers-1)+1;
		HourTasks.HourTasks = new int [HourTasks.NumbersTask];
		for (int i=0;i<HourTasks.NumbersTask;i++)   //creating  random tasks
			{
			HourTasks.HourTasks[i] = rand.nextInt(5);
			int someIndex =HourTasks.HourTasks[i];
			AllTasks.add(HourTasks.TaskName[someIndex]);
			}
		for (int i=0;i<AllTasks.size();i++)
			{ 
			boolean statusTask = true;
			int timeTask = rand.nextInt(2)+1;
			String NewTask = (String) AllTasks.get(i);
			for (int j=0;j<Numbers;j++)
			{
				CurrentWorker = (Worker) WorkerList.get(j);
				if ((statusTask==true) & (CurrentWorker.WorkerStatus==true) & (CurrentWorker.works.contains(NewTask)) & ((CurrentWorker.WeekTime+timeTask)<41))				
				{ 	
					statusTask = false;
					CurrentWorker.WorkerStatus=false;
					CurrentWorker.TaskTime=timeTask;
					CurrentWorker.WeekTime=CurrentWorker.WeekTime+timeTask;					
					
				}
				else
					{
					if (CurrentWorker.TaskTime>1)
						CurrentWorker.TaskTime--;
					else 
					{
						CurrentWorker.WorkerStatus=true;
						CurrentWorker.TaskTime=0;
					}
					}
			}
			if (statusTask == true)
				Freelance=Freelance + timeTask;	
			
		}
		AllTasks.clear(); 
	}	
    	
	for (int j=0;j<Numbers;j++)
	{
		Worker CurrentWorker = new Worker();
		CurrentWorker = (Worker) WorkerList.get(j);
		CurrentWorker.CurrentMonthWork[week]=CurrentWorker.WeekTime;   //a week time is written in the array  CurrentMonthWork
		CurrentWorker.WeekTime=0;
	}
	freelanceWeek[week] = Freelance;
	Freelance = 0;
	 
    }
	FileWriter writer;
	try {
		writer = new FileWriter("/home/user/Report.txt",false);   //writing a report in the file Report.txt in the directory /home/user/
		writer.write("UID \t   1st week,h \t   2nd week,h \t   3rd week,h \t   4th week,h \t   Pay,grn \t\t   Sum \t\t Datails \r\n");
		for (int i=0;i<Numbers;i++)
		{int sum=0;
		Worker CurrentWorker = new Worker();
		 CurrentWorker = (Worker) WorkerList.get(i);
		 writer.write(" "+i);
				for (int j=0;j<4;j++)
				{
					writer.write("\t\t"+CurrentWorker.CurrentMonthWork[j]);
					sum=sum+CurrentWorker.CurrentMonthWork[j];
				}
				
			writer.write("\t\t"+CurrentWorker.Pay);
			if (CurrentWorker.fixPay==true)
				 writer.write("\t\t"+CurrentWorker.Pay); 			
			else
				writer.write("\t\t"+sum*CurrentWorker.Pay);
			String catname = "";
			for (int k = 0; k < CurrentWorker.spec.size(); k++) 
				catname = catname + CurrentWorker.spec.get(k) + " ";
				writer.write("\t"+catname+"\r\n");
				}
		int freesum = 0;
		writer.write("Freelance \t");//anceWeek[0]+"\t\t+)"
		for (int j=0;j<4;j++)
			{
			 writer.write(freelanceWeek[j]+"\t\t");
			 freesum=freesum+freelanceWeek[j];
		 }
		 writer.write(FreelancePay+"\t\t");
		 writer.write(Integer.toString(freesum*FreelancePay));
        writer.flush();
        writer.close();
	} catch (IOException e) {
		e.printStackTrace();
	}
    
       
	
	}
}
	