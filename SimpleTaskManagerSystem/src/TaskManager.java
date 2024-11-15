import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TaskManager {
	private final List<Task> tasks = new ArrayList<>();
	
	//新增任務
	public String addTask(String name, String description){
		try {
			nullName(name);//檢查名稱是否無效
			Task task = new Task(name, description);
			tasks.add(task);
			task.getTime();
			return String.format("成功添加任務：\n%s\n", task);
		}
		catch(IllegalArgumentException e){
			return String.format("添加任務失敗：\n%s\n", e.getMessage());
		}
	}
	
	//顯示所有任務
	public String displayTasks(){
		if(tasks.isEmpty()) {
			return "目前沒有任務！\n";
		}
		else {
			StringBuilder taskList = new StringBuilder("顯示所有任務：\n");
			int index = 1;
			for(Task task : tasks) {
				taskList.append(index++).append(". ").append(task);
				taskList.append("\n\n");
			}
			return taskList.toString();
		}
	}
	
	//刪除任務
	public String deleteTask(String name){
		Task task = findTaskByName(name);
		if(task == null) {
			return String.format("未找到任務%s\n", name);
		}
		else{
			tasks.remove(task);
			return String.format("已刪除任務%s\n尚餘%d個任務未完成！\n", name, tasks.size());
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
}
