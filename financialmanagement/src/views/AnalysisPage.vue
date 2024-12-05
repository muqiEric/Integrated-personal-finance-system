<template>
  <div>
    <!-- 导航栏 -->
    <NavigationBar />

    <!-- 收支明细表格 -->
    <section class="content">
      <h2>收支明细</h2>
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
        <tr v-for="(transaction, index) in transactions" :key="index">
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
    </section>
  </div>
</template>

<script>
import NavigationBar from "../components/NavigationBar.vue";

export default {
  name: "AnalysisPage",
  components: {
    NavigationBar,
  },
  data() {
    return {
      transactions: [
        {
          id: 1,
          trans_time: "2023-11-21",
          trans_type: "收入",
          trans_party: "公司",
          goods_name: "工资",
          direction: "收入",
          amount: 5000,
          payment_method: "银行转账",
          trans_status: "成功",
          order_no: "202311210001",
          merchant_order_no: "M202311210001",
          note: "十一月工资",
        },
        {
          id: 2,
          trans_time: "2023-11-20",
          trans_type: "支出",
          trans_party: "超市",
          goods_name: "日用品",
          direction: "支出",
          amount: -200,
          payment_method: "微信支付",
          trans_status: "成功",
          order_no: "202311200001",
          merchant_order_no: "M202311200001",
          note: "家庭采购",
        },
      ],
    };
  },
};
</script>

<style scoped>
.content {
  padding: 20px;
}

h2 {
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
</style>
