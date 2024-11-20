import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.ReentrantLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskManager {
	private static final Logger logger = LoggerFactory.getLogger(TaskManager.class);
	
	private final List<Task> tasks = new ArrayList<>();
	private final ExecutorService executorService = Executors.newFixedThreadPool(3);
	private final ReentrantLock lock = new ReentrantLock();
	
	public void addTaskAsync(String name, String description) {
		Future<?> future = executorService.submit(()->{
			lock.lock();
			try {
				logger.debug("嘗試新增任務：\n任務名稱：{}\n任務描述：{}\n", name, description);
				addTask(name, description);
			}
			finally {
				lock.unlock();
			}
		});
		try {
			future.get();
		}
		catch(Exception e) {
			System.out.println("任務執行失敗：" + e.getMessage());
			logger.error("新增任務時發生異常：\n{}\n", e.getMessage(), e);

		}
	}
	//新增任務
	private void addTask(String name, String description){
		try {
			nullName(name);//檢查名稱是否無效
			Task task = new Task(name, description);
			tasks.add(task);
			task.getTime();
			logger.info("新增任務成功：\n{}\n", task);
			System.out.println("成功添加任務：\n" + task + "\n");
		}
		catch(IllegalArgumentException e){
			logger.warn("新增任務失敗，原因：\n{}\n", e.getMessage());			
			System.out.println("添加任務失敗：\n" + e.getMessage() + "\n");
		}
	}
	
	public void displayTaskAsync() {
		Future<?> future = executorService.submit(()->{
			lock.lock();
			try {
				displayTasks();
			}
			finally {
				lock.unlock();
			}
		});
		try {
			future.get();
		}
		catch(Exception e) {
			System.out.println("任務執行失敗：" + e.getMessage());
			logger.error("任務執行時發生異常：\n{}\n", e.getMessage(), e);
		}
	}
	
	//顯示所有任務
	public void displayTasks(){
		if(tasks.isEmpty()) {
			System.out.println("目前沒有任務！\n");
			System.out.println("提示：請新增任務以便管理！\n");
		}
		else {
			StringBuilder taskList = new StringBuilder("顯示所有任務：\n");
			int index = 1;
			for(Task task : tasks) {
				taskList.append(index++).append(". ").append(task);
				taskList.append("\n\n");
			}
			System.out.println(taskList.toString());
		}
	}
	
	public void deleteTaskAsync(String name) {
		Future<?> future = executorService.submit(()->{
			lock.lock();
			try {
				logger.debug("嘗試刪除{}任務\n", name);
				deleteTask(name);
			}
			finally {
				lock.unlock();
			}
		});
		try {
			future.get();
		}
		catch(Exception e) {
			logger.error("刪除任務時發生異常：\n{}\n", e.getMessage(), e);
			System.out.println("任務執行失敗：" + e.getMessage());
		}
	}
	
	//刪除任務
	public void deleteTask(String name){
		Task task = findTaskByName(name);
		if(task == null) {
			logger.warn("刪除任務失敗，未找到{}任務\n", name);
			System.out.println(String.format("未找到任務%s\n", name));
		}
		else{
			tasks.remove(task);
			logger.info("成功刪除{}任務\n", name);
			System.out.println(String.format("已刪除任務%s\n尚餘%d個任務未完成！\n", name, tasks.size()));
		}
	}
	
	//標記任務已完成
	public String markTaskAsCompleted(String name) {
		Task task = findTaskByName(name);
		if(task == null) {
			logger.warn("標記任務完成失敗，未找到{}任務\n", name);
			return String.format("未找到任務%s\n", name);
		}
		task.complete();
		return String.format("任務%s已完成！\n", name);	
	}
	
	//修改任務描述
	public String editTaskDescription(String name, String description) {
		Task task = findTaskByName(name);
		if(task == null) {
			logger.warn("修改任務描述失敗，未找到{}任務\n", name);
			return String.format("未找到任務%s\n", name);
		}
		task.setTaskDescription(description);
		return String.format("已修改任務描述為：%s\n", description);
	}
	
	//更新任務名稱
	public String editTaskName(String name, String updateName) {
		try {
			nullName(updateName);
			Task task = findTaskByName(name);
			if (task == null) {
				logger.warn("更新任務名稱，未找到{}任務\n", name);
	            return String.format("未找到任務%s\n", name);
	        }
			task.setTaskName(updateName);
			return String.format("已修改任務名稱為：%s\n", updateName);
		}
		catch(IllegalArgumentException e) {
			logger.error("更新任務時發生異常：\n{}\n", e.getMessage(), e);
			return String.format("更新任務名稱失敗：\n%s\n", e.getMessage());
		}	
	}
	
	//一致性驗證
	private Task findTaskByName(String name) {
		for(Task task : tasks) {
			if(task.getTaskName().equals(name)) {
				return task;
			}
		}
		return null;
	}
	
	//處理無效任務名稱
	private void nullName(String name) throws IllegalArgumentException{
		if(name == null || name.trim().isEmpty()) {
			throw new IllegalArgumentException("任務名稱不能為空！\n");
		}
	}
	
	//關閉執行緒池
	public void shutdown() {
		executorService.shutdown();
		try {
	        if (!executorService.awaitTermination(30, TimeUnit.SECONDS)) {
	            System.out.println("有未完成的任務，強制關閉執行緒池！");
	            executorService.shutdownNow();
	        }
	    } catch (InterruptedException e) {
			logger.error("等待執行緒池關閉時發生異常：\n{}\n", e.getMessage(), e);
	        System.out.println("等待執行緒池關閉時出現錯誤：" + e.getMessage());
	        executorService.shutdownNow();
	    }
	}
}