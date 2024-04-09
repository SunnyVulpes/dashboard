import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  server: {
    proxy: {
      '/api': {
        target: 'http://124.221.53.69:8081', // 目标后端服务地址
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, '') // 将 /api 替换为空
      },
    },
  },
})


