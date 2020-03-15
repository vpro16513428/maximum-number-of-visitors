package test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Map.Entry;

public class test {
	static Scanner sca= new Scanner(System.in);
	static TreeMap<LocalDateTime, Integer> visitors_record = new TreeMap<LocalDateTime, Integer>();
	static int max=0;
	public static void main(String[] args) {
		intro();
		//�Y�ϥΪ̿�Jexit�hadd_visitor�^��false���}�j��
		while (add_visitor()) {}
		sca.close();
	}

	private static void intro() {
		System.out.println("No record for now, plz input new record:");
		System.out.println("Imagine this program is an access control door, it records the arrival and departure times of visitors");
		System.out.println("Each visitor is expressed as follows");
		System.out.println("arrival departure(separate by space)");
		System.out.println("Example:");
		System.out.println("2010/7/13-0:0:3 2010/7/13-0:0:5");
		System.out.println("or input 2010/7/13-0:0:3 and 2010/7/13-0:0:5 by diff line");
		System.out.println("Example:");
		System.out.println("2010/7/13-0:0:3");
		System.out.println("2010/7/13-0:0:5");
		System.out.println("type \"exit\" to terminate");
		System.out.println("type \"show\" to show the time period of maximum number of visitors");
		System.out.println("start input:");
	}
	private static boolean add_visitor() {
		String[] temp=getinput(false);
		LocalDateTime arrival;
		LocalDateTime departure;
		
		//�ѼƬ�exit
		if (temp[0].equals("exit")) {
			System.out.println("Thx for your patient!");
			return false;
		}else if (temp[0].equals("show")) {
			show();
			return true;
		//��J��ӰѼƮ�
		}else {
			arrival=LocalDateTime.parse(temp[0], DateTimeFormatter.ofPattern("y/M/d-H:m:s"));
			departure=LocalDateTime.parse(temp[1], DateTimeFormatter.ofPattern("y/M/d-H:m:s"));
		}
		//�B�z�Ѽ�
		//arrival���s�b�h���J
		if (!visitors_record.containsKey(arrival)) {
			visitors_record.put(arrival,0);
			//arrival�e����timestamp
			if (visitors_record.lowerKey(arrival)!=null) {
				visitors_record.put(arrival,visitors_record.get(visitors_record.lowerKey(arrival)));
			}
		}
		//departure���s�b�h���J
		if (!visitors_record.containsKey(departure)) {
			visitors_record.put(departure,0);
			visitors_record.put(departure,visitors_record.get(visitors_record.lowerKey(departure)));
		}
		//�qarrival��departure���e�@��timestamp��+1�H��
		for (Entry<LocalDateTime, Integer> entry : visitors_record.entrySet()) {
			if (entry.getKey().compareTo(arrival)>=0&&entry.getKey().compareTo(departure)<0) {
				visitors_record.put(entry.getKey(), entry.getValue()+1);
				if (entry.getValue()>max) {
					max=visitors_record.get(entry.getKey());
				}	
			}
		}
				
		return true;
	}
	
	private static String[] getinput(Boolean required_only_one) {
		String input=sca.nextLine();
		String[] arrSplit = input.split(" ");
		LocalDateTime arrival;
		LocalDateTime departure;
		//�u�ݨD�@�ӰѼƮ�
		if (required_only_one==true) {
			//�@�椺�L����Ѽ�
			if (arrSplit[0].equals("")) {
				System.out.println("Nothing input");
				return getinput(true);
			//�@�椺��J�j��@�ӰѼ�
			}else if (arrSplit.length>1) {
				System.out.println("You have already input a parameter, you only need to input one more parameter");
				return getinput(true);
			//�@�椺��J�@�ӰѼƬ�exit
			}else if (arrSplit[0].equals("exit")) {
				System.out.println("Cannot input another \"exit\" when a parameter has been input");
				System.out.println("Try again input a valid parameter");
				return getinput(true);
			//��J�@�ӰѼƫD���T�ɶ��榡
			}else if (!isLocalDateTime(arrSplit[0])) {
				System.out.println("Invalid input");
				System.out.println("Try again input a valid parameter");
				return getinput(true);
			}
			return arrSplit;
		//���`�ݨD�ѼƮ�(�i������өΤ@�ӥ��`�ɶ��榡�Τ@��exit)
		}else {
			//�@�椺�L����Ѽ�
			if (arrSplit[0].equals("")) {
				System.out.println("Nothing input");
				return getinput(false);
			//�@�椺��J�j���ӰѼ�
			}else if (arrSplit.length>2) {
				System.out.println("Invalid input");
				System.out.println("Try again input 1 or 2 valid parameter");
				return getinput(false);
			//�@�椺��J��ӰѼ�
			}else if (arrSplit.length==2) {
				//�䤤�@�ӰѼƫD���T�ɶ��榡
				if (!isLocalDateTime(arrSplit[0])||!isLocalDateTime(arrSplit[1])) {
					System.out.println("Invalid input");
					System.out.println("Try again input 1 or 2 valid parameter");
					return getinput(false);
				}
				arrival=LocalDateTime.parse(arrSplit[0], DateTimeFormatter.ofPattern("y/M/d-H:m:s"));
				departure=LocalDateTime.parse(arrSplit[1], DateTimeFormatter.ofPattern("y/M/d-H:m:s"));
				//departure���barrival�᭱
				if (!departure.isAfter(arrival)) {
					System.out.println("departure you input is not after arrival");
					System.out.println("Try again input 1 or 2 valid parameter");
					return getinput(false);
				}
				
				//��ӰѼƳ��O���T�ɶ��榡
				return arrSplit;
			//�@�椺��J�@�ӰѼ�
			}else {
				//��J�@��exit��show��
				if (arrSplit[0].equals("exit")||arrSplit[0].equals("show")) {
					return arrSplit;
				//��J�@�ӰѼƫD���T�ɶ��榡
				}else if (!isLocalDateTime(arrSplit[0])) {
					System.out.println("Invalid input");
					System.out.println("Try again input 1 or 2 valid parameter");
					return getinput(false);
				}
				arrival=LocalDateTime.parse(arrSplit[0], DateTimeFormatter.ofPattern("y/M/d-H:m:s"));
				String temp;
				temp=getinput(true)[0];
				departure=LocalDateTime.parse(temp, DateTimeFormatter.ofPattern("y/M/d-H:m:s"));
				//departure���barrival�᭱
				while (!departure.isAfter(arrival)) {
					System.out.println("departure you input is not after arrival");
					System.out.println("Try again input a valid parameter");
					temp=getinput(true)[0];
					departure=LocalDateTime.parse(temp, DateTimeFormatter.ofPattern("y/M/d-H:m:s"));
				}
				String[] result = new String[2];
				result[0]=arrSplit[0];
				result[1]=temp;
				return result;
			}
		}
	}
	private static boolean isLocalDateTime(String s) {
		try {
			LocalDateTime.parse(s, DateTimeFormatter.ofPattern("y/M/d-H:m:s"));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	private static void show() {
		//��X�̦h�H�ƪ��ɶ��϶�
		for (Entry<LocalDateTime, Integer> entry : visitors_record.entrySet()) {
			if (entry.getValue()==max) {
				System.out.println(entry.getKey().format(DateTimeFormatter.ofPattern("y/M/d-H:m:s"))+"~"+visitors_record.higherKey(entry.getKey()).format(DateTimeFormatter.ofPattern("y/M/d-H:m:s"))+" "+entry.getValue()+" this time period is maximum number of visitors");
			}	
		}
	}
	
}
