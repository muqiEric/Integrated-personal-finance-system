<template>
  <header class="nav-bar">
    <img src="../assets/86db5a7dc000fc26bb2e0caeaabeea3f.jpg" alt="装饰图片" class="header-image" />
    <h1 class="header-title">财务管理系统</h1>
    <nav>
      <router-link to="/analysis" class="nav-item">收支明细</router-link>
      <router-link to="/budget" class="nav-item">预算</router-link>
      <router-link to="/upload" class="nav-item">用户上传</router-link>
      <router-link to="/income-stats" class="nav-item">收入统计</router-link>
      <router-link to="/expense-stats" class="nav-item">支出统计</router-link>
      <router-link to="/my" class="nav-item">我的</router-link>
    </nav>
  </header>
</template>

<script>
export default {
  name: "NavigationBar",
};
</script>

<style scoped>
.nav-bar {
  background-color: #f8f9fa;
  padding: 20px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-bottom: 2px solid #ddd;
}

.header-image {
  height: 80px;
  width: auto;
  margin-right: 20px;
}

.header-title {
  font-size: 28px;
  color: #333;
  margin: 0;
}

nav {
  display: flex;
  align-items: center;
}

.nav-item {
  margin-left: 15px;
  text-decoration: none;
  color: #007bff;
  font-weight: bold;
  padding: 5px 10px;
  border-radius: 4px;
  transition: background-color 0.3s;
}

.nav-item:hover {
  background-color: #0056b3;
  color: white;
}
</style>
