import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TaskManager {
	private final List<Task> tasks = new ArrayList<>();
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	//新增任務
	public String addTask(String name, String description){
		try {
			Task task = new Task(name, description);
			tasks.add(task);
			String time = LocalDateTime.now().format(formatter);
			return String.format("成功添加任務：\n%s\n任務添加時間：%s", task, time);
		}
		catch(IllegalArgumentException e){
			return String.format("添加任務失敗：\n%s", e.getMessage());
		}
	}
	
	//顯示所有任務
	public String displayTasks(){
		if(tasks.isEmpty()) {
			return "目前沒有任務";
		}
		else {
			StringBuilder taskList = new StringBuilder("顯示所有任務：\n");
			int index = 0;
			for(Task task : tasks) {
				taskList.append(index++).append(". ").append(task);
				if(task.isCompleted()) {
					taskList.append("任務已完成");
				}
				taskList.append("\n");
			}
			return taskList.toString();
		}
	}
	
	//刪除任務
	public String deleteTask(String name){
		if(tasks.isEmpty()) {
			return "目前沒有任務";
		}
		else{
			for(Iterator<Task> iterator = tasks.iterator(); iterator.hasNext();) {
				Task task = iterator.next();
				if(task.getTaskName().equals(name)) {
					iterator.remove();
					return String.format("已刪除任務%s", name);
				}
			}
			return String.format("未找到任務%s", name);
		}
	}
	
	//標記任務已完成
	public String isCompleted(String name) {
		Task task = findTaskByName(name);
		if(task == null) {
			return String.format("未找到任務%s", name);
		}
		task.complete();
		return String.format("任務%s已完成：", task);	
	}
	
	//修改任務描述
	public String editTaskDescription(String name, String description) {
		Task task = findTaskByName(name);
		if(task == null) {
			return "未尋到該任務";
		}
		task.setTaskDescription(description);
		return String.format("已修改任務描述為：%s\n", task);
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
}
