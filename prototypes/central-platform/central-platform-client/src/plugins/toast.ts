import Toast from "vue-toastification"
import "vue-toastification/dist/index.css"

const options = {
    transition: "Vue-Toastification__fade",
    maxToasts: 5,
    newestOnTop: true,
    position: "bottom-right",
    timeout: 3000,
    closeOnClick: true,
    pauseOnFocusLoss: false,
    pauseOnHover: false,
    draggable: true,
    draggablePercent: 0.6,
    showCloseButtonOnHover: true,
    hideProgressBar: false,
    closeButton: "button",
    icon: true,
    rtl: false
}


export default defineNuxtPlugin(nuxtApp => {
    nuxtApp.vueApp.use(Toast, options)
})