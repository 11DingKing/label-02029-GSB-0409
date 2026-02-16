<template>
  <router-link :to="`/book/${book.id}`" class="book-card card">
    <div class="book-cover">
      <img :src="book.coverImage || 'https://via.placeholder.com/200x280?text=No+Cover'" :alt="book.title" />
      <span class="quality-badge" :class="qualityClass">{{ book.quality }}</span>
    </div>
    <div class="book-info">
      <h3 class="book-title">{{ book.title }}</h3>
      <p class="book-author">{{ book.author }}</p>
      <div class="book-price">
        <span class="current-price">¥{{ book.price }}</span>
        <span class="original-price" v-if="book.originalPrice">¥{{ book.originalPrice }}</span>
      </div>
    </div>
  </router-link>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  book: { type: Object, required: true }
})

const qualityClass = computed(() => {
  const q = props.book.quality
  if (q?.includes('九')) return 'excellent'
  if (q?.includes('八')) return 'good'
  return 'fair'
})
</script>

<style scoped>
.book-card {
  display: block;
}

.book-cover {
  position: relative;
  aspect-ratio: 3/4;
  overflow: hidden;
  background: var(--gray-100);
}

.book-cover img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.3s;
}

.book-card:hover .book-cover img {
  transform: scale(1.05);
}

.quality-badge {
  position: absolute;
  top: 12px;
  right: 12px;
  padding: 4px 10px;
  border-radius: 20px;
  font-size: 12px;
  font-weight: 500;
  background: rgba(255,255,255,0.95);
}

.quality-badge.excellent { color: var(--success); }
.quality-badge.good { color: var(--primary); }
.quality-badge.fair { color: var(--warning); }

.book-info {
  padding: 16px;
}

.book-title {
  font-size: 15px;
  font-weight: 600;
  color: var(--gray-800);
  margin-bottom: 4px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.book-author {
  font-size: 13px;
  color: var(--gray-500);
  margin-bottom: 12px;
}

.book-price {
  display: flex;
  align-items: baseline;
  gap: 8px;
}

.current-price {
  font-size: 18px;
  font-weight: 700;
  color: var(--accent);
}

.original-price {
  font-size: 13px;
  color: var(--gray-400);
  text-decoration: line-through;
}
</style>
