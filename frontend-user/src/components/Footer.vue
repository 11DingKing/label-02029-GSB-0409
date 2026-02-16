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
        </div>
        <div class="footer-links">
          <div class="link-group">
            <h4>关于我们</h4>
            <a href="#">平台介绍</a>
            <a href="#">联系我们</a>
          </div>
          <div class="link-group">
            <h4>帮助中心</h4>
            <a href="#">如何购买</a>
            <a href="#">如何出售</a>
          </div>
        </div>
      </div>
      <div class="footer-bottom">
        <p>© 2024 二手书市场. All rights reserved.</p>
      </div>
    </div>
  </footer>
</template>

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

.footer-brand p {
  color: var(--gray-400);
  font-size: 14px;
}

.footer-links {
  display: flex;
  gap: 80px;
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
}

.link-group a:hover {
  color: white;
}

.footer-bottom {
  padding-top: 24px;
  text-align: center;
  color: var(--gray-500);
  font-size: 14px;
}
</style>
