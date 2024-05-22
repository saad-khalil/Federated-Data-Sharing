import { defineStore } from "pinia"
import { useToast } from "vue-toastification";
import { type RemovableRef, useSessionStorage } from "@vueuse/core"

import { useOrganizationStore } from "../Organization/organizationStore";

import type { MinifiedUser, User, UserCredentials } from "~/types/User/User";

export const useUserStore = defineStore("user", () =>{

    const { $trpcClient } = useNuxtApp();
    const toast = useToast()
    const organizationStore = useOrganizationStore();

    const { users } = $trpcClient
    const { getOrganizations, setCurrentOrganization } = organizationStore

    const isAuthenticated : RemovableRef<boolean> = ref<boolean>(false)
    const user : RemovableRef<User> = useSessionStorage<User>("user", {} as User)

    const getUser : ComputedRef<RemovableRef<User>> = computed(() => user)

    async function register( user_details : MinifiedUser) {
        toast.info("Registering Instance.")
        const { data } = await users.register.useQuery({
            email: user_details.email,
            password: user_details.password,
            name: user_details.name,
            secret_key: user_details.secret_key
        })

        if(data.value?.response.payload?.organization){
            user.value = data.value?.response.payload?.user

            getOrganizations.value = []
            getOrganizations.value.push(data.value?.response.payload?.organization)
            getOrganizations.value.push(...data.value?.response.payload.organization_subscribers)
        }

        if(!(data.value?.response.payload?.user == null || data.value?.response.payload?.user == undefined)){
            toast.success("Successfully Registered!")
            isAuthenticated.value = true;
            navigateTo("/home")
        }
        else {
            toast.error("No user found")
        }
    }

    async function authenticate( credentials : UserCredentials) {
        toast.info("Attempting to Authenticate.")
        const { data } = await users.authenticate.useQuery({
            email: credentials.email,
            password: credentials.password
        })


        if(data.value?.response.payload?.organization){
            user.value = data.value?.response.payload?.user

            getOrganizations.value = []
            getOrganizations.value.push(data.value?.response.payload?.organization)
            getOrganizations.value.push(...data.value?.response.payload.organization_subscribers)
            setCurrentOrganization(data.value?.response.payload?.organization)

        }

        if(!(data.value?.response.payload?.user == null || data.value?.response.payload?.user == undefined)){
            toast.success("Successfully Authenticated!")
            isAuthenticated.value = true
            navigateTo("/home")
        }
        else {
            toast.error("No user found")
        }

    }

    async function create_user( user_details : MinifiedUser){
        toast.info("Registering Instance.")
        const { data } = await users.register.useQuery({
            email: user_details.email,
            password: user_details.password,
            name: user_details.name,
            secret_key: user_details.secret_key
        })

        toast.success("Successfully Created!")
    }


    return {
        getUser, register, authenticate, user, isAuthenticated, create_user
    }
}, {
    persist: true
})

