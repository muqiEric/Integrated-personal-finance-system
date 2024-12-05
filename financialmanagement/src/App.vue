<template>
  <div id="app">
    <!-- 路由页面内容 -->
    <router-view />
  </div>
</template>

<script>
// 删除不必要的 import，保持干净
export default {
  name: "App",
};
</script>

<style>
/* 可选的全局样式 */
body {
  font-family: Arial, sans-serif;
  margin: 0;
  padding: 0;
}
</style>
