<template>
  <div>
    <h2 style="margin-bottom: 24px;">系统设置</h2>
    <a-tabs default-active-key="general">
      <a-tab-pane key="general" tab="通用设置">
        <a-card>
          <a-form layout="vertical">
            <a-form-item label="系统名称">
              <a-input v-model:value="settings.systemName" placeholder="请输入系统名称" />
            </a-form-item>
            <a-form-item label="系统语言">
              <a-select v-model:value="settings.language" :options="languageOptions" />
            </a-form-item>
            <a-form-item label="主题">
              <a-select v-model:value="settings.theme" :options="themeOptions" />
            </a-form-item>
            <a-form-item>
              <a-button type="primary" @click="message.success('保存成功')">保存</a-button>
            </a-form-item>
          </a-form>
        </a-card>
      </a-tab-pane>
      <a-tab-pane key="llm" tab="LLM 设置">
        <a-card>
          <a-form layout="vertical">
            <a-form-item label="LLM 提供商">
              <a-select v-model:value="settings.llmProvider" :options="llmOptions" />
            </a-form-item>
            <a-form-item label="API Key">
              <a-input-password v-model:value="settings.apiKey" placeholder="请输入 API Key" />
            </a-form-item>
            <a-form-item label="模型">
              <a-input v-model:value="settings.model" placeholder="请输入模型名称" />
            </a-form-item>
            <a-form-item>
              <a-button type="primary" @click="message.success('保存成功')">保存</a-button>
            </a-form-item>
          </a-form>
        </a-card>
      </a-tab-pane>
    </a-tabs>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { message } from 'ant-design-vue'

const settings = ref({
  systemName: 'SpecFlow',
  language: 'zh',
  theme: 'light',
  llmProvider: 'deepseek',
  apiKey: '',
  model: 'deepseek-chat',
})

const languageOptions = [
  { label: '简体中文', value: 'zh' },
  { label: 'English', value: 'en' },
]

const themeOptions = [
  { label: '浅色', value: 'light' },
  { label: '深色', value: 'dark' },
]

const llmOptions = [
  { label: 'DeepSeek', value: 'deepseek' },
  { label: '通义千问', value: 'qwen' },
]
</script>
