<template>
  <div>
    <!-- 导航栏 -->
    <NavigationBar />

    <!-- 主体内容 -->
    <div class="page-container">
      <!-- 主页标题 -->
      <h2 class="page-title">主页</h2>

      <div class="user-info">
        <p class="user-id"><strong>用户ID：</strong>{{ userId }}</p>

        <!-- 家庭成员部分 -->
        <div class="family-members">
          <h3>家庭成员</h3>
          <table class="family-table">
            <thead>
            <tr>
              <th>姓名</th>
              <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(member, index) in familyMembers" :key="index">
              <td>{{ member.name }}</td>
              <td><button @click="removeFamilyMember(index)" class="remove-btn">删除</button></td>
            </tr>
            </tbody>
          </table>
        </div>

        <!-- 添加家庭成员按钮 -->
        <button @click="showAddForm = !showAddForm" class="add-btn">添加家庭成员</button>

        <!-- 输入框：显示家庭成员账号和密码 -->
        <div v-if="showAddForm" class="add-member-form">
          <input
              v-model="newFamilyMember.account"
              type="text"
              placeholder="输入家庭成员账号"
              class="input-field"
          />
          <input
              v-model="newFamilyMember.password"
              type="password"
              placeholder="输入家庭成员密码"
              class="input-field"
          />
          <button @click="addFamilyMember" class="submit-btn">提交</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import NavigationBar from "@/components/NavigationBar.vue";

export default {
  name: "MyPage",
  components: {
    NavigationBar,
  },
  data() {
    return {
      userId: "12345", // 示例用户ID
      showAddForm: false, // 控制是否显示添加家庭成员的表单
      newFamilyMember: { account: "", password: "" }, // 输入框绑定的对象
      familyMembers: [], // 存储成功添加的家庭成员（只显示姓名）
      // 模拟家庭成员数据库
      familyMemberDatabase: [
        { name: "张三", account: "zhangsan", password: "123456" },
        { name: "李四", account: "lisi", password: "password" },
        { name: "王五", account: "wangwu", password: "qwerty" },
      ],
    };
  },
  methods: {
    // 添加家庭成员
    addFamilyMember() {
      const { account, password } = this.newFamilyMember;

      if (!account || !password) {
        alert("请填写账号和密码！");
        return;
      }

      // 验证账号和密码是否匹配
      const member = this.familyMemberDatabase.find(
          (m) => m.account === account && m.password === password
      );

      if (!member) {
        alert("账号或密码错误！");
        return;
      }

      // 检查是否已存在
      if (this.familyMembers.some((m) => m.name === member.name)) {
        alert("该家庭成员已添加！");
        return;
      }

      // 添加成员
      this.familyMembers.push({ name: member.name });
      this.newFamilyMember = { account: "", password: "" }; // 清空输入框
      this.showAddForm = false; // 隐藏输入框
    },

    // 删除家庭成员
    removeFamilyMember(index) {
      this.familyMembers.splice(index, 1);
    },
  },
};
</script>

<style scoped>
.page-container {
  margin: 0 auto;
  max-width: 900px;
  padding: 30px;
  font-family: Arial, sans-serif;
  background-color: #f9f9f9;
  border-radius: 12px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.page-title {
  text-align: center;
  font-size: 36px;
  color: #333;
  margin-bottom: 25px;
}

.user-info {
  background-color: #ffffff;
  padding: 25px;
  border-radius: 12px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.user-id {
  font-size: 20px;
  color: #555;
  margin-bottom: 20px;
}

.family-members h3 {
  font-size: 24px;
  margin-bottom: 15px;
  color: #333;
}

.family-table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 20px;
}

.family-table th,
.family-table td {
  padding: 15px;
  text-align: center;
  border: 1px solid #e0e0e0;
}

.family-table th {
  background-color: #f1f1f1;
  font-weight: bold;
}

.remove-btn {
  background-color: #e74c3c;
  color: white;
  border: none;
  padding: 8px 15px;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.remove-btn:hover {
  background-color: #c0392b;
}

.add-btn {
  display: block;
  width: 200px;
  margin: 25px auto;
  padding: 12px;
  font-size: 18px;
  background-color: #3498db;
  color: white;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.add-btn:hover {
  background-color: #2980b9;
}

.add-member-form {
  display: flex;
  flex-direction: column;
  gap: 15px;
  margin-top: 20px;
}

.input-field {
  padding: 12px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 8px;
  background-color: #f9f9f9;
  width: 100%;
}

.submit-btn {
  background-color: #2ecc71;
  color: white;
  border: none;
  padding: 12px 20px;
  border-radius: 8px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.submit-btn:hover {
  background-color: #27ae60;
}
</style>
