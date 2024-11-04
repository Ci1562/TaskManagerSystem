import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		TaskManager taskManager = new TaskManager();
		Scanner scanner = new Scanner(System.in);
		boolean running = true;
		String delimiter = "**********";
		
		while(running) {
			System.out.println(delimiter + "請選擇您的操作：");
			System.out.println("1. 新增任務");
			System.out.println("2. 顯示所有任務");
			System.out.println("3. 刪除任務");
			System.out.println("4. 標記任務完成");
			System.out.println("5. 修改任務描述");
			System.out.println("0. 退出");
			System.out.print("請輸入您的選項：");
			
			int choice = scanner.nextInt();
			scanner.nextLine();
			System.out.println(delimiter);
			
			switch(choice) {
				case 1:
					System.out.print("\n請輸入任務名稱：");
					String name = scanner.nextLine();
					System.out.print("請輸入任務描述：");
					String description = scanner.nextLine();
					System.out.println("\n"+taskManager.addTask(name, description));
					break;
				case 2:
					System.out.println("\n" + taskManager.displayTasks());
					System.out.println(delimiter);
					break;
				case 3:
					System.out.print("請輸入欲刪除的任務名稱：");
					name = scanner.nextLine();
					System.out.println(taskManager.deleteTask(name));
					System.out.println(delimiter);
					break;
				case 4:
					System.out.print("\n請輸入已完成的任務名稱：");
					name = scanner.nextLine();
					System.out.println(taskManager.isCompleted(name));
					break;
				case 5:
					System.out.print("請輸入欲修改描述的任務名稱：");
					name = scanner.nextLine();
					System.out.print("請輸入欲修改的任務描述：");
					description = scanner.nextLine();
					System.out.println("\n" + taskManager.editTaskDescription(name, description));
					System.out.println(delimiter);
					break;
				case 0:
					running = false;
					System.out.println("程式已退出： ）");
					break;
				default:
					System.out.println("無效選項，請輸入有效選項");
			}
		}
		scanner.close();
	}
}
