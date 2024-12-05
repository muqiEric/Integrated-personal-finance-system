<template>
  <div>
    <!-- 导航栏 -->
    <NavigationBar />

    <!-- 收入统计表格 -->
    <section class="content">
      <h2>收入统计</h2>
      <table>
        <thead>
        <tr>
          <th>ID</th>
          <th>交易时间</th>
          <th>交易类型</th>
          <th>交易对象</th>
          <th>商品名称</th>
          <th>方向</th>
          <th>金额</th>
          <th>支付方式</th>
          <th>交易状态</th>
          <th>订单号</th>
          <th>商户订单号</th>
          <th>备注</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(transaction, index) in incomeTransactions" :key="index">
          <td>{{ transaction.id }}</td>
          <td>{{ transaction.trans_time }}</td>
          <td>{{ transaction.trans_type }}</td>
          <td>{{ transaction.trans_party }}</td>
          <td>{{ transaction.goods_name }}</td>
          <td>{{ transaction.direction }}</td>
          <td>{{ transaction.amount }}</td>
          <td>{{ transaction.payment_method }}</td>
          <td>{{ transaction.trans_status }}</td>
          <td>{{ transaction.order_no }}</td>
          <td>{{ transaction.merchant_order_no }}</td>
          <td>{{ transaction.note }}</td>
        </tr>
        </tbody>
      </table>

      <!-- 饼状图 -->
      <h3>收入分类统计</h3>
      <div class="chart-container">
        <canvas id="incomeChart"></canvas>
      </div>
    </section>
  </div>
</template>

<script>
import NavigationBar from "../components/NavigationBar.vue";
import Chart from "chart.js/auto";

export default {
  name: "IncomeStatsPage",
  components: {
    NavigationBar,
  },
  props: {
    transactions: {
      type: Array,
      required: true,
    },
  },
  computed: {
    incomeTransactions() {
      return this.transactions.filter((transaction) => transaction.direction === "收入");
    },
  },
  mounted() {
    this.renderIncomeChart();
  },
  methods: {
    renderIncomeChart() {
      // 获取收入数据，按商品名称分组统计金额
      const incomeData = this.incomeTransactions.reduce((acc, curr) => {
        const key = curr.goods_name || "其他";
        acc[key] = (acc[key] || 0) + curr.amount;
        return acc;
      }, {});

      const ctx = document.getElementById("incomeChart").getContext("2d");

      new Chart(ctx, {
        type: "pie",
        data: {
          labels: Object.keys(incomeData),
          datasets: [
            {
              data: Object.values(incomeData),
              backgroundColor: ["#36A2EB", "#FF6384", "#FFCE56", "#4BC0C0", "#9966FF"],
            },
          ],
        },
        options: {
          responsive: true,
          maintainAspectRatio: false, // 允许调整宽高
          animation: {
            duration: 1500, // 动画持续时间
            easing: "easeOutBounce", // 动画效果
          },
          plugins: {
            legend: {
              position: "top", // 图例位置
            },
          },
        },
      });
    },
  },
};
</script>

<style scoped>
.content {
  padding: 20px;
}

h2,
h3 {
  margin-bottom: 20px;
  color: #333;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

thead th {
  background-color: #f2f2f2;
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}

tbody td {
  border: 1px solid #ddd;
  padding: 8px;
}

tbody tr:hover {
  background-color: #f9f9f9;
}

.chart-container {
  width: 600px; /* 设置饼状图容器宽度 */
  height: 600px; /* 设置饼状图容器高度 */
  margin: 20px auto; /* 居中 */
}

canvas {
  display: block;
  width: 100%; /* 图表占满容器 */
  height: 100%;
}
</style>
