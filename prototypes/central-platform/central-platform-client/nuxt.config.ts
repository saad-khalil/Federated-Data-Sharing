// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  devtools: { enabled: true },
  srcDir: 'src',
  build: {
    transpile: [
      'vue-toastification'
    ]
  },
  runtimeConfig: {
    public: {
      API_BASE_URL: process.env.API_ROUTE
    }
  },
  components: [
    {
      path: '~/components',
      pathPrefix: false,
    }
  ],
  imports: {
    dirs: ['stores', 'composables/**'],
  },
  modules: [
    '@vueuse/nuxt',
    [
      '@pinia/nuxt',
      {
          autoImports: ['defineStore', 'acceptHMRUpdate'],
      },
    ],
    '@pinia-plugin-persistedstate/nuxt',
  ],
  css: [
    '~/assets/css/main.css'
  ],
  postcss: {
    plugins: {
      tailwindcss: {},
      autoprefixer: {}
    }
  }
})
