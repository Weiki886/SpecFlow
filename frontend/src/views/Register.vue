<template>
  <div class="login-page">
    <!-- 左侧产品展示 -->
    <div class="product-side">
      <div class="product-inner">
        <div class="browser-frame">
          <div class="browser-bar">
            <div class="browser-dots">
              <span class="dot dot-red" />
              <span class="dot dot-yellow" />
              <span class="dot dot-green" />
            </div>
            <div class="browser-address">
              <svg width="10" height="10" viewBox="0 0 10 10" fill="none"><path d="M1 5H9M5 1L9 5L5 9" stroke="#94A3B8" stroke-width="1.2" stroke-linecap="round" stroke-linejoin="round"/></svg>
              specflow.app/dashboard
            </div>
          </div>

          <div class="app-layout">
            <div class="sidebar">
              <div class="sidebar-logo">SF</div>
              <div class="sidebar-nav">
                <div class="nav-item">
                  <span class="nav-dot" />
                  <span class="nav-label">首页</span>
                </div>
                <div class="nav-item">
                  <span class="nav-dot" />
                  <span class="nav-label">需求</span>
                </div>
                <div class="nav-item">
                  <span class="nav-dot" />
                  <span class="nav-label">任务</span>
                </div>
                <div class="nav-item">
                  <span class="nav-dot" />
                  <span class="nav-label">审查</span>
                </div>
              </div>
              <div class="sidebar-user">
                <div class="user-avatar">A</div>
                <div class="user-info">
                  <div class="user-name">Alex</div>
                  <div class="user-role">开发者</div>
                </div>
              </div>
            </div>

            <div class="main-content">
              <div class="page-header">
                <div class="page-title">仪表盘</div>
                <div class="header-badge">本周</div>
              </div>

              <div class="stats-row">
                <div class="stat-card">
                  <div class="stat-label">总需求</div>
                  <div class="stat-value">42</div>
                  <div class="stat-trend up">+3 本周</div>
                </div>
                <div class="stat-card">
                  <div class="stat-label">进行中</div>
                  <div class="stat-value">18</div>
                  <div class="stat-trend neutral">进行中</div>
                </div>
                <div class="stat-card">
                  <div class="stat-label">待审查</div>
                  <div class="stat-value">7</div>
                  <div class="stat-trend down">需处理</div>
                </div>
                <div class="stat-card">
                  <div class="stat-label">已完成</div>
                  <div class="stat-value">17</div>
                  <div class="stat-trend up">+5 本周</div>
                </div>
              </div>

              <div class="chart-area">
                <div class="chart-label">需求趋势</div>
                <div class="chart-bars">
                  <div class="bar-group">
                    <div class="bar" style="height: 40%" />
                    <div class="bar-label">一</div>
                  </div>
                  <div class="bar-group">
                    <div class="bar" style="height: 60%" />
                    <div class="bar-label">二</div>
                  </div>
                  <div class="bar-group">
                    <div class="bar" style="height: 55%" />
                    <div class="bar-label">三</div>
                  </div>
                  <div class="bar-group">
                    <div class="bar" style="height: 75%" />
                    <div class="bar-label">四</div>
                  </div>
                  <div class="bar-group">
                    <div class="bar" style="height: 70%" />
                    <div class="bar-label">五</div>
                  </div>
                  <div class="bar-group">
                    <div class="bar" style="height: 90%" />
                    <div class="bar-label">六</div>
                  </div>
                  <div class="bar-group">
                    <div class="bar bar-today" style="height: 100%" />
                    <div class="bar-label">日</div>
                  </div>
                </div>
              </div>

              <div class="recent-section">
                <div class="section-title">最近需求</div>
                <div class="list-item">
                  <div class="item-status pending" />
                  <div class="item-title">用户登录模块开发</div>
                  <div class="item-tag tag-high">高</div>
                </div>
                <div class="list-item">
                  <div class="item-status done" />
                  <div class="item-title">API 文档集成</div>
                  <div class="item-tag tag-done">完成</div>
                </div>
                <div class="list-item">
                  <div class="item-status progress" />
                  <div class="item-title">数据库设计与评审</div>
                  <div class="item-tag tag-mid">中</div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="product-caption">
          <span class="caption-dot" />
          智能化管理软件开发生命周期
        </div>
      </div>
    </div>

    <!-- 右侧注册表单 -->
    <div class="form-side">
      <div class="form-container">
        <div class="form-brand">
          <div class="brand-mark">SF</div>
          <div class="brand-name">SpecFlow</div>
        </div>

        <div class="form-heading">
          <h1>创建账号</h1>
          <p>加入 SpecFlow，开启智能化开发之旅</p>
        </div>

        <a-form
          ref="formRef"
          :model="form"
          :rules="rules"
          layout="vertical"
          @finish="handleRegister"
          class="login-form"
        >
          <a-form-item name="username" label="用户名">
            <a-input v-model:value="form.username" placeholder="请输入用户名" size="large" class="form-input" />
          </a-form-item>

          <a-form-item name="email" label="邮箱">
            <a-input v-model:value="form.email" placeholder="请输入邮箱" size="large" class="form-input" />
          </a-form-item>

          <a-form-item name="password" label="密码">
            <a-input-password v-model:value="form.password" placeholder="请输入密码" size="large" class="form-input" />
          </a-form-item>

          <a-form-item name="confirmPassword" label="确认密码">
            <a-input-password v-model:value="form.confirmPassword" placeholder="请再次输入密码" size="large" class="form-input" />
          </a-form-item>

          <a-form-item>
            <a-button type="primary" html-type="submit" block :loading="loading" size="large" class="submit-btn">
              注 册
            </a-button>
          </a-form-item>
        </a-form>

        <div class="form-divider" />

        <p class="form-alt">
          已有账号？
          <router-link to="/login" class="link-primary">立即登录</router-link>
        </p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { message } from 'ant-design-vue'

const router = useRouter()
const formRef = ref()
const loading = ref(false)
const form = ref({ username: '', email: '', password: '', confirmPassword: '' })

const validateConfirmPassword = async (_rule: any, value: string) => {
  if (value !== form.value.password) throw new Error('两次输入的密码不一致')
}

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱格式', trigger: 'blur' },
  ],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
  confirmPassword: [
    { required: true, message: '请确认密码', trigger: 'blur' },
    { validator: validateConfirmPassword, trigger: 'blur' },
  ],
}

async function handleRegister() {
  try {
    await formRef.value.validate()
    loading.value = true
    message.success('注册成功，请登录')
    router.push('/login')
  } catch {}
  finally { loading.value = false }
}
</script>

<style scoped>
.login-page {
  display: flex;
  min-height: 100vh;
  width: 100%;
}

.product-side {
  flex: 1.15;
  background: #F1F5F9;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 40px;
  position: relative;
}

.product-inner {
  width: 100%;
  max-width: 580px;
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.browser-frame {
  background: #fff;
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0 8px 40px rgba(15, 23, 42, 0.12), 0 2px 8px rgba(15, 23, 42, 0.06);
}

.browser-bar {
  background: #F8FAFC;
  border-bottom: 1px solid #E2E8F0;
  padding: 10px 14px;
  display: flex;
  align-items: center;
  gap: 12px;
}

.browser-dots { display: flex; gap: 5px; }
.dot { width: 10px; height: 10px; border-radius: 50%; }
.dot-red { background: #FF5F57; }
.dot-yellow { background: #FFBD2E; }
.dot-green { background: #28C840; }

.browser-address {
  flex: 1;
  background: #fff;
  border: 1px solid #E2E8F0;
  border-radius: 5px;
  padding: 4px 10px;
  font-size: 11px;
  color: #64748B;
  display: flex;
  align-items: center;
  gap: 5px;
  max-width: 280px;
  margin: 0 auto;
}

.app-layout {
  display: flex;
  height: 360px;
}

.sidebar {
  width: 140px;
  background: #1E293B;
  padding: 16px 0;
  display: flex;
  flex-direction: column;
  flex-shrink: 0;
}

.sidebar-logo {
  width: 32px;
  height: 32px;
  background: #1677FF;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 11px;
  font-weight: 800;
  color: #fff;
  margin: 0 12px 20px;
}

.sidebar-nav {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 2px;
  padding: 0 8px;
}

.nav-item {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 7px 10px;
  border-radius: 6px;
  cursor: default;
}

.nav-dot {
  width: 6px;
  height: 6px;
  border-radius: 50%;
  background: #475569;
  flex-shrink: 0;
}

.nav-label {
  font-size: 12px;
  color: #94A3B8;
}

.sidebar-user {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 12px 12px 0;
  border-top: 1px solid #334155;
  margin-top: 8px;
}

.user-avatar {
  width: 28px;
  height: 28px;
  background: #334155;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 11px;
  font-weight: 600;
  color: #94A3B8;
  flex-shrink: 0;
}

.user-name { font-size: 12px; font-weight: 500; color: #CBD5E1; }
.user-role { font-size: 10px; color: #64748B; }

.main-content {
  flex: 1;
  padding: 20px 24px;
  overflow: hidden;
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.page-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.page-title { font-size: 16px; font-weight: 600; color: #0F172A; }

.header-badge {
  background: #EFF6FF;
  color: #3B82F6;
  font-size: 10px;
  font-weight: 500;
  padding: 2px 8px;
  border-radius: 10px;
}

.stats-row {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 10px;
}

.stat-card {
  background: #F8FAFC;
  border: 1px solid #E2E8F0;
  border-radius: 8px;
  padding: 12px;
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.stat-label { font-size: 10px; color: #94A3B8; }
.stat-value { font-size: 20px; font-weight: 700; color: #0F172A; line-height: 1; }
.stat-trend { font-size: 10px; font-weight: 500; }
.stat-trend.up { color: #22C55E; }
.stat-trend.down { color: #EF4444; }
.stat-trend.neutral { color: #94A3B8; }

.chart-area {
  flex: 1;
  background: #F8FAFC;
  border: 1px solid #E2E8F0;
  border-radius: 8px;
  padding: 14px;
  display: flex;
  flex-direction: column;
  gap: 10px;
  min-height: 0;
}

.chart-label { font-size: 11px; font-weight: 500; color: #64748B; }

.chart-bars {
  flex: 1;
  display: flex;
  align-items: flex-end;
  gap: 8px;
}

.bar-group {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 4px;
  height: 100%;
  justify-content: flex-end;
}

.bar { width: 100%; background: #CBD5E1; border-radius: 3px 3px 0 0; }
.bar-today { background: #1677FF; }
.bar-label { font-size: 9px; color: #94A3B8; }

.recent-section {
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.section-title { font-size: 11px; font-weight: 500; color: #64748B; margin-bottom: 2px; }

.list-item {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 6px 8px;
  background: #F8FAFC;
  border: 1px solid #E2E8F0;
  border-radius: 6px;
}

.item-status { width: 7px; height: 7px; border-radius: 50%; flex-shrink: 0; }
.item-status.done { background: #22C55E; }
.item-status.progress { background: #3B82F6; }
.item-status.pending { background: #F59E0B; }

.item-title {
  flex: 1;
  font-size: 11px;
  color: #334155;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.item-tag {
  font-size: 9px;
  font-weight: 500;
  padding: 1px 6px;
  border-radius: 3px;
  flex-shrink: 0;
}

.tag-high { background: #FEF2F2; color: #EF4444; }
.tag-mid { background: #FFF7ED; color: #F97316; }
.tag-done { background: #F0FDF4; color: #22C55E; }

.product-caption {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  font-size: 13px;
  color: #64748B;
}

.caption-dot {
  width: 6px;
  height: 6px;
  background: #1677FF;
  border-radius: 50%;
}

/* 右侧表单 */
.form-side {
  width: 440px;
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #fff;
  padding: 60px 52px;
}

.form-container {
  width: 100%;
  max-width: 340px;
}

.form-brand {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 48px;
}

.brand-mark {
  width: 38px;
  height: 38px;
  background: #1677FF;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 14px;
  font-weight: 800;
  color: #fff;
  flex-shrink: 0;
}

.brand-name {
  font-size: 18px;
  font-weight: 700;
  color: #0F172A;
  letter-spacing: -0.3px;
}

.form-heading { margin-bottom: 36px; }

.form-heading h1 {
  font-size: 26px;
  font-weight: 700;
  color: #0F172A;
  margin-bottom: 6px;
  letter-spacing: -0.5px;
}

.form-heading p { font-size: 14px; color: #64748B; margin: 0; }

.login-form :deep(.ant-form-item-label > label) {
  font-weight: 500;
  color: #334155;
  font-size: 13px;
}

.form-input {
  border-radius: 8px;
  height: 44px;
  font-size: 14px;
}

.submit-btn {
  height: 46px;
  border-radius: 8px;
  font-size: 15px;
  font-weight: 600;
  background: #1677FF;
  border-color: #1677FF;
  letter-spacing: 1px;
  transition: background 0.2s, box-shadow 0.2s;
}

.submit-btn:hover {
  background: #0958D9;
  border-color: #0958D9;
  box-shadow: 0 4px 14px rgba(22, 119, 255, 0.25);
}

.form-divider {
  height: 1px;
  background: #F1F5F9;
  margin: 24px 0;
}

.form-alt { text-align: center; font-size: 13px; color: #64748B; }

.link-primary { color: #1677FF; font-weight: 500; }
.link-primary:hover { color: #0958D9; }

@media (max-width: 900px) {
  .product-side { display: none; }
  .form-side { width: 100%; }
}
</style>
