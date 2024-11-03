public class Task {
	private String taskName;
	private String taskDescription;
	private boolean isCompleted;
	
	//建構子，初始化任務的屬性
	public Task(String name, String description) {
		if(name == null || name.trim().isEmpty()) {
			throw new IllegalArgumentException("任務名稱不能為空，請提出有效任務名稱");
		}
		this.taskName = name;
		this.taskDescription = description;
		this.isCompleted = false;
	}
	
	//設置setter方法供修改任務屬性
	public void setTaskName(String name) {
		if(name != null && !name.trim().isEmpty()) {
			this.taskName = name;
		}
	}
	public void setTaskDescription(String description) {
		if(description != null && !description.trim().isEmpty()) {
			this.taskDescription = description;
		}
	}
	public void complete() {
		this.isCompleted = true;
	}
	
	//用Getter方法間接取得任務屬性資訊
	public String getTaskName() {
		return taskName;
	}
	public String getTaskDescription() {
		return taskDescription;
	}
	public boolean isCompleted() {
		return isCompleted;
	}
	
	//改寫toString並格式化
	@Override
	public String toString() {
		return String.format("任務名稱：%s\n任務具體描述：%s\n是否已完成：%b", taskName, taskDescription, isCompleted?"已完成":"未完成");
	}
}
