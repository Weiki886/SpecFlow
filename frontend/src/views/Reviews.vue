<template>
  <div>
    <div class="page-header">
      <h2>代码审查</h2>
      <a-button type="primary">发起审查</a-button>
    </div>

    <a-table :columns="columns" :data-source="data" :pagination="false" row-key="id">
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'status'">
          <a-tag :color="getStatusColor(record.status)">{{ record.status }}</a-tag>
        </template>
      </template>
    </a-table>
  </div>
</template>

<script setup lang="ts">
const columns = [
  { title: 'ID', dataIndex: 'id', key: 'id', width: 80 },
  { title: '标题', dataIndex: 'title', key: 'title' },
  { title: '提交人', dataIndex: 'author', key: 'author', width: 120 },
  { title: '审查人', dataIndex: 'reviewer', key: 'reviewer', width: 120 },
  { title: '分支', dataIndex: 'branch', key: 'branch', width: 160 },
  { title: '状态', key: 'status', width: 120 },
]

const data = [
  { id: 1, title: 'feat: 添加登录模块', author: '张三', reviewer: '李四', branch: 'feature/login', status: '已通过' },
  { id: 2, title: 'fix: 修复注册接口', author: '李四', reviewer: '王五', branch: 'fix/register', status: '待审查' },
  { id: 3, title: 'feat: Swagger 集成', author: '王五', reviewer: '张三', branch: 'feature/swagger', status: '待审查' },
]

function getStatusColor(s: string) {
  return s === '已通过' ? 'green' : s === '待审查' ? 'orange' : 'red'
}
</script>

<style scoped>
.page-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
.page-header h2 { margin: 0; }
</style>
