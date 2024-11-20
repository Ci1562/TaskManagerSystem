import java.util.InputMismatchException;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	public static void main(String[] args) {
		TaskManager taskManager = new TaskManager();
		Scanner scanner = new Scanner(System.in);
		boolean running = true;
		String delimiter = "**********";
		String updateName;
		//主程式的日誌測試
		logger.info("Main class INFO message");
		do{
			try {
				System.out.println(delimiter + "\n請選擇您的操作：");
				System.out.println("1. 新增任務");
				System.out.println("2. 顯示所有任務");
				System.out.println("3. 刪除任務");
				System.out.println("4. 標記任務完成");
				System.out.println("5. 修改任務名稱");
				System.out.println("6. 修改任務描述");
				System.out.println("0. 退出");
				System.out.print("請輸入您的選項：");
				
				int choice = scanner.nextInt();
				scanner.nextLine();
				System.out.println(delimiter);
				
				switch(choice) {
					case 1:
						System.out.print("請輸入任務名稱：");
						String name = scanner.nextLine();
						System.out.print("請輸入任務描述：");
						String description = scanner.nextLine();
						taskManager.addTaskAsync(name, description);
						break;
					case 2:
						taskManager.displayTaskAsync();
						break;
					case 3:
						System.out.print("請輸入欲刪除的任務名稱：");
						name = scanner.nextLine();
						taskManager.deleteTaskAsync(name);
						break;
					case 4:
						System.out.print("請輸入已完成的任務名稱：");
						name = scanner.nextLine();
						System.out.println(taskManager.markTaskAsCompleted(name));
						break;
					case 5:
						System.out.print("請輸入欲更新的任務名稱：");
						name = scanner.nextLine();
						System.out.print("請輸入更新後的任務名稱：");
						updateName = scanner.nextLine();
						System.out.println("\n" + taskManager.editTaskName(name, updateName));
						break;
					case 6:
						System.out.print("請輸入欲修改描述的任務名稱：");
						name = scanner.nextLine();
						System.out.print("請輸入欲修改的任務描述：");
						description = scanner.nextLine();
						System.out.println("\n" + taskManager.editTaskDescription(name, description));
						break;
					case 0:
						running = false;
						logger.info("程式正常退出");
						System.out.println("程式已退出： ）");
						taskManager.shutdown();
						break;
					default:
						System.out.println("無效選項，請輸入數字作為有效選項");
					}
				}catch(InputMismatchException e){
					System.out.println("輸入格式錯誤，請輸入數字作為選項！");
					scanner.nextLine();
				}
			}while(running);			
		scanner.close();

	}
}