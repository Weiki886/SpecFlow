<template>
  <div>
    <div class="page-header">
      <h2>任务管理</h2>
      <a-button type="primary">新建任务</a-button>
    </div>

    <a-table :columns="columns" :data-source="data" :pagination="false" row-key="id">
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'progress'">
          <a-progress :percent="record.progress" size="small" :stroke-color="getProgressColor(record.progress)" />
        </template>
        <template v-else-if="column.key === 'status'">
          <a-tag :color="getStatusColor(record.status)">{{ record.status }}</a-tag>
        </template>
      </template>
    </a-table>
  </div>
</template>

<script setup lang="ts">
const columns = [
  { title: 'ID', dataIndex: 'id', key: 'id', width: 80 },
  { title: '任务名称', dataIndex: 'name', key: 'name' },
  { title: '负责人', dataIndex: 'assignee', key: 'assignee', width: 120 },
  { title: '进度', key: 'progress', width: 200 },
  { title: '状态', key: 'status', width: 120 },
]

const data = [
  { id: 1, name: '登录页面开发', assignee: '张三', progress: 100, status: '已完成' },
  { id: 2, name: '注册页面开发', assignee: '李四', progress: 60, status: '进行中' },
  { id: 3, name: '首页布局', assignee: '王五', progress: 30, status: '进行中' },
]

function getProgressColor(p: number) {
  if (p >= 80) return '#67c23a'
  if (p >= 40) return '#e6a23c'
  return '#909399'
}
function getStatusColor(s: string) {
  return s === '已完成' ? 'green' : s === '进行中' ? 'blue' : 'default'
}
</script>

<style scoped>
.page-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
.page-header h2 { margin: 0; }
</style>
