<template>
  <div class="details-page">
    <h2>收支明细</h2>

    <!-- 查询条件 -->
    <div class="filters">
      <input type="text" placeholder="请输入收支年月" />
      <input type="text" placeholder="请输入收支备注" />
      <button>查询</button>
    </div>

    <!-- 数据表格 -->
    <table>
      <thead>
      <tr>
        <th>全选</th>
        <th>编号</th>
        <th>收支备注</th>
        <th>金额</th>
        <th>收支日期</th>
        <th>收支类型</th>
        <th>操作</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(item, index) in records" :key="index">
        <td><input type="checkbox" /></td>
        <td>{{ item.id }}</td>
        <td>{{ item.note }}</td>
        <td>{{ item.amount }}</td>
        <td>{{ item.date }}</td>
        <td>{{ item.type }}</td>
        <td>
          <button class="edit-btn">修改</button>
          <button class="delete-btn">删除</button>
        </td>
      </tr>
      </tbody>
    </table>
    <button class="delete-selected">批量删除</button>
  </div>
</template>

<script>
export default {
  name: "DetailsPage",
  data() {
    return {
      records: [
        { id: 1, note: "早餐", amount: -25, date: "2023-11-21", type: "餐饮" },
        { id: 2, note: "奖金", amount: 500, date: "2023-11-20", type: "收入" },
        { id: 3, note: "房租", amount: -800, date: "2023-11-18", type: "支出" },
        // 添加更多记录
      ],
    };
  },
};
</script>

<style scoped>
/* 收支明细页面样式 */
.details-page {
  font-family: Arial, sans-serif;
}

.filters {
  margin-bottom: 20px;
}

.filters input {
  margin-right: 10px;
  padding: 5px;
}

.filters button {
  padding: 5px 10px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.filters button:hover {
  background-color: #0056b3;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 20px;
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

.edit-btn {
  background-color: #28a745;
  color: white;
  border: none;
  padding: 5px 10px;
  border-radius: 4px;
  cursor: pointer;
}

.edit-btn:hover {
  background-color: #218838;
}

.delete-btn {
  background-color: #dc3545;
  color: white;
  border: none;
  padding: 5px 10px;
  border-radius: 4px;
  cursor: pointer;
}

.delete-btn:hover {
  background-color: #c82333;
}

.delete-selected {
  background-color: #ffc107;
  border: none;
  color: white;
  padding: 5px 10px;
  border-radius: 4px;
  cursor: pointer;
}

.delete-selected:hover {
  background-color: #e0a800;
}
</style>
