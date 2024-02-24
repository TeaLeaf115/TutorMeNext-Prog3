import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;

public class Main {

	private static final String PRINTER_NAME = "OUTPUT/Printer.txt";
	private static final Time EARLIEST_TIME = new Time(14, 15);
	private static final Time LATEST_TIME = new Time(15, 30);
	private static final int PAUSE_LENGTH = 20;
	private static final Scanner inputScanner = new Scanner(System.in);
	private static FileWriter fw;

	public static void main(String [] args) {
		Welcome();
		String fileName = GetFileName();
		try {
			fw = new FileWriter(PRINTER_NAME);
			File file = new File(fileName);
			Scanner requestFile = new Scanner(file);
			ListQueue<HelpRequest> fileQueue = FillFileQueue(requestFile);
			Teacher teacher = GetTeacherInfo();
			Time startTime = GetTime("start", EARLIEST_TIME);
			Time endTime = GetTime("end", startTime);
			Result results = new Result();
			PrintHeading(teacher, startTime, endTime);
			RunAndPrintSimulation(fileQueue, teacher, startTime, endTime, results);
			PrintWrapUp(teacher, results);
			fw.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		finally {
			inputScanner.close();
		}
	}

	private static void Welcome() {
		clearScreen();
		System.out.println("Welcome to the \"MeNext\" Simulation");
		System.out.println("==================================");
		System.out.println();
		System.out.println("This program simulates the help given to students after school.");
		inputScanner.nextLine();
	}

	private static String GetFileName() {
		clearScreen();
		System.out.println("Get the name of the file of help requests");
		System.out.println("=========================================");
		System.out.println();
		System.out.println("Enter the name of the file:  ");
		return inputScanner.nextLine();
	}

	private static void ErrorMessage(String error) {
		clearScreen();
		System.out.println("Error Message");
		System.out.println("=============");
		System.out.println();
		System.out.println(error);
		inputScanner.nextLine();
	}

	private static ListQueue<HelpRequest> FillFileQueue(Scanner requestFile) {
		clearScreen();
		System.out.println("Reading help requests");
		System.out.println("=====================");
		System.out.println();
		System.out.print("reading");
		ListQueue<HelpRequest> fileQueue = new ListQueue<>();
		while (requestFile.hasNextLine()) {
			fileQueue.add(ReadHelpRequest(requestFile));
			System.out.print(".");
		}
		inputScanner.nextLine();
		return fileQueue;
	}

	private static Teacher GetTeacherInfo() {
		clearScreen();
		System.out.println("Enter teacher information");
		System.out.println("=========================");
		System.out.println();
		Teacher teacher = new Teacher();
		System.out.print("Enter the teacher's name:  ");
		teacher.setName(inputScanner.nextLine());
		System.out.println();

		Experience experience;
		do {
			System.out.print("Enter experience:\n\t(\"first year\"|\"intermediate\"|\"experienced\"):  ");
			experience = new Experience(inputScanner.nextLine());
			System.out.println();
		} while (!Experience.LegalExperience(experience.toString()));
		teacher.setExperience(experience);
		return teacher;
	}

	private static Time GetTime(String whichTime, Time earliestTime) {
		clearScreen();
		System.out.println("Enter time");
		System.out.println("==========");
		System.out.println();
		Time time = new Time();
		do {
			String timeString;
			do {
				System.out.print("Enter " + whichTime + " time (" + earliestTime + ".." + Main.LATEST_TIME + "):  ");
				timeString = inputScanner.nextLine();
				System.out.println();
			} while (!Time.LegalTimeString(timeString));
			time.setFromString(timeString);
		} while (time.compareTo(earliestTime) < 0 || time.compareTo(Main.LATEST_TIME) > 0);
		return time;
	}

	private static void PrintHeading(Teacher teacher, Time startTime, Time endTime) {
		System.out.println("Simulation File");
		System.out.println("===============");
		System.out.println();
		System.out.println();
		System.out.println("Run Duration");
		System.out.println("============");
		System.out.println(startTime + " - " + endTime);
		System.out.println();
		System.out.println();
		System.out.println("Teacher");
		System.out.println("=======");
		System.out.println();
		PrintTeacher(teacher, true);
		System.out.println();
		System.out.println();
	}

	private static void RunAndPrintSimulation(ListQueue<HelpRequest> fileQueue, Teacher teacher, Time startTime, Time endTime, Result results) throws IOException{
		System.out.println("Simulation Run");
		System.out.println("==============");
		System.out.println();
		Time currentTime = new Time(startTime);
		while (currentTime.compareTo(endTime) <= 0) {
			clearScreen();
			System.out.println("Simulation Run");
			System.out.println("==============");
			System.out.println();
			System.out.println("Time:  " + currentTime + "    ");
			try {
				Thread.sleep(PAUSE_LENGTH);
			} catch(Exception e) {e.printStackTrace();}
			RunAndPrintOneMinute(currentTime, teacher, fileQueue, results);
			RunAndPrintOneMinute(currentTime, teacher, fileQueue, results);
			currentTime.nextMinute();
		}
		System.out.println();
		System.out.println();
		System.out.println("Simulation done...");
		inputScanner.nextLine();
	}

	private static void PrintWrapUp(Teacher teacher, Result results) throws IOException{
		fw.write("Teacher At End\n");
		fw.write("==============\n");
		fw.write("/n/n");
		PrintTeacher(teacher);
		fw.write("\n\n");
		PrintResult(results);		
	}

	private static HelpRequest ReadHelpRequest(Scanner requestFile) {
		Queue<String> q = new LinkedList<>();
		for (int i = 0; i < 6; i++) {
			q.add(requestFile.nextLine());
		}
		return new HelpRequest(q);
	}

	private static void PrintTeacher(Teacher teacher) throws IOException{
		fw.write("Teacher:\n");
		fw.write("\t\"" + teacher.getName() + "\"\n");
		fw.write("\t" + teacher.getExperience() + "\n");
		fw.write("Current ");
		PrintRequest(teacher.getCurrentRequest());
		fw.write("Teacher ");
		PrintStack(teacher.getRequestStack());
		fw.write("Teacher ");
		PrintQueue(teacher.getRequestQueue());
	}

	private static void PrintTeacher(Teacher teacher, boolean t) {
		System.out.println("Teacher:");
		System.out.println("\t\"" + teacher.getName() + "\"");
		System.out.println("\t" + teacher.getExperience());
		System.out.print("Current ");
		PrintRequest(teacher.getCurrentRequest(), t);
		System.out.print("Teacher ");
		PrintStack(teacher.getRequestStack(), t);
		System.out.print("Teacher ");
		PrintQueue(teacher.getRequestQueue(), t);
	}

	private static void RunAndPrintOneMinute(Time currentTime, Teacher teacher, ListQueue<HelpRequest> fileQueue, Result results) throws IOException{
			fw.write("Time:  " + currentTime + "    \n");
		if (!fileQueue.isEmpty() && fileQueue.peek().getTime().compareTo(currentTime) <= 0) {
			while (!fileQueue.isEmpty() && fileQueue.peek().getTime().compareTo(currentTime) <= 0) {
				HelpRequest request = fileQueue.remove();
                
				fw.write("\t" + teacher.getName() + "\" accepts request:  \"");
				fw.write(request.getName() + "\" @ " + request.getTime());
				fw.write(", " + request.getDemeanor());
				TeacherAcceptsRequest(request, teacher);
			}
		}
		else
			Help(currentTime, teacher, results);
	}

	private static void PrintResult(Result results) throws IOException{
		fw.write("Simulation summary:\n");
		fw.write("===================\n");
		fw.write("\n");
		fw.write("Number helped:  " + results.getNumberHelped());
		fw.write("\nTotal wait time:  " + results.getTotalWaitTime());
		fw.write("\nAverage wait time:  ");
		if (results.getNumberHelped() > 0)
			fw.write(Double.toString((float) results.getTotalWaitTime() / (float) results.getNumberHelped()));
		else
			fw.write("0.0");
		fw.write("\n");
	}

	private static void	PrintRequest(HelpRequest request) throws IOException{
		fw.write("Request:  \"" + request.getName() + "\", @ " + request.getTime() + "\n");
		fw.write("\tDemeanor:  " + request.getDemeanor() + "\n");
		fw.write("\tError:  " + request.getError());
		fw.write("\tMinutes with help:  " + request.getMinutesWithHelp() + "\n");
		fw.write("\tMinutes without help:  " + request.getMinutesWithoutHelp() + "\n");
	}
	private static void	PrintRequest(HelpRequest request, boolean t) {
		System.out.println("Request:  \"" + request.getName() + "\", @ " + request.getTime());
		System.out.println("\tDemeanor:  " + request.getDemeanor());
		System.out.println("\tError:  " + request.getError());
		System.out.println("\tMinutes with help:  " + request.getMinutesWithHelp());
		System.out.println("\tMinutes without help:  " + request.getMinutesWithoutHelp());
	}

	private static void	PrintStack(ArrayStack<HelpRequest> stack) throws IOException{
		fw.write("Request Stack:  \n");
		ArrayStack<HelpRequest> tempStack = new ArrayStack<>();
		while (!stack.isEmpty()) {
			HelpRequest request = stack.pop();
			tempStack.push(request);
			PrintRequest(request);
		}
		while (!tempStack.isEmpty())
			stack.push(tempStack.pop());
	}

	private static void	PrintQueue(ListQueue<HelpRequest> queue) throws IOException {
		fw.write("Request Queue:  \n");
		ListQueue<HelpRequest> tempQueue = new ListQueue<>();
		while (!queue.isEmpty()) {
			HelpRequest request = queue.remove();
			tempQueue.add(request);
			PrintRequest(request);
		}
		while (!tempQueue.isEmpty())
			queue.add(tempQueue.remove());
	}

	private static void	PrintStack(ArrayStack<HelpRequest> stack, boolean t) {
		System.out.println("Request Stack:  ");
		ArrayStack<HelpRequest> tempStack = new ArrayStack<>();
		while (!stack.isEmpty()) {
			HelpRequest request = stack.pop();
			tempStack.push(request);
			PrintRequest(request, t);
		}
		while (!tempStack.isEmpty())
			stack.push(tempStack.pop());
	}

	private static void	PrintQueue(ListQueue<HelpRequest> queue, boolean t) {
		System.out.println("Request Queue:  ");
		ListQueue<HelpRequest> tempQueue = new ListQueue<>();
		while (!queue.isEmpty()) {
			HelpRequest request = queue.remove();
			tempQueue.add(request);
			PrintRequest(request, t);
		}
		while (!tempQueue.isEmpty())
			queue.add(tempQueue.remove());
	}

	private static void TeacherAcceptsRequest(HelpRequest request, Teacher teacher) throws IOException{
		if (teacher.getExperience().equals(Experience.FIRST_YEAR)) {
			if (request.getDemeanor().equals(Demeanor.RUDE))
				teacher.getRequestStack().push(request);
			else if (request.getDemeanor().equals(Demeanor.POLITE))
				teacher.getRequestQueue().add(request);
			else
				throw new IllegalStateException("TeacherAcceptsRequest(), illegal Demeanor."); 
		}
		else if (teacher.getExperience().equals(Experience.INTERMEDIATE))
			teacher.getRequestQueue().add(request);
		else if (teacher.getExperience().equals(Experience.EXPERIENCED)) {
			if (request.getDemeanor().equals(Demeanor.RUDE))
				teacher.getRequestQueue().add(request);
			else if (request.getDemeanor().equals(Demeanor.POLITE)) {
				if (request.getError().equals(Error.COMPILING) || request.getError().equals(Error.LINKING))
					teacher.getRequestStack().push(request);
				else if (request.getError().equals(Error.RUNTIME))
					teacher.getRequestQueue().add(request);
				else
					throw new IllegalStateException("TeacherAcceptsRequest(), illegal Error.");
			}
			else
				throw new IllegalStateException("TeacherAcceptsRequest(), illegal Demeanor.");
		}
		else
			throw new IllegalStateException("TeacherAcceptsRequest(), illegal Experience.");
	}

	private static void Help(Time currentTime, Teacher teacher, Result results) throws IOException{
		if (!teacher.getCurrentRequest().getError().equals(Error.UNDEFINED)) {
			HelpRequest helpRequest = teacher.getCurrentRequest();
			helpRequest.setMinutesWithHelp(helpRequest.getMinutesWithHelp() - 1);
			teacher.setCurrentRequest(helpRequest);
			if (teacher.getCurrentRequest().getMinutesWithHelp() == 0)
				fw.write("\tProblem fixed:  \"");
			else
				fw.write("\tHelping:  \"");
			fw.write(teacher.getCurrentRequest().getName() + "\" (" + teacher.getCurrentRequest().getMinutesWithHelp() + ")\n");
		}
		if (teacher.getCurrentRequest().getMinutesWithHelp() == 0) {
			if (!teacher.getRequestStack().isEmpty()) {
				teacher.setCurrentRequest(teacher.getRequestStack().pop());
				results.setNumberHelped(results.getNumberHelped() + 1);
				results.setTotalWaitTime(results.getTotalWaitTime() + currentTime.inMinutes() - teacher.getCurrentRequest().getTime().inMinutes());
            
				fw.write("\tFrom stack:  \"" + teacher.getCurrentRequest().getName());
				fw.write("\" now current student (" + teacher.getCurrentRequest().getMinutesWithHelp() + "), ");
				fw.write(teacher.getCurrentRequest().getError() + "\n");
			}
			else if (!teacher.getRequestQueue().isEmpty()) {
				teacher.setCurrentRequest(teacher.getRequestQueue().remove());
				results.setNumberHelped(results.getNumberHelped() + 1);
				results.setTotalWaitTime(results.getTotalWaitTime() + currentTime.inMinutes() - teacher.getCurrentRequest().getTime().inMinutes());

				fw.write("\tFrom queue:  \"" + teacher.getCurrentRequest().getName());
				fw.write("\" now current student (" + teacher.getCurrentRequest().getMinutesWithHelp() + "), ");
				fw.write(teacher.getCurrentRequest().getError() + "\n");
			}
			else {
				teacher.setCurrentRequest(new HelpRequest());
				fw.write("\tNo one to help.\n");
			}
		}
	}

	public static void clearScreen() {  
		System.out.print("\033[H\033[2J");  
		System.out.flush();
	}
}