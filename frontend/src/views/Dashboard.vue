<template>
  <div class="dashboard-page">
    <a-row :gutter="[16, 16]">
      <a-col :xs="24" :sm="12" :md="6">
        <a-card class="stat-card">
          <a-statistic title="总需求数" :value="42" :value-style="{ color: '#409eff' }" />
        </a-card>
      </a-col>
      <a-col :xs="24" :sm="12" :md="6">
        <a-card class="stat-card">
          <a-statistic title="进行中任务" :value="18" :value-style="{ color: '#e6a23c' }" />
        </a-card>
      </a-col>
      <a-col :xs="24" :sm="12" :md="6">
        <a-card class="stat-card">
          <a-statistic title="待审查代码" :value="7" :value-style="{ color: '#f56c6c' }" />
        </a-card>
      </a-col>
      <a-col :xs="24" :sm="12" :md="6">
        <a-card class="stat-card">
          <a-statistic title="本周完成" :value="12" :value-style="{ color: '#67c23a' }" />
        </a-card>
      </a-col>
    </a-row>
  </div>
</template>

<script setup lang="ts">
import { ACol, ARow, ACard, AStatistic } from 'ant-design-vue'
</script>

<style scoped>
.dashboard-page { width: 100%; }
.stat-card { text-align: center; }
</style>
