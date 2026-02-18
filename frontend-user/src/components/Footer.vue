<template>
  <footer class="footer">
    <div class="container">
      <div class="footer-content">
        <div class="footer-brand">
          <div class="logo">
            <svg width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <path d="M4 19.5A2.5 2.5 0 0 1 6.5 17H20"></path>
              <path d="M6.5 2H20v20H6.5A2.5 2.5 0 0 1 4 19.5v-15A2.5 2.5 0 0 1 6.5 2z"></path>
            </svg>
            <span>二手书市场</span>
          </div>
          <p>让每一本好书找到新的主人</p>
          <div class="footer-stats">
            <div class="stat-item">
              <span class="stat-num">{{ stats.bookCount || 0 }}</span>
              <span class="stat-label">在售书籍</span>
            </div>
            <div class="stat-item">
              <span class="stat-num">{{ stats.userCount || 0 }}</span>
              <span class="stat-label">注册用户</span>
            </div>
            <div class="stat-item">
              <span class="stat-num">{{ stats.orderCount || 0 }}</span>
              <span class="stat-label">成交订单</span>
            </div>
          </div>
        </div>
        <div class="footer-links">
          <div class="link-group">
            <h4>快速导航</h4>
            <router-link to="/">首页</router-link>
            <router-link to="/books">全部书籍</router-link>
            <router-link to="/publish">发布书籍</router-link>
            <router-link to="/orders">我的订单</router-link>
          </div>
          <div class="link-group">
            <h4>关于我们</h4>
            <a href="#" @click.prevent="showAbout">平台介绍</a>
            <a href="#" @click.prevent="showContact">联系我们</a>
            <a href="#" @click.prevent="showPrivacy">隐私政策</a>
          </div>
          <div class="link-group">
            <h4>帮助中心</h4>
            <a href="#" @click.prevent="showHelp('buy')">如何购买</a>
            <a href="#" @click.prevent="showHelp('sell')">如何出售</a>
            <a href="#" @click.prevent="showHelp('faq')">常见问题</a>
          </div>
          <div class="link-group">
            <h4>联系方式</h4>
            <p class="contact-info">
              <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M4 4h16c1.1 0 2 .9 2 2v12c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2V6c0-1.1.9-2 2-2z"/>
                <polyline points="22,6 12,13 2,6"/>
              </svg>
              support@bookstore.com
            </p>
            <p class="contact-info">
              <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M22 16.92v3a2 2 0 0 1-2.18 2 19.79 19.79 0 0 1-8.63-3.07 19.5 19.5 0 0 1-6-6 19.79 19.79 0 0 1-3.07-8.67A2 2 0 0 1 4.11 2h3a2 2 0 0 1 2 1.72 12.84 12.84 0 0 0 .7 2.81 2 2 0 0 1-.45 2.11L8.09 9.91a16 16 0 0 0 6 6l1.27-1.27a2 2 0 0 1 2.11-.45 12.84 12.84 0 0 0 2.81.7A2 2 0 0 1 22 16.92z"/>
              </svg>
              400-123-4567
            </p>
          </div>
        </div>
      </div>
      <div class="footer-bottom">
        <p>© 2024 二手书市场. 保留所有权利.</p>
        <div class="footer-social">
          <a href="#" title="微信" @click.prevent>
            <svg width="20" height="20" viewBox="0 0 24 24" fill="currentColor">
              <path d="M8.691 2.188C3.891 2.188 0 5.476 0 9.53c0 2.212 1.17 4.203 3.002 5.55a.59.59 0 0 1 .213.665l-.39 1.48c-.019.07-.048.141-.048.213 0 .163.13.295.29.295a.326.326 0 0 0 .167-.054l1.903-1.114a.864.864 0 0 1 .717-.098 10.16 10.16 0 0 0 2.837.403c.276 0 .543-.027.811-.05-.857-2.578.157-4.972 1.932-6.446 1.703-1.415 3.882-1.98 5.853-1.838-.576-3.583-4.196-6.348-8.596-6.348zM5.785 5.991c.642 0 1.162.529 1.162 1.18a1.17 1.17 0 0 1-1.162 1.178A1.17 1.17 0 0 1 4.623 7.17c0-.651.52-1.18 1.162-1.18zm5.813 0c.642 0 1.162.529 1.162 1.18a1.17 1.17 0 0 1-1.162 1.178 1.17 1.17 0 0 1-1.162-1.178c0-.651.52-1.18 1.162-1.18zm5.34 2.867c-1.797-.052-3.746.512-5.28 1.786-1.72 1.428-2.687 3.72-1.78 6.22.942 2.453 3.666 4.229 6.884 4.229.826 0 1.622-.12 2.361-.336a.722.722 0 0 1 .598.082l1.584.926a.272.272 0 0 0 .14.047c.134 0 .24-.111.24-.247 0-.06-.023-.12-.038-.177l-.327-1.233a.582.582 0 0 1-.023-.156.49.49 0 0 1 .201-.398C23.024 18.48 24 16.82 24 14.98c0-3.21-2.931-5.837-6.656-6.088V8.89c-.135-.01-.269-.03-.406-.03zm-2.53 3.274c.535 0 .969.44.969.982a.976.976 0 0 1-.969.983.976.976 0 0 1-.969-.983c0-.542.434-.982.97-.982zm4.844 0c.535 0 .969.44.969.982a.976.976 0 0 1-.969.983.976.976 0 0 1-.969-.983c0-.542.434-.982.969-.982z"/>
            </svg>
          </a>
          <a href="#" title="微博" @click.prevent>
            <svg width="20" height="20" viewBox="0 0 24 24" fill="currentColor">
              <path d="M10.098 20.323c-3.977.391-7.414-1.406-7.672-4.02-.259-2.609 2.759-5.047 6.74-5.441 3.979-.394 7.413 1.404 7.671 4.018.259 2.6-2.759 5.049-6.739 5.443zM9.05 17.219c-.384.616-1.208.884-1.829.602-.612-.279-.793-.991-.406-1.593.379-.595 1.176-.861 1.793-.601.622.263.82.972.442 1.592zm1.27-1.627c-.141.237-.449.353-.689.253-.236-.09-.313-.361-.177-.586.138-.227.436-.346.672-.24.239.09.315.36.194.573zm.176-2.719c-1.893-.493-4.033.45-4.857 2.118-.836 1.704-.026 3.591 1.886 4.21 1.983.64 4.318-.341 5.132-2.179.8-1.793-.201-3.642-2.161-4.149zm7.563-1.224c-.346-.105-.579-.18-.405-.649.381-1.017.422-1.896-.001-2.52-.791-1.163-2.924-1.102-5.32-.026-.001-.001-.765.335-.568-.271.383-1.186.324-2.178-.27-2.749-1.346-1.294-4.924-.03-7.996 2.824C1.893 11.281.5 13.597.5 15.622c0 3.876 4.97 6.237 9.831 6.237 6.369 0 10.604-3.695 10.604-6.63 0-1.773-1.496-2.779-2.876-3.18zm.878-6.809c-.563-.663-1.413-1.022-2.332-.981l.014-.001c-.278.012-.494.245-.483.521.012.276.244.494.521.483.639-.029 1.217.221 1.622.683.405.462.58 1.065.493 1.696-.038.273.153.525.426.564.273.038.525-.153.564-.426.126-.91-.127-1.781-.711-2.449l-.114-.09zm1.19-2.36c-1.074-1.263-2.692-1.95-4.447-1.869-.276.012-.492.246-.48.522.012.276.246.492.522.48 1.467-.068 2.819.506 3.713 1.56.895 1.054 1.259 2.449 1.001 3.838-.049.272.133.533.405.582.272.049.533-.133.582-.405.309-1.663-.127-3.335-1.201-4.618l-.095-.09z"/>
            </svg>
          </a>
        </div>
      </div>
    </div>

    <!-- 弹窗 -->
    <div v-if="modalVisible" class="modal-overlay" @click="modalVisible = false">
      <div class="modal-content" @click.stop>
        <button class="modal-close" @click="modalVisible = false">&times;</button>
        <h3>{{ modalTitle }}</h3>
        <div v-html="modalContent"></div>
      </div>
    </div>
  </footer>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import api from '../api'

const stats = ref({ bookCount: 0, userCount: 0, orderCount: 0 })
const modalVisible = ref(false)
const modalTitle = ref('')
const modalContent = ref('')

const fetchStats = async () => {
  try {
    const res = await api.get('/stats')
    if (res.code === 200) {
      stats.value = res.data
    }
  } catch (e) {
    // 静默处理
  }
}

const showAbout = () => {
  modalTitle.value = '平台介绍'
  modalContent.value = `
    <p>二手书市场是一个专注于二手书籍交易的在线平台，致力于让每一本好书找到新的主人。</p>
    <p>我们的愿景是打造一个绿色、便捷、可信赖的二手书交易社区，让知识得以传递，让阅读更加普及。</p>
    <h4>平台特色：</h4>
    <ul>
      <li>海量书籍资源，涵盖各类学科</li>
      <li>安全便捷的交易流程</li>
      <li>专业的书籍品质保障</li>
      <li>贴心的客户服务</li>
    </ul>
  `
  modalVisible.value = true
}

const showContact = () => {
  modalTitle.value = '联系我们'
  modalContent.value = `
    <p>如有任何问题或建议，欢迎通过以下方式联系我们：</p>
    <p><strong>客服邮箱：</strong>support@bookstore.com</p>
    <p><strong>客服电话：</strong>400-123-4567</p>
    <p><strong>工作时间：</strong>周一至周五 9:00-18:00</p>
    <p><strong>公司地址：</strong>北京市海淀区中关村大街1号</p>
  `
  modalVisible.value = true
}

const showPrivacy = () => {
  modalTitle.value = '隐私政策'
  modalContent.value = `
    <p>我们非常重视您的隐私保护，承诺按照相关法律法规保护您的个人信息安全。</p>
    <h4>信息收集：</h4>
    <p>我们仅收集提供服务所必需的信息，包括注册信息、交易信息等。</p>
    <h4>信息使用：</h4>
    <p>您的信息仅用于提供服务、改善用户体验，不会出售给第三方。</p>
    <h4>信息保护：</h4>
    <p>我们采用业界标准的安全技术保护您的信息安全。</p>
  `
  modalVisible.value = true
}

const showHelp = (type) => {
  if (type === 'buy') {
    modalTitle.value = '如何购买'
    modalContent.value = `
      <ol>
        <li>浏览书籍：在首页或书籍列表页面浏览感兴趣的书籍</li>
        <li>查看详情：点击书籍查看详细信息、价格和卖家信息</li>
        <li>加入购物车：选择心仪的书籍加入购物车</li>
        <li>确认订单：填写收货地址，确认订单信息</li>
        <li>完成支付：选择支付方式完成付款</li>
        <li>等待收货：卖家发货后等待快递送达</li>
      </ol>
    `
  } else if (type === 'sell') {
    modalTitle.value = '如何出售'
    modalContent.value = `
      <ol>
        <li>注册登录：首先注册并登录您的账号</li>
        <li>发布书籍：点击"发布书籍"填写书籍信息</li>
        <li>上传图片：拍摄清晰的书籍照片上传</li>
        <li>设置价格：根据书籍品相合理定价</li>
        <li>等待买家：书籍上架后等待买家下单</li>
        <li>发货：收到订单后及时发货</li>
      </ol>
    `
  } else {
    modalTitle.value = '常见问题'
    modalContent.value = `
      <h4>Q: 如何保证书籍质量？</h4>
      <p>A: 我们要求卖家如实描述书籍品相，买家收货后如有问题可申请退款。</p>
      <h4>Q: 支持哪些支付方式？</h4>
      <p>A: 目前支持支付宝、微信支付等主流支付方式。</p>
      <h4>Q: 多久能收到书籍？</h4>
      <p>A: 一般3-7个工作日内送达，具体以物流信息为准。</p>
      <h4>Q: 如何申请退款？</h4>
      <p>A: 在订单详情页面点击"申请退款"，填写退款原因即可。</p>
    `
  }
  modalVisible.value = true
}

onMounted(fetchStats)
</script>

<style scoped>
.footer {
  background: var(--gray-900);
  color: white;
  padding: 64px 0 24px;
  margin-top: 80px;
}

.footer-content {
  display: flex;
  justify-content: space-between;
  padding-bottom: 48px;
  border-bottom: 1px solid rgba(255,255,255,0.1);
}

.footer-brand .logo {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 18px;
  font-weight: 700;
  margin-bottom: 12px;
}

.footer-brand > p {
  color: var(--gray-400);
  font-size: 14px;
  margin-bottom: 24px;
}

.footer-stats {
  display: flex;
  gap: 24px;
}

.stat-item {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.stat-num {
  font-size: 24px;
  font-weight: 700;
  color: var(--primary);
}

.stat-label {
  font-size: 12px;
  color: var(--gray-400);
  margin-top: 4px;
}

.footer-links {
  display: flex;
  gap: 60px;
}

.link-group h4 {
  font-size: 14px;
  font-weight: 600;
  margin-bottom: 16px;
  color: var(--gray-300);
}

.link-group a {
  display: block;
  color: var(--gray-400);
  font-size: 14px;
  margin-bottom: 10px;
  transition: color 0.2s;
  cursor: pointer;
}

.link-group a:hover {
  color: white;
}

.contact-info {
  display: flex;
  align-items: center;
  gap: 8px;
  color: var(--gray-400);
  font-size: 14px;
  margin-bottom: 10px;
}

.contact-info svg {
  flex-shrink: 0;
}

.footer-bottom {
  padding-top: 24px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  color: var(--gray-500);
  font-size: 14px;
}

.footer-social {
  display: flex;
  gap: 16px;
}

.footer-social a {
  color: var(--gray-400);
  transition: color 0.2s;
}

.footer-social a:hover {
  color: white;
}

/* 弹窗样式 */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0,0,0,0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.modal-content {
  background: white;
  color: var(--gray-900);
  padding: 32px;
  border-radius: 12px;
  max-width: 500px;
  max-height: 80vh;
  overflow-y: auto;
  position: relative;
}

.modal-close {
  position: absolute;
  top: 12px;
  right: 16px;
  background: none;
  border: none;
  font-size: 24px;
  cursor: pointer;
  color: var(--gray-500);
}

.modal-close:hover {
  color: var(--gray-900);
}

.modal-content h3 {
  margin-bottom: 16px;
  font-size: 20px;
}

.modal-content h4 {
  margin: 16px 0 8px;
  font-size: 16px;
}

.modal-content p {
  margin-bottom: 12px;
  line-height: 1.6;
}

.modal-content ul, .modal-content ol {
  padding-left: 20px;
  margin-bottom: 12px;
}

.modal-content li {
  margin-bottom: 8px;
  line-height: 1.6;
}

@media (max-width: 768px) {
  .footer-content {
    flex-direction: column;
    gap: 32px;
  }
  
  .footer-links {
    flex-wrap: wrap;
    gap: 32px;
  }
  
  .footer-bottom {
    flex-direction: column;
    gap: 16px;
    text-align: center;
  }
}
</style>
