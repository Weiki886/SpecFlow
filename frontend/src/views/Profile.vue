<template>
  <div>
    <h2 style="margin-bottom: 24px;">个人中心</h2>
    <a-row :gutter="[24, 24]">
      <a-col :xs="24" :md="12">
        <a-card title="基本信息">
          <a-descriptions :column="1" bordered size="small">
            <a-descriptions-item label="用户名">{{ authStore.userInfo?.username || '—' }}</a-descriptions-item>
            <a-descriptions-item label="用户 ID">{{ authStore.userInfo?.id || '—' }}</a-descriptions-item>
            <a-descriptions-item label="角色">开发者</a-descriptions-item>
          </a-descriptions>
        </a-card>
      </a-col>
      <a-col :xs="24" :md="12">
        <a-card title="账号操作">
          <a-space direction="vertical" style="width: 100%;" :size="12">
            <a-button type="primary" block>修改密码</a-button>
            <a-button type="default" block>修改头像</a-button>
            <a-button type="primary" danger block @click="handleLogout">退出登录</a-button>
          </a-space>
        </a-card>
      </a-col>
    </a-row>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { message } from 'ant-design-vue'
import { useAuthStore } from '../stores/auth'

const router = useRouter()
const authStore = useAuthStore()

function handleLogout() {
  authStore.logout()
  message.success('已退出登录')
  router.push('/login')
}
</script>
