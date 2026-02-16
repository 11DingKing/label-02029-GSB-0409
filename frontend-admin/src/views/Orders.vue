<template>
  <div class="orders-page">
    <div class="page-header">
      <h2>订单管理</h2>
    </div>
    
    <el-card>
      <div style="margin-bottom: 16px">
        <el-select v-model="statusFilter" placeholder="订单状态" clearable @change="fetchOrders" style="width: 150px">
          <el-option label="待付款" value="PENDING" />
          <el-option label="已付款" value="PAID" />
          <el-option label="已发货" value="SHIPPED" />
          <el-option label="已完成" value="COMPLETED" />
          <el-option label="已取消" value="CANCELLED" />
        </el-select>
      </div>
      
      <el-table :data="orders" v-loading="loading" stripe>
        <el-table-column prop="orderNo" label="订单号" width="200" />
        <el-table-column prop="totalAmount" label="金额" width="100">
          <template #default="{ row }">¥{{ row.totalAmount }}</template>
        </el-table-column>
        <el-table-column prop="receiver" label="收货人" width="100" />
        <el-table-column prop="phone" label="电话" width="130" />
        <el-table-column prop="address" label="地址" min-width="150" show-overflow-tooltip />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)">{{ getStatusText(row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createdAt" label="下单时间" width="180" />
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-select 
              v-if="row.status !== 'CANCELLED' && row.status !== 'COMPLETED'"
              v-model="row.status" 
              size="small" 
              @change="updateStatus(row)"
              style="width: 100px"
            >
              <el-option label="待付款" value="PENDING" />
              <el-option label="已付款" value="PAID" />
              <el-option label="已发货" value="SHIPPED" />
              <el-option label="已完成" value="COMPLETED" />
              <el-option label="已取消" value="CANCELLED" />
            </el-select>
          </template>
        </el-table-column>
      </el-table>
      
      <el-pagination
        v-model:current-page="page"
        :page-size="10"
        :total="total"
        layout="total, prev, pager, next"
        style="margin-top: 16px; justify-content: flex-end"
        @current-change="fetchOrders"
      />
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import api from '../api'

const orders = ref([])
const loading = ref(false)
const page = ref(1)
const total = ref(0)
const statusFilter = ref('')

const statusMap = {
  PENDING: { text: '待付款', type: 'warning' },
  PAID: { text: '已付款', type: 'primary' },
  SHIPPED: { text: '已发货', type: '' },
  COMPLETED: { text: '已完成', type: 'success' },
  CANCELLED: { text: '已取消', type: 'info' }
}

const getStatusText = (status) => statusMap[status]?.text || status
const getStatusType = (status) => statusMap[status]?.type || ''

const fetchOrders = async () => {
  loading.value = true
  try {
    const params = { page: page.value - 1, size: 10 }
    if (statusFilter.value) params.status = statusFilter.value
    const res = await api.get('/admin/orders', { params })
    if (res.code === 200) {
      orders.value = res.data.content
      total.value = res.data.totalElements
    }
  } catch (e) {
    console.error(e)
  }
  loading.value = false
}

const updateStatus = async (order) => {
  try {
    await api.put(`/admin/orders/${order.id}/status`, { status: order.status })
    ElMessage.success('状态更新成功')
  } catch (e) {
    ElMessage.error('更新失败')
    fetchOrders()
  }
}

onMounted(fetchOrders)
</script>
