<template>
  <div class="books-page">
    <div class="page-header">
      <h2>书籍管理</h2>
    </div>
    
    <el-card>
      <el-table :data="books" v-loading="loading" stripe>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column label="封面" width="80">
          <template #default="{ row }">
            <el-image :src="row.coverImage" style="width: 50px; height: 70px" fit="cover" />
          </template>
        </el-table-column>
        <el-table-column prop="title" label="书名" min-width="150" />
        <el-table-column prop="author" label="作者" width="120" />
        <el-table-column prop="price" label="价格" width="100">
          <template #default="{ row }">¥{{ row.price }}</template>
        </el-table-column>
        <el-table-column prop="stock" label="库存" width="80" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'info'">
              {{ row.status === 1 ? '上架' : '下架' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="editBook(row)">编辑</el-button>
            <el-button type="danger" size="small" @click="deleteBook(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      
      <el-pagination
        v-model:current-page="page"
        :page-size="10"
        :total="total"
        layout="total, prev, pager, next"
        style="margin-top: 16px; justify-content: flex-end"
        @current-change="fetchBooks"
      />
    </el-card>

    <el-dialog v-model="dialogVisible" title="编辑书籍" width="500px">
      <el-form :model="editForm" label-width="80px">
        <el-form-item label="书名">
          <el-input v-model="editForm.title" />
        </el-form-item>
        <el-form-item label="作者">
          <el-input v-model="editForm.author" />
        </el-form-item>
        <el-form-item label="价格">
          <el-input-number v-model="editForm.price" :min="0" :precision="2" />
        </el-form-item>
        <el-form-item label="库存">
          <el-input-number v-model="editForm.stock" :min="0" />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="editForm.status">
            <el-option label="上架" :value="1" />
            <el-option label="下架" :value="0" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveBook">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import api from '../api'

const books = ref([])
const loading = ref(false)
const page = ref(1)
const total = ref(0)
const dialogVisible = ref(false)
const editForm = ref({})

const fetchBooks = async () => {
  loading.value = true
  try {
    const res = await api.get('/admin/books', { params: { page: page.value - 1, size: 10 } })
    if (res.code === 200) {
      books.value = res.data.content
      total.value = res.data.totalElements
    }
  } catch (e) {
    console.error(e)
  }
  loading.value = false
}

const editBook = (book) => {
  editForm.value = { ...book }
  dialogVisible.value = true
}

const saveBook = async () => {
  try {
    await api.put(`/admin/books/${editForm.value.id}`, editForm.value)
    ElMessage.success('保存成功')
    dialogVisible.value = false
    fetchBooks()
  } catch (e) {
    ElMessage.error('保存失败')
  }
}

const deleteBook = async (book) => {
  try {
    await ElMessageBox.confirm('确定删除该书籍?', '提示', { type: 'warning' })
    await api.delete(`/admin/books/${book.id}`)
    ElMessage.success('删除成功')
    fetchBooks()
  } catch (e) {
    if (e !== 'cancel') ElMessage.error('删除失败')
  }
}

onMounted(fetchBooks)
</script>
