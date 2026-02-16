<template>
  <div class="categories-page">
    <div class="page-header" style="display: flex; justify-content: space-between; align-items: center">
      <h2>分类管理</h2>
      <el-button type="primary" @click="showAdd">添加分类</el-button>
    </div>
    
    <el-card>
      <el-table :data="categories" v-loading="loading" stripe>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="分类名称" />
        <el-table-column prop="icon" label="图标" />
        <el-table-column prop="sortOrder" label="排序" width="100" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'info'">
              {{ row.status === 1 ? '启用' : '禁用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="editCategory(row)">编辑</el-button>
            <el-button type="danger" size="small" @click="deleteCategory(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑分类' : '添加分类'" width="400px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="名称">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="图标">
          <el-input v-model="form.icon" />
        </el-form-item>
        <el-form-item label="排序">
          <el-input-number v-model="form.sortOrder" :min="0" />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="form.status">
            <el-option label="启用" :value="1" />
            <el-option label="禁用" :value="0" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveCategory">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import api from '../api'

const categories = ref([])
const loading = ref(false)
const dialogVisible = ref(false)
const isEdit = ref(false)
const form = ref({ name: '', icon: '', sortOrder: 0, status: 1 })

const fetchCategories = async () => {
  loading.value = true
  try {
    const res = await api.get('/admin/categories')
    if (res.code === 200) {
      categories.value = res.data
    }
  } catch (e) {
    console.error(e)
  }
  loading.value = false
}

const showAdd = () => {
  isEdit.value = false
  form.value = { name: '', icon: '', sortOrder: 0, status: 1 }
  dialogVisible.value = true
}

const editCategory = (category) => {
  isEdit.value = true
  form.value = { ...category }
  dialogVisible.value = true
}

const saveCategory = async () => {
  try {
    if (isEdit.value) {
      await api.put(`/admin/categories/${form.value.id}`, form.value)
    } else {
      await api.post('/admin/categories', form.value)
    }
    ElMessage.success('保存成功')
    dialogVisible.value = false
    fetchCategories()
  } catch (e) {
    ElMessage.error('保存失败')
  }
}

const deleteCategory = async (category) => {
  try {
    await ElMessageBox.confirm('确定删除该分类?', '提示', { type: 'warning' })
    await api.delete(`/admin/categories/${category.id}`)
    ElMessage.success('删除成功')
    fetchCategories()
  } catch (e) {
    if (e !== 'cancel') ElMessage.error('删除失败')
  }
}

onMounted(fetchCategories)
</script>
