package com;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.domain.Multiplechoiceanswers;
import com.domain.Questions;

public class TestData {
	List<Questions> questionList;
	Map<String, List<Multiplechoiceanswers>> AnswerList;

	public TestData() {
		questionList = getQuestions();
		AnswerList = getAnswers();
	}

	public Questions[] getMultipleQuestions(String Username) {
		if (Username.equals("vdwoodie")) {
			Questions[] questarray = new Questions[questionList.size()];
			int arr = 0;
			for (Questions quest : questionList){
				questarray[arr] = quest;
				arr++;
			}
			return questarray;
		} else {
			return new Questions[]{};
		}
	}
	//
	public Questions getSingleQuestion(String userName, Integer qNo) {
		if (userName.equals("vdwoodie")) {
			for (Questions quest : questionList){
				if (quest.getDisplayorder() == qNo){
					return quest;
				}
			}		
		}
		return new Questions();
	}
	//
	public Questions getSingleQuestions(String Username, String title) {
		if (Username.equals("vdwoodie")) {
			for (Questions questvar : questionList) {
				if (questvar.getTitle().equals(title)){
				return questvar;	
				}
			}
			return new Questions();
		}else{
			return new Questions();
		}	
	}
	
	public Map<String, List<Multiplechoiceanswers>> generateMultipleChoice(String Username) {
		if (Username.equals("vdwoodie")) {
			return  AnswerList;
		} else {
			return new HashMap<>();
		}
	}

	private List<Questions> getQuestions() {
		List<Questions> testquestions = new ArrayList<>();
		testquestions.add(new Questions(1, "vdwoodie", "Q1", "What is correct syntax for main method of a java class?",
				BooleanParse(1), 1));
		testquestions.add(new Questions(2, "vdwoodie", "Q2", "Which of the following is not a keyword in java?",
				BooleanParse(0), 2));
		testquestions.add(new Questions(3, "vdwoodie", "Q3", "What is a class in java?", BooleanParse(0), 3));
		testquestions.add(
				new Questions(4, "vdwoodie", "Q4", "Primitive variables are stored on Stack.", BooleanParse(0), 4));
		testquestions.add(new Questions(5, "vdwoodie", "Q5", "Objects are stored on Stack.", BooleanParse(0), 5));
		testquestions.add(new Questions(6, "vdwoodie", "Q6", "Static functions can be accessed using null reference.",
				BooleanParse(0), 6));
		testquestions.add(new Questions(7, "vdwoodie", "Q7", "Can we compare int variable with a boolean variable?",
				BooleanParse(0), 7));
		testquestions.add(new Questions(8, "vdwoodie", "Q8",
				"What of the following is the default value of a local variable?", BooleanParse(0), 8));
		testquestions.add(new Questions(9, "vdwoodie", "Q9",
				"What of the following is the default value of an instance variable?", BooleanParse(0), 9));
		testquestions
				.add(new Questions(10, "vdwoodie", "Q10", "What is the size of byte variable?", BooleanParse(1), 10));
		testquestions
				.add(new Questions(11, "vdwoodie", "Q11", "What is the size of short variable?", BooleanParse(0), 11));
		testquestions
				.add(new Questions(12, "vdwoodie", "Q12", "What is the size of int variable?", BooleanParse(0), 12));
		testquestions
				.add(new Questions(13, "vdwoodie", "Q13", "What is the size of long variable?", BooleanParse(0), 13));
		testquestions
				.add(new Questions(14, "vdwoodie", "Q14", "What is the size of float variable?", BooleanParse(0), 14));
		testquestions
				.add(new Questions(15, "vdwoodie", "Q15", "What is the size of double variable?", BooleanParse(0), 15));
		testquestions
				.add(new Questions(16, "vdwoodie", "Q16", "What is the size of char variable?", BooleanParse(0), 16));
		testquestions.add(
				new Questions(17, "vdwoodie", "Q17", "What is the size of boolean variable?", BooleanParse(0), 17));
		testquestions.add(new Questions(18, "vdwoodie", "Q18", "Is an empty .java file a valid source file?",
				BooleanParse(0), 18));
		testquestions.add(new Questions(19, "vdwoodie", "Q19", "Can we have multiple classes in same java file?",
				BooleanParse(0), 19));
		testquestions.add(new Questions(20, "vdwoodie", "Q20", "Can we have two public classes in one java file?",
				BooleanParse(0), 20));
		testquestions.add(new Questions(21, "vdwoodie", "Q21", "What is the default value of byte variable?",
				BooleanParse(1), 21));
		testquestions.add(new Questions(22, "vdwoodie", "Q22", "What is the default value of short variable?",
				BooleanParse(0), 22));
		testquestions.add(new Questions(23, "vdwoodie", "Q23", "What is the default value of byte variable?",
				BooleanParse(0), 23));
		testquestions.add(new Questions(24, "vdwoodie", "Q24", "What is the default value of short variable?",
				BooleanParse(0), 24));
		testquestions.add(new Questions(25, "vdwoodie", "Q25", "What is the default value of int variable?",
				BooleanParse(0), 25));
		return testquestions;
	}

	private Map<String, List<Multiplechoiceanswers>> getAnswers() {
		Map<String, List<Multiplechoiceanswers>> answerMap = new HashMap<>();
		List<Multiplechoiceanswers> testAnswer = new ArrayList<>();
		testAnswer.add(new Multiplechoiceanswers(5, "Q1", "public static int mainString[]args", "A", BooleanParse(1),BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(6, "Q1", "public int mainString[]args", "B", BooleanParse(0),BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(7, "Q1", "public static void mainString[]args", "C", BooleanParse(1),BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(8, "Q1", "None of the above.", "D", BooleanParse(0), BooleanParse(0)));
		answerMap.put("Q1", cloneAnswerList(testAnswer));
		testAnswer = new ArrayList<>();
		testAnswer.add(new Multiplechoiceanswers(9, "Q2", "static", "A", BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(10, "Q2", "Boolean", "B", BooleanParse(1), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(11, "Q2", "void", "C", BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(12, "Q2", "private", "D", BooleanParse(0), BooleanParse(0)));
		answerMap.put("Q2", cloneAnswerList(testAnswer));
		testAnswer = new ArrayList<>();	
		testAnswer.add(new Multiplechoiceanswers(13, "Q3","A class is a blue print from which individual objects are created. A class can contain fields and methods to describe the behavior of an object.","A", BooleanParse(1), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(14, "Q3", "class is a special data type.", "B", BooleanParse(0),BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(15, "Q3", "class is used to allocate memory to a data type.", "C",BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(16, "Q3", "none of the above.", "D", BooleanParse(0), BooleanParse(0)));
		answerMap.put("Q3", cloneAnswerList(testAnswer));
		testAnswer = new ArrayList<>();
		testAnswer.add(new Multiplechoiceanswers(17, "Q4", "True", "A", BooleanParse(1), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(18, "Q4", "False", "B", BooleanParse(0), BooleanParse(0)));
		answerMap.put("Q4", cloneAnswerList(testAnswer));
		testAnswer = new ArrayList<>();
		testAnswer.add(new Multiplechoiceanswers(19, "Q5", "True", "A", BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(20, "Q5", "False", "B", BooleanParse(1), BooleanParse(0)));
		answerMap.put("Q5", cloneAnswerList(testAnswer));
		testAnswer = new ArrayList<>();
		testAnswer.add(new Multiplechoiceanswers(21, "Q6", "True", "A", BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(22, "Q6", "False", "B", BooleanParse(1), BooleanParse(0)));
		answerMap.put("Q6", cloneAnswerList(testAnswer));
		testAnswer = new ArrayList<>();
		testAnswer.add(new Multiplechoiceanswers(23, "Q7", "True", "A", BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(24, "Q7", "False", "B", BooleanParse(1), BooleanParse(0)));
		answerMap.put("Q7", cloneAnswerList(testAnswer));
		testAnswer = new ArrayList<>();
		testAnswer.add(new Multiplechoiceanswers(25, "Q8", "null", "A", BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(26, "Q8", "0", "B", BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(27, "Q8", "Depends upon the type of variable", "C", BooleanParse(0),BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(28, "Q8", "Not assigned", "D", BooleanParse(1), BooleanParse(0)));
		answerMap.put("Q8", cloneAnswerList(testAnswer));
		testAnswer = new ArrayList<>();
		testAnswer.add(new Multiplechoiceanswers(29, "Q9", "null", "A", BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(30, "Q9", "0", "B", BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(31, "Q9", "Depends upon the type of variable", "C", BooleanParse(1),BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(32, "Q9", "Not assigned", "D", BooleanParse(0), BooleanParse(0)));
		answerMap.put("Q9", cloneAnswerList(testAnswer));
		testAnswer = new ArrayList<>();
		testAnswer.add(new Multiplechoiceanswers(33, "Q10", "8 bit", "A", BooleanParse(1), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(34, "Q10", "16 bit", "B", BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(35, "Q10", "32 bit", "C", BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(36, "Q10", "64 bit", "D", BooleanParse(1), BooleanParse(0)));
		answerMap.put("Q10", cloneAnswerList(testAnswer));
		testAnswer = new ArrayList<>();
		testAnswer.add(new Multiplechoiceanswers(37, "Q11", "8 bit", "A", BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(38, "Q11", "16 bit", "B", BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(39, "Q11", "32 bit", "C", BooleanParse(1), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(40, "Q11", "64 bit", "D", BooleanParse(0), BooleanParse(0)));
		answerMap.put("Q11", cloneAnswerList(testAnswer));
		testAnswer = new ArrayList<>();
		testAnswer.add(new Multiplechoiceanswers(41, "Q12", "8 bit", "A", BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(42, "Q12", "16 bit", "B", BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(43, "Q12", "32 bit", "C", BooleanParse(1), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(44, "Q12", "64 bit", "D", BooleanParse(0), BooleanParse(0)));
		answerMap.put("Q12", cloneAnswerList(testAnswer));
		testAnswer = new ArrayList<>();
		testAnswer.add(new Multiplechoiceanswers(45, "Q13", "8 bit", "A", BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(46, "Q13", "16 bit", "B", BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(47, "Q13", "32 bit", "C", BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(48, "Q13", "64 bit", "D", BooleanParse(1), BooleanParse(0)));
		answerMap.put("Q13", cloneAnswerList(testAnswer));
		testAnswer = new ArrayList<>();
		testAnswer.add(new Multiplechoiceanswers(49, "Q14", "8 bit", "A", BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(50, "Q14", "16 bit", "B", BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(51, "Q14", "32 bit", "C", BooleanParse(1), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(52, "Q14", "64 bit", "D", BooleanParse(0), BooleanParse(0)));
		answerMap.put("Q14", cloneAnswerList(testAnswer));
		testAnswer = new ArrayList<>();
		testAnswer.add(new Multiplechoiceanswers(53, "Q15", "8 bit", "A", BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(54, "Q15", "16 bit", "B", BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(55, "Q15", "32 bit", "C", BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(56, "Q15", "64 bit", "D", BooleanParse(1), BooleanParse(0)));
		answerMap.put("Q15", cloneAnswerList(testAnswer));
		testAnswer = new ArrayList<>();
		testAnswer.add(new Multiplechoiceanswers(57, "Q16", "8 bit", "A", BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(58, "Q16", "16 bit", "B", BooleanParse(1), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(59, "Q16", "32 bit", "C", BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(60, "Q16", "64 bit", "D", BooleanParse(0), BooleanParse(0)));
		answerMap.put("Q16", cloneAnswerList(testAnswer));
		testAnswer = new ArrayList<>();
		testAnswer.add(new Multiplechoiceanswers(61, "Q17", "8 bit", "A", BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(62, "Q17", "16 bit", "B", BooleanParse(1), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(63, "Q17", "32 bit", "C", BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(64, "Q17", "not precisely defined", "D", BooleanParse(0), BooleanParse(0)));
		answerMap.put("Q17", cloneAnswerList(testAnswer));
		testAnswer = new ArrayList<>();
		testAnswer.add(new Multiplechoiceanswers(65, "Q18", "True", "A", BooleanParse(1), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(66, "Q18", "False", "B", BooleanParse(0), BooleanParse(0)));
		answerMap.put("Q18", cloneAnswerList(testAnswer));
		testAnswer = new ArrayList<>();
		testAnswer.add(new Multiplechoiceanswers(67, "Q19", "True", "A", BooleanParse(1), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(68, "Q19", "False", "B", BooleanParse(0), BooleanParse(0)));
		answerMap.put("Q19", cloneAnswerList(testAnswer));
		testAnswer = new ArrayList<>();
		testAnswer.add(new Multiplechoiceanswers(69, "Q20", "True", "A", BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(70, "Q20", "False", "B", BooleanParse(1), BooleanParse(0)));
		answerMap.put("Q20", cloneAnswerList(testAnswer));
		testAnswer = new ArrayList<>();
		testAnswer.add(new Multiplechoiceanswers(71, "Q21", "0", "A", BooleanParse(1), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(72, "Q21", "0.0", "B", BooleanParse(1), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(73, "Q21", "null", "C", BooleanParse(1), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(74, "Q21", "undefined", "D", BooleanParse(0), BooleanParse(0)));
		answerMap.put("Q21", cloneAnswerList(testAnswer));
		testAnswer = new ArrayList<>();
		testAnswer.add(new Multiplechoiceanswers(75, "Q22", "0.0", "A", BooleanParse(1), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(76, "Q22", "0", "B", BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(77, "Q22", "null", "C", BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(78, "Q22", "undefined", "D", BooleanParse(0), BooleanParse(0)));
		answerMap.put("Q22", cloneAnswerList(testAnswer));
		testAnswer = new ArrayList<>();
		testAnswer.add(new Multiplechoiceanswers(79, "Q23", "0", "A", BooleanParse(1), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(80, "Q23", "0.0", "B", BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(81, "Q23", "null", "C", BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(82, "Q23", "not defined", "D", BooleanParse(0), BooleanParse(0)));
		answerMap.put("Q23", cloneAnswerList(testAnswer));
		testAnswer = new ArrayList<>();
		testAnswer.add(new Multiplechoiceanswers(83, "Q24", "0.0", "A", BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(84, "Q24", "0", "B", BooleanParse(1), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(85, "Q24", "null", "C", BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(86, "Q24", "not defined", "D", BooleanParse(0), BooleanParse(0)));
		answerMap.put("Q24", cloneAnswerList(testAnswer));
		testAnswer = new ArrayList<>();
		testAnswer.add(new Multiplechoiceanswers(87, "Q25", "0", "A", BooleanParse(1), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(88, "Q25", "0.0", "B", BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(89, "Q25", "null", "C", BooleanParse(0), BooleanParse(0)));
		testAnswer.add(new Multiplechoiceanswers(90, "Q25", "not defined", "D", BooleanParse(0), BooleanParse(0)));
		answerMap.put("Q25", cloneAnswerList(testAnswer));
		return answerMap;
	}

	private boolean BooleanParse(int flag) {
		if (flag == 1) {
			return true;
		} else {
			return false;
		}
	}

	private List<Multiplechoiceanswers> cloneAnswerList(List<Multiplechoiceanswers> ansList) {
		List<Multiplechoiceanswers> tempList = new ArrayList<>();
		for (Multiplechoiceanswers ansvar : ansList) {
			ansvar.setCandidateanswer(false);
			tempList.add(ansvar);
		}
		return tempList;
	}

}
