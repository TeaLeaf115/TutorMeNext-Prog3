import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;
import java.io.File;
import java.io.FileNotFoundException;

public class Main {
	
	// final class properties
	private static final String PRINTER_NAME = "Printer.txt";
	private static final Time EARLIEST_TIME = new Time(14, 15);
	private static final Time LATEST_TIME = new Time(15, 30);
	private static final int PAUSE_LENGTH = 20;
	private static final Scanner inp = new Scanner(System.in);

	// level 0

	/*
		preconditions:	args, none.  Any file actually opened as a
			request file is formatted as prescribed.
		postconditions:  None.
	*/
	public static void main(String [] args) {
		Welcome();
		String fileName = GetFileName();
		try {
			File f = new File(fileName);
			Scanner requestFile = new Scanner(f);
			// if (requestFile.hasOpenErrorOccurred())
			// 	ErrorMessage("Could not open: \"" + fileName + "\"");
		// else {
			ListQueue<HelpRequest> fileQueue = FillFileQueue(requestFile);
			Teacher teacher = GetTeacherInfo();
			Time startTime = GetTime("start", EARLIEST_TIME, LATEST_TIME);
			Time endTime = GetTime("end", startTime, LATEST_TIME);
			Result results = new Result();
			// Scanner printer = new Scanner(PRINTER_NAME);
			PrintHeading(teacher, startTime, endTime);
			RunAndPrintSimulation(fileQueue, teacher, startTime, endTime, results);
			PrintWrapUp(teacher, results);
		// }
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			// printer.close();
			inp.close();
		}
	}


	// level 1

	/*
		preconditions:	None.
		postconditions:  None.
	*/
	private static void Welcome() {
		clearScreen();
		System.out.println("Welcome to the \"MeNext\" Simulation");
		System.out.println("==================================");
		System.out.println();
		System.out.println("This program simulates the help");
		System.out.println("given to students after school.");
		inp.nextLine();
	}

	/*
		preconditions:	None.
		postconditions:  Returns a possible file name.
	*/
	private static String GetFileName() {
		clearScreen();
		System.out.println("Get the name of the file of help requests");
		System.out.println("=========================================");
		System.out.println();
		System.out.println("Enter the name of the file:  ");
		return inp.nextLine();
	}

	/*
		preconditions:	error has been filled.
		postconditions:  None.
	*/
	private static void ErrorMessage(String error) {
		clearScreen();
		System.out.println("Error Message");
		System.out.println("=============");
		System.out.println();
		System.out.println(error);
		inp.nextLine();
	}

	/*
		preconditions:	requestFile has been opened for reading and
			is foramtted properly.
		postconditions:  Returns a ListQueue that has been filled
			with help requests from the file.  The requestFile
			has been read to through the end.
	*/
	private static ListQueue<HelpRequest> FillFileQueue(Scanner requestFile)	{
		clearScreen();
		System.out.println("Reading help requests");
		System.out.println("=====================");
		System.out.println();
		System.out.print("reading");
		ListQueue<HelpRequest> fileQueue = new ListQueue<>();
		int numEntries = 0;
		while (requestFile.hasNext())
			numEntries++;

		numEntries /= 6;

		for (int entry = 0; entry < numEntries; entry++) {
			fileQueue.add(ReadHelpRequest(requestFile));
		}
		
		// while (!requestFile.hasNextLine()) {
		// 	HelpRequest helpRequest = ReadHelpRequest(requestFile);
		// 	fileQueue.add(helpRequest);
		// 	System.out.print(".");
		// }
		// System.out.println("Finished");
		inp.nextLine();
		return fileQueue;
	}

	/*
		preconditions:  None.
		postconditions:  A Teacher is returned with all fields filled
			with valid values.
	*/
	private static Teacher GetTeacherInfo() {
		clearScreen();
		System.out.println("Enter teacher information");
		System.out.println("=========================");
		System.out.println();
		Teacher teacher = new Teacher();
		System.out.print("Enter the teacher\'s name:  ");
		teacher.setName(inp.nextLine());
		System.out.println();
		
		Experience experience;
		do {
			System.out.print("Enter experience:\n\t(\"first year\"|\"intermediate\"|\"experienced\"):  ");
			experience = new Experience(inp.nextLine());
			System.out.println();
		} while (!Experience.LegalExperience(experience.toString()));
		teacher.setExperience(experience);
		return teacher;
	}

	/*
		preconditions:	whichTime is a prompt.  earliestTime <= latestTime.
		postconditions:  A Time is returned that is >= earliestTime and
			<= latestTime.  whichTime, earliestTime, and latestTime are not 
			changed.
	*/
	private static Time GetTime(String whichTime, Time earliestTime, Time latestTime) {
		clearScreen();
		System.out.println("Enter time");
		System.out.println("==========");
		System.out.println();
		Time time = new Time();
		do {
			String timeString;
			do {
				System.out.print("Enter " + whichTime + " time (" + earliestTime + ".." + latestTime + "):  ");
				timeString = inp.nextLine();
				System.out.println();
			} while (!Time.LegalTimeString(timeString));
			time.setFromString(timeString);
		} while (time.compareTo(earliestTime) < 0 || time.compareTo(latestTime) > 0);
		return time;
	}

	/*
		preconditions:	teacher, startTime, and endTime have been filled.
			printer is opened for writing.
		postconditions:  teacher, startTime, endTime are unchanged, the
			printer has had a heading sent.
	*/
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
		PrintTeacher(teacher);
		System.out.println();
		System.out.println();
	}

	/*
		preconditions:	fileQueue has been filled from a file
			of HelpRequests, teacher, startTime, and endTime have been fileed,
			results has been initialized, and printer is opened for output.
		postconditions:  The fileQueue has been processed through any
			HelpRequests with times <= endTime.  startTime and endTime have
			not been changed, results has been updated to reflect the processing
			as has the printer.
	*/
	private static void RunAndPrintSimulation(ListQueue<HelpRequest> fileQueue, Teacher teacher, Time startTime, Time endTime, Result results) {
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


			/*
			PREVIOUS CODE CHUNK vvvvv:
				currentTime = currentTime.nextMinute();
			*/
			currentTime.nextMinute();
		}
		System.out.println();
		System.out.println();
		System.out.println("Simulation done...");
		inp.nextLine();
	}

	/*
		preconditions:  teacher and results are in a condition 
			resulting from running the simulation.  printer
			is opened and ready for output.
		postconditions:teacher and results, none.  printer
			has had a summary printed to it.
	*/
	private static void PrintWrapUp(Teacher teacher, Result results) {
		System.out.println("Teacher At End");
		System.out.println("==============");
		System.out.println();
		System.out.println();
		PrintTeacher(teacher);
		System.out.println();
		System.out.println();
		PrintResult(results);		
	}


	// level 2

	/*
		preconditions:  requestFile is opened for reading and
			formatted properly.
		postconditions:  requestFile has been read past a HelpRequest.
			That HelpRequest is returned.
	*/
	private static HelpRequest ReadHelpRequest(Scanner requestFile) {
		Queue<String> q = new LinkedList<>();
		for (int i = 0; i < 6; i++) {
			q.add(requestFile.nextLine());
		}
		
		HelpRequest helpRequest = new HelpRequest(q);
		// Time aTime = new Time();
		// String timeString = requestFile.nextLine();
		// aTime.setFromString(timeString);
		// helpRequest.setTime(aTime);
		// helpRequest.setName(requestFile.nextLine());
		// helpRequest.setDemeanor(new Demeanor(requestFile.nextLine()));
		// helpRequest.setError(new Error(requestFile.nextLine()));
		// helpRequest.setMinutesWithHelp(requestFile.nextInt());
		// requestFile.nextLine();
		// helpRequest.setMinutesWithoutHelp(requestFile.nextInt());
		// requestFile.nextLine();
		return helpRequest;
	}

	/*
		preconditions:  teacher has been filled.  printer is opened
			for output.
		postconditions:  teacher is unchanged.  The contents of teacher 
			have been sent to printer.
	*/
	private static void PrintTeacher(Teacher teacher) {
		System.out.println("Teacher:");
		System.out.println("\t\"" + teacher.getName() + "\"");
		System.out.println("\t" + teacher.getExperience());
		System.out.print("Current ");
		PrintRequest(teacher.getCurrentRequest());
		System.out.print("Teacher ");
		PrintStack(teacher.getRequestStack());
		System.out.print("Teacher ");
		PrintQueue(teacher.getRequestQueue());
	}


	/*
		preconditions:  currentTime is the current time, teacher,
			fileQueue, and results reflect all previous processing, 
			printer is ready for output.
		postconditions:   currentTime, unchanged.  teacher,
			fileQueue, results, and printer have been updated to reflect
			one minute of processing, 
			printer is ready for output.
	*/
	private static void RunAndPrintOneMinute(Time currentTime, Teacher teacher, ListQueue<HelpRequest========> fileQueue, Result results) {
		System.out.println("Time:  " + currentTime + "    ");
		if (!fileQueue.isEmpty() && ((HelpRequest) fileQueue.peek()).getTime().compareTo(currentTime) <= 0) {
			while (!fileQueue.isEmpty() && ((HelpRequest) fileQueue.peek()).getTime().compareTo(currentTime) <= 0) {
				HelpRequest request = (HelpRequest) fileQueue.remove();
				System.out.print("\t" + teacher.getName() + "\" accepts request:  \"");
				System.out.print(request.getName() + "\" @ " + request.getTime());
				System.out.println(", " + request.getDemeanor());
				TeacherAcceptsRequest(request, teacher);
			}
		}
		else
			Help(currentTime, teacher, results);
	}

	/*
		preconditions:  results reflects all previous processing.  printer
			is ready for output.
		postconditions:  results is unchanged.  printer has had
			a summary sent to it.
	*/
	private static void PrintResult(Result results) {
		System.out.println("Simulation summary:");
		System.out.println("===================");
		System.out.println();
		System.out.println("Number helped:  " + results.getNumberHelped());
		System.out.println("Total wait time:  " + results.getTotalWaitTime());
		System.out.print("Average wait time:  ");
		if (results.getNumberHelped() > 0)
			System.out.println(((float) results.getTotalWaitTime() / (float) results.getNumberHelped()));
		else
			System.out.println("0.0");
	}


	// level 3

	/*
		preconditions:  request is filled.  printer is ready for output.
		postconditions:  request is unchanged.  printer has had the request 
			sent to it.
	*/
	private static void	PrintRequest(HelpRequest request) {
		System.out.println("Request:  \"" + request.getName() + "\", @ " + request.getTime());
		System.out.println("\tDemeanor:  " + request.getDemeanor());
		System.out.println("\tError:  " + request.getError());
		System.out.println("\tMinutes with help:  " + request.getMinutesWithHelp());
		System.out.println("\tMinutes without help:  " + request.getMinutesWithoutHelp());
	}

	/*
		preconditions:  stack is filled.  printer is ready for output.
		postconditions:  stack is unchanged!!! printer has had the stack 
			sent to it.
	*/
	private static void	PrintStack(ArrayStack<HelpRequest> stack) {
		System.out.println("Request Stack:  ");
		ArrayStack<HelpRequest> tempStack = new ArrayStack<>();
		while (!stack.isEmpty()) {
			HelpRequest request = (HelpRequest)stack.pop();
			tempStack.push(request);
			PrintRequest(request);
		}
		while (!tempStack.isEmpty())
			stack.push(tempStack.pop());
	}

	/*
		preconditions:  queue is filled.  printer is ready for output.
		postconditions:  queue is unchanged!!! printer has had the queue 
			sent to it.
	*/
	private static void	PrintQueue(ListQueue<HelpRequest> queue) {
		System.out.println("Request Queue:  ");
		ListQueue<HelpRequest> tempQueue = new ListQueue<>();
		while (!queue.isEmpty()) {
			HelpRequest request = (HelpRequest) queue.remove();
			tempQueue.add(request);
			PrintRequest(request);
		}
		while (!tempQueue.isEmpty())
			queue.add(tempQueue.remove());
	}


	// level 4

	/*
		preconditions:  request is filled.  teacher reflects all
			previous processing.
		postconditions:  request is unchanged.  teacher has added
			the request to the stack or queue as appropriate.
	*/
	private static void TeacherAcceptsRequest(HelpRequest request, Teacher teacher) {
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

	/*
		preconditions:  currentTime is the current time, teacher, results, 
			and printer all reflect previous processing.
		postconditions:  currentTime is unchanged.  teacher, results, 
			and printer all reflect the process of Help.
	*/
	private static void Help(Time currentTime, Teacher teacher, Result results) {
		if (!teacher.getCurrentRequest().getError().equals(Error.UNDEFINED)) {

			HelpRequest helpRequest = teacher.getCurrentRequest();
			helpRequest.setMinutesWithHelp(helpRequest.getMinutesWithHelp() - 1);
			teacher.setCurrentRequest(helpRequest);
			if (teacher.getCurrentRequest().getMinutesWithHelp() == 0)
				System.out.print("\tProblem fixed:  \"");
			else
				System.out.print("\tHelping:  \"");
			System.out.println(teacher.getCurrentRequest().getName() + "\" (" + teacher.getCurrentRequest().getMinutesWithHelp() + ")");
		}
		if (teacher.getCurrentRequest().getMinutesWithHelp() == 0) {
			if (!teacher.getRequestStack().isEmpty()) {
				teacher.setCurrentRequest((HelpRequest) teacher.getRequestStack().pop());
				results.setNumberHelped(results.getNumberHelped() + 1);
				results.setTotalWaitTime(results.getTotalWaitTime() + currentTime.inMinutes() - teacher.getCurrentRequest().getTime().inMinutes());
				System.out.print("\tFrom stack:  \"" + teacher.getCurrentRequest().getName());
				System.out.print("\" now current student (" + teacher.getCurrentRequest().getMinutesWithHelp() + "), ");
				System.out.println(teacher.getCurrentRequest().getError());
			}
			else if (!teacher.getRequestQueue().isEmpty()) {
				teacher.setCurrentRequest((HelpRequest) teacher.getRequestQueue().remove());
				results.setNumberHelped(results.getNumberHelped() + 1);
				results.setTotalWaitTime(results.getTotalWaitTime() + currentTime.inMinutes() - teacher.getCurrentRequest().getTime().inMinutes());
				System.out.print("\tFrom queue:  \"" + teacher.getCurrentRequest().getName());
				System.out.print("\" now current student (" + teacher.getCurrentRequest().getMinutesWithHelp() + "), ");
				System.out.println(teacher.getCurrentRequest().getError());
			}
			else {
				teacher.setCurrentRequest(new HelpRequest());
				System.out.println("\tNo one to help.");
			}
		}
	}

	public static void clearScreen() {  
		System.out.print("\033[H\033[2J");  
		System.out.flush();
	}
}