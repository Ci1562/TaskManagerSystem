import java.util.ArrayList;

public class TaskManager {
	private ArrayList<Task> tasks;
	
	//建立初始化的空任務陣列
	public TaskManager() {
		tasks = new ArrayList<Task>();
	}
	
	//新增任務
	public String addTask(String name, String description){
		Task task = new Task(name, description);
		tasks.add(task);
		return String.format("已添加新任務：\n%s", task);
	}
	//顯示所有任務
	public String displayTask(){
		if(tasks.isEmpty()) {
			return "目前沒有任務";
		}
		else {
			StringBuilder taskList = new StringBuilder("顯示所有任務：\n");
			for(Task task : tasks) {
				taskList.append(task).append("\n");
			}
			return taskList.toString();
		}
	}
	//刪除任務
	public String deleteTask(String name){
		if(tasks.isEmpty()) {
			return "目前沒有任務";
		}
		else if(name == null || name.trim().isEmpty()){
			return "請提供有效任務名稱";
		}
		else{
			for(Task task : tasks) {
				if(task.getTaskName().equals(name)) {
					tasks.remove(task);
					return String.format("已刪除任務：\n%s", tasks);
				}
			}
			return "未找到該任務";
		}
	}	
}
