import { defineStore } from "pinia";

import { useSessionStorage, type RemovableRef } from "@vueuse/core";

import { Location } from "../../types/Location/Location";

export const useLocationStore = defineStore("locations", () => {

    const locations : RemovableRef<Location[]> = useSessionStorage<Location[]>("locations", [] as Location[])


    const getLocations : ComputedRef<RemovableRef<Location[]>> = computed(() => locations)

    return {
        getLocations
    }
})