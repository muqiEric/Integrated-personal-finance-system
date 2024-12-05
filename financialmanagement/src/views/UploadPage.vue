<template>
  <div class="upload-page">
    <!-- 导航栏 -->
    <NavigationBar />

    <!-- 页面内容 -->
    <h1>用户上传页面</h1>

    <!-- 拖拽上传区域 -->
    <div
        class="upload-box"
        @dragover.prevent
        @drop="handleFileDrop"
        @dragleave="dragLeave"
        @dragenter="dragEnter"
    >
      <p v-if="!dragging">拖入 Excel 文件到此处，或点击选择文件</p>
      <p v-else>释放鼠标上传文件</p>
      <input type="file" ref="fileInput" accept=".xls,.xlsx" @change="handleFileSelect" hidden />
    </div>

    <!-- 上传按钮 -->
    <button @click="confirmUpload" :disabled="!selectedFile" class="upload-button">
      确认上传
    </button>

    <!-- 导出按钮 -->
    <button @click="exportReport" class="export-button">
      导出收支明细报告
    </button>
  </div>
</template>

<script>
import NavigationBar from "../components/NavigationBar.vue";
import * as XLSX from "xlsx";

export default {
  name: "UploadPage",
  components: {
    NavigationBar,
  },
  data() {
    return {
      selectedFile: null, // 保存选择的文件
      dragging: false, // 是否正在拖拽文件
      transactions: [
        // 模拟的交易数据（可以从后台 API 获取）
        { id: 1, trans_time: "2023-11-21", trans_type: "收入", amount: 5000, note: "工资" },
        { id: 2, trans_time: "2023-11-20", trans_type: "支出", amount: -200, note: "家庭采购" },
      ],
    };
  },
  methods: {
    // 拖拽事件：文件进入框
    dragEnter() {
      this.dragging = true;
    },

    // 拖拽事件：文件离开框
    dragLeave() {
      this.dragging = false;
    },

    // 处理文件拖拽上传
    handleFileDrop(event) {
      this.dragging = false;
      const file = event.dataTransfer.files[0];
      if (file && (file.type === "application/vnd.ms-excel" || file.type.includes("spreadsheet"))) {
        this.selectedFile = file;
        this.$refs.fileInput.value = ""; // 清空文件选择框
      } else {
        alert("请上传有效的 Excel 文件！");
      }
    },

    // 处理文件选择框选择
    handleFileSelect(event) {
      const file = event.target.files[0];
      if (file && (file.type === "application/vnd.ms-excel" || file.type.includes("spreadsheet"))) {
        this.selectedFile = file;
      } else {
        alert("请上传有效的 Excel 文件！");
      }
    },

    // 确认上传文件
    confirmUpload() {
      if (!this.selectedFile) {
        alert("请先选择文件！");
        return;
      }

      // 使用 FileReader 读取文件内容
      const reader = new FileReader();
      reader.onload = (e) => {
        const data = new Uint8Array(e.target.result);
        const workbook = XLSX.read(data, { type: "array" });
        const firstSheetName = workbook.SheetNames[0];
        const worksheet = workbook.Sheets[firstSheetName];
        const jsonData = XLSX.utils.sheet_to_json(worksheet);
        console.log("解析的 Excel 数据：", jsonData);
        alert("文件上传成功！数据已解析到控制台。");
      };
      reader.readAsArrayBuffer(this.selectedFile);
    },

    // 导出收支明细报告
    exportReport() {
      const worksheet = XLSX.utils.json_to_sheet(this.transactions);
      const workbook = XLSX.utils.book_new();
      XLSX.utils.book_append_sheet(workbook, worksheet, "收支明细报告");
      XLSX.writeFile(workbook, "收支明细报告.xlsx");
      alert("收支明细报告已导出！");
    },
  },
};
</script>

<style scoped>
/* 页面整体样式 */
.upload-page {
  font-family: Arial, sans-serif;
  padding: 20px;
  text-align: center;
  color: #333;
}

/* 顶部导航栏的占位由 NavigationBar.vue 控制 */

/* 拖拽上传区域样式 */
.upload-box {
  border: 2px dashed #007bff;
  border-radius: 8px;
  padding: 40px;
  margin: 20px auto;
  width: 50%;
  text-align: center;
  color: #555;
  cursor: pointer;
  transition: border-color 0.3s ease;
}

.upload-box:hover {
  border-color: #0056b3;
}

.upload-box p {
  margin: 0;
  font-size: 16px;
}

/* 按钮样式 */
.upload-button,
.export-button {
  margin: 20px;
  padding: 10px 20px;
  font-size: 16px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  color: white;
  background-color: #007bff;
}

.upload-button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.upload-button:hover:not(:disabled),
.export-button:hover {
  background-color: #0056b3;
}
</style>
