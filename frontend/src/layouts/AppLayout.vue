<template>
  <a-layout class="app-layout">
    <a-layout-sider
      v-model:collapsed="collapsed"
      :trigger="null"
      collapsible
      :width="220"
      :collapsed-width="64"
    >
      <div class="logo-area">
        <span v-if="!collapsed">SpecFlow</span>
        <span v-else>SF</span>
      </div>
      <a-menu
        v-model:selectedKeys="selectedKeys"
        theme="light"
        mode="inline"
        :items="menuItems"
        @click="({ key }) => router.push('/' + key)"
      />
    </a-layout-sider>
    <a-layout>
      <a-layout-header class="header">
        <menu-unfold-outlined v-if="collapsed" class="trigger" @click="collapsed = !collapsed" />
        <menu-fold-outlined v-else class="trigger" @click="collapsed = !collapsed" />
        <span class="header-title">AI 驱动的软件开发生命周期治理平台</span>
      </a-layout-header>
      <a-layout-content class="content">
        <router-view />
      </a-layout-content>
    </a-layout>
  </a-layout>
</template>

<script setup lang="ts">
import { ref, h } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import {
  MenuUnfoldOutlined,
  MenuFoldOutlined,
  HomeOutlined,
  ApartmentOutlined,
  RocketOutlined,
  CodeOutlined,
  UserOutlined,
  SettingOutlined
} from '@ant-design/icons-vue'
import type { MenuProps } from 'ant-design-vue'

const router = useRouter()
const route = useRoute()
const collapsed = ref(false)
const selectedKeys = ref<string[]>([route.name as string])

const menuItems: MenuProps['items'] = [
  { key: 'dashboard', icon: () => h(HomeOutlined), label: '首页' },
  { key: 'requirements', icon: () => h(ApartmentOutlined), label: '需求管理' },
  { key: 'tasks', icon: () => h(RocketOutlined), label: '任务管理' },
  { key: 'reviews', icon: () => h(CodeOutlined), label: '代码审查' },
  { type: 'divider' },
  { key: 'profile', icon: () => h(UserOutlined), label: '个人中心' },
  { key: 'settings', icon: () => h(SettingOutlined), label: '系统设置' },
]
</script>

<style scoped>
.app-layout {
  height: 100vh;
  overflow: hidden;
}

.logo-area {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 18px;
  font-weight: bold;
  color: #409eff;
  border-bottom: 1px solid #f0f0f0;
  cursor: pointer;
}

.header {
  background: #fff;
  padding: 0 24px;
  display: flex;
  align-items: center;
  gap: 16px;
  height: 60px;
}

.trigger {
  font-size: 18px;
  cursor: pointer;
  transition: color 0.3s;
}

.trigger:hover {
  color: #409eff;
}

.header-title {
  font-size: 16px;
  color: #303133;
}

.content {
  margin: 16px;
  padding: 24px;
  background: #fff;
  border-radius: 8px;
  overflow-y: auto;
  height: calc(100vh - 60px - 32px);
}
</style>
