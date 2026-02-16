<template>
  <header class="header">
    <div class="container header-inner">
      <router-link to="/" class="logo">
        <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
          <path d="M4 19.5A2.5 2.5 0 0 1 6.5 17H20"></path>
          <path d="M6.5 2H20v20H6.5A2.5 2.5 0 0 1 4 19.5v-15A2.5 2.5 0 0 1 6.5 2z"></path>
        </svg>
        <span>二手书市场</span>
      </router-link>
      
      <nav class="nav">
        <router-link to="/books" class="nav-link">浏览书籍</router-link>
        <router-link to="/publish" class="nav-link" v-if="userStore.isLoggedIn">发布书籍</router-link>
      </nav>
      
      <div class="header-actions">
        <template v-if="userStore.isLoggedIn">
          <router-link to="/cart" class="cart-btn">
            <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <circle cx="9" cy="21" r="1"></circle>
              <circle cx="20" cy="21" r="1"></circle>
              <path d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"></path>
            </svg>
            <span v-if="cartStore.count" class="cart-badge">{{ cartStore.count }}</span>
          </router-link>
          <div class="user-menu" @click="showMenu = !showMenu">
            <div class="avatar">{{ userStore.userInfo?.nickname?.[0] || 'U' }}</div>
            <div class="dropdown" v-if="showMenu">
              <router-link to="/profile" class="dropdown-item">个人中心</router-link>
              <router-link to="/orders" class="dropdown-item">我的订单</router-link>
              <div class="dropdown-divider"></div>
              <div class="dropdown-item" @click="handleLogout">退出登录</div>
            </div>
          </div>
        </template>
        <template v-else>
          <router-link to="/login" class="btn btn-secondary">登录</router-link>
          <router-link to="/register" class="btn btn-primary">注册</router-link>
        </template>
      </div>
    </div>
  </header>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '../stores/user'
import { useCartStore } from '../stores/cart'

const router = useRouter()
const userStore = useUserStore()
const cartStore = useCartStore()
const showMenu = ref(false)

const handleLogout = () => {
  userStore.logout()
  router.push('/')
}

onMounted(() => {
  if (userStore.isLoggedIn) {
    cartStore.fetchCart()
  }
})
</script>

<style scoped>
.header {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  height: 80px;
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(10px);
  border-bottom: 1px solid var(--gray-100);
  z-index: 100;
}

.header-inner {
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.logo {
  display: flex;
  align-items: center;
  gap: 12px;
  font-size: 20px;
  font-weight: 700;
  color: var(--primary);
}

.nav {
  display: flex;
  gap: 32px;
}

.nav-link {
  font-weight: 500;
  color: var(--gray-600);
  transition: color 0.2s;
}

.nav-link:hover,
.nav-link.router-link-active {
  color: var(--primary);
}

.header-actions {
  display: flex;
  align-items: center;
  gap: 16px;
}

.cart-btn {
  position: relative;
  width: 44px;
  height: 44px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 50%;
  background: var(--gray-100);
  color: var(--gray-700);
  transition: all 0.2s;
}

.cart-btn:hover {
  background: var(--gray-200);
}

.cart-badge {
  position: absolute;
  top: -4px;
  right: -4px;
  min-width: 20px;
  height: 20px;
  padding: 0 6px;
  background: var(--accent);
  color: white;
  font-size: 12px;
  font-weight: 600;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.user-menu {
  position: relative;
}

.avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background: linear-gradient(135deg, var(--primary), var(--primary-light));
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: 600;
  cursor: pointer;
}

.dropdown {
  position: absolute;
  top: calc(100% + 8px);
  right: 0;
  min-width: 160px;
  background: white;
  border-radius: var(--radius-sm);
  box-shadow: var(--shadow-lg);
  padding: 8px 0;
  z-index: 10;
}

.dropdown-item {
  display: block;
  padding: 10px 16px;
  color: var(--gray-700);
  transition: background 0.2s;
  cursor: pointer;
}

.dropdown-item:hover {
  background: var(--gray-50);
}

.dropdown-divider {
  height: 1px;
  background: var(--gray-100);
  margin: 8px 0;
}
</style>
