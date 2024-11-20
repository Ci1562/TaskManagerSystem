# Task Manager System

## 📝 專案簡介
這是一個用 Java 開發的簡易任務管理系統，設計目的是練習物件導向程式設計、多執行緒處理與 Maven 專案結構。

---

## ⚙️ 系統需求
- **Java:** 17 或以上
- **Maven:** 3.8.1 或以上

---

## 📂 專案結構
```plaintext
TaskManagerSystem/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── (default package)/
│   │   │       ├── Main.java
│   │   │       └── TaskManager.java
│   │   └── resources/
│   ├── test/
│       └── java/
├── pom.xml
└── README.md
```

---

## 🚀 功能特色
1. **任務管理功能：**
   - 新增、刪除、編輯、完成任務
   - 支援任務的日期與時間格式化
2. **多執行緒支援：**
   - 使用多執行緒來模擬任務執行
3. **錯誤處理：**
   - 提供清晰的例外處理訊息

---

## 📖 學習歷程
### **1. 專案重建**
- 在重建 Maven 專案時，學會了如何正確設定 `src/main/java` 和 `src/main/resources` 作為 Source Folder。
- 解決了 Eclipse 不正確識別專案結構的問題。

### **2. 多執行緒設計**
- 學習如何使用 Java 的 `Thread` 和 `Runnable` 接口來模擬並行處理。
- 解決多執行緒環境中的資源共享問題。

### **3. Git 與 GitHub 管理**
- 清理了專案中的多餘檔案，重新初始化 Git 儲存庫，並學會了如何覆蓋遠端歷史紀錄。

