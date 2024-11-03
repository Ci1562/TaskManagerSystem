import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TaskManager {
	private final List<Task> tasks = new ArrayList<>();
	
	//新增任務
	public String addTask(String name, String description){
		try {
			Task task = new Task(name, description);
			tasks.add(task);
			return String.format("成功添加任務：\n%s", task);
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
			for(Task task : tasks) {
				taskList.append("・").append(task).append("\n");
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
					tasks.remove(task);
					return String.format("已刪除任務%s", tasks);
				}
			}
			return String.format("未找到任務%s", name);
		}
	}	
}
