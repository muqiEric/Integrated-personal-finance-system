import { createRouter, createWebHistory } from 'vue-router';

// 导入页面组件
import LoginPage from '../views/LoginPage.vue';
import AnalysisPage from '../views/AnalysisPage.vue';
import BudgetPage from '../views/BudgetPage.vue';
import UploadPage from '../views/UploadPage.vue';
import IncomeStatsPage from '../views/IncomeStatsPage.vue';
import ExpenseStatsPage from '../views/ExpenseStatsPage.vue';
import MyPage from '../views/MyPage.vue'; // 新增的我的页面

// 模拟交易数据（替换为实际的数据）
const transactions = [
    { id: 1, trans_time: "2023-11-21", trans_type: "收入", trans_party: "公司", goods_name: "工资", direction: "收入", amount: 5000, payment_method: "银行转账", trans_status: "成功", order_no: "202311210001", merchant_order_no: "M202311210001", note: "十一月工资" },
    { id: 2, trans_time: "2023-11-20", trans_type: "支出", trans_party: "超市", goods_name: "日用品", direction: "支出", amount: -200, payment_method: "微信支付", trans_status: "成功", order_no: "202311200002", merchant_order_no: "M202311200002", note: "家庭采购" },
];

const routes = [
    { path: '/', redirect: '/login' }, // 默认路由重定向到登录页
    { path: '/login', component: LoginPage }, // 登录页面
    { path: '/analysis', component: AnalysisPage, props: { transactions } }, // 收支明细页面
    { path: '/budget', component: BudgetPage }, // 预算页面
    { path: '/upload', component: UploadPage }, // 用户上传页面
    { path: '/income-stats', component: IncomeStatsPage, props: { transactions } }, // 收入统计页面
    { path: '/expense-stats', component: ExpenseStatsPage, props: { transactions } }, // 支出统计页面
    { path: '/my', component: MyPage }, // 我的页面
    { path: '/:catchAll(.*)', redirect: '/login' }, // 捕获所有未定义的路由并重定向到登录页
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
