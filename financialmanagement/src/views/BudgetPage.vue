<template>
  <div class="budget-page">
    <!-- 头部导航栏 -->
    <NavigationBar />

    <!-- 预算管理总览 -->
    <section class="budget-overview">
      <h2>预算管理</h2>
      <div class="budget-summary">
        <div class="budget-category" v-for="(category, index) in budgetCategories" :key="index">
          <h3>{{ category.name }}</h3>
          <div class="budget-progress">
            <div
                class="progress-bar"
                :style="{ width: calculateProgress(category) + '%' }"
                :class="getProgressBarClass(category)"
            ></div>
          </div>
          <p>预算：￥{{ category.budget }}  |  已用：￥{{ category.spent }}  |  剩余：￥{{ category.remaining }}</p>
        </div>
      </div>
    </section>

    <!-- 设置预算 -->
    <section class="set-budget">
      <h3>设置预算</h3>
      <form @submit.prevent="saveBudget">
        <div v-for="(category, index) in budgetCategories" :key="index" class="category-input">
          <label>{{ category.name }}:</label>
          <input type="number" v-model="category.budget" placeholder="设置预算金额" />
        </div>
        <button type="submit">保存预算</button>
      </form>
    </section>

    <!-- 提醒区域 -->
    <section v-if="showReminder" class="budget-alert">
      <p class="alert-message">{{ alertMessage }}</p>
    </section>
  </div>
</template>

<script>
import NavigationBar from "../components/NavigationBar.vue";

export default {
  name: "BudgetManagementPage",
  components: {
    NavigationBar,
  },
  data() {
    return {
      // 模拟预算类别数据
      budgetCategories: [
        { name: "食品", budget: 1500, spent: 1200, remaining: 300 },
        { name: "交通", budget: 800, spent: 600, remaining: 200 },
        { name: "娱乐", budget: 500, spent: 600, remaining: -100 }, // 负数表示超支
        { name: "医疗", budget: 300, spent: 100, remaining: 200 },
      ],
      showReminder: false, // 控制提醒显示
      alertMessage: "", // 提醒信息
    };
  },
  methods: {
    // 计算预算进度百分比
    calculateProgress(category) {
      return ((category.spent / category.budget) * 100).toFixed(2);
    },

    // 获取进度条的颜色
    getProgressBarClass(category) {
      if (category.spent >= category.budget) return "over-budget"; // 超支
      if (category.spent >= category.budget * 0.8) return "close-to-budget"; // 接近预算
      return "under-budget"; // 正常使用
    },

    // 保存用户输入的预算
    saveBudget() {
      this.budgetCategories.forEach((category) => {
        category.remaining = category.budget - category.spent;
      });
      this.checkForAlerts();
    },

    // 检查是否接近预算或超支
    checkForAlerts() {
      this.budgetCategories.forEach((category) => {
        if (category.spent >= category.budget) {
          this.alertMessage = `${category.name} 超出了预算！请及时调整。`;
          this.showReminder = true;
        } else if (category.spent >= category.budget * 0.8) {
          this.alertMessage = `${category.name} 即将超出预算！请注意。`;
          this.showReminder = true;
        } else {
          this.showReminder = false;
        }
      });
    },
  },
  mounted() {
    // 页面加载时检查预算情况
    this.checkForAlerts();
  },
};
</script>

<style scoped>
.budget-page {
  padding: 20px;
}

.budget-summary {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
}

.budget-category {
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
}

.budget-progress {
  margin-top: 10px;
  height: 20px;
  background-color: #f2f2f2;
  border-radius: 10px;
  overflow: hidden;
}

.progress-bar {
  height: 100%;
  background-color: #4caf50;
  width: 0;
  transition: width 0.3s ease-in-out;
}

.over-budget {
  background-color: red;
}

.close-to-budget {
  background-color: yellow;
}

.under-budget {
  background-color: green;
}

.set-budget {
  margin-top: 30px;
}

.set-budget form {
  display: grid;
  grid-gap: 10px;
}

.category-input {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

button {
  padding: 10px 20px;
  background-color: #4caf50;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

button:hover {
  background-color: #45a049;
}

.budget-alert {
  margin-top: 20px;
  padding: 10px;
  background-color: #ffeb3b;
  color: #333;
  border-radius: 5px;
  font-weight: bold;
}
</style>
