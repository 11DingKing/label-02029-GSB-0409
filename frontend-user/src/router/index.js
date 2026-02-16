import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  { path: '/', name: 'Home', component: () => import('../views/Home.vue') },
  { path: '/login', name: 'Login', component: () => import('../views/Login.vue') },
  { path: '/register', name: 'Register', component: () => import('../views/Register.vue') },
  { path: '/books', name: 'Books', component: () => import('../views/Books.vue') },
  { path: '/book/:id', name: 'BookDetail', component: () => import('../views/BookDetail.vue') },
  { path: '/cart', name: 'Cart', component: () => import('../views/Cart.vue'), meta: { auth: true } },
  { path: '/orders', name: 'Orders', component: () => import('../views/Orders.vue'), meta: { auth: true } },
  { path: '/publish', name: 'Publish', component: () => import('../views/Publish.vue'), meta: { auth: true } },
  { path: '/profile', name: 'Profile', component: () => import('../views/Profile.vue'), meta: { auth: true } }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('user_token')
  if (to.meta.auth && !token) {
    next('/login')
  } else {
    next()
  }
})

export default router
