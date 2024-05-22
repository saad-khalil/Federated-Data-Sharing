import { RemovableRef } from "@vueuse/core"
import { defineStore } from "pinia"

export const useNotificationStore = defineStore('notifications', () => {
    const notifications : RemovableRef<string[]> = useSessionStorage('notifications', [])

    return {
        notifications
    }
})