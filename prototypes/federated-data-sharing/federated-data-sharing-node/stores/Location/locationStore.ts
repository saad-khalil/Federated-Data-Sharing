import { defineStore  } from "pinia";
import { useToast } from "vue-toastification";

import type { Location } from "~/types/Location/Location";

export const useLocationStore = defineStore("location", () => {
    const { $trpcClient } = useNuxtApp();
    const { locations } = $trpcClient
    const toast = useToast();

    async function create_location(location_information : {
        name: string,
        address: string,
        latitude: string,
        longitude: string
        security: "private" | "subscribe" | "public",
        organization_id: number
    }) {
        const { data } = await locations.addLocation.useQuery({
            name: location_information.name,
            address: location_information.address,
            latitude: location_information.latitude,
            longitude: location_information.longitude,
            security: location_information.security,
            organization_id: location_information.organization_id
        })

        return data.value?.response.payload
    }

    return {
        create_location
    }
}, {
    persist: true
})