import { defineStore } from "pinia"
import { useSessionStorage, type RemovableRef } from "@vueuse/core"
import { User } from "~/types/User/User"

export const useUserStore = defineStore("user", () => {

    const user : RemovableRef<User> = useSessionStorage<User>("user", {} as User)



    return {
        user
    }
}, {
    persist: true
})