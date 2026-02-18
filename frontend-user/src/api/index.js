import axios from 'axios'

const api = axios.create({
  baseURL: '/api',
  timeout: 10000
})

api.interceptors.request.use(config => {
  const token = localStorage.getItem('user_token')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

api.interceptors.response.use(
  response => response.data,
  error => {
    if (error.response?.status === 401) {
      localStorage.removeItem('user_token')
      localStorage.removeItem('user_info')
      alert('登录已失效，请重新登录')
      setTimeout(() => {
        window.location.href = '/login'
      }, 1000)
    }
    return Promise.reject(error)
  }
)

export default api
