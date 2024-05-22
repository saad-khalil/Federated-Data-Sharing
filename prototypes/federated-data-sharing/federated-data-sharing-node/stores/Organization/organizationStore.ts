import { defineStore } from "pinia"
import { useSessionStorage, type RemovableRef } from "@vueuse/core"
import { useToast } from "vue-toastification";

import { useRandomString } from "#imports";

import type { Organization } from "~/types/Organization/Organization";


export const useOrganizationStore = defineStore("organization", () => {
    const { $trpcClient } = useNuxtApp();
    const { organizations } =  $trpcClient
    const toast = useToast();

    const client_organizations : RemovableRef<Organization[]> =
        useSessionStorage<Organization[]>("organization", [] as Organization[])
    const selected_organization : Ref<Organization> = ref<Organization>({} as Organization)
    const getOrganizations: ComputedRef<RemovableRef<Organization[]>> = computed(() => client_organizations)


    function addSampleOrg() : void {
        for(let i = 0 ; i < 10; i++)
        client_organizations.value.push({
            organization_id: 1,
            name: `Jumbo ${i}`,
            organization_type: "brewery",
            secret_key: "sdfsdfsdfsdfsd",
            orders: [],
            locations: [],
            security: 'public'
        })
    }

    const get_selected_organization : ComputedRef<Ref<Organization>> = computed(() => selected_organization)

    async function addDummy( name: string, type: string, privacy: "public" | "private" | "subscribe" ){
        toast.info('Generating new Organization')

        const { data } = await organizations.addOrganization.useQuery({
            name: name,
            secret_key: useRandomString(20),
            organization_type: type,
            security: privacy
        })

        console.log(data)
        toast.success("Organization successfully Generated")

        toast.info(`Use code: ${data.value?.response.payload?.secret_key} to register`)
    }

    function clear() :  void  {
        client_organizations.value = []
    }

    async function subscribe_to_organization(organization_id: number, secret_key: string) : Promise<void>{
        const { data } = await organizations.subscribe.useQuery({
            organization_id: organization_id,
            secret_key: secret_key
        })

        console.log(data)
    }

    async function getOrganizationById(organization_id: number) {
        const { data } = await organizations.getOrganizationById.useQuery({
            organization_id: organization_id
        })

        if(data.value?.response.payload){
            return data.value?.response.payload
        } else {
            return undefined
        }

    }

    function setCurrentOrganization(organization: Organization) {
        selected_organization.value = organization

    }

    return {
        getOrganizations, addSampleOrg, addDummy,clear,
        subscribe_to_organization, getOrganizationById, setCurrentOrganization,
        get_selected_organization
    }
}, {
    persist: true
})