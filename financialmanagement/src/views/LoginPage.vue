<template>
  <div class="login">
    <div class="login-container">
      <!-- 插图部分 -->
      <img class="login-image" src="@/assets/86db5a7dc000fc26bb2e0caeaabeea3f.jpg" alt="登录图片" />

      <!-- 登录标题 -->
      <h2>欢迎登录</h2>

      <!-- 登录表单 -->
      <form @submit.prevent="login">
        <input type="text" v-model="username" placeholder="请输入用户名" />
        <input type="password" v-model="password" placeholder="请输入密码" />
        <button type="submit">登录</button>
        <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
      </form>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      username: '',
      password: '',
      errorMessage: '',
    };
  },
  methods: {
    login() {
      const testUsername = 'testuser';
      const testPassword = '123456';
      if (this.username === testUsername && this.password === testPassword) {
        this.$router.push('/analysis'); // 登录成功后跳转到主页面
      } else {
        this.errorMessage = '用户名或密码错误，请重试！';
      }
    },
  },
};
</script>

<style scoped>
/* 页面居中样式 */
.login {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #f8f9fa;
}

/* 登录容器样式 */
.login-container {
  background-color: white;
  padding: 30px;
  border-radius: 8px;
  text-align: center;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  width: 300px;
}

/* 插图样式 */
.login-image {
  width: 100px;
  height: auto;
  margin-bottom: 20px;
}

/* 输入框样式 */
form input {
  margin: 10px 0;
  padding: 12px;
  width: 100%;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 16px;
}

/* 按钮样式 */
button {
  margin-top: 10px;
  padding: 12px;
  background-color: #007bff;
  border: none;
  border-radius: 4px;
  color: white;
  font-size: 16px;
  cursor: pointer;
  width: 100%;
}

button:hover {
  background-color: #0056b3;
}

/* 错误提示样式 */
.error {
  color: #e3342f;
  margin-top: 10px;
}
</style>
