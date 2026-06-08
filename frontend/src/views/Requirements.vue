<template>
  <div>
    <div class="page-header">
      <h2>需求管理</h2>
      <a-button type="primary" @click="showModal = true">新建需求</a-button>
    </div>

    <a-table :columns="columns" :data-source="data" :pagination="false" row-key="id">
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'priority'">
          <a-tag :color="getPriorityColor(record.priority)">{{ record.priority }}</a-tag>
        </template>
        <template v-else-if="column.key === 'status'">
          <a-tag :color="getStatusColor(record.status)">{{ record.status }}</a-tag>
        </template>
      </template>
    </a-table>

    <a-modal v-model:open="showModal" title="新建需求" :width="500" @ok="handleCreate">
      <a-form :model="form" layout="vertical" ref="formRef">
        <a-form-item label="需求标题" name="title" :rules="[{ required: true, message: '请输入需求标题' }]">
          <a-input v-model:value="form.title" placeholder="请输入需求标题" />
        </a-form-item>
        <a-form-item label="需求描述" name="description">
          <a-textarea v-model:value="form.description" placeholder="请输入需求描述" :rows="3" />
        </a-form-item>
        <a-form-item label="优先级" name="priority">
          <a-select v-model:value="form.priority" :options="priorityOptions" placeholder="请选择优先级" />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { message } from 'ant-design-vue'

const showModal = ref(false)
const formRef = ref()
const form = ref({ title: '', description: '', priority: undefined })

const priorityOptions = [
  { label: '高', value: 'high' },
  { label: '中', value: 'medium' },
  { label: '低', value: 'low' },
]

const columns = [
  { title: 'ID', dataIndex: 'id', key: 'id', width: 80 },
  { title: '标题', dataIndex: 'title', key: 'title' },
  { title: '优先级', key: 'priority', width: 120 },
  { title: '状态', key: 'status', width: 120 },
]

const data = [
  { id: 1, title: '用户登录模块', priority: '高', status: '进行中' },
  { id: 2, title: 'API 文档集成', priority: '高', status: '已完成' },
  { id: 3, title: '数据库设计', priority: '中', status: '进行中' },
  { id: 4, title: '前端组件库', priority: '中', status: '待开始' },
]

function getPriorityColor(p: string) {
  return p === '高' ? 'red' : p === '中' ? 'orange' : 'blue'
}
function getStatusColor(s: string) {
  return s === '已完成' ? 'green' : s === '进行中' ? 'blue' : 'default'
}

async function handleCreate() {
  try {
    await formRef.value.validate()
    message.success('需求创建成功')
    showModal.value = false
    form.value = { title: '', description: '', priority: undefined }
  } catch {}
}
</script>

<style scoped>
.page-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
.page-header h2 { margin: 0; }
</style>
