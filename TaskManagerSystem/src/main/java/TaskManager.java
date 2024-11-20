import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.ReentrantLock;

public class TaskManager {
	private final List<Task> tasks = new ArrayList<>();
	private final ExecutorService executorService = Executors.newFixedThreadPool(3);
	private final ReentrantLock lock = new ReentrantLock();
	
	public void addTaskAsync(String name, String description) {
		Future<?> future = executorService.submit(()->{
			lock.lock();
			try {
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
		}
	}
	//新增任務
	private void addTask(String name, String description){
		try {
			nullName(name);//檢查名稱是否無效
			Task task = new Task(name, description);
			tasks.add(task);
			task.getTime();
			System.out.println("成功添加任務：\n" + task + "\n");
		}
		catch(IllegalArgumentException e){
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
			System.out.println("任務執行失敗：" + e.getMessage());
		}
	}
	
	//刪除任務
	public void deleteTask(String name){
		Task task = findTaskByName(name);
		if(task == null) {
			System.out.println(String.format("未找到任務%s\n", name));
		}
		else{
			tasks.remove(task);
			System.out.println(String.format("已刪除任務%s\n尚餘%d個任務未完成！\n", name, tasks.size()));
		}
	}
	
	//標記任務已完成
	public String markTaskAsCompleted(String name) {
		Task task = findTaskByName(name);
		if(task == null) {
			return String.format("未找到任務%s\n", name);
		}
		task.complete();
		return String.format("任務%s已完成！\n", name);	
	}
	
	//修改任務描述
	public String editTaskDescription(String name, String description) {
		Task task = findTaskByName(name);
		if(task == null) {
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
	            return String.format("未找到任務%s\n", name);
	        }
			task.setTaskName(updateName);
			return String.format("已修改任務名稱為：%s\n", updateName);
		}
		catch(IllegalArgumentException e) {
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
	        System.out.println("等待執行緒池關閉時出現錯誤：" + e.getMessage());
	        executorService.shutdownNow();
	    }
	}
}